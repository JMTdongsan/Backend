from dto.request import QueryRequest
from dto.response import QueryResponse
from fastapi import FastAPI
from transformers import AutoModelForCausalLM, AutoTokenizer

model_path = "beomi/Llama-3-Open-Ko-8B"

tokenizer = AutoTokenizer.from_pretrained(model_path)
model = AutoModelForCausalLM.from_pretrained(
    model_path,
    torch_dtype="auto",
    device_map="auto",
    load_in_4bit=True
)

terminators = [
    tokenizer.eos_token_id,
    tokenizer.convert_tokens_to_ids("<|eot_id|>")
]

app = FastAPI()

@app.post("/query")
async def query(queryRequest: QueryRequest):
    messages = [
        {"role": "system", "content": "친절한 챗봇으로서 상대방의 요청에 최대한 자세하고 친절하게 답하자. 모든 대답은 한국어(Korean)으로 대답해줘."},
        {"role": "user", "content": queryRequest.query}
    ]

    input_ids = tokenizer.apply_chat_template(
        messages,
        add_generation_prompt=True,
        return_tensors="pt"
    ).to(model.device)

    outputs = model.generate(
        input_ids,
        max_new_tokens=512,
        eos_token_id=terminators,
        do_sample=True,
        temperature=1.0,
        top_p=0.9,
    )

    response = outputs[0][input_ids.shape[-1]:]
    queryResponse:QueryResponse = QueryResponse(tokenizer.decode(response, skip_special_tokens=True))

    return queryResponse

import transformers
import torch

from fastapi import FastAPI

from dto.request.queryRequest import queryRequest

model_id = "meta-llama/Meta-Llama-3-8B"

pipeline = transformers.pipeline("text-generation", model=model_id, model_kwargs={"torch_dtype": torch.bfloat16}, device_map="auto")

print(pipeline("Hey how are you doing today?"))

app = FastAPI()



@app.post("/query")
async def query(queryRequest: queryRequest):
    
    
    return {"query": queryRequest.query}

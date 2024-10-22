import logging

import uvicorn
from config import MILVUS
from fastapi import FastAPI, HTTPException
from pymilvus import Collection, MilvusClient, connections, utility
from requests import (GetArticleEmbedRequest, SearchPreferredArticleRequest,
                      SearchSimilarArticleRequest)

app = FastAPI()

# Milvus에 연결
connections.connect("default", host=MILVUS, port="19530")
collection_name = "information_db"

if not utility.has_collection(collection_name):
    raise Exception("Collection not found")

collection = Collection(collection_name)

@app.get("/get_some_articles")
def get_similar_articles():
    results = collection.query(
        expr = "",
        output_fields = ["*"],
        limit = 5
    )
    
    articles = []
    for result in results:
        articles.append({
            "id": result['id'],
            "content": result['content'],
            "url": result['source_url']
        })

    return {"articles": articles}

@app.post("/get_similar_articles")
async def get_similar_articles(request: SearchSimilarArticleRequest):
    article_id = request.id
    
    article_embed = collection.query(
        expr = "id in ['{}']".format(article_id),
        output_fields = ["embed"]
    )

    print(article_embed[0]['embed'])

    results = collection.search(
        data = [article_embed[0]['embed']],
        anns_field = "embed",
        param = {"index_type": "GPU_CAGRA", "metric_type": "L2"},
        limit = 5,
        expr = "id != '{}'".format(article_id),
        output_fields = ['id', 'content', 'source_url']
    )
    
    similar_articles = []
    for result in results[0]:
        similar_articles.append({
            "id": result.entity.get('id'),
            "content": result.entity.get('content'),
            "url": result.entity.get('source_url')
        })
    
    return {"articles": similar_articles}

@app.post("/get_preferred_articles")
async def get_preferred_articles(request: SearchPreferredArticleRequest):
    
    embed = [float(x) for x in request.userPreferenceVector.split(",")]
    
    results = collection.search(
        data = [embed],
        anns_field = "embed",
        param = {"index_type": "GPU_CAGRA", "metric_type": "L2"},
        limit = 5,
        expr = None,
        output_fields = ['id', 'content', 'source_url']
    )
    
    preferred_articles = []
    for result in results[0]:
        preferred_articles.append({
            "id": result.entity.get('id'),
            "content": result.entity.get('content'),
            "url": result.entity.get('source_url')
        })
    
    return {"articles": preferred_articles}

@app.post("/get_article_embed")
async def get_article_embed(request: GetArticleEmbedRequest):
    article_id = request.id
    
    article_embed = collection.query(
        expr = "id in ['{}']".format(article_id),
        output_fields = ["embed"]
    )

    return {"articleEmbed": ",".join(str(round(x, 3)) for x in article_embed[0]['embed'])}

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)
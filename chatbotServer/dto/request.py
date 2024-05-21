from pydantic import BaseModel

class queryRequest(BaseModel):
    query: str
from pydantic import BaseModel


class SearchSimilarArticleRequest(BaseModel):
    id: str
    
class SearchPreferredArticleRequest(BaseModel):
    userPreferenceVector: str
    
class GetArticleEmbedRequest(BaseModel):
    id: str
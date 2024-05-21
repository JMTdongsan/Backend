from pydantic import BaseModel


class QueryResponse(BaseModel):
    response: str

    def __init__(self, response: str) -> None:
        super().__init__(response=response)
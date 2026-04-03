import faiss
import numpy as np

class VectorStore:
    def __init__(self, dim=128):
        self.index = faiss.IndexFlatL2(dim)

    def add(self, vectors):
        self.index.add(np.array(vectors).astype('float32'))

    def search(self, query):
        D, I = self.index.search(query, k=5)
        return I
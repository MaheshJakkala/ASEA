class TestGeneratorAgent:
    def __init__(self, llm):
        self.llm = llm

    def run(self, patch):
        prompt = f"Write tests for this patch: {patch}"
        print("Running Test Generator...")
        return [{
            "file": "Test.java",
            "content": self.llm.generate(prompt)
        }]
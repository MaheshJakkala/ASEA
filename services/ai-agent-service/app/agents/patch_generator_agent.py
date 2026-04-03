class PatchGeneratorAgent:
    def __init__(self, llm):
        self.llm = llm

    def run(self, plan, code):
        prompt = f"""
        Fix the bug:
        Plan: {plan}
        Code: {code}
        """
        print("Running Patch Generator...")
        return {
            "diff": self.llm.generate(prompt)
        }
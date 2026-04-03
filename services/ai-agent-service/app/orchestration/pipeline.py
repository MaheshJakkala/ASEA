class AgentPipeline:

    def __init__(self, agents):
        self.agents = agents

    def run(self, input_data):
        context = self.agents["understanding"].run(input_data["repo_path"])

        bug = self.agents["localization"].run(context, input_data)

        plan = self.agents["planning"].run(bug)

        patch = self.agents["patch"].run(plan, context)

        tests = self.agents["test"].run(patch)

        return {
            "status": "SUCCESS",
            "patch": patch,
            "tests": tests
        }
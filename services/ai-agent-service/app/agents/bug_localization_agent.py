class BugLocalizationAgent:
    def run(self, context, issue):
        print("Running Bug Localization...")
        return {
            "suspected_files": context["files"],
            "reason": "Login issue detected"
        }
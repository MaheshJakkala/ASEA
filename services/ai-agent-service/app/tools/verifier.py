class Verifier:
    def verify(self, result):
        # dummy logic
        if "diff" in result["patch"]:
            return True
        return False
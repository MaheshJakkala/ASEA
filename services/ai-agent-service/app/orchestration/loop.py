class IterationLoop:

    def __init__(self, pipeline, verifier, max_iter=3):
        self.pipeline = pipeline
        self.verifier = verifier
        self.max_iter = max_iter

    def run(self, input_data):
        for i in range(self.max_iter):
            result = self.pipeline.run(input_data)

            ok = self.verifier.verify(result)

            if ok:
                result["iterations"] = i + 1
                return result

        return {"status": "FAILED"}
from evaluation.metrics import compute_metrics

def run():
    sample = {"status": "SUCCESS", "patch": {}, "tests": [1, 2]}

    metrics = compute_metrics(sample)

    print(metrics)

if __name__ == "__main__":
    run()
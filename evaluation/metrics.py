def compute_metrics(result):
    return {
        "success": result["status"] == "SUCCESS",
        "has_patch": "patch" in result,
        "num_tests": len(result.get("tests", []))
    }

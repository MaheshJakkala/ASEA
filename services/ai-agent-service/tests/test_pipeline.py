from app.main import run_agents

def test_full_pipeline():
    input_data = {
        "repo_url": "https://github.com/test/repo",
        "issue_id": 1,
        "issue_title": "Bug",
        "issue_body": "Fix this"
    }

    result = run_agents(input_data)

    assert "status" in result
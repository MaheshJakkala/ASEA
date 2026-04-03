import git
import os

class GitHubLoader:
    def clone_repo(self, repo_url):
        repo_name = repo_url.split("/")[-1]
        path = f"/tmp/{repo_name}"

        if os.path.exists(path):
            return path

        git.Repo.clone_from(repo_url, path)
        return path
from fastapi import FastAPI
from app.tools.github_loader import GitHubLoader
from app.orchestration.pipeline import AgentPipeline
from app.orchestration.loop import IterationLoop
from app.tools.verifier import Verifier

# import agents
from app.agents.code_understanding_agent import CodeUnderstandingAgent
from app.agents.bug_localization_agent import BugLocalizationAgent
from app.agents.planning_agent import PlanningAgent
from app.agents.patch_generator_agent import PatchGeneratorAgent
from app.agents.test_generator_agent import TestGeneratorAgent

from app.tools.llm import SimpleLLM

app = FastAPI()

@app.post("/run")
def run_agents(input_data: dict):

    # 1️⃣ Clone repo
    loader = GitHubLoader()
    repo_path = loader.clone_repo(input_data["repo_url"])

    # 2️⃣ Initialize LLM
    llm = SimpleLLM()

    # 3️⃣ Create agents
    agents = {
        "understanding": CodeUnderstandingAgent(),
        "localization": BugLocalizationAgent(),
        "planning": PlanningAgent(),
        "patch": PatchGeneratorAgent(llm),
        "test": TestGeneratorAgent(llm)
    }

    # 4️⃣ Pipeline + Loop
    pipeline = AgentPipeline(agents)
    loop = IterationLoop(pipeline, Verifier())

    # 5️⃣ Run system
    result = loop.run({
        "repo_path": repo_path,
        **input_data
    })

    return result
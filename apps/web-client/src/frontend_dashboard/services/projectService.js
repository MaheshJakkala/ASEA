// This file handles the REST API calls to Member 2's backend
const API_BASE_URL = "http://localhost:8080/api";

export const getProjects = async () => {
    const response = await fetch(`${API_BASE_URL}/projects`);
    return response.json();
};

export const startAgentRun = async (repoUrl, issue) => {
    const response = await fetch(`${API_BASE_URL}/runs/start`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ repoUrl, issue })
    });
    return response.json();
};
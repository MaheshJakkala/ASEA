import React, { useState } from 'react';

const IssueForm = ({ onTrigger }) => {
  const [repoUrl, setRepoUrl] = useState('');
  const [issue, setIssue] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    onTrigger(repoUrl, issue); // This starts the Phase 1: Issue Submitted [cite: 52, 60]
  };

  return (
    <div style={{ background: '#fff', padding: '20px', borderRadius: '8px', boxShadow: '0 2px 10px rgba(0,0,0,0.1)', marginBottom: '20px' }}>
      <h3>Submit New Issue</h3>
      <form onSubmit={handleSubmit}>
        <input type="text" placeholder="GitHub Repository URL" value={repoUrl} onChange={(e) => setRepoUrl(e.target.value)} style={{ width: '100%', padding: '10px', marginBottom: '10px' }} required />
        <textarea placeholder="Describe the bug..." value={issue} onChange={(e) => setIssue(e.target.value)} style={{ width: '100%', padding: '10px', height: '80px', marginBottom: '10px' }} required />
        <button type="submit" style={{ background: '#2563eb', color: 'white', padding: '10px 20px', border: 'none', borderRadius: '5px', cursor: 'pointer' }}>
          Start ASEA Agent
        </button>
      </form>
    </div>
  );
};

export default IssueForm;
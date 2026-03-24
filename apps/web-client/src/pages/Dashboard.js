import React, { useState } from 'react';
import IssueForm from '../components/IssueForm';
import PRPreview from './PRPreview';

const Dashboard = () => {
  const [currentPhase, setCurrentPhase] = useState(0); // 0 means waiting for submission
  const [showPR, setShowPR] = useState(false);

  const startAgent = (repo, issue) => {
    console.log("Starting agent for:", repo);
    setCurrentPhase(1); // Move to Phase 1: Issue Submitted [cite: 52]
  };

  return (
    <div style={{ padding: '30px', fontFamily: 'sans-serif' }}>
      <h1>ASEA Project Dashboard</h1>
      
      {/* Step 1: Issue Submission [cite: 60, 67] */}
      {currentPhase === 0 && <IssueForm onTrigger={startAgent} />}

      {/* Step 2: Progress Visualization (Phases 1-6) [cite: 42, 43] */}
      {currentPhase > 0 && (
        <div style={{ marginTop: '20px' }}>
          <h3>Agent Progress: Phase {currentPhase}</h3>
          <progress value={currentPhase} max="6" style={{ width: '100%', height: '20px' }}></progress>
          <button onClick={() => setCurrentPhase(currentPhase + 1)} style={{ marginTop: '10px' }}>
            Next Phase
          </button>
        </div>
      )}

      {/* Step 3: PR Preview [cite: 58, 65] */}
      {currentPhase === 6 && <PRPreview />}
    </div>
  );
};

export default Dashboard;
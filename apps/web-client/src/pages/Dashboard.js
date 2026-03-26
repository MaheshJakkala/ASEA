import React, { useState, useEffect } from 'react';
import IssueForm from '../components/IssueForm';
import PRPreview from './PRPreview';

const Dashboard = () => {
  const [currentPhase, setCurrentPhase] = useState(0); 

  const phases = [
    "Waiting for Issue...",
    "Phase 1: Issue Submitted",
    "Phase 2: Codebase Understanding",
    "Phase 3: Bug Localization",
    "Phase 4: Fix Planning",
    "Phase 5: Patch & Test",
    "Phase 6: PR Creation"
  ];

  // This is the "Auto-Pilot" logic
  useEffect(() => {
    let timer;
    // If the agent has started (Phase 1) but hasn't reached the end (Phase 6)
    if (currentPhase > 0 && currentPhase < 6) {
      timer = setTimeout(() => {
        setCurrentPhase((prev) => prev + 1);
      }, 5000); // 5000 milliseconds = 5 seconds
    }
    return () => clearTimeout(timer); // Cleanup timer if user leaves page
  }, [currentPhase]);

  const startAgent = (repo, issue) => {
    setCurrentPhase(1); 
  };

  return (
    <div style={{ padding: '30px', fontFamily: 'sans-serif', backgroundColor: '#f8fafc', minHeight: '100vh' }}>
      <h1 style={{ color: '#1e3a8a' }}>ASEA Project Dashboard</h1>
      
      {/* Step 1: Submission Form */}
      {currentPhase === 0 && <IssueForm onTrigger={startAgent} />}

      {/* Step 2: Auto-Progressing Visualization */}
      {currentPhase > 0 && (
        <div style={{ marginTop: '20px', background: 'white', padding: '30px', borderRadius: '12px', boxShadow: '0 4px 6px rgba(0,0,0,0.05)' }}>
          <h2 style={{ color: '#2563eb' }}>{phases[currentPhase]}</h2>
          
          <div style={{ width: '100%', backgroundColor: '#e2e8f0', borderRadius: '10px', height: '15px', margin: '20px 0' }}>
            <div style={{ 
              width: `${(currentPhase / 6) * 100}%`, 
              backgroundColor: '#10b981', 
              height: '100%', 
              borderRadius: '10px',
              transition: 'width 1s ease-in-out' 
            }}></div>
          </div>

          {currentPhase < 6 ? (
            <p style={{ color: '#64748b', fontStyle: 'italic' }}>
              Agent is working... (Moving to next phase automatically in 5s)
            </p>
          ) : (
            <PRPreview />
          )}
        </div>
      )}
    </div>
  );
};

export default Dashboard;
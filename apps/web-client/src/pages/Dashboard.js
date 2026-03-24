import React, { useState } from 'react';
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

  const startAgent = (repo, issue) => {
    setCurrentPhase(1); 
  };

  return (
    <div style={{ padding: '30px', fontFamily: 'sans-serif', backgroundColor: '#f8fafc', minHeight: '100vh' }}>
      <h1 style={{ color: '#1e3a8a' }}>ASEA Project Dashboard</h1>
      
      {/* Step 1: Submission Form */}
      {currentPhase === 0 && <IssueForm onTrigger={startAgent} />}

      {/* Step 2: Professional Progress Tracking */}
      {currentPhase > 0 && (
        <div style={{ marginTop: '20px', background: 'white', padding: '30px', borderRadius: '12px', boxShadow: '0 4px 6px rgba(0,0,0,0.05)' }}>
          <h2 style={{ color: '#2563eb' }}>{phases[currentPhase]}</h2>
          
          <div style={{ width: '100%', backgroundColor: '#e2e8f0', borderRadius: '10px', height: '15px', margin: '20px 0' }}>
            <div style={{ 
              width: `${(currentPhase / 6) * 100}%`, 
              backgroundColor: '#10b981', 
              height: '100%', 
              borderRadius: '10px',
              transition: 'width 0.5s ease-in-out' 
            }}></div>
          </div>

          {currentPhase < 6 ? (
            <button 
              onClick={() => setCurrentPhase(currentPhase + 1)} 
              style={{ padding: '10px 20px', backgroundColor: '#1e3a8a', color: 'white', border: 'none', borderRadius: '5px', cursor: 'pointer' }}
            >
              Simulate Next Step
            </button>
          ) : (
            <PRPreview />
          )}
        </div>
      )}
    </div>
  );
};

export default Dashboard;
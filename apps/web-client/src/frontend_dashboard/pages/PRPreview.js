import React from 'react';

const PRPreview = () => {
  return (
    <div style={{ padding: '20px', background: '#f1f5f9', borderRadius: '8px', marginTop: '20px' }}>
      <h2 style={{ color: '#0f172a' }}>Phase 6: Pull Request Generated</h2>
      <div style={{ background: '#1e293b', color: '#38bdf8', padding: '15px', borderRadius: '5px', fontFamily: 'monospace', whiteSpace: 'pre' }}>
        {/* We use a string here to avoid syntax errors with curly braces */}
        <p>{"+ // Fixed bug in controller"}</p>
        <p>{"+ if (userAuth) { return true; }"}</p>
      </div>
      <button style={{ marginTop: '15px', padding: '10px 15px', background: '#10b981', color: 'white', border: 'none', borderRadius: '5px', cursor: 'pointer' }}>
        View on GitHub
      </button>
    </div>
  );
};

export default PRPreview;
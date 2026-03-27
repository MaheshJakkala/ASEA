import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './frontend_dashboard/pages/Login';
import Dashboard from './frontend_dashboard/pages/Dashboard';
// Update any other imports like this...
function App() {
  return (
    <Router>
      <Routes>
        {/* This is the starting page */}
        <Route path="/" element={<Login />} />
        
        {/* This is where we go after login */}
        <Route path="/dashboard" element={<Dashboard />} />
      </Routes>
    </Router>
  );
}

export default App;
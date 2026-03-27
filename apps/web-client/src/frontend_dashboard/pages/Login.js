import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleLogin = (e) => {
    e.preventDefault();
    
    // Check if the email ends with @asea.com AND password is 123
    if (email.endsWith("@asea.com") && password === "123") {
      setError("");
      console.log("Access Granted for:", email);
      navigate('/dashboard'); 
    } else {
      // Error message for everything else
      setError("Access Denied! Use any name@asea.com and password 123.");
    }
  };
  return (
    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh', backgroundColor: '#f0f2f5' }}>
      <form onSubmit={handleLogin} style={{ background: 'white', padding: '40px', borderRadius: '10px', boxShadow: '0 4px 12px rgba(0,0,0,0.1)', width: '350px' }}>
        <h2 style={{ textAlign: 'center', color: '#1e3a8a' }}>ASEA Login</h2>
        
        {error && <p style={{ color: 'red', fontSize: '12px', textAlign: 'center' }}>{error}</p>}
        
        <input type="email" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} style={{ width: '100%', padding: '10px', margin: '10px 0', borderRadius: '5px', border: '1px solid #ddd', boxSizing: 'border-box' }} required />
        <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} style={{ width: '100%', padding: '10px', margin: '10px 0', borderRadius: '5px', border: '1px solid #ddd', boxSizing: 'border-box' }} required />
        
        <button type="submit" style={{ width: '100%', padding: '12px', backgroundColor: '#2563eb', color: 'white', border: 'none', borderRadius: '5px', cursor: 'pointer', fontWeight: 'bold' }}>
          Sign In
        </button>
      </form>
    </div>
  );
};

export default Login;
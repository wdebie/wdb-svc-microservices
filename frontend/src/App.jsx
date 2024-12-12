import React, { useState, useEffect } from 'react';
import { googleLogout, useGoogleLogin } from '@react-oauth/google';
import axios from 'axios';
import { Routes, Route, Navigate } from 'react-router-dom';
import Header from './components/Header';
import LandingPage from './components/LandingPage';
import AdminDashboard from './components/AdminDashboard';
import SchedulePage from './components/SchedulePage';
import FoodPage from './components/FoodPage';

function App() {
  const [user, setUser] = useState(null);
  const [profile, setProfile] = useState(null);

  const login = useGoogleLogin({
    onSuccess: (codeResponse) => setUser(codeResponse),
    onError: (error) => console.log('Login Failed:', error)
  });

  useEffect(
    () => {
      if (user) {
        axios
          .get(`https://www.googleapis.com/oauth2/v1/userinfo?access_token=${user.access_token}`, {
            headers: {
              Authorization: `Bearer ${user.access_token}`,
              Accept: 'application/json'
            }
          })
          .then((res) => {
            setProfile(res.data);
          })
          .catch((err) => console.log(err));
      }
    },
    [user]
  );

  const logOut = () => {
    googleLogout();
    setUser(null);
    setProfile(null);
  };

  return (
    <div className="min-h-screen flex flex-col bg-gray-900 text-white">
      <Header profile={profile} onLogin={login} onLogout={logOut} />
      <div className="flex-grow">
        <Routes>
          <Route path="/" element={<LandingPage />} />
          <Route
            path="/admin"
            element={profile ? <AdminDashboard /> : <Navigate to="/" />}
          />
          <Route path="/schedule" element={<SchedulePage />} />
          <Route path="/food" element={<FoodPage />} />
        </Routes>
      </div>
    </div>
  );
}

export default App;
import React, { useState, useEffect } from 'react';
import { googleLogout, useGoogleLogin } from '@react-oauth/google';
import axios from 'axios';
import { Routes, Route, Navigate, Link } from 'react-router-dom';
import Header from './components/Header';
import LandingPage from './components/LandingPage';
import AdminDashboard from './components/AdminDashboard';
import SchedulePage from './components/SchedulePage';
import FoodPage from './components/FoodPage';
import ManageFoodTrucks from './components/Admin/ManageFoodTrucks';
import ManageFoodItems from './components/Admin/ManageFoodItems';
import ManageArtists from './components/Admin/ManageArtists';
import ManageStages from './components/Admin/ManageStages';
import ManageSchedules from './components/Admin/ManageSchedules';

function Footer({ profile, onLogin, onLogout }) {
  return (
    <footer className="w-full flex justify-between items-center p-4 bg-background">
      <div>
        &copy; {new Date().getFullYear()} Fritfest. All rights reserved.
      </div>
      <div>
        {profile ? (
          <>
            <Link to="/admin" className="text-foreground hover:underline mr-4">
              Admin Dashboard
            </Link>
            <button
              onClick={onLogout}
              className="text-destructive hover:underline"
            >
              Log Out
            </button>
          </>
        ) : (
          <button
            onClick={onLogin}
            className="text-primary hover:underline"
          >
            Log In
          </button>
        )}
      </div>
    </footer>
  );
}

function App() {
  const [user, setUser] = useState(() => {
    // Retrieve user from local storage if available
    const savedUser = localStorage.getItem('user');
    return savedUser ? JSON.parse(savedUser) : null;
  });
  const [profile, setProfile] = useState(null);

  const login = useGoogleLogin({
    onSuccess: (codeResponse) => {
      setUser(codeResponse);
      localStorage.setItem('user', JSON.stringify(codeResponse));
      localStorage.setItem('token', codeResponse.access_token);
      console.log(codeResponse.access_token);
    },
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
    // Clear user from local storage
    localStorage.removeItem('user');
  };

  return (
    <div className="min-h-screen flex flex-col bg-background text-foreground">
      <Header />
      <div className="flex-grow">
        <Routes>
          <Route path="/" element={<LandingPage />} />
          <Route
            path="/admin/*"
            element={profile ? <AdminDashboard /> : <Navigate to="/" />}
          >
            <Route path="foodtrucks" element={<ManageFoodTrucks />} />
            <Route path="fooditems" element={<ManageFoodItems />} />
            <Route path="artists" element={<ManageArtists />} />
            <Route path="stages" element={<ManageStages />} />
            <Route path="schedules" element={<ManageSchedules />} />
          </Route>
          <Route path="/schedule" element={<SchedulePage />} />
          <Route path="/food" element={<FoodPage />} />
        </Routes>
      </div>
      <Footer profile={profile} onLogin={login} onLogout={logOut} />
    </div>
  );
}

export default App;
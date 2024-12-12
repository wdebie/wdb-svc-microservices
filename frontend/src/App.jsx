import React, { useState, useEffect } from 'react';
import { googleLogout, useGoogleLogin } from '@react-oauth/google';
import axios from 'axios';

function App() {
  const [user, setUser] = useState([]);
  const [profile, setProfile] = useState([]);

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
    setProfile(null);
  };

  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-gray-900 text-white p-4">
      <h2 className="text-3xl font-bold mb-8">React Google Login</h2>

      {profile ? (
        <div className="flex flex-col items-center space-y-4">
          <img src={profile.picture} alt="user image" className="w-24 h-24 rounded-full border-4 border-blue-500" />
          <h3 className="text-xl font-semibold">User Logged in</h3>
          <div className="text-gray-300">
            <p className="text-center">Name: {profile.name}</p>
            <p className="text-center">Email Address: {profile.email}</p>
          </div>
          <button
            onClick={logOut}
            className="mt-6 bg-red-600 hover:bg-red-700 text-white px-6 py-2 rounded-lg transition-colors duration-200"
          >
            Log out
          </button>
        </div>
      ) : (
        <button
          onClick={() => login()}
          className="bg-blue-600 hover:bg-blue-700 text-white px-6 py-3 rounded-lg flex items-center space-x-2 transition-colors duration-200"
        >
          <span>Sign in with Google</span>
          <span className="text-xl">🚀</span>
        </button>
      )}
    </div>
  );
}
export default App;
import React, { useState, useEffect } from "react";
import { googleLogout, useGoogleLogin } from "@react-oauth/google";
import axios from "axios";
import { Routes, Route, Navigate } from "react-router-dom";
import Header from "./components/Header";
import Footer from "./components/Footer"
import LandingPage from "./components/LandingPage";
import AdminDashboard from "./components/AdminDashboard";
import SchedulePage from "./components/SchedulePage";
import FoodPage from "./components/FoodPage";
import ManageFoodTrucks from "./components/Admin/ManageFoodTrucks";
import ManageFoodItems from "./components/Admin/ManageFoodItems";
import ManageArtists from "./components/Admin/ManageArtists";
import ManageStages from "./components/Admin/ManageStages";
import ManageSchedules from "./components/Admin/ManageSchedules";
import LineupPage from "./components/LineupPage";
function App() {
  const [user, setUser] = useState(() => {
    // Retrieve user from local storage if available
    const savedUser = localStorage.getItem("user");
    return savedUser ? JSON.parse(savedUser) : null;
  });
  const [profile, setProfile] = useState(null);

  const login = useGoogleLogin({
    flow: "auth-code",
    scope: "openid profile email",
    onSuccess: async (codeResponse) => {
      const { code } = codeResponse;
      try {
        // Exchange authorization code for tokens
        const response = await axios.post(
          "https://oauth2.googleapis.com/token",
          {
            code,
            client_id:
              "661879216618-0gl8krhgkntc04h7m8qfvnp3k16datne.apps.googleusercontent.com",
            client_secret: "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",
            redirect_uri: "https://wdb-svc-microservices.pages.dev",
            grant_type: "authorization_code",
          },
          {
            headers: {
              "Content-Type": "application/json",
            },
          }
        );

        const { access_token, id_token } = response.data;

        // Save user info and tokens
        const userInfo = { access_token, id_token };
        setUser(userInfo);
        localStorage.setItem("user", JSON.stringify(userInfo));
        localStorage.setItem("token", access_token);
        localStorage.setItem("jwt", id_token); // Save JWT to local storage

        console.log("Access Token:", access_token); // Short-lived, non-JWT
        console.log("ID Token (JWT):", id_token); // JWT for authentication

        // Fetch user profile information
        const profileResponse = await axios.get(
          `https://www.googleapis.com/oauth2/v1/userinfo?access_token=${access_token}`,
          {
            headers: {
              Authorization: `Bearer ${access_token}`,
              Accept: "application/json",
            },
          }
        );

        setProfile(profileResponse.data);
      } catch (error) {
        console.error(
          "Error exchanging code for token:",
          error.response?.data || error.message
        );
      }
    },
    onError: (error) => console.error("Login Failed:", error),
  });

  useEffect(() => {
    if (user) {
      axios
        .get(
          `https://www.googleapis.com/oauth2/v1/userinfo?access_token=${user.access_token}`,
          {
            headers: {
              Authorization: `Bearer ${user.access_token}`,
              Accept: "application/json",
            },
          }
        )
        .then((res) => {
          setProfile(res.data);
        })
        .catch((err) => console.log(err));
    }
  }, [user]);

  const logOut = () => {
    googleLogout();
    setUser(null);
    setProfile(null);
    // Clear user from local storage
    localStorage.removeItem("user");
    localStorage.removeItem("token");
    localStorage.removeItem("jwt");
  };

  return (
    <div className="min-h-screen flex flex-col bg-gray-900 text-white">
      <Header profile={profile} onLogin={login} onLogout={logOut} />
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
          <Route path="/lineup" element={<LineupPage />} />
        </Routes>
      </div>
      <Footer profile={profile} onLogin={login} onLogout={logOut} />
    </div>
  );
}

export default App;

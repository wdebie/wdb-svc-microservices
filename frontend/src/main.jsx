import React, { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import { GoogleOAuthProvider } from "@react-oauth/google"
import { BrowserRouter } from 'react-router-dom'

createRoot(document.getElementById('root')).render(
  <GoogleOAuthProvider clientId='661879216618-0gl8krhgkntc04h7m8qfvnp3k16datne.apps.googleusercontent.com'>
    <StrictMode>
      <BrowserRouter>
        <App />
      </BrowserRouter>
    </StrictMode>
  </GoogleOAuthProvider>
)

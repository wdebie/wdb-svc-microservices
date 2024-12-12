import React from 'react';
import { Link } from 'react-router-dom';

function Footer({ profile, onLogin, onLogout }) {
    return (
        <footer className="w-full flex justify-between items-center p-4 bg-card text-card-foreground">
            <div>
                &copy; {new Date().getFullYear()} Fritfest. All rights reserved.
            </div>
            <div>
                {profile ? (
                    <>
                        <Link to="/admin" className="text-primary mr-4">
                            Admin Dashboard
                        </Link>
                        <button
                            onClick={onLogout}
                            className="text-primary"
                        >
                            Log Out
                        </button>
                    </>
                ) : (
                    <button
                        onClick={onLogin}
                        className="text-primary"
                    >
                        Log In
                    </button>
                )}
            </div>
        </footer>
    );
}

export default Footer; 
import React from 'react';
import { Link } from 'react-router-dom';

function Header({ profile, onLogin, onLogout }) {
    return (
        <header className="w-full flex justify-between items-center p-4 bg-gray-800">
            <h1 className="text-xl font-bold">Fritfest</h1>
            <div className="flex space-x-4">
                {profile && (
                    <Link to="/admin" className="bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded-lg">
                        Admin Dashboard
                    </Link>
                )}
                {profile ? (
                    <button
                        onClick={onLogout}
                        className="bg-red-600 hover:bg-red-700 text-white px-4 py-2 rounded-lg"
                    >
                        Log Out
                    </button>
                ) : (
                    <button
                        onClick={onLogin}
                        className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg"
                    >
                        Log In
                    </button>
                )}
            </div>
        </header>
    );
}

export default Header; 
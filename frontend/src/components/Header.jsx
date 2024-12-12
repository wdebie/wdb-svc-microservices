import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Sun, Moon } from 'lucide-react';

function Header({ profile, onLogin, onLogout }) {
    const [isDarkMode, setIsDarkMode] = useState(false);

    useEffect(() => {
        const savedTheme = localStorage.getItem('theme');
        if (savedTheme === 'dark') {
            setIsDarkMode(true);
        }
    }, []);

    useEffect(() => {
        if (isDarkMode) {
            document.documentElement.classList.add('dark');
        } else {
            document.documentElement.classList.remove('dark');
        }
    }, [isDarkMode]);

    const toggleDarkMode = () => {
        setIsDarkMode(!isDarkMode);
        localStorage.setItem('theme', isDarkMode ? 'light' : 'dark');
    };

    return (
        <header className="w-full flex justify-between items-center p-4 bg-background">
            <h1 className="text-xl font-bold">Fritfest</h1>
            <div className="flex space-x-4">
                <Link to="/schedule" className="bg-gray-600 hover:bg-gray-700 text-white px-4 py-2 rounded-lg">
                    Schedule
                </Link>
                <Link to="/food" className="bg-gray-600 hover:bg-gray-700 text-white px-4 py-2 rounded-lg">
                    Food
                </Link>
                {profile && (
                    <Link to="/admin" className="bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded-lg">
                        Admin Dashboard
                    </Link>
                )}
                <button
                    onClick={toggleDarkMode}
                    className="bg-secondary hover:bg-secondary-foreground text-foreground p-2 rounded-lg"
                >
                    {isDarkMode ? <Sun size={20} /> : <Moon size={20} />}
                </button>
                {profile ? (
                    <button
                        onClick={onLogout}
                        className="bg-destructive hover:bg-destructive-foreground text-foreground px-4 py-2 rounded-lg"
                    >
                        Log Out
                    </button>
                ) : (
                    <button
                        onClick={onLogin}
                        className="bg-primary hover:bg-primary-foreground text-foreground px-4 py-2 rounded-lg"
                    >
                        Log In
                    </button>
                )}
            </div>
        </header>
    );
}

export default Header; 
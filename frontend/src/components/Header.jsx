import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Sun, Moon } from 'lucide-react';

function Header() {
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
        <header className="w-full flex justify-between items-center p-4 bg-card text-card-foreground">
            <Link to="/" className="text-xl font-bold">Fritfest</Link>
            <div className="flex space-x-4">
                <Link to="/" className="text-foreground hover:text-primary">
                    Home
                </Link>
                <Link to="/schedule" className="text-foreground hover:text-primary">
                    Schedule
                </Link>
                <Link to="/food" className="text-foreground hover:text-primary">
                    Food
                </Link>
                <Link to="/lineup" className="text-foreground hover:text-primary">
                    Lineup
                </Link>
            </div>
            <button
                onClick={toggleDarkMode}
                className="bg-secondary hover:bg-secondary-foreground text-secondary-foreground p-2 rounded-lg"
            >
                {isDarkMode ? <Sun size={20} /> : <Moon size={20} />}
            </button>
        </header>
    );
}

export default Header; 
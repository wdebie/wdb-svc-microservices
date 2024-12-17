import React, { useEffect, useState } from 'react';
import axios from 'axios';

function LineupPage() {
    const [artists, setArtists] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        axios.get('https://api.fritfest.com/artists')
            .then(response => {
                setArtists(response.data);
                setLoading(false);
            })
            .catch(err => {
                console.error(err);
                setError('Failed to fetch artists.');
                setLoading(false);
            });
    }, []);

    if (loading) {
        return <div className="p-4 max-w-4xl mx-auto text-center">Loading artists...</div>;
    }
    if (error) {
        return <div className="p-4 max-w-4xl mx-auto text-center text-destructive">{error}</div>;
    }

    return (
        <div className="p-4 max-w-4xl mx-auto">
            <h2 className="text-4xl font-bold mb-8 text-center">Artist Lineup</h2>
            <div className="flex flex-col items-center space-y-8">
                {artists.map(artist => (
                    <div key={artist.artistId} className="bg-card rounded-lg shadow-lg p-6 w-full max-w-md flex flex-col items-center justify-center transform hover:rotate-3 transition duration-300">
                        <h3 className="text-3xl font-bold text-center text-card-foreground mb-4">{artist.name}</h3>
                        <div className="mt-4 text-center">
                            <span className="inline-block bg-primary rounded-full px-3 py-1 text-sm font-semibold text-primary-foreground mr-2">🎸</span>
                            <span className="inline-block bg-primary rounded-full px-3 py-1 text-sm font-semibold text-secondary-foreground mr-2">🎤</span>
                            <span className="inline-block bg-primary rounded-full px-3 py-1 text-sm font-semibold text-accent-foreground">🎉</span>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default LineupPage;
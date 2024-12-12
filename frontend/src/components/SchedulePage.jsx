import React, { useEffect, useState } from 'react';
import axios from 'axios';

function SchedulePage() {
    const [schedules, setSchedules] = useState([]);
    const [artists, setArtists] = useState({});
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        Promise.all([
            axios.get('/api/schedules'),
            axios.get('/api/artists')
        ])
            .then(([schedulesResponse, artistsResponse]) => {
                setSchedules(schedulesResponse.data);
                setArtists(artistsResponse.data.reduce((acc, artist) => {
                    acc[artist.skuCode] = artist;
                    return acc;
                }, {}));
                setLoading(false);
            })
            .catch(err => {
                console.error(err);
                setError('Failed to fetch data.');
                setLoading(false);
            });
    }, []);

    if (loading) {
        return <div className="p-4 text-center">Loading schedules...</div>;
    }
    if (error) {
        return <div className="p-4 text-center text-destructive">{error}</div>;
    }

    const groupedSchedules = schedules.reduce((acc, schedule) => {
        const date = new Date(schedule.startTime).toLocaleDateString();
        if (!acc[date]) {
            acc[date] = [];
        }
        acc[date].push(schedule);
        return acc;
    }, {});

    return (
        <div className="p-4 max-w-4xl mx-auto">
            <h2 className="text-4xl font-bold mb-8 text-center">Event Schedule</h2>
            {Object.keys(groupedSchedules).map(date => (
                <div key={date} className="mb-8">
                    <h3 className="text-2xl font-semibold mb-4 text-center">{date}</h3>
                    <ul className="max-w-lg mx-auto">
                        {groupedSchedules[date].map(schedule => (
                            <li key={schedule.scheduleId} className="mb-2">
                                <div className="flex justify-between">
                                    <span className="font-semibold">{new Date(schedule.startTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })} - {new Date(schedule.endTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}</span>
                                    <span className="text-primary">{artists[schedule.artistSkuCode]?.name || 'Unknown Artist'}</span>
                                </div>
                            </li>
                        ))}
                    </ul>
                </div>
            ))}
        </div>
    );
}

export default SchedulePage;
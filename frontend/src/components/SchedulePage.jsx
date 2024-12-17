import React, { useEffect, useState } from 'react';
import axios from 'axios';

function SchedulePage() {
    const [schedules, setSchedules] = useState([]);
    const [artists, setArtists] = useState({});
    const [stages, setStages] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        Promise.all([
            axios.get('https://api.fritfest.com/schedules'),
            axios.get('https://api.fritfest.com/artists'),
            axios.get('https://api.fritfest.com/stages')
        ])
            .then(([schedulesResponse, artistsResponse, stagesResponse]) => {
                setSchedules(schedulesResponse.data);
                setArtists(artistsResponse.data.reduce((acc, artist) => {
                    acc[artist.skuCode] = artist;
                    return acc;
                }, {}));
                setStages(stagesResponse.data);
                setLoading(false);
            })
            .catch(err => {
                console.error(err);
                setError('Failed to fetch data.');
                setLoading(false);
            });
    }, []);

    if (loading) {
        return <div className="p-4 max-w-4xl mx-auto text-center">Loading schedules...</div>;
    }
    if (error) {
        return <div className="p-4 max-w-4xl mx-auto text-center text-destructive">{error}</div>;
    }

    const groupedSchedules = stages.reduce((acc, stage) => {
        acc[stage.stageId] = schedules.filter(schedule => schedule.stageId === stage.stageId);
        return acc;
    }, {});

    return (
        <div className="p-4 max-w-4xl mx-auto">
            <h2 className="text-4xl font-bold mb-8 text-center">Event Schedule</h2>
            <div className="flex justify-center space-x-8">
                {stages.map(stage => (
                    <div key={stage.stageId} className="mb-8">
                        <h3 className="text-2xl font-semibold mb-4 text-center">{stage.name}</h3>
                        <div className="space-y-4">
                            {groupedSchedules[stage.stageId].map(schedule => (
                                <div key={schedule.scheduleId} className="bg-card p-4 rounded-lg shadow-md flex flex-col items-center">
                                    <span className="text-lg font-semibold text-primary mb-2">{artists[schedule.artistSkuCode]?.name || 'Unknown Artist'}</span>
                                    <div className="text-muted-foreground">
                                        <span className="text-sm mr-2">{new Date(schedule.startTime).toLocaleDateString()}</span>
                                        <span>{new Date(schedule.startTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}</span>
                                        <span className="mx-2">-</span>
                                        <span>{new Date(schedule.endTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}</span>
                                    </div>
                                </div>
                            ))}
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default SchedulePage;
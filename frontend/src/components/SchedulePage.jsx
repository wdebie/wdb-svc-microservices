import React, { useEffect, useState } from 'react';
import axios from 'axios';

function SchedulePage() {
    const [schedules, setSchedules] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        axios.get('/api/schedules')
            .then(response => {
                setSchedules(response.data);
                setLoading(false);
            })
            .catch(err => {
                console.error(err);
                setError('Failed to fetch schedules.');
                setLoading(false);
            });
    }, []);

    if (loading) {
        return <div className="p-4 text-center">Loading schedules...</div>;
    }
    if (error) {
        return <div className="p-4 text-center text-red-500">{error}</div>;
    }

    return (
        <div className="p-4">
            <h2 className="text-2xl font-bold mb-4">Event Schedule</h2>
            <table className="w-full table-auto bg-gray-800 text-white">
                <thead>
                    <tr>
                        <th className="px-4 py-2">Event ID</th>
                        <th className="px-4 py-2">SKU Code</th>
                        <th className="px-4 py-2">Start Time</th>
                        <th className="px-4 py-2">End Time</th>
                        <th className="px-4 py-2">Artist SKU</th>
                        <th className="px-4 py-2">Food Truck ID</th>
                        <th className="px-4 py-2">Stage ID</th>
                    </tr>
                </thead>
                <tbody>
                    {schedules.map(schedule => (
                        <tr key={schedule.scheduleId}>
                            <td className="border px-4 py-2">{schedule.scheduleId}</td>
                            <td className="border px-4 py-2">{schedule.skuCode}</td>
                            <td className="border px-4 py-2">{new Date(schedule.startTime).toLocaleString()}</td>
                            <td className="border px-4 py-2">{new Date(schedule.endTime).toLocaleString()}</td>
                            <td className="border px-4 py-2">{schedule.artistSkuCode}</td>
                            <td className="border px-4 py-2">{schedule.foodTruckId}</td>
                            <td className="border px-4 py-2">{schedule.stageId}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default SchedulePage;
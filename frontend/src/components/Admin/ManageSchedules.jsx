import React, { useEffect, useState } from 'react';
import axios from 'axios';

function ManageSchedules() {
    const [schedules, setSchedules] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [newSchedule, setNewSchedule] = useState({
        skuCode: '',
        startTime: '',
        endTime: '',
        artistSkuCode: '',
        foodTruckId: '',
        stageId: ''
    });

    useEffect(() => {
        fetchSchedules();
    }, []);

    const fetchSchedules = async () => {
        try {
            const response = await axios.get('/api/schedules');
            setSchedules(response.data);
            setLoading(false);
        } catch (err) {
            console.error(err);
            setError('Failed to fetch schedules.');
            setLoading(false);
        }
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setNewSchedule({ ...newSchedule, [name]: value });
    };

    const handleAddSchedule = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('/api/schedules', {
                ...newSchedule,
                startTime: new Date(newSchedule.startTime).toISOString(),
                endTime: new Date(newSchedule.endTime).toISOString(),
                foodTruckId: parseInt(newSchedule.foodTruckId),
                stageId: parseInt(newSchedule.stageId)
            });
            setSchedules([...schedules, response.data]);
            setNewSchedule({
                skuCode: '',
                startTime: '',
                endTime: '',
                artistSkuCode: '',
                foodTruckId: '',
                stageId: ''
            });
        } catch (err) {
            console.error(err);
            setError('Failed to add schedule.');
        }
    };

    const handleDeleteSchedule = async (id) => {
        if (window.confirm('Are you sure you want to delete this schedule?')) {
            try {
                await axios.delete(`/api/schedule/${id}`);
                setSchedules(schedules.filter(schedule => schedule.scheduleId !== id));
            } catch (err) {
                console.error(err);
                setError('Failed to delete schedule.');
            }
        }
    };

    if (loading) {
        return <div className="p-4 text-center">Loading schedules...</div>;
    }
    if (error) {
        return <div className="p-4 text-center text-red-500">{error}</div>;
    }

    return (
        <div className="p-4">
            <h2 className="text-2xl font-bold mb-4">Manage Schedules</h2>

            <form onSubmit={handleAddSchedule} className="mb-6 bg-gray-800 p-4 rounded-lg">
                <h3 className="text-xl font-semibold mb-2">Add New Schedule</h3>
                <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                    <input
                        type="text"
                        name="skuCode"
                        value={newSchedule.skuCode}
                        onChange={handleInputChange}
                        placeholder="SKU Code"
                        required
                        className="p-2 rounded bg-gray-700 text-white"
                    />
                    <input
                        type="datetime-local"
                        name="startTime"
                        value={newSchedule.startTime}
                        onChange={handleInputChange}
                        placeholder="Start Time"
                        required
                        className="p-2 rounded bg-gray-700 text-white"
                    />
                    <input
                        type="datetime-local"
                        name="endTime"
                        value={newSchedule.endTime}
                        onChange={handleInputChange}
                        placeholder="End Time"
                        required
                        className="p-2 rounded bg-gray-700 text-white"
                    />
                    <input
                        type="text"
                        name="artistSkuCode"
                        value={newSchedule.artistSkuCode}
                        onChange={handleInputChange}
                        placeholder="Artist SKU Code"
                        required
                        className="p-2 rounded bg-gray-700 text-white"
                    />
                    <input
                        type="number"
                        name="foodTruckId"
                        value={newSchedule.foodTruckId}
                        onChange={handleInputChange}
                        placeholder="Food Truck ID"
                        required
                        className="p-2 rounded bg-gray-700 text-white"
                    />
                    <input
                        type="number"
                        name="stageId"
                        value={newSchedule.stageId}
                        onChange={handleInputChange}
                        placeholder="Stage ID"
                        required
                        className="p-2 rounded bg-gray-700 text-white"
                    />
                </div>
                <button
                    type="submit"
                    className="mt-4 bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded-lg"
                >
                    Add Schedule
                </button>
            </form>

            <table className="w-full table-auto bg-gray-800 text-white">
                <thead>
                    <tr>
                        <th className="px-4 py-2">ID</th>
                        <th className="px-4 py-2">SKU Code</th>
                        <th className="px-4 py-2">Start Time</th>
                        <th className="px-4 py-2">End Time</th>
                        <th className="px-4 py-2">Artist SKU</th>
                        <th className="px-4 py-2">Food Truck ID</th>
                        <th className="px-4 py-2">Stage ID</th>
                        <th className="px-4 py-2">Actions</th>
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
                            <td className="border px-4 py-2">
                                <button
                                    onClick={() => handleDeleteSchedule(schedule.scheduleId)}
                                    className="bg-red-600 hover:bg-red-700 text-white px-2 py-1 rounded"
                                >
                                    Delete
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default ManageSchedules;
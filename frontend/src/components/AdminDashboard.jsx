import React from 'react';

function AdminDashboard() {
    return (
        <div className="flex-grow p-4">
            <h2 className="text-3xl font-bold mb-6 text-center">Admin Dashboard</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div className="bg-gray-800 p-6 rounded-lg shadow-lg">
                    <h3 className="text-2xl font-semibold mb-2">Manage Food Trucks</h3>
                    <p className="mb-4">Create, read, update, and delete food truck entries.</p>
                    <button className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg">
                        Manage
                    </button>
                </div>
                <div className="bg-gray-800 p-6 rounded-lg shadow-lg">
                    <h3 className="text-2xl font-semibold mb-2">Manage Events</h3>
                    <p className="mb-4">Create, read, update, and delete event details.</p>
                    <button className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg">
                        Manage
                    </button>
                </div>
                <div className="bg-gray-800 p-6 rounded-lg shadow-lg">
                    <h3 className="text-2xl font-semibold mb-2">Manage Users</h3>
                    <p className="mb-4">Create, read, update, and delete user accounts.</p>
                    <button className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg">
                        Manage
                    </button>
                </div>
                <div className="bg-gray-800 p-6 rounded-lg shadow-lg">
                    <h3 className="text-2xl font-semibold mb-2">Manage Feedback</h3>
                    <p className="mb-4">View and respond to user feedback and reviews.</p>
                    <button className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg">
                        Manage
                    </button>
                </div>
            </div>
        </div>
    );
}

export default AdminDashboard; 
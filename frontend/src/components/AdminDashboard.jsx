import React from 'react';
import { Link } from 'react-router-dom';
import { Outlet } from 'react-router-dom';

function AdminDashboard() {
    return (
        <div className="flex-grow p-4">
            <h2 className="text-3xl font-bold mb-6 text-center">Admin Dashboard</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                <div className="bg-gray-800 p-6 rounded-lg shadow-lg">
                    <h3 className="text-2xl font-semibold mb-2">Manage Food Trucks</h3>
                    <p className="mb-4">Create, read, update, and delete food truck entries.</p>
                    <Link to="/manage-foodtrucks">
                        <button className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg">
                            Manage Food Trucks
                        </button>
                    </Link>
                </div>
                <div className="bg-gray-800 p-6 rounded-lg shadow-lg">
                    <h3 className="text-2xl font-semibold mb-2">Manage Food Items</h3>
                    <p className="mb-4">Create, read, update, and delete food items.</p>
                    <Link to="/manage-fooditems">
                        <button className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg">
                            Manage Food Items
                        </button>
                    </Link>
                </div>
                <div className="bg-gray-800 p-6 rounded-lg shadow-lg">
                    <h3 className="text-2xl font-semibold mb-2">Manage Artists</h3>
                    <p className="mb-4">Create, read, update, and delete artists.</p>
                    <Link to="/manage-artists">
                        <button className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg">
                            Manage Artists
                        </button>
                    </Link>
                </div>
                <div className="bg-gray-800 p-6 rounded-lg shadow-lg">
                    <h3 className="text-2xl font-semibold mb-2">Manage Stages</h3>
                    <p className="mb-4">Create, read, update, and delete stages.</p>
                    <Link to="/manage-stages">
                        <button className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg">
                            Manage Stages
                        </button>
                    </Link>
                </div>
                <div className="bg-gray-800 p-6 rounded-lg shadow-lg">
                    <h3 className="text-2xl font-semibold mb-2">Manage Schedule</h3>
                    <p className="mb-4">Create, read, update, and delete schedules.</p>
                    <Link to="/manage-schedules">
                        <button className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg">
                            Manage Schedule
                        </button>
                    </Link>
                </div>
            </div>
            <Outlet />
        </div>
    );
}

export default AdminDashboard; 
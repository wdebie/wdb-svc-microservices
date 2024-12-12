import React, { useEffect, useState } from 'react';
import axios from 'axios';

function ManageFoodItems() {
    const [foodItems, setFoodItems] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [newFoodItem, setNewFoodItem] = useState({
        skuCode: '',
        name: '',
        price: '',
        foodTruckId: ''
    });

    useEffect(() => {
        fetchFoodItems();
    }, []);

    const fetchFoodItems = async () => {
        try {
            const response = await axios.get('/api/fooditems');
            setFoodItems(response.data);
            setLoading(false);
        } catch (err) {
            console.error(err);
            setError('Failed to fetch food items.');
            setLoading(false);
        }
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setNewFoodItem({ ...newFoodItem, [name]: value });
    };

    const handleAddFoodItem = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('/api/fooditems', {
                ...newFoodItem,
                price: parseFloat(newFoodItem.price),
                foodTruckId: parseInt(newFoodItem.foodTruckId)
            });
            setFoodItems([...foodItems, response.data]);
            setNewFoodItem({
                skuCode: '',
                name: '',
                price: '',
                foodTruckId: ''
            });
        } catch (err) {
            console.error(err);
            setError('Failed to add food item.');
        }
    };

    const handleDeleteFoodItem = async (id) => {
        if (window.confirm('Are you sure you want to delete this food item?')) {
            try {
                await axios.delete(`/api/fooditem/${id}`, {
                    headers: {
                        "Content-type": "application/json",
                        "Authorization": `Bearer ${localStorage.getItem('token')}`
                    }
                });
                setFoodItems(foodItems.filter(item => item.foodItemId !== id));
            } catch (err) {
                console.error(err);
                setError('Failed to delete food item.');
            }
        }
    };

    if (loading) {
        return <div className="p-4 text-center">Loading food items...</div>;
    }
    if (error) {
        return <div className="p-4 text-center text-red-500">{error}</div>;
    }

    return (
        <div className="p-4">
            <h2 className="text-2xl font-bold mb-4">Manage Food Items</h2>

            <form onSubmit={handleAddFoodItem} className="mb-6 bg-gray-800 p-4 rounded-lg">
                <h3 className="text-xl font-semibold mb-2">Add New Food Item</h3>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <input
                        type="text"
                        name="skuCode"
                        value={newFoodItem.skuCode}
                        onChange={handleInputChange}
                        placeholder="SKU Code"
                        required
                        className="p-2 rounded bg-gray-700 text-white"
                    />
                    <input
                        type="text"
                        name="name"
                        value={newFoodItem.name}
                        onChange={handleInputChange}
                        placeholder="Name"
                        required
                        className="p-2 rounded bg-gray-700 text-white"
                    />
                    <input
                        type="number"
                        step="0.01"
                        name="price"
                        value={newFoodItem.price}
                        onChange={handleInputChange}
                        placeholder="Price (€)"
                        required
                        className="p-2 rounded bg-gray-700 text-white"
                    />
                    <input
                        type="number"
                        name="foodTruckId"
                        value={newFoodItem.foodTruckId}
                        onChange={handleInputChange}
                        placeholder="Food Truck ID"
                        required
                        className="p-2 rounded bg-gray-700 text-white"
                    />
                </div>
                <button
                    type="submit"
                    className="mt-4 bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded-lg"
                >
                    Add Food Item
                </button>
            </form>

            <table className="w-full table-auto bg-gray-800 text-white">
                <thead>
                    <tr>
                        <th className="px-4 py-2">ID</th>
                        <th className="px-4 py-2">SKU Code</th>
                        <th className="px-4 py-2">Name</th>
                        <th className="px-4 py-2">Price (€)</th>
                        <th className="px-4 py-2">Food Truck ID</th>
                        <th className="px-4 py-2">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {foodItems.map(item => (
                        <tr key={item.foodItemId}>
                            <td className="border px-4 py-2">{item.foodItemId}</td>
                            <td className="border px-4 py-2">{item.skuCode}</td>
                            <td className="border px-4 py-2">{item.name}</td>
                            <td className="border px-4 py-2">{item.price.toFixed(2)}</td>
                            <td className="border px-4 py-2">{item.foodTruckId}</td>
                            <td className="border px-4 py-2">
                                <button
                                    onClick={() => handleDeleteFoodItem(item.foodItemId)}
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

export default ManageFoodItems;
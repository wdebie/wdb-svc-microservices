import React, { useEffect, useState } from 'react';
import axios from 'axios';

function FoodPage() {
    const [foodItems, setFoodItems] = useState([]);
    const [groupedFood, setGroupedFood] = useState({});
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        axios.get('/api/fooditems')
            .then(response => {
                const items = response.data;
                setFoodItems(items);
                groupFoodByTruck(items);
                setLoading(false);
            })
            .catch(err => {
                console.error(err);
                setError('Failed to fetch food items.');
                setLoading(false);
            });
    }, []);

    const groupFoodByTruck = (items) => {
        if (!Array.isArray(items)) {
            console.error('Expected items to be an array, got:', typeof items);
            setError('Invalid data format received from server.');
            return;
        }

        const grouped = items.reduce((acc, item) => {
            const truckId = item.foodTruck.foodTruckId;
            if (!acc[truckId]) {
                acc[truckId] = {
                    truckName: item.foodTruck.name,
                    items: []
                };
            }
            acc[truckId].items.push(item);
            return acc;
        }, {});
        setGroupedFood(grouped);
    };

    if (loading) {
        return <div className="p-4 text-center">Loading food items...</div>;
    }
    if (error) {
        return <div className="p-4 text-center text-red-500">{error}</div>;
    }

    return (
        <div className="p-4">
            <h2 className="text-2xl font-bold mb-4">Food Items</h2>
            {Object.keys(groupedFood).map(truckId => (
                <div key={truckId} className="mb-6">
                    <h3 className="text-xl font-semibold mb-2">{groupedFood[truckId].truckName}</h3>
                    <table className="w-full table-auto bg-gray-800 text-white">
                        <thead>
                            <tr>
                                <th className="px-4 py-2">Food Item ID</th>
                                <th className="px-4 py-2">SKU Code</th>
                                <th className="px-4 py-2">Name</th>
                                <th className="px-4 py-2">Price (€)</th>
                            </tr>
                        </thead>
                        <tbody>
                            {groupedFood[truckId].items.map(item => (
                                <tr key={item.foodItemId}>
                                    <td className="border px-4 py-2">{item.foodItemId}</td>
                                    <td className="border px-4 py-2">{item.skuCode}</td>
                                    <td className="border px-4 py-2">{item.name}</td>
                                    <td className="border px-4 py-2">{item.price.toFixed(2)}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            ))}
        </div>
    );
}

export default FoodPage;
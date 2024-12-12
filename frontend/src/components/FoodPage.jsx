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
        return <div className="p-4 text-center text-destructive">{error}</div>;
    }

    return (
        <div className="p-4">
            <h2 className="text-4xl font-bold mb-8 text-center">Food Trucks</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
                {Object.keys(groupedFood).map(truckId => (
                    <div key={truckId} className="bg-card p-6 rounded-lg shadow-md">
                        <h3 className="text-2xl font-semibold mb-4 text-primary">{groupedFood[truckId].truckName}</h3>
                        <ul>
                            {groupedFood[truckId].items.map(item => (
                                <li key={item.foodItemId} className="mb-2">
                                    <div className="flex justify-between">
                                        <span>{item.name}</span>
                                        <span className="text-muted-foreground">{item.price.toFixed(2)}€</span>
                                    </div>
                                </li>
                            ))}
                        </ul>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default FoodPage;
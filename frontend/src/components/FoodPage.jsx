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
            <div className="flex flex-col items-center space-y-8">
                {Object.keys(groupedFood).map(truckId => (
                    <div key={truckId} className="bg-card rounded-lg shadow-lg p-6 w-full max-w-md flex flex-col items-center justify-center transform hover:rotate-3 transition duration-300">
                        <h3 className="text-3xl font-bold text-center text-card-foreground mb-4">{groupedFood[truckId].truckName}</h3>
                        <ul className="space-y-2">
                            {groupedFood[truckId].items.map(item => (
                                <li key={item.foodItemId} className="flex justify-between w-full">
                                    <span>{item.name}</span>
                                    <span className="text-muted-foreground">{item.price.toFixed(2)}€</span>
                                </li>
                            ))}
                        </ul>
                        <div className="mt-4 text-center">
                            <span className="inline-block bg-primary rounded-full px-3 py-1 text-sm font-semibold text-primary-foreground mr-2">🍔</span>
                            <span className="inline-block bg-primary rounded-full px-3 py-1 text-sm font-semibold text-secondary-foreground mr-2">🍟</span>
                            <span className="inline-block bg-primary rounded-full px-3 py-1 text-sm font-semibold text-accent-foreground">🌮</span>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default FoodPage;
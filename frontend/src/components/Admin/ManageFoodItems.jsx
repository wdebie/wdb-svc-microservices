import React, { useEffect, useState } from "react";
import axios from "axios";

function ManageFoodItems() {
  const [foodItems, setFoodItems] = useState([]);
  const [foodTrucks, setFoodTrucks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [newFoodItem, setNewFoodItem] = useState({
    name: "",
    price: "",
    foodTruckId: "",
  });

  useEffect(() => {
    fetchFoodItems();
    fetchFoodTrucks();
  }, []);

  const fetchFoodItems = async () => {
    try {
      const response = await axios.get("https://api.fritfest.com/fooditems");
      setFoodItems(response.data);
      setLoading(false);
    } catch (err) {
      console.error(err);
      setError("Failed to fetch food items.");
      setLoading(false);
    }
  };

  const fetchFoodTrucks = async () => {
    try {
      const response = await axios.get("https://api.fritfest.com/foodtrucks");
      setFoodTrucks(response.data);
    } catch (err) {
      console.error(err);
      setError("Failed to fetch food trucks.");
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewFoodItem({ ...newFoodItem, [name]: value });
  };

  const generateSkuCode = () => {
    const timestamp = new Date().getTime();
    return `FI-${timestamp}`;
  };

  const handleAddFoodItem = async (e) => {
    e.preventDefault();
    try {
      await axios.post(
        "https://api.fritfest.com/fooditem",
        {
          ...newFoodItem,
          skuCode: generateSkuCode(),
          price: parseFloat(newFoodItem.price),
          foodTruckId: parseInt(newFoodItem.foodTruckId),
        },
        {
          headers: {
            "Content-Type": "application/json; charset=UTF-8",
            Authorization: `Bearer ${localStorage.getItem("jwt")}`,
          },
        }
      );
      setNewFoodItem({
        name: "",
        price: "",
        foodTruckId: "",
      });
      await fetchFoodItems();
    } catch (err) {
      console.error(err);
      setError("Failed to add food item.");
    }
  };

  const handleDeleteFoodItem = async (id) => {
    if (window.confirm("Are you sure you want to delete this food item?")) {
      try {
        await axios.delete(`https://api.fritfest.com/fooditem/${id}`, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("jwt")}`,
          },
        });
        await fetchFoodItems();
      } catch (err) {
        console.error(err);
        setError("Failed to delete food item.");
      }
    }
  };

  if (loading) {
    return <div className="p-4 max-w-4xl mx-auto text-center">Loading food items...</div>;
  }
  if (error) {
    return <div className="p-4 max-w-4xl mx-auto text-center text-red-500">{error}</div>;
  }

  return (
    <div className="p-4 max-w-4xl mx-auto">
      <h2 className="text-2xl font-bold mb-4">Manage Food Items</h2>

      <form
        onSubmit={handleAddFoodItem}
        className="mb-6 bg-gray-800 p-4 rounded-lg"
      >
        <h3 className="text-xl font-semibold mb-2">Add New Food Item</h3>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
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
          <select
            name="foodTruckId"
            value={newFoodItem.foodTruckId}
            onChange={handleInputChange}
            required
            className="p-2 rounded bg-gray-700 text-white"
          >
            <option value="" disabled>Select Food Truck</option>
            {foodTrucks.map((truck) => (
              <option key={truck.foodTruckId} value={truck.foodTruckId}>
                {truck.name}
              </option>
            ))}
          </select>
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
            <th className="px-4 py-2">Name</th>
            <th className="px-4 py-2">Price (€)</th>
            <th className="px-4 py-2">Food Truck</th>
            <th className="px-4 py-2">Actions</th>
          </tr>
        </thead>
        <tbody>
          {foodItems.map((item) => (
            <tr key={item.foodItemId}>
              <td className="border px-4 py-2">{item.foodItemId}</td>
              <td className="border px-4 py-2">{item.name}</td>
              <td className="border px-4 py-2">{item.price.toFixed(2)}</td>
              <td className="border px-4 py-2">{item.foodTruck ? item.foodTruck.name : "Unknown"}</td>
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

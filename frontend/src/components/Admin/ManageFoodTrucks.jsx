import React, { useEffect, useState } from "react";
import axios from "axios";

function ManageFoodTrucks() {
  const [foodTrucks, setFoodTrucks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [newTruck, setNewTruck] = useState({
    name: "",
    repName: "",
    repPhone: "",
  });

  useEffect(() => {
    fetchFoodTrucks();
  }, []);

  const fetchFoodTrucks = async () => {
    try {
      const response = await axios.get("https://api.fritfest.com/foodtrucks");
      setFoodTrucks(response.data);
      setLoading(false);
    } catch (err) {
      console.error(err);
      setError("Failed to fetch food trucks.");
      setLoading(false);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewTruck({ ...newTruck, [name]: value });
  };

  const generateSkuCode = () => {
    const timestamp = new Date().getTime();
    return `FT-${timestamp}`;
  };

  const handleAddTruck = async (e) => {
    e.preventDefault();
    try {
      await axios.post(
        "https://api.fritfest.com/foodtruck",
        {
          ...newTruck,
          skuCode: generateSkuCode(),
        },
        {
          headers: {
            "Content-Type": "application/json; charset=UTF-8",
            Authorization: `Bearer ${localStorage.getItem("jwt")}`,
          },
        }
      );
      setNewTruck({
        name: "",
        repName: "",
        repPhone: "",
      });
      await fetchFoodTrucks();
    } catch (err) {
      console.error(err);
      setError("Failed to add food truck.");
    }
  };

  const handleDeleteTruck = async (id) => {
    if (window.confirm("Are you sure you want to delete this food truck?")) {
      try {
        await axios.delete(`https://api.fritfest.com/foodtruck/${id}`, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("jwt")}`,
          },
        });
        await fetchFoodTrucks();
      } catch (err) {
        console.error(err);
        setError("Failed to delete food truck.");
      }
    }
  };

  if (loading) {
    return <div className="p-4 max-w-4xl mx-auto text-center">Loading food trucks...</div>;
  }
  if (error) {
    return <div className="p-4 max-w-4xl mx-auto text-center text-red-500">{error}</div>;
  }

  return (
    <div className="p-4 max-w-4xl mx-auto">
      <h2 className="text-2xl font-bold mb-4">Manage Food Trucks</h2>

      <form onSubmit={handleAddTruck} className="mb-6 bg-gray-800 p-4 rounded-lg">
        <h3 className="text-xl font-semibold mb-2">Add New Food Truck</h3>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <input
            type="text"
            name="name"
            value={newTruck.name}
            onChange={handleInputChange}
            placeholder="Name"
            required
            className="p-2 rounded bg-gray-700 text-white"
          />
          <input
            type="text"
            name="repName"
            value={newTruck.repName}
            onChange={handleInputChange}
            placeholder="Representative Name"
            required
            className="p-2 rounded bg-gray-700 text-white"
          />
          <input
            type="text"
            name="repPhone"
            value={newTruck.repPhone}
            onChange={handleInputChange}
            placeholder="Representative Phone"
            required
            className="p-2 rounded bg-gray-700 text-white"
          />
        </div>
        <button
          type="submit"
          className="mt-4 bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded-lg"
        >
          Add Food Truck
        </button>
      </form>

      <table className="w-full table-auto bg-gray-800 text-white">
        <thead>
          <tr>
            <th className="px-4 py-2">ID</th>
            <th className="px-4 py-2">Name</th>
            <th className="px-4 py-2">Representative Name</th>
            <th className="px-4 py-2">Representative Phone</th>
            <th className="px-4 py-2">Actions</th>
          </tr>
        </thead>
        <tbody>
          {foodTrucks.map((truck) => (
            <tr key={truck.foodTruckId}>
              <td className="border px-4 py-2">{truck.foodTruckId}</td>
              <td className="border px-4 py-2">{truck.name}</td>
              <td className="border px-4 py-2">{truck.repName}</td>
              <td className="border px-4 py-2">{truck.repPhone}</td>
              <td className="border px-4 py-2">
                <button
                  onClick={() => handleDeleteTruck(truck.foodTruckId)}
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

export default ManageFoodTrucks;

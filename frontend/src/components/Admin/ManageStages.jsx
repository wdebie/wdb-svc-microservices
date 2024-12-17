import React, { useEffect, useState } from "react";
import axios from "axios";

function ManageStages() {
  const [stages, setStages] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [newStage, setNewStage] = useState({
    name: "",
    capacity: "",
  });

  useEffect(() => {
    fetchStages();
  }, []);

  const fetchStages = async () => {
    try {
      const response = await axios.get("https://api.fritfest.com/stages");
      setStages(response.data);
      setLoading(false);
    } catch (err) {
      console.error(err);
      setError("Failed to fetch stages.");
      setLoading(false);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewStage({ ...newStage, [name]: value });
  };

  const handleAddStage = async (e) => {
    e.preventDefault();
    try {
      await axios.post(
        "https://api.fritfest.com/stage",
        {
          ...newStage,
          capacity: parseInt(newStage.capacity),
        },
        {
          headers: {
            "Content-Type": "application/json; charset=UTF-8",
            Authorization: `Bearer ${localStorage.getItem("jwt")}`,
          },
        }
      );
      setNewStage({
        name: "",
        capacity: "",
      });
      await fetchStages();
    } catch (err) {
      console.error(err);
      setError("Failed to add stage.");
    }
  };

  const handleDeleteStage = async (id) => {
    if (window.confirm("Are you sure you want to delete this stage?")) {
      try {
        await axios.delete(`https://api.fritfest.com/stage/${id}`, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("jwt")}`,
          },
        });
        await fetchStages();
      } catch (err) {
        console.error(err);
        setError("Failed to delete stage.");
      }
    }
  };

  if (loading) {
    return <div className="p-4 max-w-4xl mx-auto text-center">Loading stages...</div>;
  }
  if (error) {
    return <div className="p-4 max-w-4xl mx-auto text-center text-red-500">{error}</div>;
  }

  return (
    <div className="p-4 max-w-4xl mx-auto">
      <h2 className="text-2xl font-bold mb-4">Manage Stages</h2>

      <form
        onSubmit={handleAddStage}
        className="mb-6 bg-gray-800 p-4 rounded-lg"
      >
        <h3 className="text-xl font-semibold mb-2">Add New Stage</h3>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
          <input
            type="text"
            name="name"
            value={newStage.name}
            onChange={handleInputChange}
            placeholder="Stage Name"
            required
            className="p-2 rounded bg-gray-700 text-white"
          />
          <input
            type="number"
            name="capacity"
            value={newStage.capacity}
            onChange={handleInputChange}
            placeholder="Capacity"
            required
            className="p-2 rounded bg-gray-700 text-white"
          />
        </div>
        <button
          type="submit"
          className="mt-4 bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded-lg"
        >
          Add Stage
        </button>
      </form>

      <table className="w-full table-auto bg-gray-800 text-white">
        <thead>
          <tr>
            <th className="px-4 py-2">ID</th>
            <th className="px-4 py-2">Name</th>
            <th className="px-4 py-2">Capacity</th>
            <th className="px-4 py-2">Actions</th>
          </tr>
        </thead>
        <tbody>
          {stages.map((stage) => (
            <tr key={stage.stageId}>
              <td className="border px-4 py-2">{stage.stageId}</td>
              <td className="border px-4 py-2">{stage.name}</td>
              <td className="border px-4 py-2">{stage.capacity}</td>
              <td className="border px-4 py-2">
                <button
                  onClick={() => handleDeleteStage(stage.stageId)}
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

export default ManageStages;

import React, { useEffect, useState } from "react";
import axios from "axios";

function ManageSchedules() {
  const [schedules, setSchedules] = useState([]);
  const [artists, setArtists] = useState([]);
  const [stages, setStages] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [newSchedule, setNewSchedule] = useState({
    skuCode: "",
    startTime: "",
    endTime: "",
    artistSkuCode: "",
    stageId: "",
  });

  useEffect(() => {
    fetchSchedules();
    fetchArtists();
    fetchStages();
  }, []);

  const fetchSchedules = async () => {
    try {
      const response = await axios.get("https://api.fritfest.com/schedules");
      setSchedules(response.data);
      setLoading(false);
    } catch (err) {
      console.error(err);
      setError("Failed to fetch schedules.");
      setLoading(false);
    }
  };

  const fetchArtists = async () => {
    try {
      const response = await axios.get("https://api.fritfest.com/artists");
      setArtists(response.data);
    } catch (err) {
      console.error(err);
      setError("Failed to fetch artists.");
    }
  };

  const fetchStages = async () => {
    try {
      const response = await axios.get("https://api.fritfest.com/stages");
      setStages(response.data);
    } catch (err) {
      console.error(err);
      setError("Failed to fetch stages.");
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewSchedule({ ...newSchedule, [name]: value });
  };

  const generateSkuCode = () => {
    const timestamp = new Date().getTime();
    return `SCH-${timestamp}`;
  };

  const handleAddSchedule = async (e) => {
    e.preventDefault();
    try {
      await axios.post(
        "https://api.fritfest.com/schedule",
        {
          ...newSchedule,
          skuCode: generateSkuCode(),
          startTime: new Date(newSchedule.startTime).toISOString(),
          endTime: new Date(newSchedule.endTime).toISOString(),
          foodTruckId: null,
          stageId: parseInt(newSchedule.stageId),
        },
        {
          headers: {
            "Content-Type": "application/json; charset=UTF-8",
            Authorization: `Bearer ${localStorage.getItem("jwt")}`,
          },
        }
      );
      setNewSchedule({
        skuCode: "",
        startTime: "",
        endTime: "",
        artistSkuCode: "",
        stageId: "",
      });
      await fetchSchedules();
    } catch (err) {
      console.error(err);
      setError("Failed to add schedule.");
    }
  };

  const handleDeleteSchedule = async (id) => {
    if (window.confirm("Are you sure you want to delete this schedule?")) {
      try {
        await axios.delete(`https://api.fritfest.com/schedule/${id}`, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("jwt")}`,
          },
        });
        await fetchSchedules();
      } catch (err) {
        console.error(err);
        setError("Failed to delete schedule.");
      }
    }
  };

  if (loading) {
    return <div className="p-4 max-w-4xl mx-auto text-center">Loading schedules...</div>;
  }
  if (error) {
    return <div className="p-4 max-w-4xl mx-auto text-center text-red-500">{error}</div>;
  }

  return (
    <div className="p-4 max-w-4xl mx-auto">
      <h2 className="text-2xl font-bold mb-4">Manage Schedules</h2>

      <form
        onSubmit={handleAddSchedule}
        className="mb-6 bg-gray-800 p-4 rounded-lg"
      >
        <h3 className="text-xl font-semibold mb-2">Add New Schedule</h3>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
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
          <select
            name="artistSkuCode"
            value={newSchedule.artistSkuCode}
            onChange={handleInputChange}
            required
            className="p-2 rounded bg-gray-700 text-white"
          >
            <option value="" disabled>Select Artist</option>
            {artists.map((artist) => (
              <option key={artist.skuCode} value={artist.skuCode}>
                {artist.name}
              </option>
            ))}
          </select>
          <select
            name="stageId"
            value={newSchedule.stageId}
            onChange={handleInputChange}
            required
            className="p-2 rounded bg-gray-700 text-white"
          >
            <option value="" disabled>Select Stage</option>
            {stages.map((stage) => (
              <option key={stage.stageId} value={stage.stageId}>
                {stage.name}
              </option>
            ))}
          </select>
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
            <th className="px-4 py-2">Start Time</th>
            <th className="px-4 py-2">End Time</th>
            <th className="px-4 py-2">Artist</th>
            <th className="px-4 py-2">Stage</th>
            <th className="px-4 py-2">Actions</th>
          </tr>
        </thead>
        <tbody>
          {schedules.map((schedule) => {
            const artist = artists.find((artist) => artist.skuCode === schedule.artistSkuCode);
            const stage = stages.find((stage) => stage.stageId === schedule.stageId);
            return (
              <tr key={schedule.scheduleId}>
                <td className="border px-4 py-2">{schedule.scheduleId}</td>
                <td className="border px-4 py-2">
                  {new Date(schedule.startTime).toLocaleDateString()} {new Date(schedule.startTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
                </td>
                <td className="border px-4 py-2">
                  {new Date(schedule.endTime).toLocaleDateString()} {new Date(schedule.endTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
                </td>
                <td className="border px-4 py-2">{artist ? artist.name : "Unknown Artist"}</td>
                <td className="border px-4 py-2">{stage ? stage.name : "Unknown Stage"}</td>
                <td className="border px-4 py-2">
                  <button
                    onClick={() => handleDeleteSchedule(schedule.scheduleId)}
                    className="bg-red-600 hover:bg-red-700 text-white px-2 py-1 rounded"
                  >
                    Delete
                  </button>
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
}

export default ManageSchedules;

import React, { useEffect, useState } from "react";
import axios from "axios";

function ManageArtists() {
  const [artists, setArtists] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [newArtist, setNewArtist] = useState({
    name: "",
    repEmail: "",
    repPhone: "",
    bookingPrice: "",
  });

  useEffect(() => {
    fetchArtists();
  }, []);

  const fetchArtists = async () => {
    try {
      const response = await axios.get("https://api.fritfest.com/artists");
      if (Array.isArray(response.data)) {
        setArtists(response.data);
      } else {
        setArtists([]);
        setError("Unexpected response format: expected an array.");
      }
      setLoading(false);
    } catch (err) {
      console.error(err);
      setError("Failed to fetch artists.");
      setLoading(false);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewArtist({ ...newArtist, [name]: value });
  };

  const generateSkuCode = () => {
    const timestamp = new Date().getTime();
    return `ART-${timestamp}`;
  };

  const handleAddArtist = async (e) => {
    e.preventDefault();
    try {
      await axios.post(
        "https://api.fritfest.com/artist",
        {
          ...newArtist,
          skuCode: generateSkuCode(),
          bookingPrice: parseFloat(newArtist.bookingPrice),
        },
        {
          headers: {
            "Content-Type": "application/json; charset=UTF-8",
            Authorization: `Bearer ${localStorage.getItem("jwt")}`,
          },
        }
      );
      setNewArtist({
        name: "",
        repEmail: "",
        repPhone: "",
        bookingPrice: "",
      });
      await fetchArtists();
    } catch (err) {
      console.error(err);
      setError("Failed to add artist.");
    }
  };

  const handleDeleteArtist = async (skuCode) => {
    if (window.confirm("Are you sure you want to delete this artist?")) {
      try {
        await axios.delete(`https://api.fritfest.com/artist/${skuCode}`, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("jwt")}`,
          },
        });
        await fetchArtists();
      } catch (err) {
        console.error(err);
        setError("Failed to delete artist.");
      }
    }
  };

  if (loading) {
    return <div className="p-4 max-w-4xl mx-auto text-center">Loading artists...</div>;
  }
  if (error) {
    return <div className="p-4 max-w-4xl mx-auto text-center text-red-500">{error}</div>;
  }

  return (
    <div className="p-4 max-w-4xl mx-auto">
      <h2 className="text-2xl font-bold mb-4">Manage Artists</h2>

      <form onSubmit={handleAddArtist} className="mb-6 bg-gray-800 p-4 rounded-lg">
        <h3 className="text-xl font-semibold mb-2">Add New Artist</h3>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <input
            type="text"
            name="name"
            value={newArtist.name}
            onChange={handleInputChange}
            placeholder="Name"
            required
            className="p-2 rounded bg-gray-700 text-white"
          />
          <input
            type="text"
            name="repEmail"
            value={newArtist.repEmail}
            onChange={handleInputChange}
            placeholder="Representative Email"
            required
            className="p-2 rounded bg-gray-700 text-white"
          />
          <input
            type="text"
            name="repPhone"
            value={newArtist.repPhone}
            onChange={handleInputChange}
            placeholder="Representative Phone"
            required
            className="p-2 rounded bg-gray-700 text-white"
          />
          <input
            type="number"
            step="0.01"
            name="bookingPrice"
            value={newArtist.bookingPrice}
            onChange={handleInputChange}
            placeholder="Booking Price"
            required
            className="p-2 rounded bg-gray-700 text-white"
          />
        </div>
        <button
          type="submit"
          className="mt-4 bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded-lg"
        >
          Add Artist
        </button>
      </form>

      <table className="w-full table-auto bg-gray-800 text-white">
        <thead>
          <tr>
            <th className="px-4 py-2">Name</th>
            <th className="px-4 py-2">Actions</th>
          </tr>
        </thead>
        <tbody>
          {artists.map((artist) => (
            <tr key={artist.skuCode}>
              <td className="border px-4 py-2">{artist.name}</td>
              <td className="border px-4 py-2">
                <button
                  onClick={() => handleDeleteArtist(artist.skuCode)}
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

export default ManageArtists;

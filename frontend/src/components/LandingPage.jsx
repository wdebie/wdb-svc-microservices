import React from 'react';

function LandingPage() {
    return (
        <main className="flex-grow flex flex-col items-center justify-center bg-cover bg-center text-white p-4" style={{ backgroundImage: "url('/main-photo.webp')" }}>
            <h2 className="text-6xl font-bold mb-12 text-center">Welcome to Fritfest!</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8 max-w-6xl">
                <div className="bg-gray-800 bg-opacity-75 p-6 rounded-lg">
                    <h3 className="text-2xl font-bold mb-4">Delicious Food</h3>
                    <p>Explore a wide variety of cuisines from our amazing food trucks.</p>
                </div>
                <div className="bg-gray-800 bg-opacity-75 p-6 rounded-lg">
                    <h3 className="text-2xl font-bold mb-4">Live Music</h3>
                    <p>Enjoy fantastic performances from local and international artists.</p>
                </div>
                <div className="bg-gray-800 bg-opacity-75 p-6 rounded-lg">
                    <h3 className="text-2xl font-bold mb-4">Family Fun</h3>
                    <p>Bring your loved ones for a day filled with entertainment and activities.</p>
                </div>
                <div className="bg-gray-800 bg-opacity-75 p-6 rounded-lg">
                    <h3 className="text-2xl font-bold mb-4">Craft Beverages</h3>
                    <p>Quench your thirst with a selection of unique craft drinks and cocktails.</p>
                </div>
                <div className="bg-gray-800 bg-opacity-75 p-6 rounded-lg">
                    <h3 className="text-2xl font-bold mb-4">Local Vendors</h3>
                    <p>Shop for handmade crafts and products from talented local artisans.</p>
                </div>
                <div className="bg-gray-800 bg-opacity-75 p-6 rounded-lg">
                    <h3 className="text-2xl font-bold mb-4">VIP Experience</h3>
                    <p>Upgrade to VIP for exclusive perks, seating, and special tastings.</p>
                </div>
            </div>
        </main>
    );
}

export default LandingPage;
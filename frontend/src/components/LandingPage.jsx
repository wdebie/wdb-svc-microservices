import React from 'react';

function LandingPage() {
    return (
        <main className="flex-grow flex flex-col items-center justify-center bg-gray-900 text-white p-4">
            <h2 className="text-4xl font-bold mb-6 text-center">Welcome to Fritfest!</h2>
            <p className="text-lg text-center max-w-2xl mb-8">
                Fritfest is the premier food truck festival celebrating the best in street food. Join us for a day filled with delicious eats, live music, and a vibrant community spirit.
            </p>
            <img src="/src/assets/foodtruck.svg" alt="Food Truck" className="mt-8 w-64 h-64 object-contain" />
        </main>
    );
}

export default LandingPage; 
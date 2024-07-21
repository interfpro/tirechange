import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import Booking from './Booking';
import Management from './Management';
import './App.css';

function App() {

  return (
    <Router>
      <div>
        <nav>
          <ul>
            <li>
              <Link to="/">Broneerimine</Link>
            </li>
            <li>
              <Link to="/management">Haldus</Link>
            </li>
          </ul>
        </nav>

        <Routes>
          <Route path="/" element={<Booking />} />
          <Route path="/management" element={<Management />} />
        </Routes>
      </div>
    </Router>
  );
};

export default App;

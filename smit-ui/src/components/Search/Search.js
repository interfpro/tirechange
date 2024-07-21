import React, { useState, useEffect } from 'react';
import DatePicker from 'react-datepicker';
import './Search.css';
import 'react-datepicker/dist/react-datepicker.css';
import { Queries } from '../../util/Queries.js';
import { Helper } from '../../util/Helper.js';

const Search = ({ searchTimes }) => {
  const [workshop, setWorkshop] = useState('');
  const [from, setFrom] = useState(null);
  const [until, setUntil] = useState(null);
  const [workshops, setWorkshops] = useState([]);
  const [type, setType] = useState('');

  useEffect(() => {
    Queries.getWorkshops().then(workshops => {
      setWorkshops(workshops);
    });
  }, []);

  const search = (event) => {
    event.preventDefault();
    searchTimes(Helper.formatDate(from), Helper.formatDate(until), workshop, type);
  };

  const handleWorkshopChange = (event) => {
    setWorkshop(event.target.value);
  };

  const handleTypeChange = (event) => {
    setType(event.target.value);
  };

  return (
    <div className="search-container">
      <form onSubmit={search}>
        <div className="search-group">
          <label>
            <span style={{ color: "red", fontSize: 'small' }}>*</span> Periood:
          </label>
          <div className="date-picker-container">
            <DatePicker
              selected={from}
              onChange={setFrom}
              dateFormat="yyyy-MM-dd"
              required
            />
            <label>-</label>
            <DatePicker
              selected={until}
              onChange={setUntil}
              dateFormat="yyyy-MM-dd"
              required
            />
          </div>
        </div>
        <div className="search-group">
          <label> Töökoda: </label>
          <select onChange={handleWorkshopChange} value={workshop}>
            <option value="choose">Vali</option>
            {workshops.map((ws) => (
              <option key={ws.id} value={ws.name}>{ws.name}</option>
            ))}
          </select>
        </div>
        <div className="search-group">
          <label> Sõiduki tüüp: </label>
          <select onChange={handleTypeChange} value={type}>
            <option value="choose">Vali</option>
            <option value="car">Sõiduauto</option>
            <option value="truck">Veoauto</option>
          </select>
        </div>
        <div className="search-group">
          <button type="submit" className="submit-button">
            Otsi vabasid aegasid
          </button>
        </div>
      </form>
    </div>
  );
};

export default Search;

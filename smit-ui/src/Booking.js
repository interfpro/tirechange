import './Booking.css';
import React, { useState, useEffect } from 'react';
import { useQuery } from 'react-query';
import {Queries} from './util/Queries.js';
import {Helper} from './util/Helper.js';
import TimeTable from './components/TimeTable/TimeTable.js';
import Search from './components/Search/Search.js';
import BookingDialog from './components/BookingDialog/BookingDialog.js';

function Booking() {

  const [times, setTimes] = useState([]);
  const [open, setOpen] = useState(false);
  const [selectedRow, setSelectedRow] = useState(null);

  useEffect(() => {
    const currentDate = new Date();
    const nextMonthDate = new Date(currentDate);
    nextMonthDate.setMonth(nextMonthDate.getMonth() + 1);
    Queries.getAvailableTimes(Helper.formatDate(currentDate), Helper.formatDate(nextMonthDate)).then(sectors => {
      setTimes(sectors);
    });
  }, []);

  const columns = React.useMemo(
      () => [
        {
          Header: 'Töökoda',
          accessor: 'workshop',
        },
        {
          Header: 'Address',
          accessor: 'address',
        },
        {
          Header: 'Vaba aeg',
          accessor: 'time',
        },
        {
          Header: 'Sõiduki tüüp',
          accessor: 'type',
        },
        {
          Header: '',
          accessor: 'book',
          Cell: ({ row }) => (
          <button className="booking-button" onClick={() => handleBookClick(row)}>
            Broneeri
          </button>
      ),
        }
      ],
      []
  );

  const handleBookClick = (row) => {
    setSelectedRow(row.original);
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    setSelectedRow(null);
  };

  const searchTimes = (from, until, workshop, type) => {
    Queries.getAvailableTimes(from, until, workshop, type).then(times => {
      setTimes(times);
    });
  }


  return (
    <div className="Booking">
      <header className="Booking-header">
        <div>
          <h1>Aegade broneerimine</h1>
          <Search searchTimes={searchTimes} />
          <TimeTable columns={columns} data={times} />
        </div>
      </header>
      <BookingDialog handleClose={handleClose} setOpen={setOpen} setSelectedRow={setSelectedRow} selectedRow={selectedRow} open={open} />
    </div>
  );
}

export default Booking;

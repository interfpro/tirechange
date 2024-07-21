import React, { useState } from 'react';
import { useTable } from 'react-table';
import './BookingDialog.css';
import {Queries} from '../../util/Queries.js';
import { Dialog, DialogTitle, DialogContent, DialogActions, Button } from '@mui/material';


const BookingDialog = ({ handleClose, setOpen, setSelectedRow, selectedRow, open }) => {
  const [address, setAddress] = useState('');

  const handleSubmit = (event) => {
    Queries.postEntries(selectedRow.id, selectedRow.workshop, address);
    setOpen(false);
    setSelectedRow(null);
  };

  const handleAddressChange = (event) => {
    setAddress(event.target.value);
  };

  return (
    <Dialog open={open} onClose={handleClose}>
      <form onSubmit={handleSubmit}>
        <DialogTitle>Broneeri aeg</DialogTitle>
        <DialogContent>
          <strong>Sisesta kontaktaadress ja kinnita broneering</strong>
          {selectedRow && (
            <div>
              <p>Töökoda: {selectedRow.workshop}</p>
              <p>Aeg: {selectedRow.time}</p>
              <div>
                <textarea
                  id="textarea"
                  value={address.trim()}
                  onChange={handleAddressChange}
                  rows="3"
                  cols="60"
                  placeholder="Kontaktaadress..."
                  className="textarea-input"
                  required
                />
              </div>
            </div>
          )}
        </DialogContent>
        <DialogActions>
          <button
            type="button"
            onClick={handleClose}
            className="cancel-button"
          >
            Katkesta
          </button>
          <button
            type="submit"
            className="submit-button"
          >
            Kinnita
          </button>
        </DialogActions>
      </form>
    </Dialog>
  );
};

export default BookingDialog;

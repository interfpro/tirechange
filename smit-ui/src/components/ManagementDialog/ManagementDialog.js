import React, { useState, useEffect } from 'react';
import { useTable } from 'react-table';
import './ManagementDialog.css';
import {Queries} from '../../util/Queries.js';
import { Dialog, DialogTitle, DialogContent, DialogActions, Button } from '@mui/material';


const ManagementDialog = ({ handleClose, setOpen, setSelectedRow, selectedRow, open }) => {

  const [address, setAddress] = useState(selectedRow?.address || '');
  const [types, setTypes] = useState(selectedRow?.types || '');

  useEffect(() => {
    if (selectedRow) {
      handleAddressChange({ target: { value: selectedRow.address } });
      handleTypesChange({ target: { value: selectedRow.types } });
    }
  }, [selectedRow]);

  const handleSubmit = event => {
    Queries.editWorkshop(selectedRow.id, selectedRow.name, address, types);

    setOpen(false);
    setSelectedRow(null);
    window.location.reload();
  }

  const handleAddressChange = event => {
    setAddress(event.target.value);
  }

  const handleTypesChange = event => {
    setTypes(event.target.value);
  }

  return (
    <div>
      <Dialog open={open} onClose={handleClose} maxWidth="md" fullWidth={true}>
        <DialogTitle>Töökoja andmete muutmine</DialogTitle>
        <DialogContent>
          {selectedRow && (
            <div className="management-container">
              <h3><strong>{selectedRow.name}</strong></h3>
              <div className="management-group">
                <label> Aadress: </label>
                <input type="text" onChange={handleAddressChange} value={address.trim()} />
              </div>
              <div className="management-group">
                <label> Sõiduki tüübid: </label>
                <input type="text" onChange={handleTypesChange} value={types.trim()} />
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
          onClick={handleSubmit}
          className="submit-button"
        >
          Kinnita
        </button>
        </DialogActions>
      </Dialog>
    </div>
  );
};

export default ManagementDialog;

import './Management.css';
import React, { useState, useEffect } from 'react';
import ManagementTable from './components/ManagementTable/ManagementTable.js';
import ManagementDialog from './components/ManagementDialog/ManagementDialog.js';
import {Queries} from './util/Queries.js';

const Management = () => {

  const [workshops, setWorkshops] = useState([]);
  const [open, setOpen] = useState(false);
  const [selectedRow, setSelectedRow] = useState(null);

  const columns = React.useMemo(
      () => [
        {
          Header: 'Töökoda',
          accessor: 'name',
        },
        {
          Header: 'Address',
          accessor: 'address',
        },
        {
          Header: 'Sõiduki tüübid',
          accessor: 'types',
        },
        {
          Header: '',
          accessor: 'edit',
          Cell: ({ row }) => (
          <button onClick={() => handleManagementClick(row)}>
            Muuda
          </button>
      ),
        }
      ],
      []
  );

  const handleManagementClick = (row) => {
    console.log('Button clicked for row:', row);
    setSelectedRow(row.original);
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    setSelectedRow(null);
  };

  useEffect(() => {
    const data = Queries.getWorkshops().then(workshops => {
      setWorkshops(workshops);
    });
  }, []);

  return (
    <div className="Management">
      <header className="Management-header">
        <div>
          <h1>Töökodade haldus</h1>
          <ManagementTable columns={columns} data={workshops} />
        </div>
      </header>
      <ManagementDialog handleClose={handleClose} setOpen={setOpen} setSelectedRow={setSelectedRow} selectedRow={selectedRow} open={open} />
    </div>
  );
};

export default Management;

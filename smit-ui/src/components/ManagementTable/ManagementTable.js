import React from 'react';
import { useTable } from 'react-table';
import './ManagementTable.css';


const ManagementTable = ({ columns, data }) => {
  const { headerGroups, rows, prepareRow } = useTable({ columns, data });

  return (
    <table className="styled-table">
      <thead>
        {headerGroups.map((headerGroup) => {
          const headerGroupProps = headerGroup.getHeaderGroupProps();
          return (
            <tr key={headerGroupProps.key} >
              {headerGroup.headers.map((column) => {
                const columnProps = column.getHeaderProps();
                return (
                  <th key={columnProps.key} >
                    {column.render('Header')}
                  </th>
                );
              })}
            </tr>
          );
        })}
      </thead>
      <tbody>
        {rows.map((row) => {
          prepareRow(row);
          const rowProps = row.getRowProps();
          return (
            <tr key={rowProps.key} >
              {row.cells.map((cell) => {
                const cellProps = cell.getCellProps();
                return (
                  <td key={cellProps.key} >
                    {cell.render('Cell')}
                  </td>
                );
              })}
            </tr>
          );
        })}
      </tbody>
    </table>
  );
};

export default ManagementTable;

import React, { useState, useEffect } from 'react';
import EmployeeItem from './EmployeeItem';
import './EmployeeList.css';

const EmployeeList = () => {  
  const [employees, setEmployees] = useState([]);

  useEffect(() => {
    fetch('http://localhost:4001/employee')
      .then(response => response.json())
      .then(data => setEmployees(data))
      .catch(error => console.error('Error fetching employee data:', error));
  }, []);

  return (
    <div className="employee-list-container">
      <table className="employee-table">
        <thead>
          <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Projects</th>
          </tr>
        </thead>
        <tbody>
          {employees.map(employee => (
            <EmployeeItem key={employee.id} employee={employee} />
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default EmployeeList;

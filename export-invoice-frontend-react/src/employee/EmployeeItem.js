import React from 'react';

const EmployeeItem = ({ employee }) => {
  return (
    <tr className="employee-item">
      <td>{employee.name}</td>
      <td>{employee.email}</td>
      <td>{employee.phone}</td>
      <td>{employee.projects.join(', ')}</td>
    </tr>
  );
};

export default EmployeeItem;

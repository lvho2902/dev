import React from 'react';

const ProjectItem = ({ project }) => {
  return (
    <tr className="project-item">
      <td>{project.name}</td>
      <td>{project.startDate}</td>
      <td>{project.dueDate}</td>
      <td>{project.reference}</td>
      <td>{project.billable}</td>
      <td>{project.rate}</td>
      <td>{project.capexCode}</td>
      <td>{project.purchaseOrder}</td>
    </tr>
  );
};

export default ProjectItem;

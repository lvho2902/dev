import React, { useState, useEffect } from 'react';
import ProjectItem from '../project/ProjectItem';
import './ProjectList.css';

const ProjectList = () => {

    const [projects, setProjects] = useState([]);
  
    useEffect(() => {
      fetch('http://localhost:4001/project')
        .then(response => response.json())
        .then(data => setProjects(data))
        .catch(error => console.error('Error fetching project data:', error));
    }, []);
  
    return (
      <div className="project-list-container">  
        <table className="project-table">
          <thead>
            <tr>
              <th>Name</th>
              <th>Start Date</th>
              <th>Due Date</th>
              <th>Reference</th>
              <th>Billable</th>
              <th>Rate</th>
              <th>Capex Code</th>
              <th>Purchase Order</th>
            </tr>
          </thead>
          <tbody>
            {projects.map(project => (
              <ProjectItem key={project.id} project={project} />
            ))}
          </tbody>
        </table>
      </div>
    );
  };
  
  export default ProjectList;
  
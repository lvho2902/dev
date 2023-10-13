package com.lvho.invoice.mapper;

import com.lvho.invoice.dto.ProjectDto;
import com.lvho.invoice.entity.Project;

public class ProjectMapper {
    public static Project toProject(ProjectDto dto){
        Project project = new Project();
        project.id = dto.id;
        project.name = dto.name;
        project.desciprtion = dto.desciprtion;
        project.billable = dto.billable;
        project.startDate = dto.startDate;
        project.dueDate = dto.dueDate;
        project.capexCode = dto.capexCode;
        project.rate = dto.rate;
        project.reference = dto.reference;
        return project;
    }
}

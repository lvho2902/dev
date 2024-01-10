package com.lvho.invoice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lvho.invoice.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> 
{
    public Project findByName(String name);
}

package com.cms.carManagementSystem.service;

import com.cms.carManagementSystem.dto.DepartmentDTO;
import com.cms.carManagementSystem.dto.MinistryDTO;
import com.cms.carManagementSystem.entity.Department;
import com.cms.carManagementSystem.entity.Ministry;
import com.cms.carManagementSystem.repository.DepartmentRepo;
import com.cms.carManagementSystem.repository.MinistryRepo;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    private final DepartmentRepo departmentRepo;

    private final ModelMapper modelMapper;

    private final MinistryRepo ministryRepo;

    public DepartmentService(DepartmentRepo departmentRepo, ModelMapper modelMapper, MinistryRepo ministryRepo) {
        this.departmentRepo = departmentRepo;
        this.modelMapper = modelMapper;
        this.ministryRepo = ministryRepo;
    }

    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        Ministry ministry = ministryRepo.findById(departmentDTO.getMinistryId()).orElseThrow(
                () -> new EntityNotFoundException("Ministry not found with id " + departmentDTO.getMinistryId()));
        Department convertDTOToEntity = modelMapper.map(departmentDTO, Department.class);
        convertDTOToEntity.setMinistry(ministry);
        Department savedDepartment = departmentRepo.save(convertDTOToEntity);
        DepartmentDTO departmentDTO2 = modelMapper.map(savedDepartment, DepartmentDTO.class);
        departmentDTO2.setMinistryDTO(modelMapper.map(ministry, MinistryDTO.class));
        return departmentDTO2;
    }

    public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {
        Ministry ministry = ministryRepo.findById(departmentDTO.getMinistryId()).orElseThrow(
                () -> new EntityNotFoundException("Ministry not found with id " + departmentDTO.getMinistryId()));
        Department updateDepartment = departmentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with id " + id));
        modelMapper.map(departmentDTO, updateDepartment);
        updateDepartment.setMinistry(ministry);
        Department updatedDepartment = departmentRepo.save(updateDepartment);
        DepartmentDTO convertEntityToDTO = modelMapper.map(updatedDepartment, DepartmentDTO.class);
        convertEntityToDTO.setMinistryDTO(modelMapper.map(ministry, MinistryDTO.class));
        return convertEntityToDTO;
    }

    public List<DepartmentDTO> getAllDepartments() {
        List<Department> allDepartments = departmentRepo.findAll();
        return allDepartments.stream().map(department -> {
            DepartmentDTO convertEntityToDTO = modelMapper.map(department, DepartmentDTO.class);
            convertEntityToDTO.setMinistryDTO(modelMapper.map(department.getMinistry(), MinistryDTO.class));
            return convertEntityToDTO;
        }).collect(Collectors.toList());
    }

    public DepartmentDTO getDepartmentById(Long id) {
        Department departmentById = departmentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with id " + id));
        DepartmentDTO convertEntityToDTO = modelMapper.map(departmentById, DepartmentDTO.class);
        convertEntityToDTO.setMinistryDTO(modelMapper.map(departmentById.getMinistry(), MinistryDTO.class));
        return convertEntityToDTO;
    }

    public void deleteDepartment(Long id) {
        Department deleteDepartment = departmentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with id" + id));
        departmentRepo.delete(deleteDepartment);
    }
}

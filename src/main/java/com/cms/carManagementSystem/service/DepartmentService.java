package com.cms.carManagementSystem.service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.carManagementSystem.dto.DepartmentDTO;
import com.cms.carManagementSystem.dto.MinistryDTO;
import com.cms.carManagementSystem.entity.Department;
import com.cms.carManagementSystem.entity.Ministry;
import com.cms.carManagementSystem.repository.DepartmentRepo;
import com.cms.carManagementSystem.repository.MinistryRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
@Slf4j
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
        log.info("Creating a new department with details: {}", departmentDTO);

        Ministry ministry = ministryRepo.findById(departmentDTO.getMinistryId())
                .orElseThrow(() -> {
                    log.error("Ministry not found with id {}", departmentDTO.getMinistryId());
                    return new EntityNotFoundException("Ministry not found with id " + departmentDTO.getMinistryId());
                });

        Department convertDTOToEntity = modelMapper.map(departmentDTO, Department.class);
        convertDTOToEntity.setMinistry(ministry);

        log.info("Saving department: {}", convertDTOToEntity);
        Department savedDepartment = departmentRepo.save(convertDTOToEntity);

        DepartmentDTO departmentDTO2 = modelMapper.map(savedDepartment, DepartmentDTO.class);
        departmentDTO2.setMinistryDTO(modelMapper.map(ministry, MinistryDTO.class));

        log.info("Department created with ID: {}", savedDepartment.getDepartmentId());
        return departmentDTO2;
    }

    public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {
        log.info("Updating department with ID: {}", id);

        Ministry ministry = ministryRepo.findById(departmentDTO.getMinistryId())
                .orElseThrow(() -> {
                    log.error("Ministry not found with id {}", departmentDTO.getMinistryId());
                    return new EntityNotFoundException("Ministry not found with id " + departmentDTO.getMinistryId());
                });

        Department updateDepartment = departmentRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Department not found with id {}", id);
                    return new EntityNotFoundException("Department not found with id " + id);
                });

        log.info("Mapping DTO to entity: {}", departmentDTO);
        modelMapper.map(departmentDTO, updateDepartment);
        updateDepartment.setMinistry(ministry);

        log.info("Saving updated department: {}", updateDepartment);
        Department updatedDepartment = departmentRepo.save(updateDepartment);

        DepartmentDTO convertEntityToDTO = modelMapper.map(updatedDepartment, DepartmentDTO.class);
        convertEntityToDTO.setMinistryDTO(modelMapper.map(ministry, MinistryDTO.class));

        log.info("Department updated with ID: {}", updatedDepartment.getDepartmentId());
        return convertEntityToDTO;
    }

    public List<DepartmentDTO> getAllDepartments() {
        log.info("Fetching all departments");

        List<Department> allDepartments = departmentRepo.findAll();
        List<DepartmentDTO> departmentDTOList = allDepartments.stream().map(department -> {
            DepartmentDTO convertEntityToDTO = modelMapper.map(department, DepartmentDTO.class);
            convertEntityToDTO.setMinistryDTO(modelMapper.map(department.getMinistry(), MinistryDTO.class));
            return convertEntityToDTO;
        }).collect(Collectors.toList());

        log.info("Retrieved {} departments", departmentDTOList.size());
        return departmentDTOList;
    }

    public DepartmentDTO getDepartmentById(Long id) {
        log.info("Fetching department with ID: {}", id);

        Department departmentById = departmentRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Department not found with id {}", id);
                    return new EntityNotFoundException("Department not found with id " + id);
                });

        DepartmentDTO convertEntityToDTO = modelMapper.map(departmentById, DepartmentDTO.class);
        convertEntityToDTO.setMinistryDTO(modelMapper.map(departmentById.getMinistry(), MinistryDTO.class));

        log.info("Department found: {}", departmentById.getName());
        return convertEntityToDTO;
    }

    public void deleteDepartment(Long id) {
        log.info("Deleting department with ID: {}", id);

        Department deleteDepartment = departmentRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Department not found with id {}", id);
                    return new EntityNotFoundException("Department not found with id" + id);
                });

        departmentRepo.delete(deleteDepartment);
        log.info("Department with ID {} deleted", id);
    }
}

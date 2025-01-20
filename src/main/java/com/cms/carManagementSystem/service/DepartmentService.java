package com.cms.carManagementSystem.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.carManagementSystem.dto.DepartmentDTO;
import com.cms.carManagementSystem.dto.MinistryDTO;
import com.cms.carManagementSystem.entity.Department;
import com.cms.carManagementSystem.entity.Ministry;
import com.cms.carManagementSystem.exception.ResourceNotFoundException;
import com.cms.carManagementSystem.repository.DepartmentRepo;
import com.cms.carManagementSystem.repository.MinistryRepo;

import jakarta.persistence.EntityNotFoundException;

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
				() -> new EntityNotFoundException("Ministry not found with id " + departmentDTO.getMinistryDTO().getMinistryId()));
		Department createDepartment = modelMapper.map(departmentDTO, Department.class);
		createDepartment.setMinistry(ministry);
		System.out.println("Creating department");
		Department savedDepartment = departmentRepo.save(createDepartment);
		System.out.println("Department created: " + savedDepartment);
		DepartmentDTO departmentDTO2 = modelMapper.map(savedDepartment, DepartmentDTO.class);
		departmentDTO2.setMinistryDTO(modelMapper.map(ministry, MinistryDTO.class));
		return departmentDTO2;
	}

	public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {
		Ministry ministry = ministryRepo.findById(departmentDTO.getMinistryDTO().getMinistryId()).orElseThrow(
				() -> new EntityNotFoundException("Ministry not found with id " + departmentDTO.getMinistryDTO().getMinistryId()));
		Department updateDepartment = departmentRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
		updateDepartment.setMinistry(ministry);
		updateDepartment.setName(departmentDTO.getName());
		updateDepartment.setDescription(departmentDTO.getDescription());
		Department updatedDepartment = departmentRepo.save(updateDepartment);
		 
		return modelMapper.map(updatedDepartment, DepartmentDTO.class);
	}

	public List<DepartmentDTO> getAllDepartments() {
		List<Department> allDepartments = departmentRepo.findAll();
		return allDepartments.stream().map(department -> modelMapper.map(department, DepartmentDTO.class))
				.collect(Collectors.toList());
	}

	public DepartmentDTO getDepartmentById(Long id) {
		Department dedpartmentById = departmentRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
		return modelMapper.map(dedpartmentById, DepartmentDTO.class);
	}

	public void deleteDepartment(Long id) {

		Department deleteDepartment = departmentRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found with id" + id));
		departmentRepo.delete(deleteDepartment);

	}
}

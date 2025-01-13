package com.cms.carManagementSystem.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cms.carManagementSystem.dto.DepartmentDTO;
import com.cms.carManagementSystem.entity.Department;
import com.cms.carManagementSystem.exception.ResourceNotFoundException;
import com.cms.carManagementSystem.repository.DepartmentRepo;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentRepo departmentRepo;

	@Qualifier("modelMapper")
	@Autowired
	private ModelMapper modelMapper;

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

	public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
		Department createDepartment = modelMapper.map(departmentDTO, Department.class);
		createDepartment.setCreatedItUp(new Date());
		Department savedDepartment = departmentRepo.save(createDepartment);
		return modelMapper.map(savedDepartment, DepartmentDTO.class);
	}

	public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {
		Department updateDepartment = departmentRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
		updateDepartment.setName(departmentDTO.getName());
		updateDepartment.setDescription(departmentDTO.getDescription());
		updateDepartment.setUpdatedItUp(new Date());
		Department updatedDepartment = departmentRepo.save(updateDepartment);
		return modelMapper.map(updatedDepartment, DepartmentDTO.class);
	}

	public void deleteDepartment(Long id) {

		Department deleteDepartment = departmentRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found with id" + id));
		departmentRepo.delete(deleteDepartment);

	}
}

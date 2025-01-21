package com.cms.carManagementSystem.service;

import java.util.Date;
import java.util.List;
import java.util.jar.Attributes.Name;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cms.carManagementSystem.dto.MinistryDTO;
import com.cms.carManagementSystem.entity.Ministry;
import com.cms.carManagementSystem.exception.ResourceNotFoundException;
import com.cms.carManagementSystem.repository.MinistryRepo;

@Service
public class MinistryService {

	private final MinistryRepo ministryRepo;

	@Qualifier("modelMapper")
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	public MinistryService(MinistryRepo ministryRepo) {
		this.ministryRepo = ministryRepo;
	}

	public MinistryDTO createMinistry(MinistryDTO ministryDTO) {
		Ministry ministry = modelMapper.map(ministryDTO, Ministry.class);
		Ministry savedMinistry = ministryRepo.save(ministry);
		return modelMapper.map(savedMinistry, MinistryDTO.class);
	}

	public MinistryDTO updateMinistry(Long id, MinistryDTO ministryDTO) {
		Ministry updateMinistry = ministryRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Ministry not found with id " + id));
		updateMinistry.setName(ministryDTO.getName());
		updateMinistry.setAddress(ministryDTO.getAddress());
		updateMinistry.setDescription(ministryDTO.getDescription());
		Ministry updatedMinistry = ministryRepo.save(updateMinistry);
		return modelMapper.map(updatedMinistry, MinistryDTO.class);
	}

	public List<MinistryDTO> getAllMinistries() {
		List<Ministry> allMinistries = ministryRepo.findAll();
		return allMinistries.stream().map(ministry -> modelMapper.map(ministry, MinistryDTO.class))
				.collect(Collectors.toList());
	}

	public MinistryDTO getMinistryById(Long id) {
		Ministry ministry = ministryRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Ministry not found with id " + id));
		return modelMapper.map(ministry, MinistryDTO.class);
	}

	public void deleteMinistry(Long id) {
		Ministry delMinistry = ministryRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Ministry not found with id " + id));

		ministryRepo.delete(delMinistry);
	}
}

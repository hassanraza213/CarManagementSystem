package com.cms.carManagementSystem.service;

import com.cms.carManagementSystem.dto.MinistryDTO;
import com.cms.carManagementSystem.entity.Ministry;
import com.cms.carManagementSystem.repository.MinistryRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MinistryService {


    private final MinistryRepo ministryRepo;

    @Qualifier("modelMapper")
    @Autowired
    private ModelMapper modelMapper;

    public MinistryService(MinistryRepo ministryRepo) {
        this.ministryRepo = ministryRepo;
    }

    public MinistryDTO createMinistry(MinistryDTO ministryDTO) {
        log.info("Creating a new ministry with details: {}", ministryDTO);
        Ministry ministry = modelMapper.map(ministryDTO, Ministry.class);
        Ministry savedMinistry = ministryRepo.save(ministry);
        log.info("Ministry created successfully with ID: {}", savedMinistry.getMinistryId());
        return modelMapper.map(savedMinistry, MinistryDTO.class);
    }

    public MinistryDTO updateMinistry(Long id, MinistryDTO ministryDTO) {
        log.info("Updating ministry with ID: {}", id);
        Ministry updateMinistry = ministryRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Ministry not found with ID: {}", id);
                    return new EntityNotFoundException("Ministry not found with id " + id);
                });
        updateMinistry.setName(ministryDTO.getName());
        updateMinistry.setAddress(ministryDTO.getAddress());
        updateMinistry.setDescription(ministryDTO.getDescription());
        Ministry updatedMinistry = ministryRepo.save(updateMinistry);
        log.info("Ministry updated successfully with ID: {}", updatedMinistry.getMinistryId());
        return modelMapper.map(updatedMinistry, MinistryDTO.class);
    }

    public List<MinistryDTO> getAllMinistries() {
        log.info("Fetching all ministries");
        List<Ministry> allMinistries = ministryRepo.findAll();
        log.info("Total ministries fetched: {}", allMinistries.size());
        return allMinistries.stream().map(ministry -> modelMapper.map(ministry, MinistryDTO.class))
                .collect(Collectors.toList());
    }

    public MinistryDTO getMinistryById(Long id) {
        log.info("Fetching ministry with ID: {}", id);
        Ministry ministry = ministryRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Ministry not found with ID: {}", id);
                    return new EntityNotFoundException("Ministry not found with id " + id);
                });
        log.info("Ministry fetched successfully with ID: {}", ministry.getMinistryId());
        return modelMapper.map(ministry, MinistryDTO.class);
    }

    public void deleteMinistry(Long id) {
        log.info("Deleting ministry with ID: {}", id);
        Ministry delMinistry = ministryRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Ministry not found with ID: {}", id);
                    return new EntityNotFoundException("Ministry not found with id " + id);
                });
        ministryRepo.delete(delMinistry);
        log.info("Ministry deleted successfully with ID: {}", id);
    }
}

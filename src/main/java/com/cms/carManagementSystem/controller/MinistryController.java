package com.cms.carManagementSystem.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cms.carManagementSystem.dto.MinistryDTO;
import com.cms.carManagementSystem.entity.Ministry;
import com.cms.carManagementSystem.exception.ResourceNotFoundException;
import com.cms.carManagementSystem.service.MinistryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ministries")
public class MinistryController {

	@Autowired
	private final MinistryService ministryService;

	public MinistryController(MinistryService ministryService) {
		this.ministryService = ministryService;
	}

	@PostMapping
	public ResponseEntity<MinistryDTO> createMinistry(@Valid @RequestBody MinistryDTO ministryDTO) {
		MinistryDTO createMinistry = ministryService.createMinistry(ministryDTO);
		return new ResponseEntity<>(createMinistry, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<MinistryDTO> findMinistryById(@PathVariable Long id) {
		try {
			MinistryDTO ministryDTO = ministryService.getMinistryById(id);
			return ResponseEntity.ok(ministryDTO);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@GetMapping
	public List<MinistryDTO> getAllMinistries() {
		return ministryService.getAllMinistries();
	}

	@PutMapping("/{id}")
	public ResponseEntity<MinistryDTO> updateMinistry(@PathVariable Long id,
			@Valid @RequestBody MinistryDTO ministryDTO) {
		try {
		MinistryDTO updateMinistry = ministryService.updateMinistry(id, ministryDTO);
		return ResponseEntity.ok(updateMinistry);
		}
		catch(ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteMinistry(@PathVariable Long id) {
		try {
			ministryService.deleteMinistry(id);
			return ResponseEntity.ok("Ministry with ID " + id + " has been deleted.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ministry with ID " + id + " not found.");
		}
	}
}

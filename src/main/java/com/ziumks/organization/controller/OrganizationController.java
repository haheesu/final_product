package com.ziumks.organization.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ziumks.organization.mapper.OrganizationMapper;
import com.ziumks.organization.model.Organization;

@RestController
@RequestMapping("board")
public class OrganizationController {
	
	private OrganizationMapper mapper;

	public OrganizationController (OrganizationMapper mapper) {
		this.mapper = mapper;
	}
	
	@GetMapping("/org/{id}")
	public Organization getOrganization(@PathVariable("id") Long id) {
		return mapper.getOrganization(id);
	}
	
	@GetMapping("/org/all")
	public List<Organization> getOrganizationList(){
		return mapper.getOrganizationList();
	}
	
	@PutMapping("/org/{id}")
	public void putOrganization(@PathVariable("id") Long id, @RequestParam("name") String name) {
		mapper.insertOrganization(id, name);
	}
	
	@PostMapping("/org/{id}")
	public void postOrganization(@PathVariable("id") Long id, @RequestParam("name") String name) {
		mapper.updateOrganization(id, name);
	}
	
	@DeleteMapping("/org/{id}")
	public void deleteOrganization(@PathVariable("id") Long id) {
		mapper.deleteOrganization(id);
	}
} 

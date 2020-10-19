package com.ziumks.organization.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ziumks.organization.mapper.OrganizationMapper;
import com.ziumks.organization.model.Organization;

@Controller
@RequestMapping("/board")
public class HomeController {
	
	private OrganizationMapper mapper;

	public HomeController (OrganizationMapper mapper) {
		this.mapper = mapper;
	}
	
	@GetMapping
	public String index() {
		return "index";
	}
	
	
	@RequestMapping("/list")
	public String getOrganizationList(Model model, HttpServletRequest request){
		List<Organization> orgList = new ArrayList<>();
		orgList = mapper.getOrganizationList();
		model.addAttribute("org", orgList);
		return "board/list";
	}
	@RequestMapping("/detail/{id}")
	public String getOrganization(@PathVariable("id") Long id, Model model) {
		model.addAttribute("detail", mapper.getOrganization(id));
		return "board/detail";
	}
	
	
	@RequestMapping("/insert")
	public String putOrganizationForm(@ModelAttribute Organization org) {
		return "board/insert";
	}
	@RequestMapping("/insertProc")
	public String putOrganizationProc(@ModelAttribute Organization org, HttpServletRequest request) {
		mapper.insertOrganization(org.getId(), org.getName());
		return "forward:/board/list";
	}
	
	
	@RequestMapping("/update/{id}")
	public String postOrganization(@PathVariable("id") Long id, Model model) {
		model.addAttribute("detail", mapper.getOrganization(id));
		return "board/update";
	}
	@RequestMapping("/updateProc")
	public String postOrganizationProc(@ModelAttribute Organization org) {
		mapper.updateOrganization(org.getId(), org.getName());
		Long id = org.getId();
		String identity = Long.toString(id);
		return "redirect:/board/detail/" + identity;
	}
	
	
	@RequestMapping("/delete/{id}")
	public String deleteOrganization(@PathVariable("id") Long id) {
		mapper.deleteOrganization(id);
		return "redirect:/board/list";
	}
}

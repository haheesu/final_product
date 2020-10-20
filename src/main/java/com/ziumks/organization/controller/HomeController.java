package com.ziumks.organization.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.ziumks.organization.mapper.OrganizationMapper;
import com.ziumks.organization.model.FileVO;
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
	public String getOrganization(@PathVariable("id") int id, Model model) {
		model.addAttribute("detail", mapper.getOrganization(id));
		return "board/detail";
	}
	
	
	@RequestMapping("/insert")
	public String putOrganizationForm(@ModelAttribute("org") Organization organization, Model model) {
		return "board/insert";
	}
	@RequestMapping("/insertProc")
	public String putOrganizationProc(@ModelAttribute Organization org, @RequestPart MultipartFile files, 
			HttpServletRequest request) throws IllegalStateException, IOException, Exception{
		if (files.isEmpty()) {
			mapper.insertOrganization(org.getId(), org.getName());
		} else {
			String fileName = files.getOriginalFilename(); // 사용자 컴퓨터에 저장된 파일명 그대로
			String fileNameExtension = FilenameUtils.getExtension(fileName).toLowerCase(); // 확장자
			File destinationFile; // DB에 저장할 파일명
			String destinationFileName;
			String fileUrl = "C:\\Users\\USER\\Desktop\\workspace\\myhome\\files";
			
			do { // 우선 실행 후 // 고유명 생성
				destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + fileNameExtension;
				destinationFile = new File(fileUrl + destinationFileName); // 합쳐주기
			} while (destinationFile.exists());
			
			if (destinationFile.getParentFile() != null) {
				destinationFile.getParentFile().mkdirs(); // 디렉토리
			}
			files.transferTo(destinationFile);
			
			mapper.insertOrganization(org.getId(), org.getName());
			
			FileVO file = new FileVO();
			file.setId(org.getId());
			file.setFilename(destinationFileName);
			file.setFileoriginname(fileName);
			file.setFileurl(fileUrl);
			
			mapper.fileInsert(file);
		}
		return "forward:/board/list";
	}
	
	
	@RequestMapping("/update/{id}")
	public String postOrganization(@PathVariable("id") int id, Model model) {
		model.addAttribute("detail", mapper.getOrganization(id));
		return "board/update";
	}
	@RequestMapping("/updateProc")
	public String postOrganizationProc(@ModelAttribute Organization org) {
		mapper.updateOrganization(org.getId(), org.getName());
		int id = org.getId();
		String identity = Integer.toString(id);
		return "redirect:/board/detail/" + identity;
	}
	
	
	@RequestMapping("/delete/{id}")
	public String deleteOrganization(@PathVariable("id") int id) {
		mapper.deleteOrganization(id);
		return "redirect:/board/list";
	}
	
	
}

package com.ziumks.organization.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		if (mapper.fileDetail(id) == null) {
			return "board/detail";
		} else {
			model.addAttribute("file", mapper.fileDetail(id));
			return "board/detail";
		}
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
			
			destinationFile.getParentFile().mkdirs(); // 디렉토리
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
	
	@RequestMapping("/fileDown/{id}")
	private void fileDown(@PathVariable("id") int id, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException, Exception{
		
		request.setCharacterEncoding("UTF-8");
		FileVO fileVO = mapper.fileDetail(id);
		
		//파일 업로드 경로
		try {
			String fileUrl = fileVO.getFileurl();
			System.out.println(fileUrl);
			fileUrl += "/";
			String savePath = fileUrl;
			String fileName = fileVO.getFilename();
			
			//실제 내보낼 파일명
			String originFileName = fileVO.getFileoriginname();
			InputStream in = null;
			OutputStream os = null;
			File file = null;
			Boolean skip = false;
			String client = "";
			
			//파일을 읽어 스트림에 담기
			try {
				file = new File(savePath, fileName);
				in = new FileInputStream(file);
			} catch (FileNotFoundException fe) {
				// TODO: handle exception
				skip = true;
			}
			
			client = request.getHeader("User-Agent");
			
			//파일 다운로드 헤더 지정
			response.reset();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Description", "HTML Generated Data");
			
			if (!skip) {
				//IE
				if (client.indexOf("MSIE") != -1) {
					response.setHeader("Content-Disposition", "attachment; filename=\""
						+ java.net.URLEncoder.encode(originFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
				//IE 11 이상
				} else if (client.indexOf("Trident") != -1) {
					response.setHeader("Content-Disposition", "attachment; filename=\""
						+ java.net.URLEncoder.encode(originFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
				// 한글 파일명 처리
				} else {
					response.setHeader("Content-Disposition", "attachment; filename=\"" +
						new String(originFileName.getBytes("UTF-8"), "ISO8859_1") + "\"");
					response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
				}
				
				response.setHeader("Content-Length", "" + file.length());
				os = response.getOutputStream();
				byte b[] = new byte[(int) file.length()];
				int leng = 0;
				
				while((leng = in.read(b)) > 0) {
					os.write(b, 0, leng);
				}
			} else {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script> alert('파일을 찾을 수 없습니다.'); history.back(); </script>");
				out.flush();
			}
			
			in.close();
			os.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("ERROR : " + e.getStackTrace());
		}
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

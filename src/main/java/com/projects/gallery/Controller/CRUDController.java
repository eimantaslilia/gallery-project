package com.projects.gallery.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.projects.gallery.Entity.Image;
import com.projects.gallery.Service.CRUDService;

@RestController
public class CRUDController {
	
	@Autowired
	private CRUDService crudService;
	
	@GetMapping("/")
	public ModelAndView mainPage(
				Model model, 
				@RequestParam("page") Optional<Integer> page, 
				@RequestParam("size") Optional<Integer> size) {
		
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(18);
	
		Page<Image> imagePage = crudService.findAllByPage(PageRequest.of(currentPage - 1 , pageSize));
		
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("imagePage", imagePage);
		
		int totalPages = imagePage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
				.boxed().collect(Collectors.toList());
		mav.addObject("pageNumbers", pageNumbers);	
		}
		return mav;	      
	}
	
	@PostMapping("/uploadImage")
	public RedirectView uploadImage(@RequestParam("file")MultipartFile file, RedirectAttributes ra) throws IOException {
	
		if(file.isEmpty()) {
			ra.addFlashAttribute("message", "The file is empty!");
			return new RedirectView("/");
		}
		
		String imageName = StringUtils.cleanPath(file.getOriginalFilename());
		
		if(!crudService.checkFileName(imageName)) {
			ra.addFlashAttribute("message", "This image format is not supported");
			return new RedirectView ("/");
		}

		crudService.uploadFile(file, imageName);
		
		ra.addFlashAttribute("message", "The image has been uploaded");
		return new RedirectView("/");
	}

	@GetMapping("/deleteImage")
	public RedirectView deleteImage(@RequestParam("imageId")int imageId, RedirectAttributes ra) {
		
		crudService.deleteById(imageId);
		ra.addFlashAttribute("message", "The image has been deleted");
		return new RedirectView("/");
	}

}


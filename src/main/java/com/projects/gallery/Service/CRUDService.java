package com.projects.gallery.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.projects.gallery.Entity.Image;
import com.projects.gallery.Repository.ImageRepository;



@Service
public class CRUDService {

	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private AmazonS3 s3client;
	
	@Value("${aws.s3.bucket}")
	private String bucketName;
	
	@Value("${aws.endpointUrl}")
	private String endpointUrl;
	
	
	public void uploadFile(MultipartFile file, String name) throws IOException {
	    
		String fileUrl ="";
		
	    try {
	      
	      File convertedFile = convertMultipartToFile(file);
	      
	      fileUrl = endpointUrl + "/" + bucketName + "/" + name;
	      
	          s3client.putObject(new PutObjectRequest(bucketName, name, convertedFile).withCannedAcl(CannedAccessControlList.PublicRead));
	      convertedFile.delete();
	          
	      Image image = new Image(name, fileUrl);
	      imageRepository.save(image);
	      
	    } catch (Exception ex) {
	    	ex.printStackTrace();
	    }	    
	  }

	public Page<Image> findAllByPage(Pageable pageable){
		Page<Image> imagePage = imageRepository.findAllByOrderByIdDesc(pageable);
		return imagePage;
	}	

	public void deleteById(int theId) {
		
		Optional<Image> image = imageRepository.findById(theId);
		String imageName = image.get().getImageName();
		
		s3client.deleteObject(bucketName, imageName);
		imageRepository.deleteById(theId);
	}
	
    public boolean checkFileName(String name) {
    	
    	List<String> formats = Arrays.asList(".webp", ".jpg", ".jpeg", ".png", ".bmp");

    	for (String format : formats){
    	if(name.toLowerCase().contains(format)) { 
        return true;
    		} 
    	}
    	return false;
	}
	
	private File convertMultipartToFile(MultipartFile file) throws IOException{
		
		File convertedFile = new File(file.getOriginalFilename());
		FileOutputStream fileOutputStream = new FileOutputStream(convertedFile);
		fileOutputStream.write(file.getBytes());
		fileOutputStream.close();
		
		return convertedFile;
	}
}

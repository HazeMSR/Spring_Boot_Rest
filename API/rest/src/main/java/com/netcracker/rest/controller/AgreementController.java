package com.netcracker.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.netcracker.rest.model.Agreement;
import com.netcracker.rest.service.AgreementService;

@RestController
public class AgreementController {
	
	@Autowired
	AgreementService agreementService;
	
	   @RequestMapping(value = "/agreement", method = RequestMethod.POST)
	   public ResponseEntity<Object> createProduct(@RequestBody Agreement agree) throws IOException {
	      String filename = agreementService.save(agree);
	      File file = new File(filename);
	      InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
	      HttpHeaders headers = new HttpHeaders();
	         
	      headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
	      headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
	      headers.add("Pragma", "no-cache");
	      headers.add("Expires", "0");
	         
	      ResponseEntity<Object> 
	      responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(
	         MediaType.parseMediaType("application/txt")).body(resource);
	         
	      return responseEntity;
	   }
	   
	   @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)	   
	   public Agreement fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
		   File convertFile = new File("/var/tmp/"+file.getOriginalFilename());
	    	if (convertFile.createNewFile()) {
	    		System.out.println("File created: " + convertFile.getName());
			} else {
				System.out.println("File already exists.");
			}
		   return agreementService.read(convertFile.getName());
	   }
}

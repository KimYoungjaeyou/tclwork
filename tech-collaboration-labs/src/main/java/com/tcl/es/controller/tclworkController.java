package com.tcl.es.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.tcl.es.service.tclworkService;

@RestController
@RequestMapping("/api/*")
public class tclworkController {
	
	@Autowired
	tclworkService elasticApi;
		
		@GetMapping("/test")
		public String analysisapi(@RequestParam String analyzer,
				@RequestParam String text ) {
			
			elasticApi.esAnalysis(analyzer,text);
						
				
			return elasticApi.esAnalysis(analyzer,text);
		}
		   
		   

}

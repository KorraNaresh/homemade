package com.homemadewonder.www.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.homemadewonder.www.entity.Enqiures;
import com.homemadewonder.www.service.EnqiuresService;

@RestController
@RequestMapping("/api/enqiures")
public class EnqiuresController {

	@Autowired
	private EnqiuresService enqiuresService;

	@GetMapping("/getallequires")
	public List<Enqiures> getAllEnqiures() {
		return enqiuresService.getAllEnqiures();
	}

	@GetMapping("/{eid}")
	public ResponseEntity<Enqiures> getEnqiuresById(@PathVariable Long eid) {
		Enqiures enquiry = enqiuresService.getEnqiuresById(eid);
		if (enquiry != null) {
			return ResponseEntity.ok(enquiry);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	 @PostMapping("/save")
	    public ResponseEntity<Enqiures> createEnqiures(
	            @RequestParam String name,
	            @RequestParam String email,
	            @RequestParam String phoneNumber,
	            @RequestParam(name = "message") String message) {

	        Enqiures enquiry = new Enqiures();
	        enquiry.setName(name);
	        enquiry.setEmail(email);
	        enquiry.setPhoneNumber(phoneNumber);
	        enquiry.setMessage(message);

	        // Assuming enqiuresService is an instance of EnqiuresService
	        Enqiures createdEnquiry = enqiuresService.create(enquiry);
	        return new ResponseEntity<>(createdEnquiry, HttpStatus.CREATED);
	    }

	@PutMapping("/update/{eid}")
	public ResponseEntity<Enqiures> updateEnqiures(@PathVariable Long eid, @RequestParam String name,
			@RequestParam String email, @RequestParam String phoneNumber,
			@RequestParam(name = "message") String message) {

		Enqiures updatedEnquiry = new Enqiures();

		updatedEnquiry.setName(name);
		updatedEnquiry.setEmail(email);
		updatedEnquiry.setPhoneNumber(phoneNumber);
		updatedEnquiry.setMessage(message);

		updatedEnquiry = enqiuresService.updateEntity(eid, updatedEnquiry);

		if (updatedEnquiry != null) {
			return ResponseEntity.ok(updatedEnquiry);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/delete/{eid}")
	public ResponseEntity<Void> deleteEnqiures(@PathVariable Long eid) {
		enqiuresService.deleteEntity(eid);
		return ResponseEntity.noContent().build();
	}

}

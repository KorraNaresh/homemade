package com.homemadewonder.www.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.multipart.MultipartFile;

import com.homemadewonder.www.customeropeartions.Rolesenum;
import com.homemadewonder.www.entity.Admin;
import com.homemadewonder.www.excpetion.AdminNotFoundException;
import com.homemadewonder.www.service.AdminService;


@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	
	Logger logger =LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private AdminService adminService;

	@PostMapping("/save")
	public ResponseEntity<Admin> saveAdmin(

			@RequestParam String adminName, @RequestParam long contact, @RequestParam String address,
			@RequestParam String password, @RequestParam(required = false) String websiteUrl,
			@RequestParam String privacyAndPolices, @RequestParam String aboutUs, @RequestParam Rolesenum role,
			@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {

		Admin admin = new Admin();

		admin.setAdminName(adminName);
		admin.setContact(contact);
		admin.setAddress(address);
		admin.setPassword(password);
		admin.setWebsiteUrl(websiteUrl);
		admin.setRole(role);
		admin.setPrivacyAndPolices(privacyAndPolices);
		admin.setAboutUs(aboutUs);

		if (file != null && !file.isEmpty()) {

			byte[] fileBytes = file.getBytes();
			admin.setFile(fileBytes);
		}

		Admin responseAdmin = adminService.createAdmin(admin);

		return new ResponseEntity<>(responseAdmin, HttpStatus.OK);
	}
	
	
	
	@GetMapping("/getAdmin/{adminId}")
	public ResponseEntity<Admin> getAdminById(@PathVariable Long adminId) {
	    Admin admin = adminService.getAdminById(adminId);
	    return ResponseEntity.ok(admin);
	}


    @GetMapping("/getAllAdmins")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    @PutMapping("/updateAdmin/{adminId}")
    public ResponseEntity<Admin> updateAdminById(@PathVariable Long adminId,
            @RequestParam String adminName,
            @RequestParam long contact,
            @RequestParam String address,
            @RequestParam String password,
            @RequestParam(required = false)String websiteUrl,
            @RequestParam String privacyAndPolicies,
            @RequestParam String aboutUs,
            @RequestParam(required = false) MultipartFile file,
            @RequestParam Rolesenum role) {
        try {
            Admin updatedAdmin = adminService.updateAdmin(adminId, adminName, contact, address, password, websiteUrl, privacyAndPolicies, aboutUs, role, file);
            return ResponseEntity.ok(updatedAdmin);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/deleteAdmin/{adminId}")
    public ResponseEntity<String> deleteAdminById(@PathVariable Long adminId) {
        try {
            adminService.deleteAdminByAdminId(adminId);
            return ResponseEntity.ok("Admin with ID " + adminId + " deleted successfully.");
        } catch (AdminNotFoundException e) {
          
            return ResponseEntity.notFound().build();
        } 
    }
    @PostMapping("/login")
    public ResponseEntity<String> loginAdmin(
            @RequestParam String adminName, @RequestParam String password) {
    	System.out.print("hello");
        try {
          
            boolean isAuthenticated = adminService.authenticateAdmin(adminName, password);
            
            if (isAuthenticated) {
              
                return ResponseEntity.ok("Login successful");
            } else {
                
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
            }
        } catch (AdminNotFoundException e) {
           
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found");
        }
    }

}

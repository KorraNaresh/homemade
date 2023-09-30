package com.homemadewonder.www.serviceimpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.homemadewonder.www.customeropeartions.Rolesenum;
import com.homemadewonder.www.entity.Admin;
import com.homemadewonder.www.excpetion.AdminNotFoundException;
import com.homemadewonder.www.repository.AdminReposiotry;
import com.homemadewonder.www.service.AdminService;


@Service
public class Adminserviceimpl implements AdminService {

	@Autowired
	private AdminReposiotry adminReposiotry;

	@Override
	public Admin createAdmin(Admin admin)		 {

		return adminReposiotry.save(admin);
	}

	@Override
	public Admin getAdminById(Long adminId) {
	    return adminReposiotry.findById(adminId)
	            .orElseThrow(() -> new AdminNotFoundException("Admin with ID " + adminId + " not found"));
	}


	@Override
	public List<Admin> getAllAdmins() {

		return adminReposiotry.findAll();
	}


    @Override
    public void deleteAdminByAdminId(Long adminId) {
    	adminReposiotry.deleteById(adminId);
    }

    @Override
    public Admin updateAdmin(Long adminId, String adminName, long contact, String address, String password, String websiteUrl,
                            String privacyAndPolices, String aboutUs, Rolesenum role, MultipartFile file) throws IOException {
        Optional<Admin> adminOptional = adminReposiotry.findById(adminId);

        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
         
            admin.setAdminName(adminName);
            admin.setContact(contact);
            admin.setAddress(address);
            admin.setPassword(password);
            admin.setWebsiteUrl(websiteUrl);
            admin.setPrivacyAndPolices(privacyAndPolices);
            admin.setAboutUs(aboutUs);
            admin.setRole(role);

 

            return adminReposiotry.save(admin);
        } else {
            throw new AdminNotFoundException("Admin with ID " + adminId + " not found");
        }
    }
    @Override
    public boolean authenticateAdmin(String adminName, String password) {
        try {
            List<Admin> adminList = adminReposiotry.findByAdminName(adminName);
            if (!adminList.isEmpty()) {
                Admin admin = adminList.get(0);
                if (admin.getPassword().equals(password)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; 
        }
        return false; 
    }
}

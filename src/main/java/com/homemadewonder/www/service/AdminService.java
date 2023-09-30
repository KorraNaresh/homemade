package com.homemadewonder.www.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.homemadewonder.www.customeropeartions.Rolesenum;
import com.homemadewonder.www.entity.Admin;

public interface AdminService {

	Admin createAdmin(Admin admin)	;

    Admin getAdminById(Long adminId);

    List<Admin> getAllAdmins();

    void deleteAdminByAdminId(Long adminId);

    Admin updateAdmin(Long adminId, String adminName, long contact, String address, String password, String websiteUrl,
                     String privacyAndPolices, String aboutUs, Rolesenum role, MultipartFile file) throws IOException;
    boolean authenticateAdmin(String adminName, String password);
}



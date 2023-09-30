package com.homemadewonder.www.AdminControllerTest;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import com.homemadewonder.www.controller.AdminController;
import com.homemadewonder.www.customeropeartions.Rolesenum;
import com.homemadewonder.www.entity.Admin;
import com.homemadewonder.www.service.AdminService;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveAdmin() throws Exception {

        Admin admin = new Admin();
        admin.setAdminName("John Doe");
        admin.setContact(1234567890L);
        admin.setAddress("123 Main Street");
        admin.setPassword("password123");
        admin.setWebsiteUrl("https://example.com");
        admin.setPrivacyAndPolices("Privacy Policy Text");
        admin.setAboutUs("About Us Text");
        admin.setRole(Rolesenum.ROLE_ADMIN);

        Mockito.when(adminService.createAdmin(Mockito.any(Admin.class))).thenReturn(admin);


        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "image data".getBytes());
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/admin/save")
                .file(file)
                .param("adminName", "John Doe")
                .param("contact", "1234567890")
                .param("address", "123 Main Street")
                .param("password", "password123")
                .param("websiteUrl", "https://example.com")
                .param("privacyAndPolices", "Privacy Policy Text")
                .param("aboutUs", "About Us Text")
                .param("role", "ROLE_ADMIN") 
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.adminName").value("John Doe"));
    }
    
    
    @Test
    public void testGetAdminById() throws Exception {

        Admin admin = new Admin();
        admin.setAdminId(1L);
        admin.setAdminName("John Doe");
        
        when(adminService.getAdminById(1L)).thenReturn(admin);


        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/getAdmin/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.adminId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.adminName").value("John Doe"));
    }

    @Test
    public void testGetAllAdmins() throws Exception {

        List<Admin> admins = new ArrayList<>();
        
        Admin admin1 = new Admin();
        admin1.setAdminId(1L);
        admin1.setAdminName("John Doe");
        admin1.setContact(1234567890L);
        admin1.setAddress("123 Main Street");
        admin1.setPassword("password123");
        admin1.setWebsiteUrl("https://example.com");
        admin1.setPrivacyAndPolices("Privacy Policy Text");
        admin1.setAboutUs("About Us Text");
        admin1.setRole(Rolesenum.ROLE_ADMIN); 

        Admin admin2 = new Admin();
        admin2.setAdminId(2L);
        admin2.setAdminName("Jane Smith");
        admin2.setContact(9876543210L);
        admin2.setAddress("456 Elm Street");
        admin2.setPassword("password456");
        admin2.setWebsiteUrl("https://example2.com");
        admin2.setPrivacyAndPolices("Privacy Policy Text 2");
        admin2.setAboutUs("About Us Text 2");
        admin2.setRole(Rolesenum.ROLE_ADMIN);
   

        admins.add(admin1);
        admins.add(admin2);


        Mockito.when(adminService.getAllAdmins()).thenReturn(admins);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/getAllAdmins"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].adminId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].adminName").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].contact").value(1234567890))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address").value("123 Main Street"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].password").value("password123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].websiteUrl").value("https://example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].privacyAndPolices").value("Privacy Policy Text"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].aboutUs").value("About Us Text"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].role").value("ROLE_ADMIN"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].adminId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].adminName").value("Jane Smith"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].contact").value(9876543210L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].address").value("456 Elm Street"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].password").value("password456"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].websiteUrl").value("https://example2.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].privacyAndPolices").value("Privacy Policy Text 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].aboutUs").value("About Us Text 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].role").value("ROLE_ADMIN"));
    }
    
    
    @Test
    public void testUpdateAdminById() throws Exception {

        Admin admin = new Admin();
        admin.setAdminId(1L); 
        admin.setAdminName("John Doe");
        admin.setContact(1234567890L);
        admin.setAddress("123 Main Street");
        admin.setPassword("password123");
        admin.setWebsiteUrl("https://example.com");
        admin.setPrivacyAndPolices("Privacy Policy Text");
        admin.setAboutUs("About Us Text");
        admin.setRole(Rolesenum.ROLE_ADMIN); 

 
        Mockito.when(adminService.updateAdmin(anyLong(), anyString(), anyLong(), anyString(), anyString(), nullable(String.class), anyString(), anyString(), any(Rolesenum.class), nullable(MultipartFile.class))).thenReturn(admin);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/updateAdmin/1")
                .param("adminName", "John Doe")
                .param("contact", "1234567890")
                .param("address", "123 Main Street")
                .param("password", "password123")
                .param("websiteUrl", "https://example.com")
                .param("privacyAndPolicies", "Privacy Policy Text")
                .param("aboutUs", "About Us Text")
                .param("role", "ROLE_ADMIN") 
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.adminId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.adminName").value("John Doe"));
    }

    @Test
    public void testDeleteAdminById() throws Exception {

        Mockito.doNothing().when(adminService).deleteAdminByAdminId(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/admin/deleteAdmin/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Admin with ID 1 deleted successfully."));
    }

}
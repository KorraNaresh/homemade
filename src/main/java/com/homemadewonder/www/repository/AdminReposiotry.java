package com.homemadewonder.www.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homemadewonder.www.entity.Admin;

public interface AdminReposiotry extends JpaRepository<Admin, Long> {
	List<Admin> findByAdminName(String adminName);
}

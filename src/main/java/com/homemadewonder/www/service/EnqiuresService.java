package com.homemadewonder.www.service;

import java.util.List;

import com.homemadewonder.www.entity.Enqiures;

public interface EnqiuresService {

	List<Enqiures> getAllEnqiures();

	Enqiures getEnqiuresById(Long eid);

	Enqiures create(Enqiures entity);

	Enqiures updateEntity(Long eid, Enqiures entity);

	void deleteEntity(Long eid);

}

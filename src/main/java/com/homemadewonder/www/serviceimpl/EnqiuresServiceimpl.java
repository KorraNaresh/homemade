package com.homemadewonder.www.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homemadewonder.www.entity.Enqiures;
import com.homemadewonder.www.repository.EnqiuresRepository;
import com.homemadewonder.www.service.EnqiuresService;

@Service
public class EnqiuresServiceimpl implements EnqiuresService {

	@Autowired
	private EnqiuresRepository enqiuresRepository;

	@Override
	public List<Enqiures> getAllEnqiures() {

		return enqiuresRepository.findAll();
	}

	@Override
	public Enqiures getEnqiuresById(Long eid) {

		return enqiuresRepository.findById(eid).orElse(null);
	}

	@Override
	public Enqiures create(Enqiures enqiuery) {

		return enqiuresRepository.save(enqiuery);
	}

	@Override
	public Enqiures updateEntity(Long eid, Enqiures entity) {
		if (enqiuresRepository.existsById(eid)) {
			entity.seteId(eid);
			return enqiuresRepository.save(entity);
		}

		return null;
	}

	@Override
	public void deleteEntity(Long eid) {

		enqiuresRepository.deleteById(eid);
	}

}

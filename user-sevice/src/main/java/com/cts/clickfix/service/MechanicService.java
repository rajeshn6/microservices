package com.cts.clickfix.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.cts.clickfix.entity.Mechanic;
import com.cts.clickfix.exception.MechanicIdIsNotFoundException;
import com.cts.clickfix.exception.MechanicUpdateFailureException;
import com.cts.clickfix.model.MechanicDto;
import com.cts.clickfix.repository.MechanicRepository;

@Service
public class MechanicService { //Source code under test or SUT
	@Autowired
	private MechanicRepository mechanicRepository;

	@Autowired
	private ModelMapper modelMapper;

	public MechanicDto addMechanic(MechanicDto mechanicDto) {  //MUT
		Mechanic mechanic = modelMapper.map(mechanicDto, Mechanic.class);
		Mechanic resultEntity = mechanicRepository.save(mechanic);
		return modelMapper.map(resultEntity, MechanicDto.class);
	}
	public MechanicDto getMechanicById(int mechanicId) { 
		MechanicDto mechanicDto = null;
		Optional<Mechanic> optionalMechanic = mechanicRepository.findById(mechanicId);
		if (optionalMechanic.isEmpty()) {
			throw new MechanicIdIsNotFoundException("mechanic id: "+mechanicId+" is not there in the db");
		} else {
			
			mechanicDto = modelMapper.map(optionalMechanic.get(), MechanicDto.class);
		}
		return mechanicDto;

	}

	public String deleteMechanicById(@PathVariable int mechanicId) {
		String response = null;
		boolean result = mechanicRepository.existsById(mechanicId);
		if (result) {
			mechanicRepository.deleteById(mechanicId);
			response = "mechanic with id: " + mechanicId + " is deleted sucessfully";
		} else {
			response = "delete is failed due to none existence of mechanic id: " + mechanicId;
		}
		return response;
	}

	public MechanicDto updateMechanic(MechanicDto mechanicDto) {
		MechanicDto mechanicDto2=null;
		boolean result=mechanicRepository.existsById(mechanicDto.getMechanicId());
		if(result) {
		    Mechanic m	=mechanicRepository.save(modelMapper.map(mechanicDto,Mechanic.class));
			mechanicDto2=modelMapper.map(m,MechanicDto.class);
		}else {
			throw new MechanicUpdateFailureException("mechanic id is not exists");
		}
		return mechanicDto2;	
	}

}













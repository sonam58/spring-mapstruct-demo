package com.mapstruct.springmapstructdemo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.mapstruct.springmapstructdemo.entity.Doctor;
import com.mapstruct.springmapstructdemo.model.DoctorDTO;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

//	DoctorMapper INSTANCE = Mappers.getMapper(DoctorMapper.class);
	DoctorDTO modelToDTO(Doctor doctor);

	Doctor dtoToModel(DoctorDTO doctorDTO);

	void updateDoctor(DoctorDTO doctorDTO, @MappingTarget Doctor doctor);
	
	List<DoctorDTO> modelToDtoList(List<Doctor> doctor);
}

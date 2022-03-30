package com.mapstruct.springmapstructdemo.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mapstruct.springmapstructdemo.entity.Doctor;
import com.mapstruct.springmapstructdemo.exception.EmptyInputException;
import com.mapstruct.springmapstructdemo.mapper.DoctorMapper;
import com.mapstruct.springmapstructdemo.model.DoctorDTO;
import com.mapstruct.springmapstructdemo.repository.DoctorRepository;

@Service
public class DoctorService {
	@PersistenceContext
	private EntityManager entityManager;

	private final DoctorMapper doctorMapper;
	private final DoctorRepository doctorRepository;

	@Autowired
	public DoctorService(DoctorMapper doctorMapper, DoctorRepository doctorRepository) {
		super();
		this.doctorMapper = doctorMapper;
		this.doctorRepository = doctorRepository;
	}

	public void saveDoctor(DoctorDTO doctorDTO) {
		Doctor doctor = doctorMapper.dtoToModel(doctorDTO);
		if (doctor.getFirstname().isEmpty() || doctor.getFirstname().length() == 0) {
			throw new EmptyInputException();
		}
		doctor = doctorRepository.save(doctor);

	}

	public DoctorDTO getDoctor(BigInteger doctorId) {
		Optional<Doctor> doctorEntityOptional = doctorRepository.findById(doctorId);
		Doctor doctor = doctorEntityOptional.get();
		return doctorMapper.modelToDTO(doctor);
	}

//	public List<DoctorDTO> getDoctors(Doctor doctor) {
//		return doctorMapper.modelToDtoList((List<Doctor>) doctorRepository.findAll());
//	}

	public void updateDoctor(BigInteger doctorId, DoctorDTO doctorDTO) {
		Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);
		if (optionalDoctor.isPresent()) {
			doctorMapper.updateDoctor(doctorDTO, optionalDoctor.get());
			doctorRepository.save(optionalDoctor.get());
		} else {
			System.out.println("Exception will be thrown");
		}
	}

	public void deleteDoctor(BigInteger doctorId) throws Exception {
		Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new Exception("Doctor not found"));
		doctorRepository.delete(doctor);

	}
//----------------------------------------FindByName-----------------------------------------//

	public List<DoctorDTO> getDoctorByName(String firstname) {
		return doctorMapper.modelToDtoList(doctorRepository.findByFirstname(firstname));
	}

//-------------------------------------------Sorting------------------------------------------//

	public List<DoctorDTO> findDoctorsWithPaginationAndSorting(int page, int pageSize) {
		Page<Doctor> doctorSort = doctorRepository
				.findAll(PageRequest.of(page, pageSize, Sort.by("firstname").ascending()));
		List<Doctor> doctorList = doctorSort.getContent();

		List<DoctorDTO> doctorDTOList = doctorMapper.modelToDtoList(doctorList);
		return doctorDTOList;
	}

//---------------------------------------Criteria Query-------------------------------------//

	@Transactional
	public List<DoctorDTO> getDoctors() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Doctor> criteriaQuery = criteriaBuilder.createQuery(Doctor.class);
		Root<Doctor> doctorRoot = criteriaQuery.from(Doctor.class);
		criteriaQuery.select(doctorRoot);
		TypedQuery<Doctor> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Doctor> doctorList = typedQuery.getResultList();
		List<DoctorDTO> doctorDTOList = doctorMapper.modelToDtoList(doctorList);
		return doctorDTOList;

//		List<Predicate> searchCriterias = new ArrayList<>();
//		String firstname = doctorDTO.getFirstname();
//		if((firstname != "") && (firstname != null)) {
//			searchCriterias.add(criteriaBuilder.like(doctorRoot.get("firstname"), "%"+firstname+"%"));
//		}
//	
//		return entityManager.createQuery(criteriaQuery).getResultList();
	}

}

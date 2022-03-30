package com.mapstruct.springmapstructdemo.controller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mapstruct.springmapstructdemo.entity.Doctor;
import com.mapstruct.springmapstructdemo.model.DoctorDTO;
import com.mapstruct.springmapstructdemo.service.DoctorService;

@RestController
public class DoctorController {
	@Autowired
	DoctorService doctorService;

	@PostMapping(path = "/doctors")
	public ResponseEntity<?> saveDoctor(@RequestBody DoctorDTO doctorDTO) {
		doctorService.saveDoctor(doctorDTO);
		return ResponseEntity.ok("Doctor added");
	}

//	@GetMapping(path = "/doctors")
//	public ResponseEntity<?> getDoctors(@RequestBody Doctor doctor) {
//		List<DoctorDTO> doctorDTO = doctorService.getDoctors(doctor);
//		return ResponseEntity.ok(doctorDTO);
//	}

	@GetMapping(path = "/doctors/{doctorId}")
	public ResponseEntity<DoctorDTO> getDoctor(@PathVariable("doctorId") BigInteger doctorId) {
		DoctorDTO doctorDTO = doctorService.getDoctor(doctorId);
		return ResponseEntity.ok(doctorDTO);

	}
//----------------------------------findByName---------------------------------//

	@GetMapping(path = "/doctors/firstname")
	public ResponseEntity<?> getDoctorByName(@RequestParam String firstname) {
		List<DoctorDTO> doctorDto = doctorService.getDoctorByName(firstname);
		return ResponseEntity.ok(doctorDto);
	}

//------------------------------------------------------------------------------//

	@PutMapping(path = "/doctors/{doctorId}")
	public ResponseEntity<?> updateDoctor(@PathVariable("doctorId") BigInteger doctorId,
			@RequestBody DoctorDTO doctorDTO) {
		doctorService.updateDoctor(doctorId, doctorDTO);
		return ResponseEntity.ok("Doctor updated Successfully");
	}

	@DeleteMapping(path = "/doctors/{doctorId}")
	public ResponseEntity<?> deleteDoctor(@PathVariable("doctorId") BigInteger doctorId) throws Exception {

		doctorService.deleteDoctor(doctorId);
		return ResponseEntity.ok("Doctor deleted successfully");
	}
//----------------------------------Pagination & Sorting---------------------------------//

	@GetMapping(path = "/doctors")
	public ResponseEntity<List<DoctorDTO>> getProductWithSort(
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "pageSize", required = false, defaultValue = "4") int pageSize) {
		List<DoctorDTO> doctorWithPagination = doctorService.findDoctorsWithPaginationAndSorting(page, pageSize);
		return ResponseEntity.ok(doctorWithPagination);

	}
	
	
//--------------------------------------Criteria Query------------------------------------------------//
	
	
	@GetMapping(path="/doctors/search")
	public ResponseEntity<List<DoctorDTO>> getDoctors(){
		List<DoctorDTO> doctorsList = doctorService.getDoctors();
		return ResponseEntity.ok(doctorsList);
	}
}

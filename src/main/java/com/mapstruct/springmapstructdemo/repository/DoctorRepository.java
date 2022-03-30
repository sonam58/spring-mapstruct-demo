package com.mapstruct.springmapstructdemo.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mapstruct.springmapstructdemo.entity.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, BigInteger> {

	List<Doctor> findByFirstname(String firstname);
}

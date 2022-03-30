package com.mapstruct.springmapstructdemo.entity;


import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table
@Data
public class Doctor {
	@SequenceGenerator(name= "doctor_id_seq", sequenceName="doctor_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor_id_seq")
	@Id
	private BigInteger id;
	private String firstname;
	private String lastname;
}

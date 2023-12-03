package com.dongmanee.domain.university.service;

import java.util.List;
import java.util.Optional;

import com.dongmanee.domain.member.controller.port.MemberControllerUniversityService;
import com.dongmanee.domain.member.controller.port.SingUpControllerUniversityService;
import com.dongmanee.domain.university.controller.port.UniversityControllerUniversityService;
import com.dongmanee.domain.university.domain.University;

public interface UniversityService extends UniversityControllerUniversityService, SingUpControllerUniversityService,
	MemberControllerUniversityService {
	List<University> findAll();

	Optional<University> findById(Long id);
}
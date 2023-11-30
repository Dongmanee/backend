package com.dongmanee.domain.club.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dongmanee.domain.club.domain.Club;
import com.dongmanee.domain.club.dto.request.RequestCreateClub;
import com.dongmanee.domain.club.controller.mapper.ClubMapper;
import com.dongmanee.domain.club.controller.port.ClubService;
import com.dongmanee.domain.member.domain.Member;
import com.dongmanee.domain.member.service.MemberService;
import com.dongmanee.global.utils.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ClubController {
	private final ClubMapper clubMapper;
	private final ClubService clubService;
	private final MemberService memberService;

	@PostMapping("/club")
	public ApiResponse<?> createClub(@Valid @RequestBody RequestCreateClub createClub,
		@AuthenticationPrincipal UserDetails userDetails) {
		Member requestMember = memberService.getMemberFromUserId(Long.parseLong(userDetails.getUsername()));
		Club club = clubMapper.toEntity(createClub);
		clubService.createClub(club, requestMember);
		return ApiResponse.success(HttpStatus.CREATED, "클럽이 생성되었습니다.");
	}

	// TODO 1. 클럽 가입 요청 기능 추가
}

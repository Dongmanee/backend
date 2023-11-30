package com.dongmanee.domain.club.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dongmanee.domain.club.domain.Club;
import com.dongmanee.domain.club.domain.ClubSns;
import com.dongmanee.domain.club.dto.request.RequestEditClubDescriptionAndAddress;
import com.dongmanee.domain.club.dto.request.RequestSns;
import com.dongmanee.domain.club.controller.mapper.ClubMapper;
import com.dongmanee.domain.club.controller.mapper.ClubSnsMapper;
import com.dongmanee.domain.club.controller.port.ClubInfoUpdateService;
import com.dongmanee.global.utils.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ClubInfoEditeController {
	private final ClubMapper clubMapper;
	private final ClubInfoUpdateService clubInfoUpdateService;
	private final ClubSnsMapper clubSnsMapper;

	@PatchMapping("/club/{club-id}")
	@PreAuthorize("hasAnyAuthority('ROLE_HOST', 'ROLE_ADMIN') and hasAnyAuthority('ROLE_CLUB_USER')")
	public ApiResponse<?> editClubDescriptionAndAddress(@RequestBody RequestEditClubDescriptionAndAddress dto,
		@AuthenticationPrincipal UserDetails userDetails, @PathVariable("club-id") Long clubId) {
		Club club = clubMapper.toEntity(clubId, dto);

		clubInfoUpdateService.editClubDescriptionAndAddress(Long.parseLong(userDetails.getUsername()), club);
		return ApiResponse.isOk("클럽 정보가 수정되었습니다.");
	}

	@PostMapping("/club/{club-id}/sns")
	@PreAuthorize("hasAnyAuthority('ROLE_HOST', 'ROLE_ADMIN') and hasAnyAuthority('ROLE_CLUB_USER')")
	public ApiResponse<?> addClubSns(@Valid @RequestBody RequestSns request,
		@AuthenticationPrincipal UserDetails userDetails, @PathVariable("club-id") Long clubId) {
		ClubSns requestSns = clubSnsMapper.toEntity(request);

		clubInfoUpdateService.addClubSns(Long.parseLong(userDetails.getUsername()), requestSns, clubId);
		return ApiResponse.isCreated("클럽 Sns가 추가되었습니다");
	}

	@PatchMapping("/club/{club-id}/sns/{sns-id}")
	@PreAuthorize("hasAnyAuthority('ROLE_HOST', 'ROLE_ADMIN') and hasAnyAuthority('ROLE_CLUB_USER')")
	public ApiResponse<?> editClubSns(@Valid @RequestBody RequestSns request,
		@AuthenticationPrincipal UserDetails userDetails,
		@PathVariable("club-id") Long clubId, @PathVariable("sns-id") Long snsId) {
		ClubSns requestSns = clubSnsMapper.toEntity(request);

		clubInfoUpdateService.editClubSns(Long.parseLong(userDetails.getUsername()), requestSns, clubId, snsId);
		return ApiResponse.isOk("클럽 Sns가 수정되었습니다");
	}

	@DeleteMapping("/club/{club-id}/sns/{sns-id}")
	@PreAuthorize("hasAnyAuthority('ROLE_HOST', 'ROLE_ADMIN') and hasAnyAuthority('ROLE_CLUB_USER')")
	public ApiResponse<?> removeClubSns(@AuthenticationPrincipal UserDetails userDetails,
		@PathVariable("club-id") Long clubId, @PathVariable("sns-id") Long snsId) {

		clubInfoUpdateService.removeClubSns(Long.parseLong(userDetails.getUsername()), clubId, snsId);
		return ApiResponse.isNoContent("클럽 Sns가 삭제되었습니다");
	}

	// TODO 1. 지원서 기능 추가 이후 지원서 수정 기능 추가
}

package com.dongmanee.domain.club.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dongmanee.domain.club.controller.apidoc.ClubControllerApiDocs;
import com.dongmanee.domain.club.controller.mapper.ClubMapper;
import com.dongmanee.domain.club.domain.Club;
import com.dongmanee.domain.club.dto.request.PostSearchingInfo;
import com.dongmanee.domain.club.dto.request.RequestCreateClub;
import com.dongmanee.domain.club.dto.response.postsearch.PostSearchResponse;
import com.dongmanee.domain.club.enums.PostCategory;
import com.dongmanee.domain.club.service.ClubService;
import com.dongmanee.domain.club.service.PostPagingService;
import com.dongmanee.domain.member.domain.Member;
import com.dongmanee.domain.member.service.MemberService;
import com.dongmanee.domain.post.domain.Post;
import com.dongmanee.domain.security.domain.CustomUserDetails;
import com.dongmanee.global.utils.ApiResult;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "클럽", description = "클럽 API 명세서")
@RestController
@RequiredArgsConstructor
@RequestMapping("/clubs")
@Slf4j
public class ClubController implements ClubControllerApiDocs {
	private final ClubMapper clubMapper;
	private final ClubService clubService;
	private final MemberService memberService;
	private final PostPagingService postPagingService;

	//TODO: 클럽 정보 가져오는 URL 리턴으로 변경
	@PostMapping
	public ApiResult<?> createClub(@Valid @RequestBody RequestCreateClub createClub,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		Member requestMember = memberService.findById(Long.parseLong(userDetails.getUsername()));
		Club club = clubMapper.toEntity(createClub, clubService);
		clubService.createClub(club, requestMember);
		return ApiResult.isCreated("클럽이 생성되었습니다.");
	}

	//TODO: 현재 Like, Comment 미구현으로 인해 null 값 반환
	@GetMapping("/{club-id}/posts")
	public ApiResult<List<PostSearchResponse>> getClubNotify(
		@PathVariable("club-id") Long requestClubId, @RequestParam(name = "category") PostCategory category,
		@RequestParam(value = "oldest-post-id", required = false) Long cursor, @RequestParam("size") Integer pageSize) {
		PostSearchingInfo postSearchingRequestDto = clubMapper.toDto(requestClubId, category, cursor, pageSize);
		List<Post> posts = postPagingService.pagingDivider(postSearchingRequestDto);
		List<PostSearchResponse> collect = posts.stream()
			.map(clubMapper::postListToResponse)
			.collect(Collectors.toList());

		return ApiResult.isOk(collect, "조회에 성공하였습니다");
	}

	// TODO 1. 클럽 가입 요청 기능 추가
}

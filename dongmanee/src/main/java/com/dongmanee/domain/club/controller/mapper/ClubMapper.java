package com.dongmanee.domain.club.controller.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.dongmanee.domain.club.domain.Club;
import com.dongmanee.domain.club.domain.ClubCategory;
import com.dongmanee.domain.club.dto.request.PostSearchingInfo;
import com.dongmanee.domain.club.dto.request.RequestCreateClub;
import com.dongmanee.domain.club.dto.request.RequestEditClubDescriptionAndAddress;
import com.dongmanee.domain.club.dto.response.postsearch.PostSearchResponse;
import com.dongmanee.domain.club.enums.PostCategory;
import com.dongmanee.domain.club.service.ClubService;
import com.dongmanee.domain.member.dto.response.MainPageMemberClubDto;
import com.dongmanee.domain.post.domain.Post;

@Mapper(componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	uses = {ClubService.class})
public interface ClubMapper {
	@Mapping(source = "categoryId", target = "category", qualifiedByName = "categoryConverter")
	Club toEntity(RequestCreateClub requestCreateClub,
		@Context ClubService clubService);

	Club toEntity(Long id, RequestEditClubDescriptionAndAddress dto);

	PostSearchingInfo toDto(Long clubId, PostCategory postCategory, Long cursor, Integer pageSize);

	@Mapping(source = "id", target = "postId")
	@Mapping(source = "title", target = "postTitle")
	@Mapping(source = "createdAt", target = "postCreatedAt")
	@Mapping(source = "body", target = "postBody")
	@Mapping(source = "category.name", target = "postCategoryName")
	@Mapping(source = "member.id", target = "postWriter.writerId")
	@Mapping(source = "member.name", target = "postWriter.writerName")
	@Mapping(source = "member.profileImageUrl", target = "postWriter.writerImage")
	PostSearchResponse postListToResponse(Post post);

	List<MainPageMemberClubDto> toMemberJoinedClubResponseDto(List<Club> clubs);

	@Named("categoryConverter")
	default ClubCategory categoryConverter(Long categoryId, @Context ClubService clubService) {
		return clubService.findById(categoryId);
	}
}

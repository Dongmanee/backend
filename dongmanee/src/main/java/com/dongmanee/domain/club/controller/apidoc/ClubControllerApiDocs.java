package com.dongmanee.domain.club.controller.apidoc;

import java.time.LocalDateTime;
import java.util.List;

import com.dongmanee.domain.club.dto.request.RequestCreateClub;
import com.dongmanee.domain.club.dto.response.postsearch.PostSearchResponse;
import com.dongmanee.domain.club.enums.PostCategory;
import com.dongmanee.domain.security.domain.CustomUserDetails;
import com.dongmanee.global.utils.ApiResult;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface ClubControllerApiDocs {
	@Operation(summary = "클럽 생성 요청")
	@ApiResponses({
		@ApiResponse(responseCode = "201",
			description = "생성 성공",
			content = @Content(schema = @Schema(implementation = ApiResult.class),
				examples = @ExampleObject(name = "클럽 생성 성공",
					value = """
						{
							"status": 201,
							"message": "클럽이 생성되었습니다.",
							"data": null
						}
												""")
			)),
		@ApiResponse(responseCode = "401",
			description = "인증에 실패하였습니다. - JWT토큰 혹은 ClubUserRole",
			content = @Content(schema = @Schema(implementation = ApiResult.class),
				examples = @ExampleObject(name = "인증 실패",
					value = """
						{
							"status": 401,
							"message": "인증에 실패하였습니다.",
							"daa": null
						}
												""")
			)),
		@ApiResponse(responseCode = "404",
			description = "카테고리 검색 실패",
			content = @Content(schema = @Schema(implementation = ApiResult.class),
				examples = @ExampleObject(name = "카테고리 검색 실패",
					value = """
						{
							"status": 404,
							"message": "내부 오류로 카테고리가 잘못 설정되었습니다.",
							"data": null
						}
												""")
			))
	})
	ApiResult<?> createClub(RequestCreateClub createClub, CustomUserDetails userDetails);

	@Operation(summary = "포스트 조회 요청", description = "특정 클럽의 포스트를 타입별로 조회 <br>"
		+ "타입은 MAIN_PAGE(메인 공지사항),ALL(전체),ANNOUNCEMENT(공지사항),FREE(자유),QUESTION(문의사항) 중으로 전송<br>"
		+ "최초 조회시 oldest-post-id의 값을 넣지 않은채로 전송<br>"
		+ "이후 조회시 가장 마지막으로 받은 post의 postId 값을 oldest-post-id에 넣어서 보내면 그 이후 데이터 전송<br>"
		+ "현재 Like, Comment 미구현으로 인해 null 값 반환<br>"
		+ "data에 배열 형식으로 데이터 반환<br>"
		+ "메인 공지사항은 1개만 반환, 없으면 존재X<br>"
		+ "나머지는 여러개로 반환")
	@ApiResponses({
		@ApiResponse(responseCode = "200",
			description = "조회 성공",
			content = @Content(schema = @Schema(implementation = ApiResult.class),
				examples = @ExampleObject(name = "post 조회 성공",
					value = """
								{
									"status": 200,
									"message": "조회에 성공하였습니다",
									"data": [
								                     {
								                         "postId": 1,
								                         "postTitle": "testTitle",
								                         "postCreatedAt": "2023-11-15T20:00:00",
								                         "postBody": "testBody",
								                         "postCategoryName": "test",
								                         "postWriter": {
								                             "writerId": 1,
								                             "writerName": "Tester",
								                             "writerImage": "ImageUrl"
								                         },
								                         "postLikesNum": 현재 미구현,
								                         "postCommentNum": 현재 미구현,
								                         "isLike": 현재 미구현
								                     }
								                 ]
								}
						""")
			))
	})
	public ApiResult<List<PostSearchResponse>> getClubNotify(Long requestClubId, PostCategory category,
		Long cursor, Integer pageSize);
}

package com.dongmanee.domain.member.controller.apidoc;

import org.springframework.security.core.userdetails.UserDetails;

import com.dongmanee.domain.member.dto.request.RequestUpdateMemberDetails;
import com.dongmanee.domain.member.dto.request.RequestUpdatePassword;
import com.dongmanee.domain.member.dto.response.ResponseMember;
import com.dongmanee.domain.member.dto.response.ResponseMemberDetails;
import com.dongmanee.global.utils.ApiResult;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "멤버", description = "멤버 API 명세")
public interface MemberControllerApiDocs {

	@Operation(summary = "멤버 정보 조회")
	@ApiResponses({
		@ApiResponse(responseCode = "200",
			description = "요청 성공",
			content = @Content(schema = @Schema(implementation = ApiResult.class),
				examples = @ExampleObject(name = "멤버 정보 조회 성공",
					value = """
						{
							"status":200,
							"message":"멤버 정보 조회 성공",
							"data": {
								"university": "한밭대학교",
								"department": "컴퓨터공학과",
								"name": "홍길동",
								"profileImageUrl": null
							}
						}
							"""))),
	})
	ApiResult<ResponseMember> findMemberById(Long id);

	@Operation(summary = "멤버 세부 정보 조회")
	@ApiResponses({
		@ApiResponse(responseCode = "200",
			description = "요청 성공",
			content = @Content(schema = @Schema(implementation = ApiResult.class),
				examples = @ExampleObject(name = "멤버 정보 조회 성공",
					value = """
						{
							"status":200,
							"message":"멤버 정보 조회 성공",
							"data": {
								"university": "한밭대학교",
								"studentId": "20181010",
								"department": "컴퓨터공학과",
								"name": "홍길동",
								"phone": "010-1234-5678",
								"email": "example@example.org",
								"birth": "1999-01-01",
								"profileImageUrl": null
							}
						}
							"""))),
	})
	ApiResult<ResponseMemberDetails> findMemberDetailsById(UserDetails userDetails);

	@Operation(summary = "멤버 세부 정보 수정")
	@ApiResponses({
		@ApiResponse(responseCode = "200",
			description = "요청 성공",
			content = @Content(schema = @Schema(implementation = ApiResult.class),
				examples = @ExampleObject(name = "멤버 정보 수정 성공",
					value = """
						{
							"status":200,
							"message":"멤버 정보 수정 성공",
							"data": {
								"university": "한밭대학교",
								"studentId": "20181010",
								"department": "컴퓨터공학과",
								"name": "홍길동",
								"phone": "010-1234-5678",
								"email": "example@example.org",
								"birth": "1999-01-01",
								"profileImageUrl": null
							}
						}
							"""))),
	})
	ApiResult<ResponseMemberDetails> updateMemberDetails(UserDetails userDetails, RequestUpdateMemberDetails request);

	@Operation(summary = "멤버 비밀번호 변경")
	@ApiResponses({
		@ApiResponse(responseCode = "204",
			description = "요청 성공",
			content = @Content(schema = @Schema(implementation = ApiResult.class),
				examples = @ExampleObject(name = "멤버 비밀번호 변경 성공",
					value = """
						{
							"status":204,
							"message":"비밀번호 변경 성공",
							"data": null
						}
							"""))),
	})
	ApiResult<?> updateMemberPassword(UserDetails userDetails, RequestUpdatePassword request);
}

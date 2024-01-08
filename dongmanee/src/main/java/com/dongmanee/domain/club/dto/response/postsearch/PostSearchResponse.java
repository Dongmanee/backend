package com.dongmanee.domain.club.dto.response.postsearch;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostSearchResponse {
	private Long postId;
	private String postTitle;
	private LocalDateTime postCreatedAt;
	private String postBody;
	private String postCategoryName;
	private PostWriter postWriter;
	// TODO: 아래 Like, Comment 부분은 기능 생성 이후 매퍼에서 기능 구현 변경
	private Long postLikesNum;
	private Long postCommentNum;
	private Boolean isLike;
}

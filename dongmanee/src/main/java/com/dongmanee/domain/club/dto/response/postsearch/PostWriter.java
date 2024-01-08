package com.dongmanee.domain.club.dto.response.postsearch;

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
public class PostWriter {
	private Long writerId;
	private String writerName;
	private String writerImage;
}

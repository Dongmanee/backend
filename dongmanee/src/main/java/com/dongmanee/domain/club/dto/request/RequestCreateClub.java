package com.dongmanee.domain.club.dto.request;

import jakarta.validation.constraints.NotNull;
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
public class RequestCreateClub {
	@NotNull(message = "카테고리 id가 입력되지 않았습니다")
	private Long categoryId;
	@NotNull(message = "동아리의 이름을 지정하여하 합니다.")
	private String name;
	private String description;
	private String clubMainImage;
	private String clubBackgroundImage;
	private String address;
}
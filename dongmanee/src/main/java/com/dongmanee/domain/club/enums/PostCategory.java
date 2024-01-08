package com.dongmanee.domain.club.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostCategory {
	MAIN_PAGE("메인 공지사항"),
	ALL("전체"),
	ANNOUNCEMENT("공지사항"),
	FREE("자유"),
	QUESTION("문의사항");

	private final String value;

}

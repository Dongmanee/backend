package com.dongmanee.domain.club.service;

import java.util.List;

import com.dongmanee.domain.club.dto.request.PostSearchingInfo;
import com.dongmanee.domain.post.domain.Post;

public interface PostPagingService {
	List<Post> pagingDivider(PostSearchingInfo request);

}

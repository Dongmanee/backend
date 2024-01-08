package com.dongmanee.domain.club.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dongmanee.domain.club.dto.request.PostSearchingInfo;
import com.dongmanee.domain.club.enums.PostCategory;
import com.dongmanee.domain.post.dao.PostRepository;
import com.dongmanee.domain.post.domain.Post;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostPagingServiceImpl implements PostPagingService {

	private final PostRepository postRepository;

	public List<Post> pagingDivider(PostSearchingInfo request) {
		switch (request.getPostCategory()) {
			case MAIN_PAGE -> {
				return findAnnouncement(request.getClubId());
			}
			case ALL -> {
				return findPosts(request.getClubId(), request.getCursor(), request.getPageSize());
			}
			case ANNOUNCEMENT, QUESTION -> {
				return findPostsWithCategory(request.getClubId(), request.getCursor(), request.getPageSize(),
					request.getPostCategory());
			}
			case FREE -> {
				return findFreeCategoryPosts(request.getClubId(), request.getCursor(), request.getPageSize());
			}
		}
		return new ArrayList<>();
	}

	private List<Post> findAnnouncement(Long clubId) {
		Pageable pageable = PageRequest.of(0, 1, Sort.by("createdAt").descending());
		return postRepository.findAnnouncementPostByClubId(clubId, pageable);
	}

	private List<Post> findPosts(Long clubId, Long cursor, Integer pageSize) {
		Pageable pageable = PageRequest.of(0, pageSize);
		return postRepository.findEveryPostsAfterCursor(clubId, cursor, pageable);
	}

	private List<Post> findPostsWithCategory(Long clubId, Long cursor, Integer pageSize,
		PostCategory postCategory) {
		Pageable pageable = PageRequest.of(0, pageSize);
		return postRepository.findSpecificPostsAfterCursor(clubId, postCategory.getValue(), cursor, pageable);
	}

	private List<Post> findFreeCategoryPosts(Long clubId, Long cursor, Integer pageSize) {
		Pageable pageable = PageRequest.of(0, pageSize);
		return postRepository.findWithoutSpecificPostsAfterCursor(clubId, cursor, pageable);
	}

}

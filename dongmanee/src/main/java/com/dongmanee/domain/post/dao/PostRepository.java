package com.dongmanee.domain.post.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.dongmanee.domain.post.domain.Post;

public interface PostRepository {

	List<Post> findEveryPostsAfterCursor(Long clubId, Long cursor, Pageable pageable);

	List<Post> findSpecificPostsAfterCursor(Long clubId, String category, Long cursor, Pageable pageable);

	List<Post> findWithoutSpecificPostsAfterCursor(Long clubId, Long cursor, Pageable pageable);

	List<Post> findAnnouncementPostByClubId(Long clubId, Pageable pageable);
}

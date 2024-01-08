package com.dongmanee.domain.post.dao.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dongmanee.domain.post.dao.PostRepository;
import com.dongmanee.domain.post.domain.Post;

public interface PostJpaRepository extends JpaRepository<Post, Long>, PostRepository {

	@Query("SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.category LEFT JOIN FETCH p.category.club LEFT JOIN FETCH p.member "
		+ "WHERE p.category.club.id = :clubId AND (:cursor IS NULL OR p.id < :cursor) ORDER BY p.createdAt DESC")
	List<Post> findEveryPostsAfterCursor(@Param("clubId") Long clubId, @Param("cursor") Long cursor, Pageable pageable);

	@Query("SELECT p FROM Post p "
		+ "LEFT JOIN FETCH p.category LEFT JOIN FETCH p.category.club  LEFT JOIN FETCH p.member "
		+ "WHERE p.category.name = :category AND p.category.club.id = :clubId "
		+ "AND (:cursor IS NULL OR p.id < :cursor) "
		+ "ORDER BY p.createdAt DESC")
	List<Post> findSpecificPostsAfterCursor(@Param("clubId") Long clubId, @Param("category") String category,
		@Param("cursor") Long cursor, Pageable pageable);

	@Query("SELECT DISTINCT p FROM Post p "
		+ "LEFT JOIN FETCH p.category LEFT JOIN FETCH p.category.club LEFT JOIN FETCH p.member "
		+ "WHERE p.category.name != '공지사항' AND p.category.name != '문의사항' AND "
		+ "p.category.club.id = :clubId AND (:cursor IS NULL OR p.id < :cursor) "
		+ "ORDER BY p.createdAt DESC")
	List<Post> findWithoutSpecificPostsAfterCursor(@Param("clubId") Long clubId, Long cursor,
		Pageable pageable);

	@Query("SELECT DISTINCT p FROM Post p "
		+ "LEFT JOIN FETCH p.category LEFT JOIN FETCH p.category.club LEFT JOIN FETCH p.member "
		+ "WHERE p.category.club.id = :clubId AND p.category.name = '공지사항' ORDER BY p.createdAt DESC")
	Page<Post> findAnnouncementPostsByClubId(Long clubId, Pageable pageable);

	default List<Post> findAnnouncementPostByClubId(Long clubId, Pageable pageable) {
		return findAnnouncementPostsByClubId(clubId, pageable)
			.stream()
			.toList();
	}
}

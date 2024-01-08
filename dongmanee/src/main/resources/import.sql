-- university 더미 데이터
INSERT INTO university (name, created_at, update_at) VALUES ('한밭대학교', '2023-11-15 19:57:57', '2023-11-15 19:57:57');
INSERT INTO university (name, created_at, update_at) VALUES ('한남대학교', '2023-11-15 19:58:00', '2023-11-15 19:58:00');
INSERT INTO university (name, created_at, update_at) VALUES ('충남대학교', '2023-11-15 19:58:30', '2023-11-15 19:58:30');
INSERT INTO university (name, created_at, update_at) VALUES ('우송대학교', '2023-11-15 19:59:00', '2023-11-15 19:59:00');
INSERT INTO university (name, created_at, update_at) VALUES ('대전대학교', '2023-11-15 20:00:00', '2023-11-15 20:00:00');

-- club_category 더미 데이터
INSERT INTO club_category (name, created_at, update_at) VALUES ('category1', '2023-11-15 19:59:00', '2023-11-15 19:59:00');
INSERT INTO club_category (name, created_at, update_at) VALUES ('category2', '2023-11-15 20:00:00', '2023-11-15 20:00:00');

-- user 더미 데이터
INSERT INTO member (university_id, role, student_id, department, name, phone, email, password, birth, created_at, update_at) VALUES (1, 'ROLE_USER', '12345678','testDepartment','Tester','010-1234-5678','test@gmail.com','$2a$10$EBS76qlSxOQkuOyIBaoSruBIeu64i1hU/n9AsRUaPTXt//QaEsErS','1999-01-01','2023-11-15 20:00:00', '2023-11-15 20:00:00');

-- club 더미 데이터
INSERT INTO club (university_id, category_id, name, is_deleted, created_at, update_at) VALUES (1, 1, 'testClub', false, '2023-11-15 20:00:00', '2023-11-15 20:00:00');

-- club_user 더미 데이터
INSERT INTO club_user (member_id, club_id, club_role, created_at, update_at) VALUES (1, 1, 'HOST', '2023-11-15 20:00:00', '2023-11-15 20:00:00');

-- club_post_category
INSERT INTO club_post_category (is_public, club_id, name, created_at, update_at) VALUES (true, 1, 'test', '2023-11-15 20:00:00', '2023-11-15 20:00:00');
INSERT INTO club_post_category (is_public, club_id, name, created_at, update_at) VALUES (true, 1, '공지사항', '2023-11-15 20:00:00', '2023-11-15 20:00:00');
INSERT INTO club_post_category (is_public, club_id, name, created_at, update_at) VALUES (true, 1, '문의사항', '2023-11-15 20:00:00', '2023-11-15 20:00:00');

-- post 더미 데이터
INSERT INTO club_post (is_deleted, category_id, created_at, member_id, update_at, body, title) VALUES (false, 2, '2023-11-15 10:00:00', 1, '2023-11-15 20:00:00', 'testBody', 'testTitle1');
INSERT INTO club_post (is_deleted, category_id, created_at, member_id, update_at, body, title) VALUES (false, 2, '2023-11-15 11:00:00', 1, '2023-11-15 20:00:00', 'testBody', 'testTitle2');
INSERT INTO club_post (is_deleted, category_id, created_at, member_id, update_at, body, title) VALUES (false, 2, '2023-11-15 12:00:00', 1, '2023-11-15 20:00:00', 'testBody', 'testTitle3');
INSERT INTO club_post (is_deleted, category_id, created_at, member_id, update_at, body, title) VALUES (false, 3, '2023-11-15 13:00:00', 1, '2023-11-15 20:00:00', 'testBody', 'testTitle4');
INSERT INTO club_post (is_deleted, category_id, created_at, member_id, update_at, body, title) VALUES (false, 3, '2023-11-15 14:00:00', 1, '2023-11-15 20:00:00', 'testBody', 'testTitle5');
INSERT INTO club_post (is_deleted, category_id, created_at, member_id, update_at, body, title) VALUES (false, 3, '2023-11-15 15:00:00', 1, '2023-11-15 20:00:00', 'testBody', 'testTitle6');
INSERT INTO club_post (is_deleted, category_id, created_at, member_id, update_at, body, title) VALUES (false, 1, '2023-11-15 16:00:00', 1, '2023-11-15 20:00:00', 'testBody', 'testTitle7');
INSERT INTO club_post (is_deleted, category_id, created_at, member_id, update_at, body, title) VALUES (false, 1, '2023-11-15 17:00:00', 1, '2023-11-15 20:00:00', 'testBody', 'testTitle8');
INSERT INTO club_post (is_deleted, category_id, created_at, member_id, update_at, body, title) VALUES (false, 1, '2023-11-15 18:00:00', 1, '2023-11-15 20:00:00', 'testBody', 'testTitle9');
-- university 더미 데이터
INSERT INTO university (name, created_at, update_at) VALUES ('한밭대학교', '2023-11-15 19:57:57', '2023-11-15 19:57:57');
INSERT INTO university (name, created_at, update_at) VALUES ('한남대학교', '2023-11-15 19:58:00', '2023-11-15 19:58:00');
INSERT INTO university (name, created_at, update_at) VALUES ('충남대학교', '2023-11-15 19:58:30', '2023-11-15 19:58:30');
INSERT INTO university (name, created_at, update_at) VALUES ('우송대학교', '2023-11-15 19:59:00', '2023-11-15 19:59:00');
INSERT INTO university (name, created_at, update_at) VALUES ('대전대학교', '2023-11-15 20:00:00', '2023-11-15 20:00:00');

-- user 더미 데이터
INSERT INTO member (university_id, role, student_id, department, name, phone, email, password, birth, created_at, update_at) VALUES (1, 'ROLE_USER', '12345678','testDepartment','Tester','010-1234-5678','test@gmail.com','$2a$10$EBS76qlSxOQkuOyIBaoSruBIeu64i1hU/n9AsRUaPTXt//QaEsErS','1999-01-01','2023-11-15 20:00:00', '2023-11-15 20:00:00');

-- club_category 더미 데이터
INSERT INTO club_category (name, created_at, update_at) VALUES ('category1', '2023-11-15 19:59:00', '2023-11-15 19:59:00');
INSERT INTO club_category (name, created_at, update_at) VALUES ('category2', '2023-11-15 20:00:00', '2023-11-15 20:00:00');

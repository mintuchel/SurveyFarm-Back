DROP TABLE IF EXISTS regioncodes;
CREATE TABLE IF NOT EXISTS regioncodes (
    name VARCHAR(255),
    code INT PRIMARY KEY
);

DROP TABLE IF EXISTS jobcodes;
CREATE TABLE IF NOT EXISTS jobcodes (
    name VARCHAR(255),
    code INT PRIMARY KEY
);

DROP TABLE IF EXISTS agecodes;
CREATE TABLE IF NOT EXISTS agecodes (
    name VARCHAR(255),
    code INT PRIMARY KEY
);

DROP TABLE IF EXISTS gendercodes;
CREATE TABLE IF NOT EXISTS gendercodes (
    name VARCHAR(255),
    code INT PRIMARY KEY
);

-- CREATE TABLE IF NOT EXISTS deadline_surveys (
--     id INTEGER NOT NULL AUTO_INCREMENT,
--     sid INTEGER,
--     days_left INTEGER,
--     PRIMARY KEY (id)
-- ) engine = InnoDB;
--
-- -- MYSQL에 직접 등록하는 마감임박설문 event scheduler
-- -- CLI 에서는 DELIMETER 통해서 써야하지만
-- -- schema.sql 에서는 블록단위로 취급하기 때문에 이렇게 써도 ㄱㅊ음
-- CREATE EVENT IF NOT EXISTS update_deadline_surveys
-- ON SCHEDULE EVERY 1 DAY
-- STARTS CURRENT_DATE + INTERVAL 1 DAY
-- DO
-- BEGIN
--     DELETE FROM deadline_surveys;
--
--     INSERT INTO deadline_surveys (sid, days_left)
--     SELECT id, DATEDIFF(end_at, CURDATE())
--     FROM Survey
--     WHERE DATEDIFF(end_at, CURDATE()) <= 3 AND DATEDIFF(end_at, CURDATE()) >= 0;
-- END;
--
-- -- CLI 용 테스트 코드
-- DELIMITER //
--
-- CREATE EVENT IF NOT EXISTS update_deadline_surveys
-- ON SCHEDULE EVERY 1 DAY
-- STARTS CURRENT_DATE + INTERVAL 1 DAY
-- COMMENT '마감임박설문 매일 자정에 최신화'
-- DO
-- BEGIN
-- DELETE FROM deadline_surveys;
--
-- INSERT INTO deadline_surveys (sid, days_left)
-- SELECT id, DATEDIFF(end_at, CURDATE())
-- FROM Survey
-- WHERE DATEDIFF(end_at, CURDATE()) <= 3 AND DATEDIFF(end_at, CURDATE()) >= 0;
-- END //
--
-- DELIMITER ;
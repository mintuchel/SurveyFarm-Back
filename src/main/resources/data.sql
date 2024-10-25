-- regioncodes 데이터 삽입
INSERT INTO regioncodes (name, code) VALUES ('전체', 1);            -- (int) Math.pow(2, 0)  --> 1
INSERT INTO regioncodes (name, code) VALUES ('서울', 2);             -- (int) Math.pow(2, 1)  --> 2
INSERT INTO regioncodes (name, code) VALUES ('경기', 4);             -- (int) Math.pow(2, 2)  --> 4
INSERT INTO regioncodes (name, code) VALUES ('인천', 8);             -- (int) Math.pow(2, 3)  --> 8
INSERT INTO regioncodes (name, code) VALUES ('대전', 16);            -- (int) Math.pow(2, 4)  --> 16
INSERT INTO regioncodes (name, code) VALUES ('세종', 32);            -- (int) Math.pow(2, 5)  --> 32
INSERT INTO regioncodes (name, code) VALUES ('충남', 64);            -- (int) Math.pow(2, 6)  --> 64
INSERT INTO regioncodes (name, code) VALUES ('충북', 128);           -- (int) Math.pow(2, 7)  --> 128
INSERT INTO regioncodes (name, code) VALUES ('광주', 256);           -- (int) Math.pow(2, 8)  --> 256
INSERT INTO regioncodes (name, code) VALUES ('전남', 512);           -- (int) Math.pow(2, 9)  --> 512
INSERT INTO regioncodes (name, code) VALUES ('전북', 1024);          -- (int) Math.pow(2, 10) --> 1024
INSERT INTO regioncodes (name, code) VALUES ('대구', 2048);          -- (int) Math.pow(2, 11) --> 2048
INSERT INTO regioncodes (name, code) VALUES ('경북', 4096);          -- (int) Math.pow(2, 12) --> 4096
INSERT INTO regioncodes (name, code) VALUES ('부산', 8192);          -- (int) Math.pow(2, 13) --> 8192
INSERT INTO regioncodes (name, code) VALUES ('울산', 16384);         -- (int) Math.pow(2, 14) --> 16384
INSERT INTO regioncodes (name, code) VALUES ('경남', 32768);         -- (int) Math.pow(2, 15) --> 32768
INSERT INTO regioncodes (name, code) VALUES ('강원', 65536);         -- (int) Math.pow(2, 16) --> 65536
INSERT INTO regioncodes (name, code) VALUES ('제주', 131072);        -- (int) Math.pow(2, 17) --> 131072
INSERT INTO regioncodes (name, code) VALUES ('외국', 262144);        -- (int) Math.pow(2, 18) --> 262144

-- jobcodes 데이터 삽입
INSERT INTO jobcodes (name, code) VALUES ('전체', 1);                -- (int) Math.pow(2, 0)  --> 1
INSERT INTO jobcodes (name, code) VALUES ('기획', 2);                -- (int) Math.pow(2, 1)  --> 2
INSERT INTO jobcodes (name, code) VALUES ('법조', 4);                -- (int) Math.pow(2, 2)  --> 4
INSERT INTO jobcodes (name, code) VALUES ('인사', 8);                -- (int) Math.pow(2, 3)  --> 8
INSERT INTO jobcodes (name, code) VALUES ('회계', 16);               -- (int) Math.pow(2, 4)  --> 16
INSERT INTO jobcodes (name, code) VALUES ('마케팅', 32);             -- (int) Math.pow(2, 5)  --> 32
INSERT INTO jobcodes (name, code) VALUES ('개발', 64);               -- (int) Math.pow(2, 6)  --> 64
INSERT INTO jobcodes (name, code) VALUES ('디자인', 128);            -- (int) Math.pow(2, 7)  --> 128
INSERT INTO jobcodes (name, code) VALUES ('물류/무역', 256);         -- (int) Math.pow(2, 8)  --> 256
INSERT INTO jobcodes (name, code) VALUES ('배송업', 512);            -- (int) Math.pow(2, 9)  --> 512
INSERT INTO jobcodes (name, code) VALUES ('영업', 1024);             -- (int) Math.pow(2, 10) --> 1024
INSERT INTO jobcodes (name, code) VALUES ('고객상담', 2048);         -- (int) Math.pow(2, 11) --> 2048
INSERT INTO jobcodes (name, code) VALUES ('금융/보험', 4096);        -- (int) Math.pow(2, 12) --> 4096
INSERT INTO jobcodes (name, code) VALUES ('요식업', 8192);           -- (int) Math.pow(2, 13) --> 8192
INSERT INTO jobcodes (name, code) VALUES ('서비스업', 16384);       -- (int) Math.pow(2, 14) --> 16384
INSERT INTO jobcodes (name, code) VALUES ('설계', 32768);            -- (int) Math.pow(2, 15) --> 32768
INSERT INTO jobcodes (name, code) VALUES ('제조업', 65536);          -- (int) Math.pow(2, 16) --> 65536
INSERT INTO jobcodes (name, code) VALUES ('교육', 131072);           -- (int) Math.pow(2, 17) --> 131072
INSERT INTO jobcodes (name, code) VALUES ('건축', 262144);           -- (int) Math.pow(2, 18) --> 262144
INSERT INTO jobcodes (name, code) VALUES ('의료', 524288);           -- (int) Math.pow(2, 19) --> 524288
INSERT INTO jobcodes (name, code) VALUES ('스포츠', 1048576);        -- (int) Math.pow(2, 20) --> 1048576
INSERT INTO jobcodes (name, code) VALUES ('공공/복지', 2097152);     -- (int) Math.pow(2, 21) --> 2097152
INSERT INTO jobcodes (name, code) VALUES ('학생', 4194304);         -- (int) Math.pow(2, 22) --> 4194304

-- agecodes 데이터 삽입
INSERT INTO agecodes (name, code) VALUES ('초등학생', 1);            -- (int) Math.pow(2, 0)  --> 1
INSERT INTO agecodes (name, code) VALUES ('중학생', 2);              -- (int) Math.pow(2, 1)  --> 2
INSERT INTO agecodes (name, code) VALUES ('고등학생', 4);            -- (int) Math.pow(2, 2)  --> 4
INSERT INTO agecodes (name, code) VALUES ('대학생', 8);              -- (int) Math.pow(2, 3)  --> 8
INSERT INTO agecodes (name, code) VALUES ('10대', 16);               -- (int) Math.pow(2, 4)  --> 16
INSERT INTO agecodes (name, code) VALUES ('20대', 32);               -- (int) Math.pow(2, 5)  --> 32
INSERT INTO agecodes (name, code) VALUES ('30대', 64);               -- (int) Math.pow(2, 6)  --> 64
INSERT INTO agecodes (name, code) VALUES ('40대', 128);              -- (int) Math.pow(2, 7)  --> 128
INSERT INTO agecodes (name, code) VALUES ('50대', 256);              -- (int) Math.pow(2, 8)  --> 256
INSERT INTO agecodes (name, code) VALUES ('60대', 512);              -- (int) Math.pow(2, 9)  --> 512
INSERT INTO agecodes (name, code) VALUES ('70대', 1024);             -- (int) Math.pow(2, 10) --> 1024

-- gendercodes 데이터 삽입
INSERT INTO gendercodes (name, code) VALUES ('남자', 1);
INSERT INTO gendercodes (name, code) VALUES ('여자', 2);
INSERT INTO gendercodes (name, code) VALUES ('전체', 4);
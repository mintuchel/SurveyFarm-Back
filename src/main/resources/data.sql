-- regioncodes 데이터 삽입
INSERT INTO regioncodes (name, code) VALUES ('전체', 0);
INSERT INTO regioncodes (name, code) VALUES ('서울', 1);          -- (int) Math.pow(2, 0)  --> 1
INSERT INTO regioncodes (name, code) VALUES ('경기', 2);          -- (int) Math.pow(2, 1)  --> 2
INSERT INTO regioncodes (name, code) VALUES ('인천', 4);          -- (int) Math.pow(2, 2)  --> 4
INSERT INTO regioncodes (name, code) VALUES ('대전', 8);          -- (int) Math.pow(2, 3)  --> 8
INSERT INTO regioncodes (name, code) VALUES ('세종', 16);         -- (int) Math.pow(2, 4)  --> 16
INSERT INTO regioncodes (name, code) VALUES ('충남', 32);         -- (int) Math.pow(2, 5)  --> 32
INSERT INTO regioncodes (name, code) VALUES ('충북', 64);         -- (int) Math.pow(2, 6)  --> 64
INSERT INTO regioncodes (name, code) VALUES ('광주', 128);        -- (int) Math.pow(2, 7)  --> 128
INSERT INTO regioncodes (name, code) VALUES ('전남', 256);        -- (int) Math.pow(2, 8)  --> 256
INSERT INTO regioncodes (name, code) VALUES ('전북', 512);        -- (int) Math.pow(2, 9)  --> 512
INSERT INTO regioncodes (name, code) VALUES ('대구', 1024);       -- (int) Math.pow(2, 10) --> 1024
INSERT INTO regioncodes (name, code) VALUES ('경북', 2048);       -- (int) Math.pow(2, 11) --> 2048
INSERT INTO regioncodes (name, code) VALUES ('부산', 4096);       -- (int) Math.pow(2, 12) --> 4096
INSERT INTO regioncodes (name, code) VALUES ('울산', 8192);       -- (int) Math.pow(2, 13) --> 8192
INSERT INTO regioncodes (name, code) VALUES ('경남', 16384);      -- (int) Math.pow(2, 14) --> 16384
INSERT INTO regioncodes (name, code) VALUES ('강원', 32768);      -- (int) Math.pow(2, 15) --> 32768
INSERT INTO regioncodes (name, code) VALUES ('제주', 65536);      -- (int) Math.pow(2, 16) --> 65536

-- jobcodes 데이터 삽입
INSERT INTO jobcodes (name, code) VALUES ('전체', 0);
INSERT INTO jobcodes (name, code) VALUES ('기획·전략', 1);              -- (int) Math.pow(2, 0)  --> 1
INSERT INTO jobcodes (name, code) VALUES ('법무·사무·총무', 2);          -- (int) Math.pow(2, 1)  --> 2
INSERT INTO jobcodes (name, code) VALUES ('인사·HR', 4);                 -- (int) Math.pow(2, 2)  --> 4
INSERT INTO jobcodes (name, code) VALUES ('회계·세무', 8);              -- (int) Math.pow(2, 3)  --> 8
INSERT INTO jobcodes (name, code) VALUES ('마케팅·광고·MD', 16);        -- (int) Math.pow(2, 4)  --> 16
INSERT INTO jobcodes (name, code) VALUES ('개발·데이터', 32);            -- (int) Math.pow(2, 5)  --> 32
INSERT INTO jobcodes (name, code) VALUES ('물류·무역', 64);             -- (int) Math.pow(2, 6)  --> 64
INSERT INTO jobcodes (name, code) VALUES ('운전·운송·배송', 128);        -- (int) Math.pow(2, 7)  --> 128
INSERT INTO jobcodes (name, code) VALUES ('영업', 256);                  -- (int) Math.pow(2, 8)  --> 256
INSERT INTO jobcodes (name, code) VALUES ('고객상담·TM', 512);          -- (int) Math.pow(2, 9)  --> 512
INSERT INTO jobcodes (name, code) VALUES ('금융·보험', 1024);            -- (int) Math.pow(2, 10) --> 1024
INSERT INTO jobcodes (name, code) VALUES ('식·음료', 2048);              -- (int) Math.pow(2, 11) --> 2048
INSERT INTO jobcodes (name, code) VALUES ('고객서비스·리테일', 4096);    -- (int) Math.pow(2, 12) --> 4096
INSERT INTO jobcodes (name, code) VALUES ('엔지니어링·설계', 8192);     -- (int) Math.pow(2, 13) --> 8192
INSERT INTO jobcodes (name, code) VALUES ('제조·생산', 16384);           -- (int) Math.pow(2, 14) --> 16384
INSERT INTO jobcodes (name, code) VALUES ('교육', 32768);                -- (int) Math.pow(2, 15) --> 32768
INSERT INTO jobcodes (name, code) VALUES ('건축·시설', 65536);           -- (int) Math.pow(2, 16) --> 65536
INSERT INTO jobcodes (name, code) VALUES ('의료·바이오', 131072);        -- (int) Math.pow(2, 17) --> 131072
INSERT INTO jobcodes (name, code) VALUES ('미디어·문화·스포츠', 262144); -- (int) Math.pow(2, 18) --> 262144
INSERT INTO jobcodes (name, code) VALUES ('공공·복지', 524288);          -- (int) Math.pow(2, 19) --> 524288
INSERT INTO jobcodes (name, code) VALUES ('중학생', 1048576);            -- (int) Math.pow(2, 20) --> 1048576
INSERT INTO jobcodes (name, code) VALUES ('고등학생', 2097152);          -- (int) Math.pow(2, 21) --> 2097152
INSERT INTO jobcodes (name, code) VALUES ('대학생', 4194304);            -- (int) Math.pow(2, 22) --> 4194304

-- agecodes 데이터 삽입
INSERT INTO agecodes (name, code) VALUES ('전체', 0);
INSERT INTO agecodes (name, code) VALUES ('10대', 1);                -- (int) Math.pow(2, 0)  --> 1
INSERT INTO agecodes (name, code) VALUES ('20대', 2);                -- (int) Math.pow(2, 1)  --> 2
INSERT INTO agecodes (name, code) VALUES ('30대', 4);                -- (int) Math.pow(2, 2)  --> 4
INSERT INTO agecodes (name, code) VALUES ('40대', 8);                -- (int) Math.pow(2, 3)  --> 8
INSERT INTO agecodes (name, code) VALUES ('50대 이상', 16);          -- (int) Math.pow(2, 4)  --> 16

-- gendercodes 데이터 삽입
INSERT INTO gendercodes (name, code) VALUES ('전체', 0);
INSERT INTO gendercodes (name, code) VALUES ('남자', 1);
INSERT INTO gendercodes (name, code) VALUES ('여자', 2);
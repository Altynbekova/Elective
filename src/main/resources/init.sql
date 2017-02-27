DROP TABLE IF EXISTS ROLE;
DROP TABLE IF EXISTS USER;
DROP TABLE IF EXISTS STUDENT;
DROP TABLE IF EXISTS LECTURER;
DROP TABLE IF EXISTS STUDENT_COURSE;
DROP TABLE IF EXISTS COURSE;

CREATE TABLE IF NOT EXISTS ROLE (
  ROLE_ID   IDENTITY    NOT NULL PRIMARY KEY,
  ROLE_NAME VARCHAR(20) NOT NULL UNIQUE,
);

CREATE TABLE IF NOT EXISTS USER (
  USER_ID IDENTITY PRIMARY KEY,
  FIRST_NAME  VARCHAR(20) NOT NULL,
  LAST_NAME   VARCHAR(20) NOT NULL,
  LOGIN       VARCHAR(12) NOT NULL UNIQUE,
  PASSWORD    VARCHAR(80) NOT NULL,
  ROLE_ID     INT         NOT NULL,
  FOREIGN KEY (ROLE_ID) REFERENCES ROLE (ROLE_ID)
);

CREATE TABLE IF NOT EXISTS LECTURER (
  LECTURER_ID INT PRIMARY KEY,
  JOB_TITLE   VARCHAR(20),
  FOREIGN KEY (LECTURER_ID) REFERENCES USER(USER_ID)
);

CREATE TABLE IF NOT EXISTS STUDENT (
  STUDENT_ID INT PRIMARY KEY,
  FOREIGN KEY (STUDENT_ID) REFERENCES USER(USER_ID)
);

CREATE TABLE IF NOT EXISTS COURSE (
  COURSE_ID   IDENTITY PRIMARY KEY,
  NAME        VARCHAR(50) NOT NULL,
  DESCRIPTION VARCHAR(255),
  IS_AVAILABLE BOOLEAN NOT NULL DEFAULT TRUE,
  LECTURER_ID INT         NOT NULL,
  FOREIGN KEY (LECTURER_ID) REFERENCES LECTURER (LECTURER_ID),
  UPPER_NAME VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS STUDENT_COURSE (
  ID          IDENTITY PRIMARY KEY,
  COURSE_ID   INT NOT NULL,
  STUDENT_ID  INT NOT NULL,
  IS_COMPLETE BOOLEAN DEFAULT FALSE,
  GRADE       TINYINT,
  FEEDBACK    VARCHAR(255),
  FOREIGN KEY (COURSE_ID) REFERENCES COURSE (COURSE_ID) ON DELETE SET NULL,
  FOREIGN KEY (STUDENT_ID) REFERENCES STUDENT (STUDENT_ID),
  UNIQUE (COURSE_ID,STUDENT_ID),
  CHECK GRADE > 0 AND GRADE < 101
);


INSERT INTO ROLE (ROLE_NAME) VALUES ('LECTURER'), ('STUDENT');

INSERT INTO USER (FIRST_NAME, LAST_NAME, LOGIN, PASSWORD, ROLE_ID) VALUES (
  'Лектор', 'Лекторов', 'admin', '$2a$10$7PqmexZy/ynKXKws0fK8PO4KHBt.EtiHq2un30ilhTYNP76ewk3EO',
  (SELECT ROLE_ID
   FROM ROLE
   WHERE ROLE_NAME = 'LECTURER')
);
INSERT INTO LECTURER(LECTURER_ID, JOB_TITLE) VALUES (IDENTITY(), 'Associate professor');

INSERT INTO USER (FIRST_NAME, LAST_NAME, LOGIN, PASSWORD, ROLE_ID) VALUES (
  'Студент', 'Студентов', 'user', '$2a$10$5sAo.cHgw27OjR4czEDRqODDrnXjS0GsxQsao9Ty4O2c5wbIG6ebS',
  (SELECT ROLE_ID
   FROM ROLE
   WHERE ROLE_NAME = 'STUDENT')
);
INSERT INTO STUDENT (STUDENT_ID) VALUES (IDENTITY());

INSERT INTO COURSE (NAME, UPPER_NAME, DESCRIPTION, LECTURER_ID) VALUES
  ('Course name', 'COURSE NAME', 'Course description', 1),
  ('Course name2', 'COURSE NAME2', 'Course_description2', 1);

INSERT INTO STUDENT_COURSE (COURSE_ID, STUDENT_ID, IS_COMPLETE, GRADE, FEEDBACK) VALUES
  (1, 2, TRUE, 100, 'Course feedback'),
  (2, 2, FALSE, NULL, 'Course_feedback2');

UPDATE USER SET PASSWORD='$2a$10$VdJC61TIyWfn3./QqWRkS.sVyPSUEuYMRJUsguEhKUVDYuiJvKi8i' WHERE USER_ID=1;
UPDATE USER SET PASSWORD='$2a$10$CcTcJ6cYYAllDFONbxRStu/.j2Re6xWI6kPG6cV.hiOho/ZikfgAy' WHERE USER_ID=2;
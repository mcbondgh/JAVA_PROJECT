-- Create Database 
CREATE DATABASE cass;

-- select and use the database 
USE cass;
-----------------------------------------------
-- Create the student registration table --
CREATE TABLE users(
	id INT PRIMARY KEY AUTO_INCREMENT, 
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    studentId INT,
    email VARCHAR(100),
    password VARCHAR(100),
    comfirm_password VARCHAR(100),
    level TINYINT,
    class VARCHAR(50),
    personality VARCHAR(50),
    programme VARCHAR(50),
    department VARCHAR(50),
    date_added DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(id) REFERENCES stu_attendance(added_by) 
);

ALTER TABLE students
ADD COLUMN gender VARCHAR(10) AFTER studentId;

-----------------------------------------------------------------------------------
-- create lecturer registration table ---
CREATE TABLE staff (
	id INT AUTO_INCREMENT,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    username  VARCHAR(50) UNIQUE,
    password VARCHAR(50),
    phone_number VARCHAR(100),
    department VARCHAR(50),
    teaches VARCHAR(50),
    `rank` VARCHAR(50),
    added_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    added_by INT,
    PRIMARY KEY (id)    
);

ALTER TABLE staff
ADD COLUMN gender VARCHAR(10) AFTER last_name;

----------------------------------------------------------------------------------
-- create student attendance table 
CREATE TABLE stu_attendance(
	id INT PRIMARY KEY AUTO_INCREMENT,
    studentId INT,
    status VARCHAR(2),
    stu_record_id INT, 
    programme VARCHAR(100),
    attendance_date DATE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    added_by INT
);
-----------------------------------------------------------------------------------------
ALTER TABLE stu_attendance 
ADD COLUMN updated_at DATETIME DEFAULT CURRENT_TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP AFTER created_at;

-----------------------------------------------------------------------------------------

-- create staff attendance table ---
CREATE TABLE staff_attendance(
	id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    status VARCHAR(2),
    course VARCHAR(50),
    staff_record_id INT,
    attendance_date DATE, 
    created_at DATETIME DEFAULT NOW(),
    added_by INT
);

ALTER TABLE staff_attendance 
ADD COLUMN updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP AFTER created_at;

DESCRIBE staff_attendance;

-------------------------------------------------------------------------------------

-- create signin table 
CREATE TABLE signin(
	id INT AUTO_INCREMENT,
    user_id INT,
    position VARCHAR(50),
    signedin_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);

ALTER TABLE signin 
MODIFY COLUMN signedin_at DATETIME DEFAULT CURRENT_TIMESTAMP;

-------------------------------------------------------------------------------------

-- create signout table 
CREATE TABLE signout (
	id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    signedout_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-------------------------------------------------------------------------------------
ALTER TABLE stu_attendance
ADD FOREIGN KEY (added_by) REFERENCES students(id) ON DELETE SET NULL;

ALTER TABLE staff_attendance 
ADD FOREIGN KEY(added_by) REFERENCES staff (id) ON DELETE SET NULL;


ALTER TABLE students 
MODIFY COLUMN confirm_password VARCHAR(100) AFTER password;

-- CHANGE studentid FROM int to varchar
ALTER TABLE students 
MODIFY COLUMN studentId VARCHAR(50) NOT NULL;

SELECT * FROM users;

ALTER TABLE staff DROP CONSTRAINT username;
ALTER TABLE staff CHANGE username staffid VARCHAR(50);
ALTER TABLE stu_attendance DROP FOREIGN KEY stu_attendance_ibfk_1;
TRUNCATE TABLE students;
ALTER TABLE users
MODIFY COLUMN studentId VARCHAR(50);

ALTER TABLE students
ADD COLUMN personality VARCHAR(50) AFTER `level`;

ALTER TABLE users 
CHANGE comfirm_password confirm_password VARCHAR(100);

ALTER TABLE users 
ADD COLUMN gender VARCHAR(20) AFTER confirm_password;

ALTER TABLE users 
MODIFY COLUMN level INT;

ALTER TABLE users DROP CONSTRAINT users_ibfk_1;
SELECT * FROM users;
UPDATE users 
SET first_name = "PRINCE"
WHERE id = 1;

ALTER TABLE users 
CHANGE studentId userId VARCHAR(50);

ALTER TABLE signout 
ADD FOREIGN KEY(user_id) REFERENCES signin(id) ON DELETE SET NULL ON UPDATE SET NULL;
ALTER TABLE signout DROP CONSTRAINT signout_ibfk_1;


/*RENAME USERS TABLE TO STUDENTS*/
ALTER TABLE users RENAME TO students;

ALTER TABLE students DROP COLUMN password;
ALTER TABLE students DROP COLUMN confirm_password;

ALTER TABLE students 
CHANGE personality status VARCHAR(50);

SELECT * FROM students;
ALTER TABLE students 
CHANGE student_id userName VARCHAR(40);
ALTER TABLE students
ADD COLUMN user_id INT AFTER department;

UPDATE users
SET programme = "ROUTING & SWITCHING"
WHERE id = 7;

ALTER TABLE users
DROP CONSTRAINT users_ibfk_1;
ALTER TABLE students 
DROP FOREIGN KEY students_ib_fk_1;

ALTER TABLE students 

ALTER TABLE staff
DROP COLUMN password;

SELECT * FROM USERS;
ALTER TABLE users
ADD COLUMN user_role VARCHAR(20)AFTER user_id;

ALTER TABLE users 
DROP first_name;
ALTER TABLE users
DROP last_name;

ALTER TABLE staff
DROP COLUMN confirm_password;
ALTER TABLE students 
CHANGE  userName student_id VARCHAR(50);

ALTER TABLE staff 
ADD COLUMN confirm_password VARCHAR(100) AFTER password;
ALTER TABLE staff 
MODIFY COLUMN password VARCHAR(100);

ALTER TABLE staff
ADD COLUMN user_id INT
AFTER `rank`;

ALTER TABLE users 
MODIFY COLUMN user_id VARCHAR(40);
ALTER TABLE users 
CHANGE user_id userName VARCHAR(40);

/*CREATE THE user TABLE */
CREATE TABLE users (
id INT AUTO_INCREMENT,
userName VARCHAR(50),
user_id INT,
password VARCHAR(100) NOT NULL, 
confirm_password VARCHAR(100) NOT NULL, 
user_role VARCHAR(20),
date_added DATETIME DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY(id)
);

SELECT * FROM students;
SELECT * FROM staff;
SELECT * FROM users;
SELECT * FROM signin;
SELECT * FROM signout;
SELECT * FROM subjects;


SELECT 
si.user_id, si.userName, si.signedin_at, so.signedout_at
FROM signin si
JOIN signout so
ON si.user_id = so.user_id
ORDER BY si.signedin_at DESC;


DELETE FROM users WHERE id = 4;

ALTER TABLE users
ADD FOREIGN KEY(user_id) REFERENCES students(id);

ALTER TABLE users 
ADD FOREIGN KEY(user_id) REFERENCES staff(id);

ALTER TABLE users
ADD COLUMN staff_id INT 
AFTER user_id;
ALTER TABLE users 
DROP FOREIGN KEY users_ibfk_2;

ALTER TABLE users 
ADD staff_id INT AFTER user_id;

ALTER TABLE users 
ADD FOREIGN KEY (user_id) REFERENCES students(id) ON DELETE SET NULL ON UPDATE SET NULL;

ALTER TABLE users 
ADD FOREIGN KEY(staff_id) REFERENCES staff(id) ON DELETE SET NULL ON UPDATE SET NULL;

ALTER TABLE signin 
CHANGE position user_role VARCHAR(50);

ALTER TABLE staff 
CHANGE COLUMN `rank` title VARCHAR(50);
SELECT * FROM users;
INSERT INTO students
VALUES(LAST_INSERT_ID(), "HELLEN", "FAIVA", "0123456A", "hellen@gmail.com", "FEMALE", 200, "D", "STUDENT", "NETWORKING", "COMPUTER SCIENCE", DEFAULT);
INSERT INTO users
VALUES(1, "mcbondgh", 1, "0000", "0000", "Student", DEFAULT);
ALTER TABLE subjects 
DROP FOREIGN KEY subjects_ibfk_1;

SELECT * FROM users;
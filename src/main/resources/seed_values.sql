DROP SCHEMA IF EXISTS leaveapp;
CREATE SCHEMA leaveapp;
USE leaveapp;

CREATE TABLE leaveapp.user (
	user_type VARCHAR(45) NOT NULL,
	user_id INTEGER NOT NULL AUTO_INCREMENT,
	name VARCHAR(45) NULL,
	password VARCHAR(100) NULL,
	username VARCHAR(45) NULL,
	annual_leaven INTEGER NULL,
	comp_leaven INTEGER NULL,
	medical_leaven INTEGER NULL,
	reports_to INTEGER NULL,
	PRIMARY KEY (user_id));

CREATE TABLE leaveapp.leaves (
	leave_id INTEGER NOT NULL AUTO_INCREMENT,
	addtnl_reason VARCHAR(45) NULL,
	comments VARCHAR(45) NULL,
	contact VARCHAR(45) NULL,
	start_date DATE NULL,
	end_date DATE NULL,
	leave_type ENUM('ANNUAL', 'MEDICAL', 'COMPENSATION') NULL,
	status ENUM('APPLIED', 'APPROVED', 'REJECTED', 'DELETED', 'CANCELLED', 'ARCHIVED', 'UPDATED') NOT NULL,
	work_dissemination VARCHAR(45) NULL,
	userid INTEGER NOT NULL,
	PRIMARY KEY (leave_id),
	CONSTRAINT lfk
		FOREIGN KEY (userid)
		REFERENCES leaveapp.user (user_id)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION);

CREATE TABLE leaveapp.overtime_hours (
	overtime_id INTEGER NOT NULL AUTO_INCREMENT,
	date DATE NULL,
	hours INTEGER NULL,
	reason VARCHAR(45) NULL,
	status ENUM('APPLIED', 'APPROVED', 'REJECTED', 'LEAVEGIVEN') NOT NULL,
	userid INTEGER NOT NULL,
	PRIMARY KEY (overtime_id),
	CONSTRAINT ofk
		FOREIGN KEY (userid)
		REFERENCES leaveapp.user (user_id)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION);

INSERT INTO leaveapp.user
(user_type, user_id, name, password, username, annual_leaven, comp_leaven, medical_leaven, reports_to, enabled)
VALUES
('manager', 1, 'Peter', '$2a$12$wuywrgwcmm3ceYoYz1z9q.74U0MdSiukjz4uF1WyYqILJPtusTrVS', 'peter', 18, 0, 60, null, true);

INSERT INTO leaveapp.user
(user_type, user_id, name, password, username, annual_leaven, comp_leaven, medical_leaven, reports_to, enabled)
VALUES
('manager', 2, 'Jane', '$2a$12$wuywrgwcmm3ceYoYz1z9q.74U0MdSiukjz4uF1WyYqILJPtusTrVS', 'jane', 18, 0, 60, 1, true);

INSERT INTO leaveapp.user
(user_type, user_id, name, password, username, annual_leaven, comp_leaven, medical_leaven, reports_to, enabled)
VALUES
('employee', 3, 'Jack', '$2a$12$wuywrgwcmm3ceYoYz1z9q.74U0MdSiukjz4uF1WyYqILJPtusTrVS', 'jack', 18, 0, 60, 1, true);

INSERT INTO leaveapp.user
(user_type, user_id, name, password, username, annual_leaven, comp_leaven, medical_leaven, reports_to, enabled)
VALUES
('employee', 4, 'Jill', '$2a$12$wuywrgwcmm3ceYoYz1z9q.74U0MdSiukjz4uF1WyYqILJPtusTrVS', 'jill', 18, 0, 60, 1, true);

INSERT INTO leaveapp.user
(user_type, user_id, name, password, username, annual_leaven, comp_leaven, medical_leaven, reports_to, enabled)
VALUES
('employee', 5, 'Jake', '$2a$12$wuywrgwcmm3ceYoYz1z9q.74U0MdSiukjz4uF1WyYqILJPtusTrVS', 'jake', 18, 0, 60, 2, true);

INSERT INTO leaveapp.user
(user_type, user_id, name, password, username, annual_leaven, comp_leaven, medical_leaven, reports_to, enabled)
VALUES
('admin', 6, 'Adam', '$2a$12$wuywrgwcmm3ceYoYz1z9q.74U0MdSiukjz4uF1WyYqILJPtusTrVS', 'adam', null, null, null, null, true);





INSERT INTO leaveapp.leaves
(leave_id, addtnl_reason, comments, contact, end_date, leave_type, start_date, status, work_dissemination, userid)
VALUES
(1, '...', '...', 91111, str_to_date('2021-01-10', '%Y-%m-%d'), 'ANNUAL', str_to_date('2021-01-08', '%Y-%m-%d'), 'APPLIED', '...', '1');

INSERT INTO leaveapp.leaves
(leave_id, addtnl_reason, comments, contact, end_date, leave_type, start_date, status, work_dissemination, userid)
VALUES
(2, '...', '...', 91111, str_to_date('2021-01-10', '%Y-%m-%d'), 'COMPENSATION', str_to_date('2021-01-08', '%Y-%m-%d'), 'DELETED', '...', '1');

INSERT INTO leaveapp.leaves
(leave_id, addtnl_reason, comments, contact, end_date, leave_type, start_date, status, work_dissemination, userid)
VALUES
(3, '...', '...', 91111, str_to_date('2021-01-10', '%Y-%m-%d'), 'MEDICAL', str_to_date('2021-01-08', '%Y-%m-%d'), 'APPROVED', '...', '1');

INSERT INTO leaveapp.leaves
(leave_id, addtnl_reason, comments, contact, end_date, leave_type, start_date, status, work_dissemination, userid)
VALUES
(4, '...', '...', 92222, str_to_date('2021-01-10', '%Y-%m-%d'), 'ANNUAL', str_to_date('2021-01-07', '%Y-%m-%d'), 'APPLIED', '...', '2');

INSERT INTO leaveapp.leaves
(leave_id, addtnl_reason, comments, contact, end_date, leave_type, start_date, status, work_dissemination, userid)
VALUES
(5, '...', '...', 92222, str_to_date('2021-01-10', '%Y-%m-%d'), 'COMPENSATION', str_to_date('2021-01-08', '%Y-%m-%d'), 'DELETED', '...', '2');

INSERT INTO leaveapp.leaves
(leave_id, addtnl_reason, comments, contact, end_date, leave_type, start_date, status, work_dissemination, userid)
VALUES
(6, '...', '...', 92222, str_to_date('2021-01-10', '%Y-%m-%d'), 'MEDICAL', str_to_date('2021-01-08', '%Y-%m-%d'), 'APPROVED', '...', '2');

INSERT INTO leaveapp.leaves
(leave_id, addtnl_reason, comments, contact, end_date, leave_type, start_date, status, work_dissemination, userid)
VALUES
(7, '...', '...', 92222, str_to_date('2021-04-01', '%Y-%m-%d'), 'COMPENSATION', str_to_date('2021-03-29', '%Y-%m-%d'), 'APPROVED', '...', '2');


INSERT INTO leaveapp.leaves
(leave_id, addtnl_reason, comments, contact, end_date, leave_type, start_date, status, work_dissemination, userid)
VALUES
(8, '...', '...', 93333, str_to_date('2021-01-10', '%Y-%m-%d'), 'ANNUAL', str_to_date('2021-01-06', '%Y-%m-%d'), 'APPLIED', '...', '3');


INSERT INTO leaveapp.leaves
(leave_id, addtnl_reason, comments, contact, end_date, leave_type, start_date, status, work_dissemination, userid)
VALUES
(9, '...', '...', 93333, str_to_date('2021-01-10', '%Y-%m-%d'), 'COMPENSATION', str_to_date('2021-01-08', '%Y-%m-%d'), 'DELETED', '...', '3');

INSERT INTO leaveapp.leaves
(leave_id, addtnl_reason, comments, contact, end_date, leave_type, start_date, status, work_dissemination, userid)
VALUES
(10, '...', '...', 93333, str_to_date('2021-01-09', '%Y-%m-%d'), 'MEDICAL', str_to_date('2021-01-01', '%Y-%m-%d'), 'APPROVED', '...', '3');


INSERT INTO leaveapp.leaves
(leave_id, addtnl_reason, comments, contact, end_date, leave_type, start_date, status, work_dissemination, userid)
VALUES
(11, '...', '...', 93333, str_to_date('2021-01-15', '%Y-%m-%d'), 'ANNUAL', str_to_date('2021-01-09', '%Y-%m-%d'), 'UPDATED', '...', '3');


INSERT INTO leaveapp.leaves
(leave_id, addtnl_reason, comments, contact, end_date, leave_type, start_date, status, work_dissemination, userid)
VALUES
(12, '...', '...', 93333, str_to_date('2021-03-15', '%Y-%m-%d'), 'COMPENSATION', str_to_date('2021-03-10', '%Y-%m-%d'), 'APPROVED', '...', '3');


INSERT INTO leaveapp.leaves
(leave_id, addtnl_reason, comments, contact, end_date, leave_type, start_date, status, work_dissemination, userid)
VALUES
(13, '...', '...', 94444, str_to_date('2021-01-15', '%Y-%m-%d'), 'ANNUAL', str_to_date('2021-01-11', '%Y-%m-%d'), 'APPLIED', '...', '4');


INSERT INTO leaveapp.leaves
(leave_id, addtnl_reason, comments, contact, end_date, leave_type, start_date, status, work_dissemination, userid)
VALUES
(14, '...', '...', 94444, str_to_date('2021-05-12', '%Y-%m-%d'), 'ANNUAL', str_to_date('2021-05-08', '%Y-%m-%d'), 'APPLIED', '...', '4');


INSERT INTO leaveapp.leaves
(leave_id, addtnl_reason, comments, contact, end_date, leave_type, start_date, status, work_dissemination, userid)
VALUES
(15, '...', '...', 94444, str_to_date('2021-01-10', '%Y-%m-%d'), 'COMPENSATION', str_to_date('2021-01-08', '%Y-%m-%d'), 'DELETED', '...', '4');


INSERT INTO leaveapp.leaves
(leave_id, addtnl_reason, comments, contact, end_date, leave_type, start_date, status, work_dissemination, userid)
VALUES
(16, '...', '...', 94444, str_to_date('2021-01-10', '%Y-%m-%d'), 'MEDICAL', str_to_date('2021-01-08', '%Y-%m-%d'), 'APPROVED', '...', '4');


INSERT INTO leaveapp.leaves
(leave_id, addtnl_reason, comments, contact, end_date, leave_type, start_date, status, work_dissemination, userid)
VALUES
(17, '...', '...', 94444, str_to_date('2021-01-09', '%Y-%m-%d'), 'COMPENSATION', str_to_date('2021-01-08', '%Y-%m-%d'), 'APPLIED', '...', '4');


INSERT INTO leaveapp.leaves
(leave_id, addtnl_reason, comments, contact, end_date, leave_type, start_date, status, work_dissemination, userid)
VALUES
(18, '...', '...', 94444, str_to_date('2021-07-23', '%Y-%m-%d'), 'COMPENSATION', str_to_date('2021-07-20', '%Y-%m-%d'), 'APPROVED', '...', '4');

INSERT INTO leaveapp.leaves
(leave_id, addtnl_reason, comments, contact, end_date, leave_type, start_date, status, work_dissemination, userid)
VALUES
(19, '...', '...', 95555, str_to_date('2021-01-10', '%Y-%m-%d'), 'ANNUAL', str_to_date('2021-01-08', '%Y-%m-%d'), 'APPLIED', '...', '5');

INSERT INTO leaveapp.leaves
(leave_id, addtnl_reason, comments, contact, end_date, leave_type, start_date, status, work_dissemination, userid)
VALUES
(20, '...', '...', 95555, str_to_date('2021-05-07', '%Y-%m-%d'), 'COMPENSATION', str_to_date('2021-05-01', '%Y-%m-%d'), 'DELETED', '...', '5');

INSERT INTO leaveapp.leaves
(leave_id, addtnl_reason, comments, contact, end_date, leave_type, start_date, status, work_dissemination, userid)
VALUES
(21, '...', '...', 95555, str_to_date('2021-01-10', '%Y-%m-%d'), 'MEDICAL', str_to_date('2021-01-08', '%Y-%m-%d'), 'APPROVED', '...', '5');

INSERT INTO leaveapp.overtime_hours
(overtime_id, date, hours, reason, status, userid)
VALUES
(1, str_to_date('2021-11-10', '%Y-%m-%d'), 2, 'Project A', 'APPLIED', 1);

INSERT INTO leaveapp.overtime_hours
(overtime_id, date, hours, reason, status, userid)
VALUES
(2, str_to_date('2021-11-10', '%Y-%m-%d'), 3, 'Project B', 'APPLIED', 2);

INSERT INTO leaveapp.overtime_hours
(overtime_id, date, hours, reason, status, userid)
VALUES
(3, str_to_date('2021-11-11', '%Y-%m-%d'), 3, 'Project B', 'APPLIED', 2);

INSERT INTO leaveapp.overtime_hours
(overtime_id, date, hours, reason, status, userid)
VALUES
(4, str_to_date('2021-11-11', '%Y-%m-%d'), 2, 'Project A', 'APPLIED', 3);

INSERT INTO leaveapp.overtime_hours
(overtime_id, date, hours, reason, status, userid)
VALUES
(5, str_to_date('2021-11-11', '%Y-%m-%d'), 2, 'Project B', 'APPLIED', 4);

INSERT INTO leaveapp.overtime_hours
(overtime_id, date, hours, reason, status, userid)
VALUES
(6, str_to_date('2021-11-13', '%Y-%m-%d'), 2, 'Project B', 'APPLIED', 4);

INSERT INTO leaveapp.overtime_hours
(overtime_id, date, hours, reason, status, userid)
VALUES
(7, str_to_date('2021-11-14', '%Y-%m-%d'), 3, 'Project B', 'APPLIED', 5);

INSERT INTO leaveapp.overtime_hours
(overtime_id, date, hours, reason, status, userid)
VALUES
(8, str_to_date('2021-11-14', '%Y-%m-%d'), 1, 'Project C', 'APPLIED', 3);

INSERT INTO leaveapp.overtime_hours
(overtime_id, date, hours, reason, status, userid)
VALUES
(9, str_to_date('2021-11-15', '%Y-%m-%d'), 1, 'Project C', 'APPLIED', 4);

INSERT INTO leaveapp.overtime_hours
(overtime_id, date, hours, reason, status, userid)
VALUES
(10, str_to_date('2021-11-15', '%Y-%m-%d'), 2, 'Project C', 'APPLIED', 3);
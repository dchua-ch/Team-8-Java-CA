DROP SCHEMA IF EXISTS leaveapp;

CREATE SCHEMA leaveapp;

USE leaveapp;

CREATE TABLE `user` (
  `user_type` varchar(31) NOT NULL,
  `user_id` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `annual_leaven` int DEFAULT NULL,
  `comp_leaven` int DEFAULT NULL,
  `medical_leaven` int DEFAULT NULL,
  `reports_to` int DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `overtime_hours` (
  `overtime_id` int NOT NULL,
  `date` date DEFAULT NULL,
  `hours` double DEFAULT NULL,
  `userid` int DEFAULT NULL,
  PRIMARY KEY (`overtime_id`),
  KEY `FK6uw8uhxt6kcjsl7f8m703o53k` (`userid`),
  CONSTRAINT `FK6uw8uhxt6kcjsl7f8m703o53k` FOREIGN KEY (`userid`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `leaves` (
  `leave_id` int NOT NULL,
  `addtnl_reason` varchar(255) DEFAULT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `leave_type` enum('ANNUAL','MEDICAL','COMPENSATION') DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `status` enum('APPLIED','APPROVED','REJECTED','DELETED','CANCELLED','ARCHIVED','UPDATED') DEFAULT NULL,
  `work_dissemination` varchar(255) DEFAULT NULL,
  `userid` int DEFAULT NULL,
  PRIMARY KEY (`leave_id`),
  KEY `FKowilv1q0wak7oi6lgkh67g2cp` (`userid`),
  CONSTRAINT `FKowilv1q0wak7oi6lgkh67g2cp` FOREIGN KEY (`userid`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


INSERT INTO leaveapp.user
(user_type, user_id, name, password, username, annual_leaven, comp_leaven, medical_leaven, reports_to)
VALUES
('manager', 1, 'Peter', 'pwd', 'peter', 18, 0, 60, null);

INSERT INTO leaveapp.user
(user_type, user_id, name, password, username, annual_leaven, comp_leaven, medical_leaven, reports_to)
VALUES
('manager', 2, 'Jane', 'pwd', 'jane', 18, 0, 60, 1);

INSERT INTO leaveapp.user
(user_type, user_id, name, password, username, annual_leaven, comp_leaven, medical_leaven, reports_to)
VALUES
('employee', 3, 'Jack', 'pwd', 'jack', 18, 0, 60, 1);

INSERT INTO leaveapp.user
(user_type, user_id, name, password, username, annual_leaven, comp_leaven, medical_leaven, reports_to)
VALUES
('employee', 4, 'Jill', 'pwd', 'jill', 18, 0, 60, 1);

INSERT INTO leaveapp.user
(user_type, user_id, name, password, username, annual_leaven, comp_leaven, medical_leaven, reports_to)
VALUES
('employee', 5, 'Jake', 'pwd', 'jake', 18, 0, 60, 2);

INSERT INTO leaveapp.user
(user_type, user_id, name, password, username, annual_leaven, comp_leaven, medical_leaven, reports_to)
VALUES
('admin', 6, 'Adam', 'pwd', 'adam', null, null, null, null);




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
(7, '...', '...', 93333, str_to_date('2021-01-10', '%Y-%m-%d'), 'ANNUAL', str_to_date('2021-01-06', '%Y-%m-%d'), 'APPLIED', '...', '3');

INSERT INTO leaveapp.leaves
(leave_id, addtnl_reason, comments, contact, end_date, leave_type, start_date, status, work_dissemination, userid)
VALUES
(8, '...', '...', 93333, str_to_date('2021-01-10', '%Y-%m-%d'), 'COMPENSATION', str_to_date('2021-01-08', '%Y-%m-%d'), 'DELETED', '...', '3');

INSERT INTO leaveapp.leaves
(leave_id, addtnl_reason, comments, contact, end_date, leave_type, start_date, status, work_dissemination, userid)
VALUES
(9, '...', '...', 93333, str_to_date('2021-01-09', '%Y-%m-%d'), 'MEDICAL', str_to_date('2021-01-01', '%Y-%m-%d'), 'APPROVED', '...', '3');

INSERT INTO leaveapp.leaves
(leave_id, addtnl_reason, comments, contact, end_date, leave_type, start_date, status, work_dissemination, userid)
VALUES
(10, '...', '...', 93333, str_to_date('2021-01-15', '%Y-%m-%d'), 'ANNUAL', str_to_date('2021-01-09', '%Y-%m-%d'), 'UPDATED', '...', '3');

INSERT INTO leaveapp.leaves
(leave_id, addtnl_reason, comments, contact, end_date, leave_type, start_date, status, work_dissemination, userid)
VALUES
(11, '...', '...', 94444, str_to_date('2021-01-15', '%Y-%m-%d'), 'ANNUAL', str_to_date('2021-01-11', '%Y-%m-%d'), 'APPLIED', '...', '4');

INSERT INTO leaveapp.leaves
(leave_id, addtnl_reason, comments, contact, end_date, leave_type, start_date, status, work_dissemination, userid)
VALUES
(12, '...', '...', 94444, str_to_date('2021-05-12', '%Y-%m-%d'), 'ANNUAL', str_to_date('2021-05-08', '%Y-%m-%d'), 'APPLIED', '...', '4');

INSERT INTO leaveapp.leaves
(leave_id, addtnl_reason, comments, contact, end_date, leave_type, start_date, status, work_dissemination, userid)
VALUES
(13, '...', '...', 94444, str_to_date('2021-01-10', '%Y-%m-%d'), 'COMPENSATION', str_to_date('2021-01-08', '%Y-%m-%d'), 'DELETED', '...', '4');

INSERT INTO leaveapp.leaves
(leave_id, addtnl_reason, comments, contact, end_date, leave_type, start_date, status, work_dissemination, userid)
VALUES
(14, '...', '...', 94444, str_to_date('2021-01-10', '%Y-%m-%d'), 'MEDICAL', str_to_date('2021-01-08', '%Y-%m-%d'), 'APPROVED', '...', '4');

INSERT INTO leaveapp.leaves
(leave_id, addtnl_reason, comments, contact, end_date, leave_type, start_date, status, work_dissemination, userid)
VALUES
(15, '...', '...', 94444, str_to_date('2021-01-09', '%Y-%m-%d'), 'COMPENSATION', str_to_date('2021-01-08', '%Y-%m-%d'), 'APPLIED', '...', '4');

INSERT INTO leaveapp.leaves
(leave_id, addtnl_reason, comments, contact, end_date, leave_type, start_date, status, work_dissemination, userid)
VALUES
(16, '...', '...', 95555, str_to_date('2021-01-10', '%Y-%m-%d'), 'ANNUAL', str_to_date('2021-01-08', '%Y-%m-%d'), 'APPLIED', '...', '5');

INSERT INTO leaveapp.leaves
(leave_id, addtnl_reason, comments, contact, end_date, leave_type, start_date, status, work_dissemination, userid)
VALUES
(17, '...', '...', 95555, str_to_date('2021-05-07', '%Y-%m-%d'), 'COMPENSATION', str_to_date('2021-05-01', '%Y-%m-%d'), 'DELETED', '...', '5');

INSERT INTO leaveapp.leaves
(leave_id, addtnl_reason, comments, contact, end_date, leave_type, start_date, status, work_dissemination, userid)
VALUES
(18, '...', '...', 95555, str_to_date('2021-01-10', '%Y-%m-%d'), 'MEDICAL', str_to_date('2021-01-08', '%Y-%m-%d'), 'APPROVED', '...', '5');
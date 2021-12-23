DROP SCHEMA IF EXISTS leaveapp2;

CREATE SCHEMA leaveapp2;

USE leaveapp2;

CREATE TABLE `user` (
  `role` varchar(31) NOT NULL,
  `user_id` int NOT NULL,
  `enabled` bit DEFAULT 1,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `annual_leaven` int DEFAULT NULL,
  `comp_leaven` int DEFAULT NULL,
  `medical_leaven` int DEFAULT NULL,
  `reports_to` int DEFAULT NULL,
  PRIMARY KEY (`user_id`)
)

--password is Bcrypt encoded. seeded password = password for all

INSERT INTO leaveapp2.user
(role, user_id, enabled, name, password, username, annual_leaven, comp_leaven, medical_leaven, reports_to)
VALUES
('manager', 1, 1, 'Peter', '$2a$12$ntvLIRJtMY.h67/YGmqlVO8U/VGWiXJxmFCb/B13gB/oFIPbIe3yC', 'peter', 18, 0, 60, null);

INSERT INTO leaveapp2.user
(role, user_id, enabled, name, password, username, annual_leaven, comp_leaven, medical_leaven, reports_to)
VALUES
('employee', 2, 1, 'Jack', '$2a$12$ntvLIRJtMY.h67/YGmqlVO8U/VGWiXJxmFCb/B13gB/oFIPbIe3yC', 'jack', 18, 0, 60, 1);

INSERT INTO leaveapp2.user
(role, user_id, enabled, name, password,username, annual_leaven, comp_leaven, medical_leaven, reports_to)
VALUES
('admin', 3, 1, 'Adam', '$2a$12$ntvLIRJtMY.h67/YGmqlVO8U/VGWiXJxmFCb/B13gB/oFIPbIe3yC', 'adam', null, null, null, null);


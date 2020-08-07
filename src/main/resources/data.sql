#TRUNCATE user;

INSERT INTO user (nome,senha,role)
VALUES 
('lucas','$2a$10$jcw3fN7T53BmxdFQUD6YTuFtZrbhi.FwZTIheRdZWlemM7a2Ob/Ba','ROLE_ADMIN student:read student:write course:read course:write');

INSERT INTO user (nome,senha,role)
VALUES 
('maria','$2a$10$jcw3fN7T53BmxdFQUD6YTuFtZrbhi.FwZTIheRdZWlemM7a2Ob/Ba','ROLE_STUDENT');

INSERT INTO user (nome,senha,role)
VALUES 
('tom','$2a$10$jcw3fN7T53BmxdFQUD6YTuFtZrbhi.FwZTIheRdZWlemM7a2Ob/Ba','ROLE_ADMINTRAINEE student:read course:read ');

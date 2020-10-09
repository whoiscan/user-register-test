insert into role(id,name) values (10,'ROLE_ADMIN');
insert into role(id,name) values (20,'ROLE_MODER');
insert into role(id,name) values (30,'ROLE_USER');
insert into users(id,email,account_non_expired,account_non_locked,credentials_non_expired,enabled,full_name,password,user_name) values (100,'example@gmail.com',true,true,true,true,'fullname','$2a$10$bu8VAaYrSit.UgXLTO4qJe3GjCi1350X.RP3dbOFrRFMfiPO0tEr.','username');
insert into users(id,email,account_non_expired,account_non_locked,credentials_non_expired,enabled,full_name,password,user_name) values (200,'2@gmail.com',true,true,true,true,'fullname 2','$2a$10$bu8VAaYrSit.UgXLTO4qJe3GjCi1350X.RP3dbOFrRFMfiPO0tEr.','username 2');
insert into user_role(user_id, role_id) values (100,20);
insert into user_role(user_id, role_id) values (200,30);
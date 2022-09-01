SELECT * FROM bookingdb.users;

-- https://bcrypt-generator.com

INSERT INTO `bookingdb`.`users` (`id`, `email`, `enabled`, `first_name`, `last_name`, `password`, `username`)
VALUES (1, 'admin', 1, 'admin', 'admin', '$2a$12$JHomENIL87pV7W1U8wnSL.4NClVdkkBOamIlZ7shbMUftraB/.ZW6', 'admin');

INSERT INTO `bookingdb`.`users` (`id`, `email`, `enabled`, `first_name`, `last_name`, `password`, `username`)
VALUES (2, 'user2', 1, 'user1', 'user1', '$2a$12$IMfCEr93vKbTariSlJwzWes8ym0Yp2y2/WZZKdgBSgj9o8ua.8N86', 'user1');

INSERT INTO `bookingdb`.`users` (`id`, `email`, `enabled`, `first_name`, `last_name`, `password`, `username`)
VALUES (3, 'user2', 1, 'user2', 'user1', '$2a$12$ulSEYAQvNS.sDJIVVrwUnOCrJI77UTQnkGdgINoaEJJ/C.zxc4Q8C', 'user2');


INSERT INTO `bookingdb`.`authorities` (`id`, `name`, `user_id`) VALUES ('1', 'ROLE_ADMIN', '1');
INSERT INTO `bookingdb`.`authorities` (`id`, `name`, `user_id`) VALUES ('2', 'ROLE_USER', '2');
INSERT INTO `bookingdb`.`authorities` (`id`, `name`, `user_id`) VALUES ('3', 'ROLE_USER', '3');


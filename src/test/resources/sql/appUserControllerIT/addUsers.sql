INSERT INTO app_user (id, age, interests, is_active, location, name, password, user_role, username, user_image_id)
VALUES
    (1, 21, ARRAY[0,1,2], true, 'Wroclaw', 'Kyrylo', '***********', 'ROLE_USER', 'kris', null),
    (2, 21, ARRAY[3,4,6], true, 'Wroclaw', 'Ksu', '***********', 'ROLE_USER', 'ksenia', null);

--Users creation
INSERT INTO app_user (id, age, interests, is_active, location, name, password, user_role, username, user_image_id)
VALUES
    (1, 21, ARRAY[0,1,2], true, 'Wroclaw', 'Kyrylo', '***********', 'ROLE_USER', 'kris', null),
    (2, 21, ARRAY[3,4,6], true, 'Wroclaw', 'Ksu', '***********', 'ROLE_USER', 'ksenia', null);

--Meetups creation
INSERT INTO meetup (id, created_date_time, event_name, interests, is_private, location, max_people, modified_date_time, start_date_time, author_id)
VALUES
    (1, CURRENT_TIMESTAMP, 'Event 1', ARRAY[0], false, 'Wroclaw', 50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '17 days' + INTERVAL '18 hours', 1),
    (2, CURRENT_TIMESTAMP, 'Event 2', ARRAY[0], false, 'Wroclaw', 50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '17 days' + INTERVAL '18 hours', 1),
    (3, CURRENT_TIMESTAMP, 'Event 3', ARRAY[0], false, 'Wroclaw', 50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '17 days' + INTERVAL '18 hours', 1),
    (4, CURRENT_TIMESTAMP, 'Event 4', ARRAY[0], false, 'Wroclaw', 50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '17 days' + INTERVAL '18 hours', 1),
    (5, CURRENT_TIMESTAMP, 'Event 5', ARRAY[0], false, 'Wroclaw', 50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '17 days' + INTERVAL '18 hours', 1),
    (6, CURRENT_TIMESTAMP, 'Event 6', ARRAY[1], false, 'Wroclaw', 50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '17 days' + INTERVAL '18 hours', 1),
    (7, CURRENT_TIMESTAMP, 'Event 7', ARRAY[1], false, 'Wroclaw', 50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '17 days' + INTERVAL '18 hours', 1),
    (8, CURRENT_TIMESTAMP, 'Event 8', ARRAY[0], true, 'Wroclaw', 50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '17 days' + INTERVAL '18 hours', 1),
    (9, CURRENT_TIMESTAMP, 'Event 9', ARRAY[0], true, 'Wroclaw', 50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '17 days' + INTERVAL '18 hours', 1),
    (10, CURRENT_TIMESTAMP, 'Event 10', ARRAY[0], true, 'Wroclaw', 50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '17 days' + INTERVAL '18 hours', 1);

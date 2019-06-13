insert into auth (id, role, login, password)
values (1, 'Controller', 'testc1', 'testc1'), (2, 'Controller', 'testc2', 'testc2'), (3, 'Controller', 'testc3', 'testc3'),
       (4, 'Admin', 'testa1', 'testa1');

insert into controllers(id, auth_id) values
(1, 1), (2,2), (3, 3);

insert into admins(id, auth_id) values
(1, 4);

insert into zones(id, description, controller_id) values
(1, 'ul. Cechowa', 1), (2, 'ul. Wolna', 2), (3, 'ul. Prezydencka', 3);

insert into parking_places(zone_id, id) values
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9), (1,10), (1, 11), (1, 12), (1, 13), (1, 14), (1,15), (1, 16), (1, 17), (1, 18), (1, 19), (1, 20),
(2, 21), (2, 22), (2, 23), (2, 24), (2, 25), (2, 26), (2, 27), (2, 28), (2, 29), (2,30), (2, 31), (2, 32), (2, 33), (2, 34), (2,35), (2, 36), (2, 37), (2, 38), (2, 39), (2, 40),
(3, 41), (3, 42), (3, 43), (3, 44), (3, 45), (3, 46), (3, 47), (3, 48), (3, 49), (3, 50), (3, 51), (3, 52), (3, 53), (3, 54), (3, 55), (3, 56), (3, 57), (3, 58), (3, 59), (3, 60);
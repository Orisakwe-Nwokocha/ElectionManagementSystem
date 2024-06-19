TRUNCATE TABLE voters CASCADE;
TRUNCATE TABLE admin CASCADE;

INSERT INTO voters(id, name, identification_number, password, address, date_of_birth, state_of_origin,
                   status, voting_id, role, date_registered) VALUES
(100, 'name1', 'identificationNumber1', 'password1', 'address1', '2024-06-19', 'state1', true, 1,
 'VOTER', '2024-06-04T15:03:03.792009700');


INSERT INTO admin(id, address, username, role, dateRegistered, dateUpdated)
VALUES
    ('400', '123 Main St, Semicolon Lagos', 'penIsUp', 'ADMIN', '2024-06-04T15:03:03.792009700', '2024-09-04T15:04:03.792009700'),

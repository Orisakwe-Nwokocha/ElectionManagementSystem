TRUNCATE TABLE voters CASCADE;
TRUNCATE TABLE admin CASCADE;
TRUNCATE TABLE elections CASCADE;
TRUNCATE TABLE candidates CASCADE;

INSERT INTO voters(id, name, identification_number, password, address, date_of_birth,
                    state_of_origin, status, voting_id, role, date_registered) VALUES
(100, 'Jane Doe', 'ID654321', 'password', '456 Elm St', '1985-02-15', 'Lagos', true,
 654321, 'VOTER', '2023-01-01T00:00:00');

INSERT INTO admin(id, address, username, password, role, date_registered) VALUES
(200, 'address', 'username', 'password', 'ADMIN', '2024-06-04T15:03:03.792009700');

INSERT INTO elections(id, title, category, start_date, end_date) VALUES
(300, 'title', 'LGA', '2024-06-04T15:03:03.792009700', '2024-06-19 12:00:00.000000'),
(301, 'State', 'STATE', '2027-08-22T15:03:03.792009700', '2027-09-21 12:00:00.000000');

INSERT INTO candidates(id, name, identification_number, password, address, date_of_birth,
                   state_of_origin, voting_id, role, party_affiliation, position_contested,
                       election_id, date_registered) VALUES
(400, 'name', '401104', 'password', 'address', '1965-05-30', 'Kano', 401104, 'CANDIDATE',
 'PDP', 'STATE', 301, '2023-01-01T00:00:00');

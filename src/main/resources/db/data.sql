TRUNCATE TABLE voters CASCADE;
TRUNCATE TABLE admin CASCADE;
TRUNCATE TABLE candidates CASCADE;

INSERT INTO voters(id, name, identification_number, password, address, date_of_birth,
                    state_of_origin, status, voting_id, role, date_registered) VALUES
(100, 'Jane Doe', 'ID654321', 'password', '456 Elm St', '1985-02-15', 'Lagos', true,
 654321, 'VOTER', '2023-01-01T00:00:00');

INSERT INTO admin(id, address, username, password, role, date_registered) VALUES
(200, 'address', 'username', 'password', 'ADMIN', '2024-06-04T15:03:03.792009700');

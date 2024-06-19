TRUNCATE TABLE voters CASCADE;

INSERT INTO voters(id, name, identification_number, password, address, date_of_birth, state_of_origin,
                   status, voting_id, role, date_registered) VALUES
(100, 'name1', 'identificationNumber1', 'password1', 'address1', '2024-06-19', 'state1', true, 1,
 'VOTER', '2024-06-04T15:03:03.792009700');
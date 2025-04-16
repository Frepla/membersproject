INSERT INTO addresses (street, postal_code, city) VALUES ('Storgatan 1', '12345', 'Stockholm');
INSERT INTO addresses (street, postal_code, city) VALUES ('Kungsgatan 22', '23456', 'Göteborg');
INSERT INTO addresses (street, postal_code, city) VALUES ('Lundavägen 10', '34567', 'Malmö');
INSERT INTO addresses (street, postal_code, city) VALUES ('Växjövägen 7', '45678', 'Växjö');
INSERT INTO addresses (street, postal_code, city) VALUES ('Umeågatan 5', '56789', 'Umeå');

INSERT INTO members (first_name, last_name, address_id, email, phone, date_of_birth)
VALUES ('Anna', 'Andersson', 1, 'anna@example.com', '0701234567', '1990-01-01');

INSERT INTO members (first_name, last_name, address_id, email, phone, date_of_birth)
VALUES ('Björn', 'Berg', 2, 'bjorn@example.com', '0707654321', '1985-05-15');

INSERT INTO members (first_name, last_name, address_id, email, phone, date_of_birth)
VALUES ('Carla', 'Carlsson', 3, 'carla@example.com', '0738248211', '1992-08-20');

INSERT INTO members (first_name, last_name, address_id, email, phone, date_of_birth)
VALUES ('David', 'Dahl', 1, 'david@example.com', '0709988776', '1980-03-10');

INSERT INTO members (first_name, last_name, address_id, email, phone, date_of_birth)
VALUES ('Eva', 'Ek', 4, 'eva@example.com', '0734567890', '1995-12-12');

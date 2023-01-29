INSERT INTO department (department_name)
VALUES
    ('Legal'),
    ('Sales'),
    ('Accounting');

INSERT INTO employee (employee_name, employee_surname, employee_address, employee_profession)
VALUES
    ('Ivan', 'Ivanov', 'Minsk, Victory Street 1', 'Lawyer'),
    ('Petr', 'Petrov', 'Minsk, Tesla Street 5', 'Sales manager'),
    ('Alesya', 'Sidorova', 'Minsk, Fabric Street 12', 'Accountant'),
    ('Maria', 'Popova', 'Minsk, Central Street 7', 'Accountant');

INSERT INTO personalCard (department_id, employee_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (3, 4);


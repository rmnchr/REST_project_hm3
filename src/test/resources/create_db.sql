CREATE TABLE department
(
    department_id IDENTITY NOT NULL,
    department_name TEXT,
    PRIMARY KEY (department_id)
);

CREATE TABLE employee
(
    employee_id IDENTITY NOT NULL,
    employee_name TEXT,
    employee_surname TEXT,
    employee_address TEXT,
    employee_profession TEXT,
    PRIMARY KEY (employee_id)
);

CREATE TABLE personalCard
(
    personalCard_id IDENTITY NOT NULL,
    department_id INTEGER,
    employee_id INTEGER,
    PRIMARY KEY (personalCard_id),
    FOREIGN KEY (department_id) REFERENCES department (department_id) ON DELETE CASCADE,
    FOREIGN KEY (employee_id) REFERENCES employee (employee_id) ON DELETE CASCADE
);

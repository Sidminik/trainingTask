CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    company_id BIGINT,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    phone_number VARCHAR(255) );

CREATE TABLE companies (
    id SERIAL PRIMARY KEY,
    company_name VARCHAR(255),
    budget NUMERIC );

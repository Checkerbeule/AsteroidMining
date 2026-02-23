-- Create the asteroids tables
CREATE TABLE IF NOT EXISTS asteroids (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    risk_profile VARCHAR(20) NOT NULL,
    distance NUMERIC NOT NULL
);
-- Create the mapping table for an asteroids resources
CREATE TABLE IF NOT EXISTS asteroid_resource (
    asteroid_id INTEGER REFERENCES asteroids(id),
    resource_type VARCHAR(20) NOT NULL,
    amount INTEGER NOT NULL,
    PRIMARY KEY (asteroid_id, resource_type)
);
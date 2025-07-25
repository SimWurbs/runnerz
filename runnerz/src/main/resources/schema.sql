DROP TABLE IF EXISTS run;

CREATE TABLE IF NOT EXISTS run (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    started_on timestamp NOT NULL,
    completed_on timestamp NOT NULL,
    miles INT NOT NULL,
    location VARCHAR(255) NOT NULL,
    version INT DEFAULT 0
);
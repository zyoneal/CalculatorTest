DROP TABLE IF EXISTS expression CASCADE;
DROP TABLE IF EXISTS result;

CREATE TABLE expression(
    id SERIAL PRIMARY KEY,
    title VARCHAR (255),
    result real
);

CREATE TABLE IF NOT EXISTS BOARDS (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name VARCHAR(50),
    boardkey VARCHAR(50),
    color INT,
    password VARCHAR(255)
);
DELETE FROM BOARDS WHERE id = 0;
INSERT INTO BOARDS (id, name, boardkey, color, password) VALUES
    (0, 'Public Board','public', 0, '');
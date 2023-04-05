CREATE TABLE IF NOT EXISTS BOARDS (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name VARCHAR(50),
    b_color INT,
    f_color INT,
    boardkey VARCHAR(50),
    password VARCHAR(255)
);

DELETE FROM BOARDS WHERE id = 0;

INSERT INTO BOARDS (id, name, b_color, f_color, boardkey, password, listb, listt) VALUES
    (0, 'Public Board', 2580,  13421772, 'public', '', 11776947, 0);



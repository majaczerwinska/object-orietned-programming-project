CREATE TABLE IF NOT EXISTS BOARDS (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name VARCHAR(50),
    boardkey VARCHAR(50),
    color INT,
    password VARCHAR(255)
);
CREATE TABLE IF NOT EXISTS LISTS (
    list_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    list_name VARCHAR(50),
    list_color INT,
    board_id INT NOT NULL
);
DELETE FROM BOARDS WHERE id = 0;
INSERT INTO BOARDS (id, name, boardkey, color, password) VALUES
    (0, 'Public Board','public', 0, '');
DELETE FROM LISTS WHERE list_id = 0;
INSERT INTO LISTS (list_id,  list_name, list_color, board_id) VALUES
    (0,'list1', 1, 0);
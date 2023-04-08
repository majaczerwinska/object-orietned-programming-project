CREATE TABLE IF NOT EXISTS BOARDS (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name VARCHAR(50),
    b_color INT,
    f_color INT,
    boardkey VARCHAR(50),
    password VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS palettes (
    palette_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    palette_name VARCHAR(250),
    bgd_color INT,
    font_color INT,
    isdefault BIT,
    FOREIGN KEY (board_id) REFERENCES BOARDS (id)
);

DELETE FROM BOARDS WHERE id = 0;

INSERT INTO BOARDS (id, name, b_color, f_color, boardkey, password, listb, listt) VALUES
    (0, 'Public Board', 3368550,  16777215, 'public', '', 1723725, 16777215);

INSERT INTO palettes (palette_id, palette_name, bgd_color, font_color, isdefault, board_id) VALUES
(0, 'default', 16777215, 0, true, 0);


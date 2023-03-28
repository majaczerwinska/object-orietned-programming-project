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
    board_id INT NOT NULL,
    list_height INT
);
CREATE TABLE IF NOT EXISTS TAGS (
     tag_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
     tag_name VARCHAR(50),
     tag_color INT,
     board_id INT NOT NULL
);
CREATE TABLE IF NOT EXISTS CARDS (
     card_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
     card_name VARCHAR(50),
     card_description VARCHAR(255),
     card_position DOUBLE,
     card_color INT,
     list_id INT NOT NULL
);
CREATE TABLE if not exists CARDS_TAGS (
    tag_id INT NOT NULL,
    card_id INT NOT NULL,
    PRIMARY KEY (tag_id, card_id),
    FOREIGN KEY (tag_id) REFERENCES TAGS(tag_id),
    FOREIGN KEY (card_id) REFERENCES CARDS(card_id)
);
DELETE FROM BOARDS WHERE id = 0;
INSERT INTO BOARDS (id, name, boardkey, color, password) VALUES
    (0, 'Public Board','public', 0, '');
DELETE FROM LISTS WHERE list_id = 0;
INSERT INTO LISTS (list_id,  list_name, list_color, board_id, list_height) VALUES
    (0,'list1', 1, 0, 1);

DELETE FROM LISTS WHERE list_id = 1;
INSERT INTO LISTS (list_id,  list_name, list_color, board_id, list_height) VALUES
    (1,'list2', 1, 0, 0);

DELETE FROM CARDS WHERE card_id = 0;
INSERT INTO CARDS (card_id,  card_name, card_description, card_color, card_position, list_id) VALUES
    (0,'sql test card', 'added description', 432, 0, 0);

DELETE FROM TAGS WHERE tag_id = 0;
INSERT INTO TAGS (tag_id, tag_name, tag_color, board_id) VALUES ( 0 , 'my tag' , 87659943 , 0 );

DELETE FROM CARDS_TAGS
WHERE tag_id = 0 AND card_id = 0;
-- INSERT INTO CARDS_TAGS (tag_id, card_id) VALUES ( 0, 0 );

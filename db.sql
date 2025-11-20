CREATE DATABASE IF NOT EXISTS ui_and_db_inclass;
USE ui_and_db_inclass;

CREATE TABLE IF NOT EXISTS translations
(
    Key_name         VARCHAR(100) NOT NULL,
    Language_code    VARCHAR(10)  NOT NULL,
    translation_text TEXT         NOT NULL,
    PRIMARY KEY (Key_name, Language_code)
);

SELECT * FROM translations;
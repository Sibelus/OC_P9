DROP DATABASE IF EXISTS `mediscreen`;
CREATE DATABASE mediscreen;
USE mediscreen;

-- TABLE CREATION
DROP TABLE IF EXISTS `personalrecord`;

CREATE TABLE personalrecord (
`id` INTEGER NOT NULL AUTO_INCREMENT,
`firstname` VARCHAR(100) NOT NULL,
`lastname` VARCHAR(100) NOT NULL,
`birthdate` DATE NOT NULL,
`sex` VARCHAR(1) NOT NULL,
`address` VARCHAR(100),
`phone` VARCHAR(100),
PRIMARY KEY (`id`)
);


-- DATA INSERTION
INSERT INTO `personalrecord` (`id`, `firstname`, `lastname`, `birthdate`, `sex`, `address`, `phone`) VALUES
(1, 'Florence', 'Aignement', '1970-12-31', 'F', 'Secret service headquarter', '100-222-3333'),
(2, 'Timon', 'Pote', '1949-03-27', 'M', 'Hakuna matata bay', '100-222-3333'),
(3, 'Teo', 'style', '2008-02-28', 'M', 'Down town street', '100-222-3333'),
(4, 'Steff', 'Bihaille', '2006-05-03', 'F', 'Secret service headquarter', '100-222-3333');
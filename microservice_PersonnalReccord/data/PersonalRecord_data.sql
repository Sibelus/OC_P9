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
`address` VARCHAR(100) NOT NULL,
`phone` VARCHAR(100) NOT NULL,
PRIMARY KEY (`id`)
);


-- DATA INSERTION
INSERT INTO `personalrecord` (`id`, `firstname`, `lastname`, `birthdate`, `sex`, `address`, `phone`) VALUES
(1, 'Florence', 'Aignement', '1966-12-31', 'F', 'Secret service headquarter', '100-222-3333'),
(2, 'Steff', 'Bihaille', '1983-03-27', 'F', 'Secret service headquarter', '100-222-3333');
-- drop for testing so any new app contexts that are spawned will be fresh
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
	`id` LONG AUTO_INCREMENT,
    `forename` VARCHAR(255) NOT NULL,
    `surname` VARCHAR(255) NOT NULL,
    `age` INT NOT NULL,
    PRIMARY KEY(`id`),
    -- <> is not equal to
    CHECK(`forename` <> ''),
    CHECK(`surname` <> ''),
    CHECK(`age` >= 18),
    CHECK(`age` <= 130)
);
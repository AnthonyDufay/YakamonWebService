-- Create schema
CREATE SCHEMA IF NOT EXISTS `webservice`;

-- Create tables
CREATE TABLE IF NOT EXISTS `webservice`.`type`
(
    `name` VARCHAR(128) NOT NULL,
    PRIMARY KEY (`name`)
);

CREATE TABLE IF NOT EXISTS `webservice`.`zone`
(
    `name` VARCHAR(128) NOT NULL,
    PRIMARY KEY (`name`)
);


CREATE TABLE IF NOT EXISTS `webservice`.`move`
(
    `name` VARCHAR(128) NOT NULL,
    PRIMARY KEY (`name`)
);

CREATE TABLE IF NOT EXISTS `webservice`.`trainer`
(
    `uuid` UUID         NOT NULL,
    `name` VARCHAR(128) NOT NULL,
    PRIMARY KEY (`uuid`)
);

CREATE TABLE IF NOT EXISTS `webservice`.`yakadex`
(
    `name` VARCHAR(128) NOT NULL,
    `previous_evolution` VARCHAR(128),
    `next_evolution` VARCHAR(128),
    PRIMARY KEY(`name`),
    FOREIGN KEY(`previous_evolution`) REFERENCES `webservice`.`yakadex`(`name`) ,
    FOREIGN KEY(`next_evolution`) REFERENCES `webservice`.`yakadex`(`name`)
 );

CREATE TABLE IF NOT EXISTS `webservice`.`yakadex_moves`
(
    `id` INT NOT NULL,
    `yakadex_id` VARCHAR(128) NOT NULL,
    `move_id` VARCHAR(128) NOT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`yakadex_id`) REFERENCES `webservice`.`yakadex`(`name`) ,
    FOREIGN KEY(`move_id`) REFERENCES `webservice`.`move`(`name`)
 );

CREATE TABLE IF NOT EXISTS `webservice`.`yakadex_zones`
(
    `id` INT NOT NULL,
    `yakadex_id` VARCHAR(128) NOT NULL,
    `zone_id` VARCHAR(128) NOT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`yakadex_id`) REFERENCES `webservice`.`yakadex`(`name`) ,
    FOREIGN KEY(`zone_id`) REFERENCES `webservice`.`zone`(`name`),
    CONSTRAINT UC_yakadex_zones UNIQUE (`yakadex_id`, `zone_id`)
 );

CREATE TABLE IF NOT EXISTS `webservice`.`yakadex_types`
(
    `id` INT NOT NULL,
    `yakadex_id` VARCHAR(128) NOT NULL,
    `type_id` VARCHAR(128) NOT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`yakadex_id`) REFERENCES `webservice`.`yakadex`(`name`) ,
    FOREIGN KEY(`type_id`) REFERENCES `webservice`.`type`(`name`)
);

CREATE TABLE IF NOT EXISTS `webservice`.`yakamon`
(
    `id` UUID NOT NULL,
    `name` VARCHAR(128) NULL,
    `yakadex_id` VARCHAR(128) NOT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`yakadex_id`) REFERENCES `webservice`.`yakadex`(`name`)
);

CREATE TABLE IF NOT EXISTS `webservice`.`trainer_yakamon`
(
    `id` INT NOT NULL AUTO_INCREMENT,
    `trainer_id` UUID NOT NULL,
    `yakamon_id` UUID NOT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`yakamon_id`) REFERENCES `webservice`.`yakamon`(`id`),
    FOREIGN KEY(`trainer_id`) REFERENCES `webservice`.`trainer`(`uuid`),
    UNIQUE (`yakamon_id`)
);

CREATE TABLE IF NOT EXISTS `webservice`.`yakamon_zone`
(
    `id` INT NOT NULL AUTO_INCREMENT,
    `yakamon_id` UUID NOT NULL,
    `zone_id` VARCHAR(128) NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`yakamon_id`) REFERENCES `webservice`.`yakamon`(`id`),
    FOREIGN KEY (`zone_id`) REFERENCES `webservice`.`zone`(`name`),
    UNIQUE (`yakamon_id`)
);


-- Insert Datas
INSERT INTO `webservice`.`type`
VALUES ('Perm'),
       ('Developer'),
       ('Subject'),
       ('Management');

INSERT INTO `webservice`.`zone`
VALUES ('MidLab'),
       ('LabSR'),
       ('Cisco'),
       ('SM14'),
       ('Pasteur'),
       ('Lab YAKA'),
       ('Lab ACU');

INSERT INTO `webservice`.`move`
VALUES ('Check presence'),
       ('Convocation'),
       ('Subject Modification'),
       ('Post a News'),
       ('Deployment'),
       ('Review'),
       ('Intranet Development'),
       ('Help');

INSERT INTO `webservice`.`trainer`
VALUES (1, 'Segfault'),
       (2, 'Mojojojo'),
       (3, 'Joel Courtois');

INSERT INTO `webservice`.`yakadex`
VALUES ('Chef YAKA', null, null),
       ('Chef ACU', null, null),

       ('Responsable des Assistants', null, null),

       ('Developer YAKA', null, null),
       ('Developer ACU', null, null),

       ('Deadline YAKA', null, null),
       ('Deadline ACU', null, null),

       ('Reviewer YAKA', null, null),
       ('Reviewer ACU', null, null),

       ('Maintainer YAKA', null, null),
       ('Maintainer ACU', null, null),

       ('Random YAKA', null, null),
       ('Random ACU', null, null);

UPDATE `webservice`.`yakadex`
SET `next_evolution` = 'Chef ACU'
WHERE `name` = 'Chef YAKA';

UPDATE `webservice`.`yakadex`
SET `previous_evolution` = 'Chef YAKA'
WHERE `name` = 'Chef ACU';

UPDATE `webservice`.`yakadex`
SET `next_evolution` = 'Responsable des Assistants'
WHERE `name` = 'Chef ACU';

UPDATE `webservice`.`yakadex`
SET `previous_evolution` = 'Chef ACU'
WHERE `name` = 'Responsable des Assistants';

UPDATE `webservice`.`yakadex`
SET `next_evolution` = 'Deadline ACU'
WHERE `name` = 'Deadline YAKA';

UPDATE `webservice`.`yakadex`
SET `previous_evolution` = 'Deadline YAKA'
WHERE `name` = 'Deadline ACU';

UPDATE `webservice`.`yakadex`
SET `next_evolution` = 'Maintainer ACU'
WHERE `name` = 'Maintainer YAKA';

UPDATE `webservice`.`yakadex`
SET `previous_evolution` = 'Maintainer YAKA'
WHERE `name` = 'Maintainer ACU';

UPDATE `webservice`.`yakadex`
SET `next_evolution` = 'Reviewer ACU'
WHERE `name` = 'Reviewer YAKA';

UPDATE `webservice`.`yakadex`
SET `previous_evolution` = 'Reviewer YAKA'
WHERE `name` = 'Reviewer ACU';

UPDATE `webservice`.`yakadex`
SET `next_evolution` = 'Developer ACU'
WHERE `name` = 'Developer YAKA';

UPDATE `webservice`.`yakadex`
SET `previous_evolution` = 'Developer YAKA'
WHERE `name` = 'Developer ACU';

UPDATE `webservice`.`yakadex`
SET `next_evolution` = 'Random ACU'
WHERE `name` = 'Random YAKA';

UPDATE `webservice`.`yakadex`
SET `previous_evolution` = 'Random YAKA'
WHERE `name` = 'Random ACU';

INSERT INTO `webservice`.`yakadex_moves`
VALUES (1, 'Chef YAKA', 'Convocation'),
       (2, 'Chef YAKA', 'Post a News'),
       (3, 'Chef ACU', 'Convocation'),
       (4, 'Chef ACU', 'Post a News'),

       (5, 'Responsable des Assistants', 'Deployment'),
       (6, 'Responsable des Assistants', 'Convocation'),
       (7, 'Responsable des Assistants', 'Intranet Development'),

       (8, 'Developer YAKA', 'Intranet Development'),
       (9, 'Developer YAKA', 'Deployment'),
       (10, 'Developer ACU', 'Intranet Development'),
       (11, 'Developer ACU', 'Deployment'),

       (12, 'Reviewer YAKA', 'Help'),
       (13, 'Reviewer YAKA', 'Subject Modification'),
       (14, 'Reviewer YAKA', 'Review'),
       (15, 'Reviewer ACU', 'Help'),
       (16, 'Reviewer ACU', 'Subject Modification'),
       (17, 'Reviewer ACU', 'Review'),

       (18, 'Deadline YAKA', 'Help'),
       (19, 'Deadline YAKA', 'Post a News'),
       (20, 'Deadline YAKA', 'Check presence'),
       (21, 'Deadline ACU', 'Help'),
       (22, 'Deadline ACU', 'Post a News'),
       (23, 'Deadline ACU', 'Check presence'),

       (24, 'Maintainer YAKA', 'Help'),
       (25, 'Maintainer YAKA', 'Subject Modification'),
       (26, 'Maintainer YAKA', 'Post a News'),
       (27, 'Maintainer YAKA', 'Deployment'),
       (28, 'Maintainer ACU', 'Help'),
       (29, 'Maintainer ACU', 'Subject Modification'),
       (30, 'Maintainer ACU', 'Post a News'),
       (31, 'Maintainer ACU', 'Deployment'),

       (32, 'Random YAKA', 'Help'),
       (33, 'Random ACU', 'Help');

INSERT INTO `webservice`.`yakadex_zones`
VALUES (1, 'Chef YAKA', 'Lab YAKA'),
       (2, 'Chef ACU', 'Lab YAKA'),
       (3, 'Chef ACU', 'Lab ACU'),
       (4, 'Chef ACU', 'Cisco'),

       (5, 'Responsable des Assistants', 'Lab YAKA'),
       (6, 'Responsable des Assistants', 'Lab ACU'),

       (7, 'Developer YAKA', 'Lab YAKA'),
       (8, 'Developer ACU', 'Lab ACU'),
       (51, 'Developer ACU', 'Lab YAKA'),

       (9, 'Deadline YAKA', 'Lab YAKA'),
       (10, 'Deadline YAKA', 'LabSR'),
       (11, 'Deadline YAKA', 'MidLab'),
       (12, 'Deadline YAKA', 'SM14'),
       (13, 'Deadline ACU', 'Lab ACU'),
       (14, 'Deadline ACU', 'LabSR'),
       (15, 'Deadline ACU', 'MidLab'),
       (16, 'Deadline ACU', 'SM14'),

       (17, 'Reviewer YAKA', 'Lab YAKA'),
       (18, 'Reviewer YAKA', 'LabSR'),
       (19, 'Reviewer YAKA', 'SM14'),
       (20, 'Reviewer YAKA', 'Pasteur'),
       (21, 'Reviewer YAKA', 'MidLab'),
       (22, 'Reviewer ACU', 'Lab ACU'),
       (23, 'Reviewer ACU', 'LabSR'),
       (24, 'Reviewer ACU', 'SM14'),
       (25, 'Reviewer ACU', 'Pasteur'),
       (26, 'Reviewer ACU', 'MidLab'),

       (27, 'Maintainer YAKA', 'Lab YAKA'),
       (28, 'Maintainer YAKA', 'LabSR'),
       (29, 'Maintainer YAKA', 'SM14'),
       (30, 'Maintainer YAKA', 'Pasteur'),
       (31, 'Maintainer YAKA', 'MidLab'),
       (32, 'Maintainer YAKA', 'Cisco'),
       (33, 'Maintainer ACU', 'Lab ACU'),
       (34, 'Maintainer ACU', 'LabSR'),
       (35, 'Maintainer ACU', 'SM14'),
       (36, 'Maintainer ACU', 'Pasteur'),
       (37, 'Maintainer ACU', 'MidLab'),
       (38, 'Maintainer ACU', 'Cisco'),

       (39, 'Random YAKA', 'Lab YAKA'),
       (40, 'Random YAKA', 'LabSR'),
       (41, 'Random YAKA', 'SM14'),
       (42, 'Random YAKA', 'Pasteur'),
       (43, 'Random YAKA', 'MidLab'),
       (44, 'Random YAKA', 'Cisco'),
       (45, 'Random ACU', 'Lab ACU'),
       (46, 'Random ACU', 'LabSR'),
       (47, 'Random ACU', 'SM14'),
       (48, 'Random ACU', 'Pasteur'),
       (49, 'Random ACU', 'MidLab'),
       (50, 'Random ACU', 'Cisco');

INSERT INTO `webservice`.`yakadex_types`
VALUES (1, 'Chef YAKA', 'Management'),
       (2, 'Chef ACU', 'Management'),

       (3, 'Responsable des Assistants', 'Management'),
       (4, 'Responsable des Assistants', 'Developer'),

       (5, 'Deadline YAKA', 'Management'),
       (6, 'Deadline YAKA', 'Perm'),
       (7, 'Deadline ACU', 'Management'),
       (8, 'Deadline ACU', 'Perm'),

       (9, 'Reviewer YAKA', 'Subject'),
       (10, 'Reviewer YAKA', 'Perm'),
       (11, 'Reviewer ACU', 'Subject'),
       (12, 'Reviewer ACU', 'Perm'),

       (13, 'Maintainer YAKA', 'Subject'),
       (14, 'Maintainer YAKA', 'Developer'),
       (15, 'Maintainer YAKA', 'Perm'),
       (16, 'Maintainer ACU', 'Developer'),
       (17, 'Maintainer ACU', 'Perm'),

       (18, 'Developer YAKA', 'Developer'),
       (19, 'Developer ACU', 'Developer'),

       (20, 'Random YAKA', 'Perm'),
       (21, 'Random ACU', 'Perm');


INSERT INTO `webservice`.`yakamon`
VALUES (1, 'theophane.vie', 'Chef YAKA'),
       (2, 'bernar_-', 'Chef ACU'),
       (3, 'laskar_g', 'Responsable des Assistants'),

       (4, 'stanislas.sokolov', 'Deadline YAKA'),
       (5, 'lisa.crasnish', 'Deadline YAKA'),
       (6, 'decret_t', 'Deadline ACU'),

       (7, 'sarah.le-moigne', 'Reviewer YAKA'),
       (8, 'quentin.barbarat', 'Reviewer YAKA'),

       (9, 'xavier.login', 'Responsable des Assistants'),

       (10, 'antoine.montes', 'Maintainer YAKA'),
       (11, 'valentin1.henry', 'Maintainer YAKA'),
       (12, 'amandine.nassiri', 'Maintainer YAKA'),
       (13, 'foucher_r', 'Maintainer YAKA'),
       (14, 'auer_e', 'Maintainer ACU'),
       (15, null, 'Maintainer YAKA'),
       (16, null, 'Maintainer YAKA'),
       (17, null, 'Maintainer YAKA'),
       (18, null, 'Maintainer YAKA'),
       (19, null, 'Maintainer ACU'),
       (20, null, 'Maintainer ACU'),

       (21, 'marc.vilain', 'Developer YAKA'),
       (22, 'suel_a', 'Developer ACU'),
       (23, null, 'Developer YAKA'),
       (24, null, 'Developer YAKA'),
       (25, null, 'Developer ACU'),
       (26, null, 'Developer ACU'),

       (27, null, 'Random YAKA'),
       (28, null, 'Random YAKA'),
       (29, null, 'Random YAKA'),
       (30, null, 'Random YAKA'),
       (31, null, 'Random YAKA'),
       (32, null, 'Random YAKA'),
       (33, null, 'Random YAKA'),
       (34, null, 'Random YAKA'),
       (35, null, 'Random YAKA'),
       (36, null, 'Random YAKA'),
       (37, null, 'Random ACU'),
       (38, null, 'Random ACU'),
       (39, null, 'Random ACU'),
       (40, null, 'Random ACU'),
       (41, null, 'Random ACU'),
       (42, null, 'Random ACU'),
       (43, null, 'Random ACU'),

       (44, null, 'Deadline YAKA'),
       (45, null, 'Random ACU'),
       (46, null, 'Random ACU'),
       (47, null, 'Random YAKA');

INSERT INTO `webservice`.`trainer_yakamon`
VALUES (1, 1, 1),    -- Chef YAKA
       (2, 1, 2),    -- Chef ACU
       (3, 1, 3),    -- Responsable des Assistants
       (4, 1, 4),    -- Deadline YAKA
       (5, 1, 5),    -- Deadline YAKA
       (6, 1, 6),    -- Deadline ACU
       (7, 1, 7),    -- Reviewer YAKA
       (8, 2, 8),    -- Reviewer YAKA
       (9, 2, 9),    -- Responsable des Assistants
       (10, 2, 10),  -- Maintainer YAKA
       (11, 2, 11),  -- Maintainer YAKA
       (12, 2, 12),  -- Maintainer YAKA
       (13, 2, 13),  -- Maintainer YAKA
       (14, 3, 14),  -- Maintainer ACU
       (15, 3, 20),  -- Maintainer ACU
       (16, 3, 21),  -- Developer YAKA
       (17, 1, 27),  -- Random YAKA
       (18, 1, 28),  -- Random YAKA
       (19, 2, 29),  -- Random YAKA
       (20, 3, 30),  -- Random YAKA
       (21, 3, 31),  -- Random YAKA
       (22, 3, 43);  -- Random ACU


INSERT INTO `webservice`.`yakamon_zone`
VALUES
       (1, 16, 'Cisco'),   -- Maintainer YAKA
       (2, 38, 'Cisco'),   -- Random ACU

       (3, 19, 'Pasteur'), -- Maintainer ACU
       (4, 33, 'Pasteur'), -- Random YAKA
       (5, 39, 'Pasteur'), -- Random ACU

       (6, 23, 'Lab YAKA'),-- Developer YAKA
       (7, 24, 'Lab YAKA'),-- Developer YAKA
       (8, 26, 'Lab YAKA'),-- Developer ACU

       (9, 22, 'Lab ACU'), -- Developer ACU
       (10, 25, 'Lab ACU'),-- Developer ACU
       (11, 41, 'Lab ACU'),-- Random ACU

       (12, 34, 'SM14'),   -- Random YAKA
       (13, 35, 'SM14'),   -- Random YAKA
       (14, 36, 'SM14'),   -- Random YAKA
       (15, 37, 'SM14'),   -- Random ACU
       (16, 18, 'SM14'),   -- Maintainer YAKA

       (17, 44, 'MidLab'), -- Deadline YAKA
       (18, 40, 'MidLab'), -- Random ACU
       (19, 45, 'MidLab'), -- Random ACU
       (20, 46, 'MidLab'), -- Random ACU
       (21, 42, 'MidLab'), -- Random ACU
       (22, 32, 'MidLab'), -- Random YAKA
       (23, 47, 'MidLab'), -- Random YAKA
       (24, 15, 'MidLab'); -- Maintainer YAKA



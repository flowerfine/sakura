create database if not exists sakura default character set utf8mb4 collate utf8mb4_unicode_ci;
use sakura;

DROP TABLE IF EXISTS `catalog_store`;
CREATE TABLE `catalog_store`
(
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    type          VARCHAR(8) NOT NULL,
    catalog_name  VARCHAR(256) NOT NULL,
    configuration TEXT,
    create_time   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    delete_time   DATETIME,
    PRIMARY KEY (id),
    UNIQUE KEY uniq_catalog (type, catalog_name, delete_time)
) ENGINE = InnoDB COMMENT ='catalog';

DROP TABLE IF EXISTS `catalog_database`;
CREATE TABLE `catalog_database`
(
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    type        VARCHAR(8) NOT NULL,
    catalog     VARCHAR(256) NOT NULL,
    `name`      VARCHAR(256) NOT NULL,
    properties  TEXT,
    remark      VARCHAR(256),
    create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    delete_time DATETIME,
    PRIMARY KEY (id),
    UNIQUE KEY uniq_name (type, catalog, `name`, delete_time)
) ENGINE = InnoDB COMMENT ='database';

DROP TABLE IF EXISTS `catalog_table`;
CREATE TABLE `catalog_table`
(
    id             BIGINT       NOT NULL AUTO_INCREMENT,
    database_id    BIGINT       NOT NULL,
    kind           VARCHAR(32)  NOT NULL,
    `name`         VARCHAR(256) NOT NULL,
    properties     TEXT,
    `schema`       TEXT,
    original_query TEXT,
    expanded_query TEXT,
    remark         VARCHAR(256),
    create_time    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    delete_time    DATETIME,
    PRIMARY KEY (id),
    UNIQUE KEY uniq_name (database_id, kind, `name`, delete_time)
) ENGINE = InnoDB COMMENT ='table';

DROP TABLE IF EXISTS catalog_function;
CREATE TABLE `catalog_function`
(
    id                BIGINT       NOT NULL AUTO_INCREMENT,
    database_id       BIGINT       NOT NULL,
    `name`            VARCHAR(256) NOT NULL,
    class_name        VARCHAR(256) NOT NULL,
    function_language VARCHAR(8)   NOT NULL,
    remark            VARCHAR(256),
    create_time       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    delete_time       DATETIME,
    PRIMARY KEY (id),
    UNIQUE KEY uniq_name (database_id, `name`, delete_time)
) ENGINE = InnoDB COMMENT ='function';

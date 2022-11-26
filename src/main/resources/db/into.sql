CREATE TABLE  IF NOT EXISTS  databasesInfo
(
    `id`   bigint,
    `name` varchar(100),
    `type` varchar(50),
    `ip` varchar(100) ,
    `port`  varchar(50),
    `userName`  varchar(255),
    `password`  varchar(255),
    `args`  varchar(255)
);
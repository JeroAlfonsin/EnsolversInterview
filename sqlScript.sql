create database ensolverintdb character set utf8;
create user user@'localhost';
set password for 'user'@'localhost' = PASSWORD('user123');
grant all on ensolverintdb.* to 'user'@'localhost';
flush privileges;


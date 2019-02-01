delimiter //
CREATE TABLE `seq` (`name` varchar(100) NOT NULL,`val` int(10) unsigned NOT NULL, PRIMARY KEY  (`name`)) ENGINE=MyISAM DEFAULT CHARSET=latin1

//
create function seq(seq_name char (100), count int) returns int
begin
 update seq set val=last_insert_id(val+count) where name=seq_name;
 return last_insert_id();
end
//
CREATE FUNCTION gettenant() 
RETURNS INT 
BEGIN
RETURN @tenant;
END

//
delimiter ;


//- WHen bin log is enabled we get this error - [HY000][1418] This function has none of DETERMINISTIC, NO SQL, or READS SQL DATA in its declaration and binary logging is enabled 
//SET GLOBAL log_bin_trust_function_creators = 1;
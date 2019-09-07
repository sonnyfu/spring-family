SELECT @@GLOBAL.tx_isolation,@@tx_isolation
-- 使用默认隔离级别
START TRANSACTION;
UPDATE Persons set Address='ZJGK' where PersonID=1
COMMIT;

-- repeatable read

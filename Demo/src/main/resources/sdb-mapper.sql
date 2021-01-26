CREATE TABLE sdb_mapper USING com.sequoiadb.spark OPTIONS(
    host '192.168.1.191:11810',
    collectionspace 'sample',
    collection 'sdb_mapper',
    user 'sdbadmin',
    password 'sdbadmin'
)
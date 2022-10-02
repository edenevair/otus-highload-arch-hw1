print('Start #################################################################');

print('user creation')
db = db.getSiblingDB('otus_hw');
db.createUser(
    {
        user: 'otus_hw_user',
        pwd: '12345678',
        roles: [{ role: 'readWrite', db: 'otus_hw' }],
    },
);

print('collection creation')
db.createCollection('chat');
db.chat.createIndex( { "user1Id": 1, "user2Id": 1 }, { unique: true } )

print('enabling sharding')
sh.enableSharding("otus_hw")

db.adminCommand( { shardCollection: "otus_hw.chat", key: { user1Id: 1 } } )

print('END #################################################################');
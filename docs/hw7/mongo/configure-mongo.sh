#!/bin/bash

echo init configsvr01
docker-compose exec configsvr01 sh -c "mongosh < /scripts/init-configserver.js"

echo init shards
docker-compose exec shard01-a sh -c "mongosh < /scripts/init-shard01.js"
docker-compose exec shard02-a sh -c "mongosh < /scripts/init-shard02.js"
docker-compose exec shard03-a sh -c "mongosh < /scripts/init-shard03.js"

sleep 10

echo init router
docker-compose exec router01 sh -c "mongosh < /scripts/init-router.js"

echo ini shardings
docker-compose exec router01 sh -c "mongosh < /scripts/init-sharding.js"
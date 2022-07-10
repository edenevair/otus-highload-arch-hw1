#!/bin/bash

docker run --platform linux/x86_64/v8 --rm --name wrk_test --net=host \
  -v /Users/skolyada/sandbox/otus/otus-highload-arch-hw1/docs/hw2/scripts:/scripts ruslanys/wrk \
  -c 1000 -t 1 -d1m --latency --timeout 40s  \
  -s /scripts/load_script.lua \
  'http://192.168.0.4:8080'
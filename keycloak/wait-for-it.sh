#!/bin/bash
# wait-for-it.sh

# Source code from https://github.com/vishnubob/wait-for-it
# This script waits for a service to become available.
# Usage:
# ./wait-for-it.sh host:port --timeout=30 -- command args

host="$1"
shift
port="$1"
shift
timeout=30
wait_for=1

while [ $wait_for -ne 0 ]
do
  nc -z "$host" "$port" && wait_for=0 || sleep 1
  timeout=$((timeout - 1))
  if [ $timeout -le 0 ]; then
    echo "Timeout waiting for $host:$port"
    exit 1
  fi
done

exec "$@"

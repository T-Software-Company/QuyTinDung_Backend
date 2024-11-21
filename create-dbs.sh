#!/bin/bash
set -e

if [ -n "$POSTGRES_INIT_DB" ]; then
  echo "Databases to create: $POSTGRES_INIT_DB"
  IFS=' ' read -r -a db_array <<< "$POSTGRES_INIT_DB"

  for db in "${db_array[@]}"; do
    echo "Creating database: $db"
    psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
		CREATE DATABASE "$db";
	EOSQL
  done
else
  echo "No databases specified in POSTGRES_INIT_DB."
fi

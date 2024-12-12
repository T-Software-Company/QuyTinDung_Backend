#!/bin/bash
set -e

echo "Running PostgreSQL initialization script..."

# Ensure the 'postgres' role exists
psql -U "$POSTGRES_USER" -d postgres -tc "SELECT 1 FROM pg_roles WHERE rolname='postgres'" | grep -q 1 || psql -U "$POSTGRES_USER" -d postgres -c "CREATE ROLE postgres WITH LOGIN SUPERUSER;"

# Read the list of databases from the environment variable
IFS=',' read -r -a db_array <<< "$POSTGRES_INIT_DB"

# Loop through each database name and create it if it doesn't exist
for db in "${db_array[@]}"; do
  echo "Checking if database $db exists..."

  # Check if the database already exists
  db_exists=$(psql -U "$POSTGRES_USER" -d postgres -t -c "SELECT 1 FROM pg_database WHERE datname = '$db'" 2>/dev/null)

  if [ -z "$db_exists" ]; then
    echo "Database $db does not exist. Creating it..."
    psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "postgres" <<-EOSQL
    CREATE DATABASE "$db";
EOSQL
    echo "Database $db created."
  else
    echo "Database $db already exists, skipping creation."
  fi
done


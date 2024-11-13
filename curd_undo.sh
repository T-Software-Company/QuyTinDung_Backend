#!/bin/bash

# Check for entity name argument
if [ -z "$1" ]; then
  echo "Usage: $0 <EntityName>"
  exit 1
fi

ENTITY_NAME=$1
FILE_PATH="com/tsoftware/qtd"
REPO_DIR="src/main/java/$FILE_PATH/repository"
SERVICE_DIR="src/main/java/$FILE_PATH/service"
IMPL_DIR="src/main/java/$FILE_PATH/service/impl"
MAPPER_DIR="src/main/java/$FILE_PATH/mapper"
CONTROLLER_DIR="src/main/java/$FILE_PATH/controller"
DTO_DIR="src/main/java/$FILE_PATH/dto"

# Delete existing files for the entity if they exist
rm -f "$REPO_DIR/${ENTITY_NAME}Repository.java"
rm -f "$SERVICE_DIR/${ENTITY_NAME}Service.java"
rm -f "$IMPL_DIR/${ENTITY_NAME}ServiceImpl.java"
rm -f "$MAPPER_DIR/${ENTITY_NAME}Mapper.java"
rm -f "$CONTROLLER_DIR/${ENTITY_NAME}Controller.java"
rm -f "$DTO_DIR/${ENTITY_NAME}Dto.java"


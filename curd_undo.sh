#!/bin/bash

# Check for entity name argument
if [ -z "$1" ]; then
  echo "Usage: $0 <EntityName>"
  exit 1
fi

ENTITY_NAME=$1
ENTITY_LOWER=$(echo "$ENTITY_NAME" | awk '{print tolower($0)}')
PACKAGE_PATH="com.tsoftware.qtd"
FILE_PATH="com/tsoftware/qtd"
REPO_DIR="src/main/java/$FILE_PATH/repository"
SERVICE_DIR="src/main/java/$FILE_PATH/service"
IMPL_DIR="src/main/java/$FILE_PATH/service/impl"
MAPPER_DIR="src/main/java/$FILE_PATH/mapper"
CONTROLLER_DIR="src/main/java/$FILE_PATH/controller"
DTO_DIR="src/main/java/$FILE_PATH/dto"
ENTITY_DIR="src/main/java/$FILE_PATH/entity"

# Delete existing files for the entity if they exist
rm -f "$REPO_DIR/${ENTITY_NAME}Repository.java"
rm -f "$SERVICE_DIR/${ENTITY_NAME}Service.java"
rm -f "$IMPL_DIR/${ENTITY_NAME}ServiceImpl.java"
rm -f "$MAPPER_DIR/${ENTITY_NAME}Mapper.java"
rm -f "$CONTROLLER_DIR/${ENTITY_NAME}Controller.java"
rm -f "$DTO_DIR/${ENTITY_NAME}Dto.java"

# Create directories if they don't exist
mkdir -p "$REPO_DIR" "$SERVICE_DIR" "$IMPL_DIR" "$MAPPER_DIR" "$CONTROLLER_DIR" "$DTO_DIR"

# Create Repository
cat <<EOF > "$REPO_DIR/${ENTITY_NAME}Repository.java"
package $PACKAGE_PATH.repository;

import $PACKAGE_PATH.entity.$ENTITY_NAME;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ${ENTITY_NAME}Repository extends JpaRepository<$ENTITY_NAME, Long> {
}
EOF

# Create Service Interface
cat <<EOF > "$SERVICE_DIR/${ENTITY_NAME}Service.java"
package $PACKAGE_PATH.service;

import $PACKAGE_PATH.dto.${ENTITY_NAME}Dto;
import java.util.List;

public interface ${ENTITY_NAME}Service {
    ${ENTITY_NAME}Dto create(${ENTITY_NAME}Dto ${ENTITY_LOWER}Dto);
    ${ENTITY_NAME}Dto update(Long id, ${ENTITY_NAME}Dto ${ENTITY_LOWER}Dto);
    void delete(Long id);
    ${ENTITY_NAME}Dto getById(Long id);
    List<${ENTITY_NAME}Dto> getAll();
}
EOF

# Create Service Implementation
cat <<EOF > "$IMPL_DIR/${ENTITY_NAME}ServiceImpl.java"
package $PACKAGE_PATH.service.impl;

import $PACKAGE_PATH.repository.${ENTITY_NAME}Repository;
import $PACKAGE_PATH.service.${ENTITY_NAME}Service;
import $PACKAGE_PATH.dto.${ENTITY_NAME}Dto;
import $PACKAGE_PATH.mapper.${ENTITY_NAME}Mapper;
import $PACKAGE_PATH.entity.$ENTITY_NAME;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ${ENTITY_NAME}ServiceImpl implements ${ENTITY_NAME}Service {

    @Autowired
    private ${ENTITY_NAME}Repository ${ENTITY_LOWER}Repository;

    @Autowired
    private ${ENTITY_NAME}Mapper ${ENTITY_LOWER}Mapper;

    @Override
    public ${ENTITY_NAME}Dto create(${ENTITY_NAME}Dto ${ENTITY_LOWER}Dto) {
        $ENTITY_NAME ${ENTITY_LOWER} = ${ENTITY_LOWER}Mapper.toEntity(${ENTITY_LOWER}Dto);
        return ${ENTITY_LOWER}Mapper.toDto(${ENTITY_LOWER}Repository.save(${ENTITY_LOWER}));
    }

    @Override
    public ${ENTITY_NAME}Dto update(Long id, ${ENTITY_NAME}Dto ${ENTITY_LOWER}Dto) {
        $ENTITY_NAME ${ENTITY_LOWER} = ${ENTITY_LOWER}Repository.findById(id).orElseThrow(() -> new RuntimeException("${ENTITY_NAME} not found"));
        ${ENTITY_L

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
MAPPER_DIR="src/main/java/$FILE_PATH/mapper"
CONTROLLER_DIR="src/main/java/$FILE_PATH/controller"
DTO_DIR="src/main/java/$FILE_PATH/dto"

# Create directories if they don't exist
mkdir -p "$REPO_DIR" "$SERVICE_DIR" "$MAPPER_DIR" "$CONTROLLER_DIR" "$DTO_DIR"

# Create Repository
REPO_FILE="$REPO_DIR/${ENTITY_NAME}Repository.java"
if [ ! -f "$REPO_FILE" ]; then
cat <<EOF > "$REPO_FILE"
package $PACKAGE_PATH.repository;

import $PACKAGE_PATH.entity.$ENTITY_NAME;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Repository
public interface ${ENTITY_NAME}Repository extends JpaRepository<$ENTITY_NAME, UUID>, JpaSpecificationExecutor<$ENTITY_NAME>  {
}
EOF
fi

# Create Service
SERVICE_FILE="$SERVICE_DIR/${ENTITY_NAME}Service.java"
if [ ! -f "$SERVICE_FILE" ]; then
cat <<EOF > "$SERVICE_FILE"
package $PACKAGE_PATH.service;

import $PACKAGE_PATH.repository.${ENTITY_NAME}Repository;
import $PACKAGE_PATH.service.${ENTITY_NAME}Service;
import $PACKAGE_PATH.dto.${ENTITY_NAME}DTO;
import $PACKAGE_PATH.mapper.${ENTITY_NAME}Mapper;
import $PACKAGE_PATH.entity.$ENTITY_NAME;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ${ENTITY_NAME}Service {

    private final ${ENTITY_NAME}Repository ${ENTITY_LOWER}Repository;

    private final ${ENTITY_NAME}Mapper ${ENTITY_LOWER}Mapper;

}
EOF
fi



# Create Mapper Interface
MAPPER_FILE="$MAPPER_DIR/${ENTITY_NAME}Mapper.java"
if [ ! -f "$MAPPER_FILE" ]; then
cat <<EOF > "$MAPPER_FILE"
package $PACKAGE_PATH.mapper;

import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import $PACKAGE_PATH.dto.${ENTITY_NAME}DTO;
import $PACKAGE_PATH.entity.$ENTITY_NAME;

@Mapper(componentModel = "spring")
public interface ${ENTITY_NAME}Mapper {
    ${ENTITY_NAME} toEntity(${ENTITY_NAME}DTO DTO);
    ${ENTITY_NAME}DTO toDTO(${ENTITY_NAME} entity);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(${ENTITY_NAME}DTO DTO, @MappingTarget ${ENTITY_NAME} entity);
}
EOF
fi

# Create Controller
CONTROLLER_FILE="$CONTROLLER_DIR/${ENTITY_NAME}Controller.java"
if [ ! -f "$CONTROLLER_FILE" ]; then
cat <<EOF > "$CONTROLLER_FILE"
package $PACKAGE_PATH.controller;

import $PACKAGE_PATH.service.${ENTITY_NAME}Service;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/${ENTITY_LOWER}s")
@RequiredArgsConstructor
public class ${ENTITY_NAME}Controller {


    private final ${ENTITY_NAME}Service ${ENTITY_LOWER}Service;

}
EOF
fi
# Create DTO
cat <<EOF > "$DTO_DIR/${ENTITY_NAME}DTO.java"
package $PACKAGE_PATH.dto;

import java.util.UUID;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ${ENTITY_NAME}DTO {
    private UUID id;
    // Add other fields based on ${ENTITY_NAME} entity
}
EOF

echo "Files for ${ENTITY_NAME} created successfully."

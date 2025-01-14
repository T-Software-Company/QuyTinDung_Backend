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

# Create directories if they don't exist
mkdir -p "$REPO_DIR" "$SERVICE_DIR" "$IMPL_DIR" "$MAPPER_DIR" "$CONTROLLER_DIR" "$DTO_DIR"

# Create Repository
REPO_FILE="$REPO_DIR/${ENTITY_NAME}Repository.java"
if [ ! -f "$REPO_FILE" ]; then
cat <<EOF > "$REPO_FILE"
package $PACKAGE_PATH.repository;

import $PACKAGE_PATH.entity.$ENTITY_NAME;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ${ENTITY_NAME}Repository extends JpaRepository<$ENTITY_NAME, UUID> {
}
EOF
fi

# Create Service Interface
SERVICE_FILE="$SERVICE_DIR/${ENTITY_NAME}Service.java"
if [ ! -f "$SERVICE_FILE" ]; then
cat <<EOF > "$SERVICE_FILE"
package $PACKAGE_PATH.service;

import $PACKAGE_PATH.dto.${ENTITY_NAME}Dto;
import java.util.List;

public interface ${ENTITY_NAME}Service {
    ${ENTITY_NAME}Dto create(${ENTITY_NAME}Dto ${ENTITY_LOWER}Dto);
    ${ENTITY_NAME}Dto update(UUID id, ${ENTITY_NAME}Dto ${ENTITY_LOWER}Dto);
    void delete(UUID id);
    ${ENTITY_NAME}Dto getById(UUID id);
    List<${ENTITY_NAME}Dto> getAll();
}
EOF
fi

# Create Service Implementation
IMPL_FILE="$IMPL_DIR/${ENTITY_NAME}ServiceImpl.java"
if [ ! -f "$IMPL_FILE" ]; then
cat <<EOF > "$IMPL_FILE"
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
        return ${ENTITY_LOWER}Mapper.toDTO(${ENTITY_LOWER}Repository.save(${ENTITY_LOWER}));
    }

    @Override
    public ${ENTITY_NAME}Dto update(UUID id, ${ENTITY_NAME}Dto ${ENTITY_LOWER}Dto) {
        $ENTITY_NAME ${ENTITY_LOWER} = ${ENTITY_LOWER}Repository.findById(id).orElseThrow(() -> new NotFoundException("${ENTITY_NAME} not found"));
        ${ENTITY_LOWER}Mapper.updateEntity(${ENTITY_LOWER}Dto, ${ENTITY_LOWER});
        return ${ENTITY_LOWER}Mapper.toDTO(${ENTITY_LOWER}Repository.save(${ENTITY_LOWER}));
    }

    @Override
    public void delete(UUID id) {
        ${ENTITY_LOWER}Repository.deleteById(id);
    }

    @Override
    public ${ENTITY_NAME}Dto getById(UUID id) {
        $ENTITY_NAME ${ENTITY_LOWER} = ${ENTITY_LOWER}Repository.findById(id).orElseThrow(() -> new NotFoundException("${ENTITY_NAME} not found"));
        return ${ENTITY_LOWER}Mapper.toDTO(${ENTITY_LOWER});
    }

    @Override
    public List<${ENTITY_NAME}Dto> getAll() {
        return ${ENTITY_LOWER}Repository.findAll().stream()
                .map(${ENTITY_LOWER}Mapper::toDTO)
                .collect(Collectors.toList());
    }
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
import $PACKAGE_PATH.dto.${ENTITY_NAME}Dto;
import $PACKAGE_PATH.entity.$ENTITY_NAME;

@Mapper(componentModel = "spring")
public interface ${ENTITY_NAME}Mapper {
    ${ENTITY_NAME} toEntity(${ENTITY_NAME}Dto dto);
    ${ENTITY_NAME}Dto toDTO(${ENTITY_NAME} entity);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(${ENTITY_NAME}Dto dto, @MappingTarget ${ENTITY_NAME} entity);
}
EOF
fi

# Create Controller
CONTROLLER_FILE="$CONTROLLER_DIR/${ENTITY_NAME}Controller.java"
if [ ! -f "$CONTROLLER_FILE" ]; then
cat <<EOF > "$CONTROLLER_FILE"
package $PACKAGE_PATH.controller;

import $PACKAGE_PATH.dto.${ENTITY_NAME}Dto;
import $PACKAGE_PATH.service.${ENTITY_NAME}Service;
import $PACKAGE_PATH.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/${ENTITY_LOWER}s")
public class ${ENTITY_NAME}Controller {

    @Autowired
    private ${ENTITY_NAME}Service ${ENTITY_LOWER}Service;

    @PostMapping
    public ResponseEntity<ApiResponse<${ENTITY_NAME}Dto>> create(@RequestBody ${ENTITY_NAME}Dto ${ENTITY_LOWER}Dto) {
        return ResponseEntity.ok(new ApiResponse<>(1000, "Created", ${ENTITY_LOWER}Service.create(${ENTITY_LOWER}Dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<${ENTITY_NAME}Dto>> update(@PathVariable UUID id, @RequestBody ${ENTITY_NAME}Dto ${ENTITY_LOWER}Dto) {
        return ResponseEntity.ok(new ApiResponse<>(1000, "Updated", ${ENTITY_LOWER}Service.update(id, ${ENTITY_LOWER}Dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        ${ENTITY_LOWER}Service.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(1000, "Deleted", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<${ENTITY_NAME}Dto>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched", ${ENTITY_LOWER}Service.getById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<${ENTITY_NAME}Dto>>> getAll() {
        return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched All", ${ENTITY_LOWER}Service.getAll()));
    }
}
EOF
fi
## Create DTO
#cat <<EOF > "$DTO_DIR/${ENTITY_NAME}Dto.java"
#package $PACKAGE_PATH.dto;
#
#import lombok.Data;
#
#@Data
#public class ${ENTITY_NAME}Dto {
#    private UUID id;
#    // Add other fields based on ${ENTITY_NAME} entity
#}
#EOF

echo "Files for ${ENTITY_NAME} created successfully."

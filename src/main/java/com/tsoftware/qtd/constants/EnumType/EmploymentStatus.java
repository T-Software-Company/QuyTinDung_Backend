package com.tsoftware.qtd.constants.EnumType;

import lombok.Getter;

@Getter
public enum EmploymentStatus {
    WORKING("Đang làm việc"),
    ON_LEAVE("Đang nghỉ phép"),
    RESIGNED("Đã nghỉ việc");

    private final String description;

    EmploymentStatus(String description) {
        this.description = description;
    }
}

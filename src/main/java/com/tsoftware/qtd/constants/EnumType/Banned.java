package com.tsoftware.qtd.constants.EnumType;

import lombok.Getter;

@Getter
public enum Banned {
    ACTIVE("Đang hoạt động"),
    LOCKED("Bị khoá");

    private final String description;

    Banned(String description) {
        this.description = description;
    }
}

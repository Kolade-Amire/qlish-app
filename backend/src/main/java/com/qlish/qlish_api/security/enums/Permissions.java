package com.qlish.qlish_api.security.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permissions {
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_WRITE("admin:write"),
    ADMIN_DELETE("admin:delete"),
    USER_DEFAULT("user:default"),
    DEV_DEFAULT("dev:default");

    private final String permission;
}

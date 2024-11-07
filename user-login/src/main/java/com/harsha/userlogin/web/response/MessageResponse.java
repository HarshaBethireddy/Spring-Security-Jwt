package com.harsha.userlogin.web.response;

public record MessageResponse(
        String message,
        String status
) {
    public static MessageResponse success(String message) {
        return new MessageResponse(message, "success");
    }

    public static MessageResponse error(String message) {
        return new MessageResponse(message, "error");
    }
}

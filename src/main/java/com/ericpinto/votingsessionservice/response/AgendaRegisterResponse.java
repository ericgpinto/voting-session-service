package com.ericpinto.votingsessionservice.response;

public record AgendaRegisterResponse(
        String id,
        String title,
        String description
) {
}

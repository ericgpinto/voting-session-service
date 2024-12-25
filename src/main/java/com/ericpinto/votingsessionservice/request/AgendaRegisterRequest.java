package com.ericpinto.votingsessionservice.request;

public record AgendaRegisterRequest(
        String title,
        String description
) {
}

package com.ericpinto.votingsessionservice.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Modelo que representa o objeto de requisição quando cria uma nova Pauta")
public record AgendaRegisterRequest(
        @Schema(description = "Titulo da pauta", example = "Pauta 1")
        String title,
        @Schema(description = "Descrição da pauta", example = "Descrição 1")
        String description
) {
}

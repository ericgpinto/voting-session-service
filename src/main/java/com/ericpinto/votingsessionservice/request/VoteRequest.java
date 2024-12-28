package com.ericpinto.votingsessionservice.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Modelo que representa o objeto de requisição enviado para votar em uma pauta")
public record VoteRequest(
        @Schema(description = "Voto do associado", example = "YES")
        String vote
) {
}

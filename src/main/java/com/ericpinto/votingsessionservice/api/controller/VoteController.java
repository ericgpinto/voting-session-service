package com.ericpinto.votingsessionservice.api.controller;

import com.ericpinto.votingsessionservice.request.VoteRequest;
import com.ericpinto.votingsessionservice.response.VoteResponse;
import com.ericpinto.votingsessionservice.service.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/votes")
@Tag(name = "API de Votações")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @Operation(summary = "Votar em uma pauta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Voto criado", content = @Content(schema = @Schema(implementation = VoteResponse.class))),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro")
    })
    @PostMapping("/agenda/{agendaId}/associate/{associateId}")
    public ResponseEntity<VoteResponse> associateVote(@PathVariable String agendaId,
                                                      @PathVariable String associateId,
                                                      @RequestBody VoteRequest voteRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(voteService.vote(agendaId, associateId, voteRequest));
    }
}

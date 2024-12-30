package com.ericpinto.votingsessionservice.api.controller;

import com.ericpinto.votingsessionservice.api.exception.CustomError;
import com.ericpinto.votingsessionservice.mapper.AssociateMapper;
import com.ericpinto.votingsessionservice.repository.AssociateRepository;
import com.ericpinto.votingsessionservice.request.VoteRequest;
import com.ericpinto.votingsessionservice.response.AssociateResponse;
import com.ericpinto.votingsessionservice.response.VoteResponse;
import com.ericpinto.votingsessionservice.service.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/votes")
@Tag(name = "API de Votações")
public class VoteController {

    private final VoteService voteService;
    private final AssociateRepository associateRepository;

    public VoteController(VoteService voteService, AssociateRepository associateRepository) {
        this.voteService = voteService;
        this.associateRepository = associateRepository;
    }

    @Operation(summary = "Votar em uma pauta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Voto criado", content = @Content(schema = @Schema(implementation = VoteResponse.class))),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado", content = @Content(schema = @Schema(implementation = CustomError.class))),
            @ApiResponse(responseCode = "422", description = "Votação inválida devido a regra de negócio", content = @Content(schema = @Schema(implementation = CustomError.class))),
            @ApiResponse(responseCode = "400", description = "Dados enviados na requisição inválidos", content = @Content(schema = @Schema(implementation = CustomError.class))),
    })
    @PostMapping("/agenda/{agendaId}/associate/{associateId}")
    public ResponseEntity<VoteResponse> associateVote(@PathVariable String agendaId,
                                                      @PathVariable String associateId,
                                                      @Valid @RequestBody VoteRequest voteRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(voteService.create(agendaId, associateId, voteRequest));
    }

    @Operation(summary = "Listar associados")
    @GetMapping("/associates")
    public ResponseEntity<List<AssociateResponse>> getAssociates() {
        return ResponseEntity.ok(associateRepository.findAll().stream().map(AssociateMapper::toResponse).toList());
    }
}

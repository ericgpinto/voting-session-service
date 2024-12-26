package com.ericpinto.votingsessionservice.api.controller;

import com.ericpinto.votingsessionservice.request.AgendaRegisterRequest;
import com.ericpinto.votingsessionservice.response.AgendaRegisterResponse;
import com.ericpinto.votingsessionservice.response.AgendaVoteResultResponse;
import com.ericpinto.votingsessionservice.response.AgendaVotingSessionResponse;
import com.ericpinto.votingsessionservice.service.AgendaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/agendas")
public class AgendaController {

    private final AgendaService agendaService;

    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @PostMapping("/register")
    public ResponseEntity<AgendaRegisterResponse> register(@RequestBody AgendaRegisterRequest agendaRegisterRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(agendaService.create(agendaRegisterRequest));
    }

    @PutMapping("/{id}/open-voting")
    public ResponseEntity<AgendaVotingSessionResponse> openVoting(@PathVariable String id) {
        AgendaVotingSessionResponse response = agendaService.openSessionToVote(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/result")
    public ResponseEntity<AgendaVoteResultResponse> getResult(@PathVariable String id) {
        return ResponseEntity.ok(agendaService.getResult(id));
    }

}

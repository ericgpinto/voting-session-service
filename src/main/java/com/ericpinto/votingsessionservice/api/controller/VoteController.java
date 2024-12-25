package com.ericpinto.votingsessionservice.api.controller;

import com.ericpinto.votingsessionservice.request.VoteRequest;
import com.ericpinto.votingsessionservice.response.VoteResponse;
import com.ericpinto.votingsessionservice.service.VoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/votes")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping("/agenda/{agendaId}/associate/{associateId}")
    public ResponseEntity<VoteResponse> associateVote(@PathVariable String agendaId,
                                                      @PathVariable String associateId,
                                                      @RequestBody VoteRequest voteRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(voteService.vote(agendaId, associateId, voteRequest));
    }
}

package com.ericpinto.votingsessionservice.response;

public record AgendaVoteResultResponse(
        String id,
        String title,
        String description,
        Long totalVotes,
        Long countingYesVotes,
        Long countingNoVotes
) {
}

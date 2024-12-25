package com.ericpinto.votingsessionservice.response;


import java.time.LocalDateTime;

public record AgendaVotingSessionResponse(
        String id,
        String title,
        String description,
        Boolean isOpenToVoting,
        LocalDateTime startTime,
        LocalDateTime endTime
) {
}

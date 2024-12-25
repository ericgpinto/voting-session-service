package com.ericpinto.votingsessionservice.response;

import java.time.LocalDateTime;

public record VoteResponse(
        String vote,
        LocalDateTime createdAt,
        AssociateResponse associate
) {
}

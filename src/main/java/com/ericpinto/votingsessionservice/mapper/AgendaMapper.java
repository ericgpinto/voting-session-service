package com.ericpinto.votingsessionservice.mapper;

import com.ericpinto.votingsessionservice.entity.AgendaEntity;
import com.ericpinto.votingsessionservice.request.AgendaRegisterRequest;
import com.ericpinto.votingsessionservice.response.AgendaRegisterResponse;
import com.ericpinto.votingsessionservice.response.AgendaVotingSessionResponse;
import org.springframework.stereotype.Component;

@Component
public class AgendaMapper {

    public static AgendaEntity toEntity(AgendaRegisterRequest request) {
        return AgendaEntity.builder()
                .title(request.title())
                .description(request.description())
                .build();
    }

    public static AgendaRegisterResponse toResponse(AgendaEntity entity) {
        return new AgendaRegisterResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription()
        );
    }

    public static AgendaVotingSessionResponse toVotingSessionResponse(AgendaEntity entity) {
        return new AgendaVotingSessionResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getVoteOpeningTime(),
                entity.getVoteClosingTime()
        );
    }
}

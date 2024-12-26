package com.ericpinto.votingsessionservice.stubs;

import com.ericpinto.votingsessionservice.entity.AgendaEntity;
import com.ericpinto.votingsessionservice.request.AgendaRegisterRequest;
import com.ericpinto.votingsessionservice.response.AgendaRegisterResponse;
import com.ericpinto.votingsessionservice.response.AgendaVoteResultResponse;

public class AgendaStub {

    private static final String ID = "676d864a196e5d4f3d6cbaa5";
    private static final String TITLE = "Title";
    private static final String DESCRIPTION = "Description";

    public static AgendaEntity createPartialAgendaEntity() {
        return AgendaEntity.builder()
                .id(ID)
                .title(TITLE)
                .description(DESCRIPTION)
                .build();
    }

    public static AgendaEntity createAgendaEntity() {
        return AgendaEntity.builder()
                .id(ID)
                .title(TITLE)
                .description(DESCRIPTION)
                .build();
    }

    public static AgendaEntity createAgendaEntityWithVotes(){
        return AgendaEntity.builder()
                .id(ID)
                .title("Title")
                .description("Description")
                .votes(VoteStub.createVotes())
                .build();
    }

    public static AgendaRegisterRequest createAgendaRegisterRequest() {
        return new AgendaRegisterRequest(TITLE, DESCRIPTION);
    }

    public static AgendaRegisterResponse createAgendaRegisterResponse() {
        return new AgendaRegisterResponse(ID, TITLE, DESCRIPTION);
    }

    public static AgendaVoteResultResponse createAgendaVoteResultResponse() {
        return new AgendaVoteResultResponse(ID, TITLE, DESCRIPTION, 10L, 6L, 4L);
    }
}

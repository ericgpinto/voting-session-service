package com.ericpinto.votingsessionservice.service;

import com.ericpinto.votingsessionservice.entity.AgendaEntity;
import com.ericpinto.votingsessionservice.entity.VoteEnum;
import com.ericpinto.votingsessionservice.exception.EntityNotFoundException;
import com.ericpinto.votingsessionservice.mapper.AgendaMapper;
import com.ericpinto.votingsessionservice.repository.AgendaRepository;
import com.ericpinto.votingsessionservice.request.AgendaRegisterRequest;
import com.ericpinto.votingsessionservice.response.AgendaRegisterResponse;
import com.ericpinto.votingsessionservice.response.AgendaVoteResultResponse;
import com.ericpinto.votingsessionservice.response.AgendaVotingSessionResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AgendaService {

    private final AgendaRepository agendaRepository;

    public AgendaService(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    public AgendaRegisterResponse create(AgendaRegisterRequest agendaRegisterRequest) {
        AgendaEntity agendaEntity = AgendaEntity.create(agendaRegisterRequest);

        agendaRepository.save(agendaEntity);

        return AgendaMapper.toResponse(agendaEntity);
    }

    public AgendaVotingSessionResponse openSessionToVote(String id) {
        AgendaEntity agenda = getById(id);
        agenda.setVoteOpeningTime(LocalDateTime.now());
        agenda.setVoteClosingTime(LocalDateTime.now().plusHours(1));

        agendaRepository.save(agenda);

        return AgendaMapper.toVotingSessionResponse(agenda);

    }

    private AgendaEntity getById(String id) {
        return agendaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Agenda not found"));
    }

    public AgendaVoteResultResponse getResult(String id){
        AgendaEntity agenda = getById(id);

        Long countingYesVotes = agenda.getVotes().stream().filter((vote) -> vote.getVote() ==  VoteEnum.YES).count();
        Long countingNoVotes = agenda.getVotes().stream().filter((vote) -> vote.getVote() == VoteEnum.NO).count();

        return new AgendaVoteResultResponse(
                agenda.getId(),
                agenda.getTitle(),
                agenda.getDescription(),
                countingYesVotes + countingNoVotes,
                countingYesVotes,
                countingNoVotes
        );
    }
}

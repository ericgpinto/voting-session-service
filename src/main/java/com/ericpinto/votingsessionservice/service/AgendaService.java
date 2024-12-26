package com.ericpinto.votingsessionservice.service;

import com.ericpinto.votingsessionservice.entity.AgendaEntity;
import com.ericpinto.votingsessionservice.exception.EntityNotFoundException;
import com.ericpinto.votingsessionservice.mapper.AgendaMapper;
import com.ericpinto.votingsessionservice.repository.AgendaRepository;
import com.ericpinto.votingsessionservice.request.AgendaRegisterRequest;
import com.ericpinto.votingsessionservice.response.AgendaRegisterResponse;
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
        AgendaEntity agenda = agendaRepository.save(AgendaMapper.toEntity(agendaRegisterRequest));
        return AgendaMapper.toResponse(agenda);
    }

    public AgendaVotingSessionResponse openSessionToVote(String id) {
        AgendaEntity agenda = agendaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Agenda not found"));
        agenda.setVoteOpeningTime(LocalDateTime.now());
        agenda.setVoteClosingTime(LocalDateTime.now().plusHours(1));

        return AgendaMapper.toVotingSessionResponse(agendaRepository.save(agenda));
    }

}

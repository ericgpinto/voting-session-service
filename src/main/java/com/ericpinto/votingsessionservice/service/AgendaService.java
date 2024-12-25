package com.ericpinto.votingsessionservice.service;

import com.ericpinto.votingsessionservice.entity.AgendaEntity;
import com.ericpinto.votingsessionservice.repository.AgendaRepository;
import com.ericpinto.votingsessionservice.request.AgendaRegisterRequest;
import com.ericpinto.votingsessionservice.response.AgendaRegisterResponse;
import com.ericpinto.votingsessionservice.response.AgendaVotingSessionResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.ericpinto.votingsessionservice.mapper.AgendaMapper.*;

@Service
public class AgendaService {

    private final AgendaRepository agendaRepository;

    public AgendaService(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    public AgendaRegisterResponse create(AgendaRegisterRequest agendaRegisterRequest) {
        AgendaEntity agenda = agendaRepository.save(toEntity(agendaRegisterRequest));
        return toResponse(agenda);
    }

    public AgendaVotingSessionResponse openSessionToVote(String id) {
        AgendaEntity agenda = agendaRepository.findById(id).orElseThrow(() -> new RuntimeException("Agenda not found"));
        agenda.setIsOpenToVoting(true);
        agenda.setStartTime(LocalDateTime.now());
        agenda.setEndTime(LocalDateTime.now().plusHours(1));

        return toVotingSessionResponse(agendaRepository.save(agenda));
    }

}

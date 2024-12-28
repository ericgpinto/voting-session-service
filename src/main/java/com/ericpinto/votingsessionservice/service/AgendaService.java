package com.ericpinto.votingsessionservice.service;

import com.ericpinto.votingsessionservice.entity.AgendaEntity;
import com.ericpinto.votingsessionservice.entity.VoteEnum;
import com.ericpinto.votingsessionservice.exception.EntityNotFoundException;
import com.ericpinto.votingsessionservice.mapper.AgendaMapper;
import com.ericpinto.votingsessionservice.producer.RabbitMQProducer;
import com.ericpinto.votingsessionservice.repository.AgendaRepository;
import com.ericpinto.votingsessionservice.request.AgendaRegisterRequest;
import com.ericpinto.votingsessionservice.response.AgendaRegisterResponse;
import com.ericpinto.votingsessionservice.response.AgendaVoteResultResponse;
import com.ericpinto.votingsessionservice.response.AgendaVotingSessionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AgendaService {

    private static final Logger log = LoggerFactory.getLogger(AgendaService.class);
    private final AgendaRepository agendaRepository;
    private final RabbitMQProducer producer;

    public AgendaService(AgendaRepository agendaRepository, RabbitMQProducer producer) {
        this.agendaRepository = agendaRepository;
        this.producer = producer;
    }

    public AgendaRegisterResponse create(AgendaRegisterRequest agendaRegisterRequest) {
        log.info("Creating new agenda.");
        AgendaEntity agendaEntity = AgendaEntity.create(agendaRegisterRequest);

        agendaRepository.save(agendaEntity);

        log.info("Created new agenda.");
        return AgendaMapper.toResponse(agendaEntity);
    }

    public AgendaVotingSessionResponse openSessionToVote(String id) {
        log.info("Opening session to vote for agenda.");
        AgendaEntity agenda = getById(id);
        agenda.setVoteOpeningTime(LocalDateTime.now());
        agenda.setVoteClosingTime(LocalDateTime.now().plusHours(1));

        agendaRepository.save(agenda);

        log.info("Opened session to vote for agenda.");
        return AgendaMapper.toVotingSessionResponse(agenda);

    }

    private AgendaEntity getById(String id) {
        return agendaRepository.findById(id).orElseThrow(() ->
        {
            log.error("Agenda with id {} not found.", id);
            return new EntityNotFoundException("Agenda not found");
        });
    }

    public AgendaVoteResultResponse countingVotes(String id) {
        log.info("Counting votes for agenda.");

        AgendaEntity agenda = getById(id);
        log.info("Getting result for agenda {}.", agenda.getId());

        Long countingYesVotes = agenda.getVotes().stream().filter((vote) -> vote.getVote() ==  VoteEnum.YES).count();
        Long countingNoVotes = agenda.getVotes().stream().filter((vote) -> vote.getVote() == VoteEnum.NO).count();

        AgendaVoteResultResponse response = new AgendaVoteResultResponse(
                agenda.getId(),
                agenda.getTitle(),
                agenda.getDescription(),
                countingYesVotes + countingNoVotes,
                countingYesVotes,
                countingNoVotes
        );

        producer.send(response);
        return response;
    }
}

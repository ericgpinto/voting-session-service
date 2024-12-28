package com.ericpinto.votingsessionservice.service;

import com.ericpinto.votingsessionservice.entity.AgendaEntity;
import com.ericpinto.votingsessionservice.entity.AssociateEntity;
import com.ericpinto.votingsessionservice.entity.VoteEntity;
import com.ericpinto.votingsessionservice.entity.VoteEnum;
import com.ericpinto.votingsessionservice.exception.EntityNotFoundException;
import com.ericpinto.votingsessionservice.exception.EnumConstantException;
import com.ericpinto.votingsessionservice.mapper.VoteMapper;
import com.ericpinto.votingsessionservice.repository.AgendaRepository;
import com.ericpinto.votingsessionservice.repository.AssociateRepository;
import com.ericpinto.votingsessionservice.repository.VoteRepository;
import com.ericpinto.votingsessionservice.request.VoteRequest;
import com.ericpinto.votingsessionservice.response.VoteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    private static final Logger log = LoggerFactory.getLogger(VoteService.class);
    private final VoteRepository voteRepository;
    private final AgendaRepository agendaRepository;
    private final AssociateRepository associateRepository;

    public VoteService(VoteRepository voteRepository,
                       AgendaRepository agendaRepository,
                       AssociateRepository associateRepository) {
        this.voteRepository = voteRepository;
        this.agendaRepository = agendaRepository;
        this.associateRepository = associateRepository;
    }

    public VoteResponse create(String idAgenda, String associateId, VoteRequest request) {
        if (!isValidEnumValue(request.vote())) {
            log.error("No enum constant {}", request.vote());
            throw new EnumConstantException("No enum constant " + request.vote());
        }

        AgendaEntity agenda = agendaRepository.findById(idAgenda).orElseThrow(() -> new EntityNotFoundException("Agenda not found"));
        log.info("Creating vote to agenda {}.", agenda.getId());

        agenda.validate(agenda);

        AssociateEntity associate = associateRepository.findById(associateId)
                .orElseThrow(() ->
                {
                    log.error("Associate with id {} not found.", associateId);
                    return new EntityNotFoundException("Associate not found");
                });

        VoteEntity vote = VoteEntity.create(request, agenda, associate);
        voteRepository.save(vote);

        log.info("Saving vote {} to agenda", vote.getId());
        agenda.addVote(vote);
        agendaRepository.save(agenda);

        log.info("Vote created with successfully.");
       return VoteMapper.toResponse(vote);
    }

    private static boolean isValidEnumValue(String value) {
        for (Enum<?> enumValue : ((Class<? extends Enum<?>>) VoteEnum.class).getEnumConstants()) {
            if (enumValue.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }



}

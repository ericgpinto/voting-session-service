package com.ericpinto.votingsessionservice.service;

import com.ericpinto.votingsessionservice.entity.AgendaEntity;
import com.ericpinto.votingsessionservice.entity.AssociateEntity;
import com.ericpinto.votingsessionservice.entity.VoteEntity;
import com.ericpinto.votingsessionservice.exception.EntityNotFoundException;
import com.ericpinto.votingsessionservice.mapper.VoteMapper;
import com.ericpinto.votingsessionservice.repository.AgendaRepository;
import com.ericpinto.votingsessionservice.repository.AssociateRepository;
import com.ericpinto.votingsessionservice.repository.VoteRepository;
import com.ericpinto.votingsessionservice.request.VoteRequest;
import com.ericpinto.votingsessionservice.response.VoteResponse;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

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

    public VoteResponse vote(String idAgenda, String associateId, VoteRequest request) {
        AgendaEntity agenda = agendaRepository.findById(idAgenda).orElseThrow(() -> new EntityNotFoundException("Agenda not found"));
        agenda.validate(agenda);

        AssociateEntity associate = associateRepository.findById(associateId).orElseThrow(() -> new EntityNotFoundException("Associate not found"));

        VoteEntity vote = VoteEntity.create(request, agenda, associate);
        voteRepository.save(vote);

        agenda.addVote(vote);
        agendaRepository.save(agenda);

       return VoteMapper.toResponse(vote);
    }



}

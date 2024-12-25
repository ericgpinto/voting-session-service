package com.ericpinto.votingsessionservice.service;

import com.ericpinto.votingsessionservice.entity.AgendaEntity;
import com.ericpinto.votingsessionservice.entity.AssociateEntity;
import com.ericpinto.votingsessionservice.entity.VoteEntity;
import com.ericpinto.votingsessionservice.mapper.VoteMapper;
import com.ericpinto.votingsessionservice.repository.AgendaRepository;
import com.ericpinto.votingsessionservice.repository.AssociateRepository;
import com.ericpinto.votingsessionservice.repository.VoteRepository;
import com.ericpinto.votingsessionservice.request.VoteRequest;
import com.ericpinto.votingsessionservice.response.VoteResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
        AgendaEntity agenda = agendaRepository.findById(idAgenda)
                .orElseThrow(() -> new RuntimeException("Agenda not found"));

       validate(agenda);

        AssociateEntity associate = associateRepository.findById(associateId).orElseThrow(() -> new RuntimeException("Associate not found"));

        VoteEntity vote = VoteEntity.save(request, associate);

        agenda.getVoteEntity().add(vote);
        agendaRepository.save(agenda);

       return VoteMapper.toResponse(voteRepository.save(vote));
    }

    private void validate(AgendaEntity agenda){
        if (LocalDateTime.now().isAfter(agenda.getEndTime())){
            throw new RuntimeException("Agenda end time is after start time");
        }
    }
}

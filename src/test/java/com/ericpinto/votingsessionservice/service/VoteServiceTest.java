package com.ericpinto.votingsessionservice.service;

import com.ericpinto.votingsessionservice.entity.AgendaEntity;
import com.ericpinto.votingsessionservice.entity.VoteEntity;
import com.ericpinto.votingsessionservice.exception.DuplicateVoteException;
import com.ericpinto.votingsessionservice.exception.EntityNotFoundException;
import com.ericpinto.votingsessionservice.exception.VoteClosedException;
import com.ericpinto.votingsessionservice.repository.AgendaRepository;
import com.ericpinto.votingsessionservice.repository.AssociateRepository;
import com.ericpinto.votingsessionservice.repository.VoteRepository;
import static com.ericpinto.votingsessionservice.stubs.VoteStub.*;

import com.ericpinto.votingsessionservice.response.VoteResponse;
import com.ericpinto.votingsessionservice.stubs.AgendaStub;
import com.ericpinto.votingsessionservice.stubs.AssociateStub;
import com.ericpinto.votingsessionservice.stubs.VoteStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoteServiceTest {

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private AgendaRepository agendaRepository;

    @Mock
    private AssociateRepository associateRepository;

    @InjectMocks
    private VoteService voteService;

    private static final String AGENDA_ID = "676d864a196e5d4f3d6cbaa5";
    private static final String ASSOCIATE_ID = "676c4855df73fcce481d24d0";

    @Test
    void shouldThrowExceptionIfAgendaNotFound() {
        when(agendaRepository.findById(AGENDA_ID)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> voteService.vote(AGENDA_ID, ASSOCIATE_ID, createVoteRequest()));

        assertEquals("Agenda not found", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionIfVoteAreClosed(){
        AgendaEntity agenda = AgendaStub.createAgendaEntity();
        agenda.setVoteClosingTime(LocalDateTime.now());

        when(agendaRepository.findById(AGENDA_ID)).thenReturn(Optional.of(agenda));

        Exception exception = assertThrows(VoteClosedException.class, () -> voteService.vote(AGENDA_ID, ASSOCIATE_ID, createVoteRequest()));

        assertEquals("Agenda is no longer open for voting", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionIfAssociateNotFound() {
        when(agendaRepository.findById(AGENDA_ID)).thenReturn(Optional.of(AgendaStub.createAgendaEntity()));
        when(associateRepository.findById(ASSOCIATE_ID)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> voteService.vote(AGENDA_ID, ASSOCIATE_ID, createVoteRequest()));

        assertEquals("Associate not found", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionIfVoteIsDuplicate() {
        when(agendaRepository.findById(AGENDA_ID)).thenReturn(Optional.of(AgendaStub.createAgendaEntityWithVotes()));
        when(associateRepository.findById(ASSOCIATE_ID)).thenReturn(Optional.of(AssociateStub.createAssociateEntity()));

        Exception exception = assertThrows(DuplicateVoteException.class, () -> voteService.vote(AGENDA_ID, ASSOCIATE_ID, createVoteRequest()));

        assertEquals("Associate has already voted on this agenda", exception.getMessage());
    }

    @Test
    void shouldCreateVote() {
        when(agendaRepository.findById(AGENDA_ID)).thenReturn(Optional.of(AgendaStub.createAgendaEntity()));
        when(associateRepository.findById(ASSOCIATE_ID)).thenReturn(Optional.of(AssociateStub.createAssociateEntity()));
        when(voteRepository.save(any(VoteEntity.class))).thenReturn(createVote());
        when(agendaRepository.save(any(AgendaEntity.class))).thenReturn(AgendaStub.createAgendaEntity());

        VoteResponse response = voteService.vote(AGENDA_ID, ASSOCIATE_ID, createVoteRequest());

        assertNotNull(response);

        verify(voteRepository, times(1)).save(any(VoteEntity.class));
        verify(agendaRepository, times(1)).save(any(AgendaEntity.class));
    }

}
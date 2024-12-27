package com.ericpinto.votingsessionservice.entity;

import com.ericpinto.votingsessionservice.exception.VoteClosedException;
import com.ericpinto.votingsessionservice.request.AgendaRegisterRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "agenda")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgendaEntity {

    private static final Logger log = LoggerFactory.getLogger(AgendaEntity.class);
    @Id
    private String id;
    private String title;
    private String description;
    private LocalDateTime voteOpeningTime;
    private LocalDateTime voteClosingTime;
    @DBRef
    private List<VoteEntity> votes = new ArrayList<>();


    public static AgendaEntity create(AgendaRegisterRequest request) {
        return AgendaEntity.builder()
                .title(request.title())
                .description(request.description())
                .build();

    }

    public void addVote(VoteEntity vote) {
        getVotes().add(vote);
    }

    public void validate(AgendaEntity agenda){
        if (LocalDateTime.now().isAfter(agenda.getVoteClosingTime())){
            log.error("Error when trying to vote on agenda {}", agenda.getId());
            throw new VoteClosedException("Agenda is no longer open for voting");
        }
    }


}

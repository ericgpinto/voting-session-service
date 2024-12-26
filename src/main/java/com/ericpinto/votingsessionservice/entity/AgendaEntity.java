package com.ericpinto.votingsessionservice.entity;

import com.ericpinto.votingsessionservice.exception.VoteClosedException;
import com.ericpinto.votingsessionservice.request.AgendaRegisterRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "agenda")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgendaEntity {

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
            throw new VoteClosedException("Agenda is no longer open for voting");
        }
    }


}

package com.ericpinto.votingsessionservice.entity;

import com.ericpinto.votingsessionservice.exception.DuplicateVoteException;
import com.ericpinto.votingsessionservice.request.VoteRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "vote")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoteEntity {
    @Id
    private String id;
    @Field
    private VoteEnum vote;
    @DBRef
    private AssociateEntity associate;
    private LocalDateTime createdAt;

    public static VoteEntity create(VoteRequest request, AgendaEntity agenda, AssociateEntity associate){
        validate(agenda, associate);

        return  VoteEntity.builder()
                .vote(VoteEnum.valueOf(request.vote()))
                .associate(associate)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static void validate(AgendaEntity agenda, AssociateEntity associate){
        boolean alreadyVoted = agenda.getVotes().stream()
                .anyMatch(vote -> vote.getAssociate().equals(associate));

        if (alreadyVoted) {
            throw new DuplicateVoteException("Associate has already voted on this agenda");
        }
    }
}

package com.ericpinto.votingsessionservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "vote")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoteEntity {
    @Id
    private String id;
    private VoteEnum vote;
    @DBRef
    private AssociateEntity associate;
    private LocalDateTime createdAt;
}

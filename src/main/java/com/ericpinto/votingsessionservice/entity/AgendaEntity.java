package com.ericpinto.votingsessionservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
}

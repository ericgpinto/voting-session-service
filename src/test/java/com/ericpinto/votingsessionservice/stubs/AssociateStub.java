package com.ericpinto.votingsessionservice.stubs;

import com.ericpinto.votingsessionservice.entity.AssociateEntity;

public class AssociateStub {

    public static AssociateEntity createAssociateEntity() {
        return AssociateEntity.builder()
                .id("676c4855df73fcce481d24d0")
                .name("Associate 1")
                .legalDocumentNumber("35868004019")
                .build();
    }
}

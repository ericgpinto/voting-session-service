package com.ericpinto.votingsessionservice.stubs;

import com.ericpinto.votingsessionservice.entity.AssociateEntity;
import com.ericpinto.votingsessionservice.entity.VoteEntity;
import com.ericpinto.votingsessionservice.entity.VoteEnum;

import java.util.List;

public class VoteStub {

    public static VoteEntity createVote() {
        return VoteEntity.builder()
                .id("676d8664196e5d4f3d6cbaa6")
                .vote(VoteEnum.YES)
                .associate(new AssociateEntity("676c4855df73fcce481d24d0", "Associate 1", "14515180053"))
                .build();
    }

    public static List<VoteEntity> createVotes(){
        return List.of(
                createVote(),
                VoteEntity.builder()
                        .id("676d86c7196e5d4f3d6cbaa9")
                        .vote(VoteEnum.YES)
                        .associate(new AssociateEntity("676d852593c489361d86155f", "Associate 2", "07557550064"))
                        .build(),
                VoteEntity.builder()
                        .id("676d86f5196e5d4f3d6cbaae")
                        .vote(VoteEnum.NO)
                        .associate(new AssociateEntity("676c4855df73fcce481d24d0", "Associate 3", "24712863056"))
                        .build()

        );
    }
}

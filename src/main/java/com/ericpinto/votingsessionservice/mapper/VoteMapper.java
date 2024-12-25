package com.ericpinto.votingsessionservice.mapper;

import com.ericpinto.votingsessionservice.entity.VoteEntity;
import com.ericpinto.votingsessionservice.entity.VoteEnum;
import com.ericpinto.votingsessionservice.request.VoteRequest;
import com.ericpinto.votingsessionservice.response.VoteResponse;
import org.springframework.stereotype.Component;

@Component
public class VoteMapper {

    public static VoteEntity toEntity(VoteRequest request){
        return VoteEntity.builder()
                .vote(VoteEnum.valueOf(request.vote()))
                .build();
    }

    public static VoteResponse toResponse(VoteEntity entity){
        return new VoteResponse(
                entity.getVote().toString(),
                entity.getCreatedAt(),
                AssociateMapper.toResponse(entity.getAssociate())
        );
    }
}

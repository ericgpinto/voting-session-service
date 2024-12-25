package com.ericpinto.votingsessionservice.mapper;

import com.ericpinto.votingsessionservice.entity.AssociateEntity;
import com.ericpinto.votingsessionservice.response.AssociateResponse;
import org.springframework.stereotype.Component;

@Component
public class AssociateMapper {

    public static AssociateResponse toResponse(AssociateEntity associateEntity) {
        return new AssociateResponse(
                associateEntity.getName(),
                associateEntity.getLegalDocumentNumber()
        );
    }
}

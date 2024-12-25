package com.ericpinto.votingsessionservice.repository;

import com.ericpinto.votingsessionservice.entity.AssociateEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociateRepository extends MongoRepository<AssociateEntity, String> {
}

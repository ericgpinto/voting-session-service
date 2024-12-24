package com.ericpinto.votingsessionservice.repository;

import com.ericpinto.votingsessionservice.entity.VoteEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends MongoRepository<VoteEntity, String> {
}

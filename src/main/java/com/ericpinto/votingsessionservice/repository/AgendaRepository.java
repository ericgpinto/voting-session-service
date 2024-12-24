package com.ericpinto.votingsessionservice.repository;

import com.ericpinto.votingsessionservice.entity.AgendaEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendaRepository extends MongoRepository<AgendaEntity, String> {
}

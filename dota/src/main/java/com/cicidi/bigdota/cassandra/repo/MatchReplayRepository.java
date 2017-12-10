package com.cicidi.bigdota.cassandra.repo;

import com.cicidi.bigdota.domain.dota.MatchReplay;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface MatchReplayRepository extends CassandraRepository<MatchReplay, String>, Serializable {
}
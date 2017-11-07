package com.cicidi.bigdota.cassandra.repo;

import com.cicidi.bigdota.domain.MatchReplay;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchReplayRepository extends CassandraRepository<MatchReplay, Long> {
}
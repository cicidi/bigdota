package com.cicidi.bigdota.cassandra.repo;

import com.cicidi.bigdota.domain.dota.Download_failed;
import com.cicidi.bigdota.domain.dota.MatchReplay;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FailedRepository extends CassandraRepository<Download_failed, String> {
}
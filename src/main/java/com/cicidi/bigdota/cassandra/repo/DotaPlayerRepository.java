package com.cicidi.bigdota.cassandra.repo;

import com.cicidi.bigdota.domain.dota.DotaPlayer;
import com.cicidi.bigdota.domain.dota.Download_failed;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DotaPlayerRepository extends CassandraRepository<DotaPlayer, String> {
}
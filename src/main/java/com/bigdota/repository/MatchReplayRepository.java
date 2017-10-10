package com.bigdota.repository;

import com.bigdota.domain.MatchReplay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by cicidi on 9/19/2017.
 */
@Repository
public interface MatchReplayRepository extends JpaRepository<MatchReplay, Long> {
    List<MatchReplay> findByUserAccount();
}

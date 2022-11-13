package com.raf.userservice.repository;

import com.raf.userservice.domain.Rank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RankRepository extends JpaRepository<Rank, Long> {

    Optional<Rank> findRankById(Long id);

    Optional<Rank> findRankByName(String name);

}

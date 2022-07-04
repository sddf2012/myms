package com.my.business.league.repository;

import com.my.business.league.domain.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueRepository extends JpaRepository<League,Integer> {
}

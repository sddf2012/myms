package com.my.business.league.service.impl;

import com.my.business.league.repository.LeagueRepository;
import com.my.business.league.service.LeagueService;
import com.my.common.domain.vo.league.LeagueVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeagueServiceImpl implements LeagueService {
    @Autowired
    private LeagueRepository leagueRepository;

    @Override
    public List<LeagueVo> getAll() {
        return leagueRepository.findAll().stream().map(league -> {
            LeagueVo vo = new LeagueVo();
            BeanUtils.copyProperties(league, vo);
            return vo;
        }).collect(Collectors.toList());
    }
}

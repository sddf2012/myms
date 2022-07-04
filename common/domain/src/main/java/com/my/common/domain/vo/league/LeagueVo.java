package com.my.common.domain.vo.league;

import lombok.Data;

import java.util.Date;

@Data
public class LeagueVo {
    private Integer id;

    private String country;

    private String code;

    private String name;

    private Date createTime;

    private Date updateTime;
}

package com.my.business.league.domain.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "ft_league")
public class League {
    @Id
    private Integer id;

    private String country;

    private String code;

    private String name;

    private Date createTime;

    private Date updateTime;
}

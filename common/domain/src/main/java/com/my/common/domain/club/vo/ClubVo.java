package com.my.common.domain.club.vo;

import lombok.Data;

import java.util.List;

/**
 * @author liu peng bo
 * date: 2021/6/29 15:58
 */
@Data
public class ClubVo {
    private String code;

    private String simpleName;

    private String fullName;

    private List<String> players;
}

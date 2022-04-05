package com.my.business.player.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liu peng bo
 * date: 2021/6/29 15:51
 */
@RestController
@RequestMapping("/player/base")
public class PlayerController {
    @GetMapping("/getClubPlayer")
    public List<String> getClubPlayer(@RequestParam(required = false) String clubCode) {
        List<String> players = new ArrayList<>();
        players.add("c luo");
        players.add("messi");
        return players;
    }
}

package com.my.business.club.controller;


import com.my.common.instrument.host.HostUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liu peng bo
 * date: 2021/6/29 15:55
 */
@Slf4j
@RestController
@RequestMapping("/club/base")
public class ClubController {
    /*@Autowired
    private RegistrationClientService clientService;

    @PostMapping("/getClub")
    public List<ClubVo> getClub(@RequestBody ClubSearchBo bo) {
        List<ClubVo> clubVos = new ArrayList<>();
        ClubVo clubVo = new ClubVo();
        clubVo.setCode("MUFC");
        clubVo.setSimpleName("曼联");
        clubVo.setFullName("曼彻斯特联足球俱乐部");
        //
        List<RegisterInstance> instances = clientService.getInstance("business-player");
        if (!CollectionUtils.isEmpty(instances)) {
            RegisterInstance instance = instances.get(0);
            String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/player/base/getClubPlayer";
            Map<String, String> params = new HashMap<>();
            params.put("clubCode", clubVo.getCode());
            try {
                String resp = HttpClientUtils.doPostWithParams(url, null, params).getContent();
                if (StringUtils.hasText(resp)) {
                    clubVo.setPlayers(JSON.parseArray(resp, String.class));
                }

            } catch (Exception e) {
                log.error("查询成员异常!", e);
            }

        }
        clubVos.add(clubVo);
        return clubVos;
    }*/

    @GetMapping("/getClub")
    public String getClub(@RequestParam(value = "timeout", required = false) Integer timeout) {
        if (timeout != null) {
            try {
                Thread.sleep(timeout * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return HostUtils.getHost() + " return fc club";
    }
}

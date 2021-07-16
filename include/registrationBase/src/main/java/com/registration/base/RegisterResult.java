package com.registration.base;

import lombok.Data;

/**
 * @author liu peng bo
 * date: 2021/7/13 13:47
 */
@Data
public class RegisterResult {
    private Boolean success = true;

    private String err;
}

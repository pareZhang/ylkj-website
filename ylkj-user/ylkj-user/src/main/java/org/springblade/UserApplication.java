package org.springblade;

import org.springblade.common.constant.CommonConstant;
import org.springblade.core.launch.BladeApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author zjm
 * @version 1.0
 * @date 2021/5/10 14:31
 * @description
 */
@EnableScheduling
@SpringBootApplication
public class UserApplication {
    public static void main(String[] args) {
        BladeApplication.run(CommonConstant.APPLICATION_SYSTEM_NAME, UserApplication.class,args);
    }
}

package org.springblade;

import org.springblade.common.constant.CommonConstant;
import org.springblade.core.launch.BladeApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Neo
 * @version 1.0
 * @date 2021/5/10 14:31
 * @description
 */
@SpringBootApplication
public class IAMApplication {
    public static void main(String[] args) {
        BladeApplication.run(CommonConstant.APPLICATION_SYSTEM_NAME,IAMApplication.class,args);
    }
}

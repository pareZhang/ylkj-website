package org.springblade.modules.log.service;

import com.baomidou.mybatisplus.extension.service.IService;
import lombok.AllArgsConstructor;
import org.springblade.core.log.model.LogApi;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zjm
 * @since 2021-05-17 9:00
 */
public interface ILogApiService extends IService<LogApi> {
}

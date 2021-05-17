package org.springblade.modules.log.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.core.log.model.LogApi;
import org.springblade.modules.log.mapper.LogApiMapper;
import org.springblade.modules.log.service.ILogApiService;
import org.springframework.stereotype.Service;

/**
 * @author zjm
 * @since 2021-05-17 9:07
 */
@Service
public class LogApiServiceImpl extends ServiceImpl<LogApiMapper, LogApi> implements ILogApiService {
}

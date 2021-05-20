package org.springblade.modules.log.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.core.log.model.LogError;
import org.springblade.modules.log.mapper.LogErrorMapper;
import org.springblade.modules.log.service.ILogErrorService;
import org.springframework.stereotype.Service;

/**
 * @author zjm
 * @since 2021-05-17 9:08
 */
@Service
public class LogErrorServiceImpl extends ServiceImpl<LogErrorMapper, LogError> implements ILogErrorService {
}

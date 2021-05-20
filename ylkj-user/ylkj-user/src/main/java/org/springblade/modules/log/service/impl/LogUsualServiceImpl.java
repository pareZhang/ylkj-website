package org.springblade.modules.log.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.core.log.model.LogUsual;
import org.springblade.modules.log.mapper.LogUsualMapper;
import org.springblade.modules.log.service.ILogUsualService;
import org.springframework.stereotype.Service;

/**
 * @author zjm
 * @since 2021-05-17 9:11
 */
@Service
public class LogUsualServiceImpl extends ServiceImpl<LogUsualMapper, LogUsual> implements ILogUsualService {
}

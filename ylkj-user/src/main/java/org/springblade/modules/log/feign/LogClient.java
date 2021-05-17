package org.springblade.modules.log.feign;

import lombok.AllArgsConstructor;
import org.springblade.core.log.feign.ILogClient;
import org.springblade.core.log.model.LogApi;
import org.springblade.core.log.model.LogError;
import org.springblade.core.log.model.LogUsual;
import org.springblade.core.tool.api.R;
import org.springblade.modules.log.service.ILogApiService;
import org.springblade.modules.log.service.ILogErrorService;
import org.springblade.modules.log.service.ILogUsualService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zjm
 * @since 2021-05-17 10:37
 * 日志服务feign实现类
 */


@RestController
@AllArgsConstructor
public class LogClient implements ILogClient {
    private ILogApiService logApiService;
    private ILogErrorService logErrorService;
    private ILogUsualService logUsualService;

    @Override
    @PostMapping(API_PREFIX + "/saveUsualLog")
    public R<Boolean> saveUsualLog(@RequestBody LogUsual log) {
        log.setParams(log.getParams().replace("&amp;", "&"));
        return R.data(logUsualService.save(log));
    }

    @Override
    @PostMapping(API_PREFIX + "/saveApiLog")
    public R<Boolean> saveApiLog(@RequestBody LogApi log) {
        log.setParams(log.getParams().replace("&amp;", "&"));
        return R.data(logApiService.save(log));
    }

    @Override
    @PostMapping(API_PREFIX+"/saveErrorLog")
    public R<Boolean> saveErrorLog(@RequestBody LogError log) {
        log.setParams(log.getParams().replace("&amp;", "&"));
        return R.data(logErrorService.save(log));
    }
}

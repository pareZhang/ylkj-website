package org.springblade.modules.log.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springblade.core.log.model.LogError;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.modules.log.model.LogErrorVo;
import org.springblade.modules.log.service.ILogErrorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zjm
 * @since 2021-05-17 8:57
 */
@RestController
@AllArgsConstructor
@RequestMapping("/log/error")
@Api(value = "error日志", tags = "error日志")
public class LogErrorController {
    private ILogErrorService logErrorService;

    /**
     * 单个详情
     *
     * @param error
     * @return
     */
    @GetMapping("/detail")
    R<LogError> detail(LogError error) {
        return R.data(logErrorService.getOne(Condition.getQueryWrapper(error)));
    }

    /**
     * 查询多条 分页
     * @param logError
     * @param query
     * @return
     */
    @GetMapping("/list")
    public R<IPage<LogErrorVo>> list(@ApiIgnore @RequestParam Map<String, Object> logError, Query query) {
        query.setAscs("create_time");
        query.setDescs(StringPool.EMPTY);
        IPage<LogError> pages = logErrorService.page(Condition.getPage(query), Condition.getQueryWrapper(logError, LogError.class));
        List<LogErrorVo> records = pages.getRecords().stream().map(logApi -> {
            LogErrorVo vo = BeanUtil.copy(logApi, LogErrorVo.class);
            vo.setStrId(Func.toStr(logApi.getId()));
            return vo;
        }).collect(Collectors.toList());
        IPage<LogErrorVo> pageVo = new Page<>(pages.getCurrent(), pages.getSize(), pages.getTotal());
        pageVo.setRecords(records);
        return R.data(pageVo);
    }
}

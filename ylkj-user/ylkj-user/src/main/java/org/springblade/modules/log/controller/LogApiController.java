package org.springblade.modules.log.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springblade.core.log.model.LogApi;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.modules.log.model.LogApiVo;
import org.springblade.modules.log.service.ILogApiService;
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
 * @since 2021-05-17 8:56
 */
@RestController
@AllArgsConstructor
@RequestMapping("/log/api")
@Api(value = "api日志",tags = "api日志")
public class LogApiController {
    private ILogApiService logService;

    /**
     * 单条 详情
     *
     * @param log
     * @return
     */
    @GetMapping("/detail")
    R<LogApi> detail(LogApi log) {
        return R.data(logService.getOne(Condition.getQueryWrapper(log)));
    }

    /**
     * 查询多条（分页）
     * @param log
     * @param query
     * @return
     */
    @GetMapping("/list")
    public R<IPage<LogApiVo>> list(@ApiIgnore @RequestParam Map<String, Object> log, Query query){
        //排序规则
        query.setAscs("create_time");
        query.setDescs(StringPool.EMPTY);
        IPage<LogApi> pages = logService.page(Condition.getPage(query), Condition.getQueryWrapper(log, LogApi.class));
        List<LogApiVo> records = pages.getRecords().stream().map(logApi -> {
            LogApiVo vo = BeanUtil.copy(logApi, LogApiVo.class);
            assert vo != null;
            vo.setStrId(Func.toStr(logApi.getId()));
            return vo;
        }).collect(Collectors.toList());
        IPage<LogApiVo> pageVo = new Page<>(pages.getCurrent(), pages.getSize(), pages.getTotal());
        pageVo.setRecords(records);
        return R.data(pageVo);
    }
}

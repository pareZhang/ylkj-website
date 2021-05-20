package org.springblade.modules.log.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springblade.core.log.model.LogUsual;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.modules.log.model.LogUsualVo;
import org.springblade.modules.log.service.ILogUsualService;
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
@RequestMapping("/log/usual")
public class LogUsualController {
    private ILogUsualService logUsualService;

    /**
     * 单条记录 详情
     * @param usual
     * @return
     */
    @GetMapping("/detail")
    R<LogUsual> detail(LogUsual usual){
        return R.data(logUsualService.getOne(Condition.getQueryWrapper(usual)));
    }
    /**
     * 查询多条(分页)
     */
    @GetMapping("/list")
    public R<IPage<LogUsualVo>> list(@ApiIgnore @RequestParam Map<String, Object> log, Query query) {
        //排序规则
        query.setAscs("create_time");
        query.setDescs(StringPool.EMPTY);
        IPage<LogUsual> pages = logUsualService.page(Condition.getPage(query), Condition.getQueryWrapper(log, LogUsual.class));
        List<LogUsualVo> records = pages.getRecords().stream().map(logApi -> {
            LogUsualVo vo = BeanUtil.copy(logApi, LogUsualVo.class);
            vo.setStrId(Func.toStr(logApi.getId()));
            return vo;
        }).collect(Collectors.toList());
        IPage<LogUsualVo> pageVo = new Page<>(pages.getCurrent(), pages.getSize(), pages.getTotal());
        pageVo.setRecords(records);
        return R.data(pageVo);
    }

}

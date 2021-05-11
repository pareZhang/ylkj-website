package org.springblade.modules.user.controller;

import io.swagger.annotations.*;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;

import javax.validation.Valid;

import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.node.INode;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.modules.user.entity.Dict;
import org.springblade.modules.user.vo.DictVO;
import org.springblade.modules.user.wrapper.DictWrapper;
import org.springblade.modules.user.service.IDictService;
import org.springblade.core.boot.ctrl.BladeController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

import static org.springblade.core.cache.constant.CacheConstant.DICT_CACHE;

/**
 * 字典表 控制器
 *
 * @author zjm
 * @since 2021-05-10
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dict")
@Api(value = "字典表", tags = "字典表接口")
public class DictController extends BladeController {

    private IDictService dictService;

    /**
     * 详情
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入dict")
    public R<DictVO> detail(Dict dict) {
        Dict detail = dictService.getOne(Condition.getQueryWrapper(dict));
        return R.data(DictWrapper.build().entityVO(detail));
    }

    /**
     * 列表 字典表
     */
    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "字典编号", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dictValue", value = "字典名称", paramType = "query", dataType = "string")
    })
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "列表", notes = "传入dict")
    public R<List<INode>> list(@ApiIgnore @RequestParam Map<String, Object> dict) {
        List<Dict> list = dictService.list(Condition.getQueryWrapper(dict, Dict.class).lambda().orderByAsc(Dict::getSort));
        return R.data(DictWrapper.build().listNodeVO(list));
    }

    /**
     * 顶级列表 字典表
     */
    @GetMapping("/parent-list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "字典编号", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dictValue", value = "字典名称", paramType = "query", dataType = "string")
    })
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "列表", notes = "传入dict")
    public R<IPage<DictVO>> parentList(@RequestParam Map<String, Object> dict, Query query) {
        return R.data(dictService.parentList(dict, query));
    }

    /**
     * 子列表
     */
    @GetMapping("/child-list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "字典编号", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dictValue", value = "字典名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "parentId", value = "字典名称", paramType = "query", dataType = "string")
    })
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "列表", notes = "传入dict")
    public R<IPage<DictVO>> childList(@RequestParam Map<String, Object> dict, @RequestParam(required = false, defaultValue = "-1") Long parentId, Query query) {
        return R.data(dictService.childList(dict, parentId, query));
    }

    /**
     * 获取字典树形结构
     *
     * @return
     */
    @GetMapping("/tree")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "树形结构", notes = "树形结构")
    public R<List<DictVO>> tree() {
        List<DictVO> tree = dictService.tree();
        return R.data(tree);
    }

    /**
     * 获取字典父节点树形结构
     *
     * @return
     */
    @GetMapping("/parent-tree")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "树形结构", notes = "树形结构")
    public R<List<DictVO>> parentTree() {
        List<DictVO> tree = dictService.parentTree();
        return R.data(tree);
    }

    /**
     * 新增或修改
     */
    @PostMapping("/submit")
    @ApiOperationSupport(order = 7)
    public R submit(@Valid @RequestBody Dict dict) {
        CacheUtil.clear(DICT_CACHE);
        return R.status(dictService.submit(dict));
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        CacheUtil.clear(DICT_CACHE);
        return R.status(dictService.removeDict(ids));
    }

    /**
     * 获取字典
     *
     * @return
     */
    @GetMapping("/dictionary")
    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "获取字典", notes = "获取字典")
    public R<List<Dict>> dictionary(String code) {
        List<Dict> tree = dictService.getList(code);
        return R.data(tree);
    }

}

package org.springblade.modules.system.controller;

import io.swagger.annotations.*;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;

import javax.validation.Valid;

import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.node.INode;
import org.springblade.core.tool.support.Kv;
import org.springblade.modules.system.entity.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springblade.modules.system.entity.Dept;
import org.springblade.modules.system.vo.DeptVO;
import org.springblade.modules.system.wrapper.DeptWrapper;
import org.springblade.modules.system.service.IDeptService;
import org.springblade.core.boot.ctrl.BladeController;

import java.util.List;
import java.util.Map;

import static org.springblade.core.cache.constant.CacheConstant.SYS_CACHE;

/**
 * 部门表 控制器
 *
 * @author zjm
 * @since 2021-05-10
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dept")
@Api(value = "部门表", tags = "部门表接口")
public class DeptController extends BladeController {

    private IDeptService deptService;

    /**
     * 详情
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入dept")
    public R<DeptVO> detail(Dept dept) {
        Dept detail = deptService.getOne(Condition.getQueryWrapper(dept));
        return R.data(DeptWrapper.build().entityVO(detail));
    }

    /**
     * 列表 部门表
     */
    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptName", value = "部门名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "fullName", value = "部门全称", paramType = "query", dataType = "string")
    })
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "列表", notes = "传入dept")
    public R<List<INode>> list(@RequestParam Map<String, Object> dept) {
        return R.data(DeptWrapper.build().listNodeVO(deptService.list()));
    }

    /**
     * 懒加载列表
     */
    @GetMapping("/lazy-list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptName", value = "部门名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "fullName", value = "部门全称", paramType = "query", dataType = "string")
    })
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "懒加载列表", notes = "传入dept")
    public R<List<INode>> lazyList(@RequestParam Map<String, Object> dept, Long parentId) {
        return R.data(DeptWrapper.build().listNodeLazyVO(deptService.lazyList(parentId, dept)));
    }

    /**
     * 获取部门树形结构
     */
    @GetMapping("/tree")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "树形结构", notes = "树形结构")
    public R<List<DeptVO>> tree() {
        return R.data(deptService.tree());
    }

    /**
     * 懒加载获取部门树形结构
     */
    @GetMapping("/lazy-tree")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "懒加载树形结构", notes = "树形结构")
    public R<List<DeptVO>> lazyTree(Long parentId) {
        return R.data(deptService.lazyTree(parentId));
    }

    /**
     * 新增或修改 部门表
     */
    @PostMapping("/submit")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "新增或修改", notes = "传入dept")
    public R submit(@Valid @RequestBody Dept dept) {
        if (deptService.submit(dept)) {
            CacheUtil.clear(SYS_CACHE);
            // 返回懒加载树更新节点所需字段
            Kv kv = Kv.create()
                    .set("id", String.valueOf(dept.getId()));
            return R.data(kv);
        }
        return R.fail("操作失败！");
    }

    /**
     * 删除 部门表
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "逻辑删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        CacheUtil.clear(SYS_CACHE);
        return R.status(deptService.removeDept(ids));
    }

}

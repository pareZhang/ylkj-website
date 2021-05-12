package org.springblade.modules.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;

import javax.validation.Valid;

import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DigestUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.user.entity.RoleClient;
import org.springblade.modules.user.service.IRoleClientService;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.modules.user.entity.AuthClient;
import org.springblade.modules.user.service.IAuthClientService;
import org.springblade.core.boot.ctrl.BladeController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springblade.core.cache.constant.CacheConstant.SYS_CACHE;

/**
 * 应用管理控制器
 *
 * @author zjm
 * @since 2021-05-10
 */
@RestController
@AllArgsConstructor
@RequestMapping("/client")
@Api(value = "用用管理", tags = "应用管理")
public class AuthClientController extends BladeController {

    private IAuthClientService clientService;

    private IRoleClientService roleClientService;

    /**
     * 详情
     *
     * @return
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入client")
    public R<AuthClient> detail(AuthClient authClient) {
        AuthClient detail = clientService.getById(authClient.getId());
        return R.data(detail);
    }

    /**
     * 查询所有
     */
    @GetMapping("/findAll")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "查询所有数据", notes = "查询所有数据")
    public R<List<AuthClient>> findAll() {
        return R.data(clientService.list());
    }

    /**
     * 分页
     */
    @GetMapping("/list")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "分页", notes = "传入client")
    public R<IPage<AuthClient>> list(AuthClient authClient, Query query) {
        IPage<AuthClient> pages = clientService.findPage(Condition.getPage(query), authClient);
        return R.data(pages);
    }

    /**
     * 新增
     */
    @PostMapping("/save")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "新增", notes = "传入client")
    public R save(@Valid @RequestBody AuthClient authClient) {
        String clientSecret = DigestUtil.md5Hex(new StringBuilder(authClient.getName()).append(authClient.getClientId()).toString());
        authClient.setClientSecret(clientSecret);
        boolean success = clientService.save(authClient);
        if (success) {
            CacheUtil.clear(SYS_CACHE);
        }
        return R.status(success);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "修改", notes = "传入client")
    public R update(@Valid @RequestBody AuthClient authClient) {
        boolean success = clientService.updateById(authClient);
        if (success) {
            CacheUtil.clear(SYS_CACHE);
        }
        return R.status(success);
    }

    /**
     * 新增或修改
     */
    @PostMapping("/submit")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "新增或修改", notes = "传入client")
    public R submit(@Valid @RequestBody AuthClient authClient) {
        String clientSecret = DigestUtil.md5Hex(new StringBuilder(authClient.getName()).append(authClient.getClientId()).toString());
        authClient.setClientSecret(clientSecret);
        boolean success = clientService.saveOrUpdate(authClient);
        if (success) {
            CacheUtil.clear(SYS_CACHE);
        }
        return R.status(success);
    }


    /**
     * 删除
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "逻辑删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        //return R.status(clientService.deleteLogic(Func.toLongList(ids)));
        List<String> arrid = Arrays.asList(ids.split(","));
        boolean success = clientService.removeByIds(arrid);
        if (success) {
            CacheUtil.clear(SYS_CACHE);
        }
        return R.status(success);
    }

    @GetMapping("/client-role-keys")
    @ApiOperationSupport(order = 9)
    @ApiOperation(value = "获取应用授权", notes = "传入角色ids")
    public R<List<String>> roleClientKeys(String roleId) {
        List<String> list = null;
        List<RoleClient> roleClients = roleClientService.roleClientKeys(Func.toLong(roleId));
        if (null != roleClients && roleClients.size() > 0) {
            list = roleClients.stream().map(roleClient -> Func.toStr(roleClient.getClientId())).collect(Collectors.toList());
        }
        return R.data(list);
    }

}

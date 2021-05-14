
package org.springblade.modules.system.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;

import oracle.jdbc.proxy.annotation.Post;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.Sm4Util;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.modules.system.entity.Crt;
import org.springblade.modules.system.wrapper.CrtWrapper;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.modules.system.vo.CrtVO;
import org.springblade.modules.system.service.ICrtService;
import org.springblade.core.boot.ctrl.BladeController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * crt证书表 控制器
 *
 * @author zjm
 * @since 2021-05-10
 */
@RestController
@AllArgsConstructor
@RequestMapping("/crt")
@Api(value = "crt证书表", tags = "crt证书表接口")
public class CrtController extends BladeController {

    private ICrtService crtService;

    /**
     * 详情
     * js是通过id来查询
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入crt")
    public R<CrtVO> detail(Crt crt) {
        Crt detail = crtService.getOne(Condition.getQueryWrapper(crt));
        return R.data(CrtWrapper.build().entityVO(detail));
    }

    /**
     * 分页 查询所有
     * @param crt
     * @param query
     * @return
     */
    @GetMapping("/list")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "分页", notes = "传入crt")
    public R<IPage<CrtVO>> list(Crt crt,Query query){
        IPage<Crt> pages = crtService.page(Condition.getPage(query), Condition.getQueryWrapper(crt));
        return R.data(CrtWrapper.build().pageVO(pages));
    }

    /**
     * 新增或修改
     * @param crt
     * @return
     */
    @PostMapping("/submit")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "新增或者修改",notes = "传入crt")
    public R submit(@Valid @RequestBody Crt crt){
        return R.status(crtService.saveOrUpdate(crt));
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "逻辑删除",notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids){
        return R.status(crtService.removeByIds(Func.toLongList(ids)));
    }

    /**
     * 下载证书
     * @param key
     * @param id
     * @param response
     */
    @GetMapping("export-cert")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "导出证书")
    public void exportCert(String key, String id, HttpServletResponse response){
        try {
            if(StringUtil.isEmpty(key)) {
                throw new Error("密钥不能为空");
            }
            if(StringUtil.isEmpty(id)) {
                throw new Error("用户编号不能为空");
            }
            BladeUser bladeUser = BladeUser.parse(Func.toLong(id));
            key = "ynld"+key+"ylkj";
            String code = Func.md5Hex(key);
            String params = JSONObject.toJSONString(bladeUser.getUserInfo());
            String str = Sm4Util.encryptEcb(code,params);
            Sm4Util.exportTxt(response,bladeUser.getName(),str);
        }catch (Exception e){
            throw new Error("导出失败");
        }
    }
}

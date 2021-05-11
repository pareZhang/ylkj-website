package org.springblade.modules.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.modules.user.entity.AuthClient;
import org.springblade.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户端表 服务类
 *
 * @author zjm
 * @since 2021-05-10
 */
public interface IAuthClientService extends IService<AuthClient> {



    /**
     * 分页查询
     * @param authClient
     * @return
     */
    IPage<AuthClient> findPage(IPage<AuthClient> page,AuthClient authClient);;
}

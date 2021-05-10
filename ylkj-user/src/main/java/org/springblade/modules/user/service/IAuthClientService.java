package org.springblade.modules.user.service;

import org.springblade.modules.user.entity.AuthClient;
import org.springblade.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户端表 服务类
 *
 * @author zjm
 * @since 2021-05-10
 */
public interface IAuthClientService extends BaseService<AuthClient> {



    /**
     * 分页查询
     * @param authClient
     * @return
     */
    IPage<AuthClient> findPage(IPage<AuthClient> page,AuthClient authClient);;
}

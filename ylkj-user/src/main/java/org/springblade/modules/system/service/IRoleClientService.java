package org.springblade.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.modules.system.entity.RoleClient;

import java.util.List;

/**
 * @author zjm
 * @since 2021-05-12 16:28
 */
public interface IRoleClientService extends IService<RoleClient> {
    /**
     *角色授权
     * @param roleId
     * @param clientIds
     * @return
     */
    boolean grant(Long roleId, List<Long> clientIds);

    /**
     *  获取选中角色已授权的应用ID
     * @param roleId
     * @return
     */
    List<RoleClient> roleClientKeys(long roleId);
}

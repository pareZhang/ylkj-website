package org.springblade.modules.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.modules.system.entity.RoleClient;
import org.springblade.modules.system.mapper.RoleClientMapper;
import org.springblade.modules.system.service.IRoleClientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zjm
 * @since 2021-05-12 16:29
 */
@Service
public class RoleClientServiceImpl extends ServiceImpl<RoleClientMapper, RoleClient> implements IRoleClientService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean grant(Long roleId, List<Long> clientIds) {
        // 删除角色配置的应用集合
        remove(Wrappers.<RoleClient>query().lambda().in(RoleClient::getRoleId,roleId));
        List<RoleClient> roleClients = new ArrayList<>();
        clientIds.forEach(clientId -> {
            RoleClient roleClient = new RoleClient();
            roleClient.setRoleId(roleId);
            roleClient.setClientId(clientId);
            roleClients.add(roleClient);
        });
        return saveBatch(roleClients);
    }

    @Override
    public List<RoleClient> roleClientKeys(long roleId) {
        return baseMapper.roleClientKeys(roleId);
    }
}

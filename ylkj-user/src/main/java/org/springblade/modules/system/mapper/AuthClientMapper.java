package org.springblade.modules.system.mapper;

import org.springblade.modules.system.entity.AuthClient;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 客户端表 Mapper 接口
 *
 * @author zjm
 * @since 2021-05-10
 */
public interface AuthClientMapper extends BaseMapper<AuthClient> {

	/**
	 * 分页
	 * @param page
	 * @param authClient
	 * @return
	 */

	List<AuthClient> findPage(IPage<AuthClient> page, AuthClient authClient);
}

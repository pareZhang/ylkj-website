package org.springblade.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.core.mp.base.BaseService;
import org.springblade.modules.system.entity.Crt;
import org.springblade.modules.system.vo.CrtVO;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * crt证书表 服务类
 *
 * @author zjm
 * @since 2021-05-10
 */
public interface ICrtService extends IService<Crt> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param crt
	 * @return
	 */
	IPage<CrtVO> selectCrtPage(IPage<CrtVO> page, CrtVO crt);

}

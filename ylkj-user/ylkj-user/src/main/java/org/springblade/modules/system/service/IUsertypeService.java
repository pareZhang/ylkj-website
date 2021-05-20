package org.springblade.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.modules.system.entity.Usertype;
import org.springblade.modules.system.vo.UsertypeVO;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务类
 *
 * @author zjm
 * @since 2021-05-10
 */
public interface IUsertypeService extends IService<Usertype> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param usertype
	 * @return
	 */
	IPage<UsertypeVO> selectUsertypePage(IPage<UsertypeVO> page, UsertypeVO usertype);

}

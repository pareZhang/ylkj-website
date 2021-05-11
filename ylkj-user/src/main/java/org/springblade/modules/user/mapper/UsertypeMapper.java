package org.springblade.modules.user.mapper;

import org.springblade.modules.user.entity.Usertype;
import org.springblade.modules.user.vo.UsertypeVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 *  Mapper 接口
 *
 * @author zjm
 * @since 2021-05-10
 */
public interface UsertypeMapper extends BaseMapper<Usertype> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param usertype
	 * @return
	 */
	List<UsertypeVO> selectUsertypePage(IPage page, UsertypeVO usertype);

}

package org.springblade.modules.system.mapper;

import org.springblade.modules.system.entity.Crt;
import org.springblade.modules.system.vo.CrtVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * crt证书表 Mapper 接口
 *
 * @author zjm
 * @since 2021-05-10
 */
public interface CrtMapper extends BaseMapper<Crt> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param crt
	 * @return
	 */
	List<CrtVO> selectCrtPage(IPage page, CrtVO crt);

}

package org.springblade.modules.system.mapper;

import org.springblade.modules.system.entity.Pay;
import org.springblade.modules.system.vo.PayVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 支付信息表 Mapper 接口
 *
 * @author zjm
 * @since 2021-05-10
 */
public interface PayMapper extends BaseMapper<Pay> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param pay
	 * @return
	 */
	List<PayVO> selectPayPage(IPage page, PayVO pay);

}

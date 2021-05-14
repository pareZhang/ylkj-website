package org.springblade.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.modules.system.entity.Crt;
import org.springblade.modules.system.entity.Feedback;
import org.springblade.modules.system.mapper.CrtMapper;
import org.springblade.modules.system.mapper.FeedbackMapper;
import org.springblade.modules.system.vo.CrtVO;
import org.springblade.modules.system.service.ICrtService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * crt证书表 服务实现类
 *
 * @author zjm
 * @since 2021-05-10
 */
@Service
public class CrtServiceImpl extends ServiceImpl<CrtMapper, Crt> implements ICrtService {

	@Override
	public IPage<CrtVO> selectCrtPage(IPage<CrtVO> page, CrtVO crt) {
		return null;
	}
}

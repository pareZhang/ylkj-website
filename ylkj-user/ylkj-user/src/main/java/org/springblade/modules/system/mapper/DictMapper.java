package org.springblade.modules.system.mapper;

import org.springblade.modules.system.entity.Dict;
import org.springblade.modules.system.vo.DictVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 字典表 Mapper 接口
 *
 * @author zjm
 * @since 2021-05-10
 */
public interface DictMapper extends BaseMapper<Dict> {

	/**
	 * 获取字典表对应中文
	 * @param code    字典编号
	 * @param dictKey 字典序号
	 * @return
	 */
    String getValue(String code, String dictKey);

	/**
	 * 获取字典表
	 * @param code
	 * @return
	 */
    List<Dict> getList(String code);

	/**
	 * 树形结构
	 * @return
	 */
	List<DictVO> tree();

	/**
	 * 树形结构
	 * @return
	 */
	List<DictVO> parentTree();
}

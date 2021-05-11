package org.springblade.modules.user.service;

import org.springblade.core.mp.support.Query;
import org.springblade.modules.user.entity.Dict;
import org.springblade.modules.user.vo.DictVO;
import org.springblade.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

/**
 * 字典表 服务类
 *
 * @author zjm
 * @since 2021-05-10
 */
public interface IDictService extends BaseService<Dict> {
	/**
	 * 获取字典表对应中文
	 *
	 * @param code    字典编号
	 * @param dictKey 字典序号
	 * @return
	 */
	String getValue(String code, String dictKey);

	/**
	 * 获取字典表
	 *
	 * @param code 字典编号
	 * @return
	 */
	List<Dict> getList(String code);

	/**
	 * 顶级列表
	 * @param dict
	 * @param query
	 * @return
	 */
    IPage<DictVO> parentList(Map<String, Object> dict, Query query);

	/**
	 * 子列表
	 * @param dict
	 * @param parentId
	 * @param query
	 * @return
	 */
	IPage<DictVO> childList(Map<String, Object> dict, Long parentId, Query query);

	/**
	 * 字典树形结构
	 * @return
	 */
	List<DictVO> tree();

	/**
	 * 字典父节点树形结构
	 * @return
	 */
	List<DictVO> parentTree();

	/**
	 * 新增或修改
	 * @param dict
	 * @return
	 */
	boolean submit(Dict dict);

	/**
	 * 删除字典
	 * @param ids
	 * @return
	 */
	boolean removeDict(String ids);
}

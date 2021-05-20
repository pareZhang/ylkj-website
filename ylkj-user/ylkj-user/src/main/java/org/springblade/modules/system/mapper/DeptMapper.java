package org.springblade.modules.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.springblade.modules.system.entity.Dept;
import org.springblade.modules.system.entity.User;
import org.springblade.modules.system.vo.DeptVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 部门表 Mapper 接口
 *
 * @author zjm
 * @since 2021-05-10
 */
public interface DeptMapper extends BaseMapper<Dept> {


	/**
	 * 懒加载部门列表
	 * @param parentId
	 * @param dept
	 * @return
	 */
    List<DeptVO> lazyList(Long parentId, Map<String, Object> dept);

	/**
	 *树形结构
	 * @return
	 */
	List<DeptVO> tree();

	/**
	 * 懒加载树形结构
	 * @param parentId
	 * @return
	 */
	List<DeptVO> lazyTree(Long parentId);

}

package org.springblade.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.modules.system.entity.Dept;
import org.springblade.modules.system.vo.DeptVO;

import java.util.List;
import java.util.Map;

/**
 * 部门表 服务类
 *
 * @author zjm
 * @since 2021-05-10
 */
public interface IDeptService extends IService<Dept> {

	/**
	 * 获取子部门ID
	 *
	 * @param deptId
	 * @return
	 */
	List<Dept> getDeptChild(Long deptId);

	/**
	 * 根据部门名称获取Id
	 * @param deptName
	 * @return
	 */
	String getDeptId(String deptName);

	/**
	 * 懒加载部门列表
	 * @param parentId
	 * @param dept
	 * @return
	 */
	List<DeptVO> lazyList(Long parentId, Map<String, Object> dept);

	/**
	 * 部门树形结构
	 * @return
	 */
	List<DeptVO> tree();

	/**
	 * 懒加载树形结构
	 * @param parentId
	 * @return
	 */
	List<DeptVO> lazyTree(Long parentId);

	/**
	 * 新增或修改
	 * @param dept
	 * @return
	 */
	boolean submit(Dept dept);

	/**
	 * 删除部门
	 * @param ids
	 * @return
	 */
	boolean removeDept(String ids);
}

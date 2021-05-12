package org.springblade.modules.system.wrapper;

import org.springblade.common.cache.SysCache;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.node.ForestNodeMerger;
import org.springblade.core.tool.node.INode;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.entity.Dept;
import org.springblade.modules.system.vo.DeptVO;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 部门表包装类,返回视图层所需的字段
 *
 * @author zjm
 * @since 2021-05-10
 */
public class DeptWrapper extends BaseEntityWrapper<Dept, DeptVO>  {

    public static DeptWrapper build() {
        return new DeptWrapper();
    }

	/**
	 * VO
	 * @param dept
	 * @return
	 */
	@Override
	public DeptVO entityVO(Dept dept) {
		DeptVO deptVO = Objects.requireNonNull(BeanUtil.copy(dept, DeptVO.class));
		if (Func.equals(dept.getParentId(), BladeConstant.TOP_PARENT_ID)) {
			deptVO.setParentName(BladeConstant.TOP_PARENT_NAME);
		} else {
			Dept parent = SysCache.getDept(dept.getParentId());
			deptVO.setParentName(parent.getDeptName());
		}
		return deptVO;
	}

	/**
	 * 列表VO
	 * @param list
	 * @return
	 */
	public List<INode> listNodeVO(List<Dept> list) {
		List<INode> collect = list.stream().map(dept -> {
			DeptVO deptVO = BeanUtil.copy(dept, DeptVO.class);
			Objects.requireNonNull(deptVO);
			return deptVO;
		}).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

	/**
	 * 懒加载列表vo
	 * @param list
	 * @return
	 */
	public List<INode> listNodeLazyVO(List<DeptVO> list) {
		List<INode> collect = list.stream().peek(dept -> {
			Objects.requireNonNull(dept);
		}).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

}

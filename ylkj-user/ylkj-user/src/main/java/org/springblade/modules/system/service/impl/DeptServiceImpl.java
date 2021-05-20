package org.springblade.modules.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.node.ForestNodeMerger;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.modules.system.entity.Dept;
import org.springblade.modules.system.entity.User;
import org.springblade.modules.system.vo.DeptVO;
import org.springblade.modules.system.mapper.DeptMapper;
import org.springblade.modules.system.service.IDeptService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 部门表 服务实现类
 *
 * @author zjm
 * @since 2021-05-10
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

	@Override
	public List<Dept> getDeptChild(Long deptId) {
		return baseMapper.selectList(Wrappers.<Dept>query().lambda().like(Dept::getAncestors, deptId));
	}

	@Override
	public String getDeptId(String deptName) {
		Dept dept = baseMapper.selectOne(Wrappers.<Dept>query().lambda().in(Dept::getDeptName, Func.toStrList(deptName)));
		if (dept != null) {
			return Func.toStr(dept.getId());
		}
		return null;
	}

	@Override
	public List<DeptVO> lazyList(Long parentId, Map<String, Object> dept) {
		if (Func.isEmpty(dept.get("parentId"))){
			parentId=null;
		}
		return baseMapper.lazyList(parentId,dept);
	}

	@Override
	public List<DeptVO> tree() {
		return ForestNodeMerger.merge(baseMapper.tree());
	}

	@Override
	public List<DeptVO> lazyTree(Long parentId) {
		return ForestNodeMerger.merge(baseMapper.lazyTree(parentId));
	}

	@Override
	public boolean submit(Dept dept) {
		//判断是否是顶级节点
		if (Func.isEmpty(dept.getParentId())){
			dept.setParentId(BladeConstant.TOP_PARENT_ID);
			dept.setAncestors(String.valueOf(BladeConstant.TOP_PARENT_ID));
		}
		//次级节点
		if (dept.getParentId()>0){
			//得到父级
			Dept parent=getById(dept.getParentId());
			if (Func.toLong(dept.getParentId()) == Func.toLong(dept.getId())){
				throw new ServiceException("父节点不可选择自身！");
			}
			//祖级节点
			String ancestors=parent.getAncestors()+ StringPool.COMMA+dept.getParentId();
			dept.setAncestors(ancestors);
		}
		return saveOrUpdate(dept);
	}

	@Override
	public boolean removeDept(String ids) {
		Integer count = baseMapper.selectCount(Wrappers.<Dept>query().lambda().in(Dept::getParentId, Func.toLongList(ids)));
		if (count > 0) {
			throw new ServiceException("请先删除子节点!");
		}
		return removeByIds(Func.toLongList(ids));
	}
}

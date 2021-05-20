package org.springblade.modules.system.wrapper;

import org.springblade.common.cache.DictCache;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.node.ForestNodeMerger;
import org.springblade.core.tool.node.INode;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.entity.Dict;
import org.springblade.modules.system.service.IDictService;
import org.springblade.modules.system.vo.DictVO;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 字典表包装类,返回视图层所需的字段
 *
 * @author zjm
 * @since 2021-05-10
 */
public class DictWrapper extends BaseEntityWrapper<Dict, DictVO> {

    private static IDictService dictService;
//    static {
//        dictService = SpringUtil.getBean(IDictService.class);
//    }

    public static DictWrapper build() {
        return new DictWrapper();
    }

	/*@Override
	public DictVO entityVO(Dict dict) {
		DictVO dictVO = BeanUtil.copy(dict, DictVO.class);
		if (Func.equals(dict.getParentId(), CommonConstant.TOP_PARENT_ID)) {
			dictVO.setParentName(CommonConstant.TOP_PARENT_NAME);
		} else {
			Dict parent = dictService.getById(dict.getParentId());
			dictVO.setParentName(parent.getDictValue());
		}
		return dictVO;
	}*/

    @Override
    public DictVO entityVO(Dict dict) {
        DictVO dictVO = Objects.requireNonNull(BeanUtil.copy(dict, DictVO.class));
        if (Func.equals(dict.getParentId(), BladeConstant.TOP_PARENT_ID)) {
            dictVO.setParentName(BladeConstant.TOP_PARENT_NAME);
        } else {
            Dict parent = DictCache.getById(dict.getParentId());
            dictVO.setParentName(parent.getDictValue());
        }
        return dictVO;
    }

    public List<INode> listNodeVO(List<Dict> list) {
        List<INode> collect = list.stream().map(dict -> BeanUtil.copy(dict, DictVO.class)).collect(Collectors.toList());
        return ForestNodeMerger.merge(collect);
    }
}

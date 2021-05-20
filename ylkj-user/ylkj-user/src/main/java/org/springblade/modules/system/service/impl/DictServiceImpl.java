/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.common.constant.CommonConstant;
import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.node.ForestNodeMerger;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.modules.system.entity.Dict;
import org.springblade.modules.system.vo.DictVO;
import org.springblade.modules.system.mapper.DictMapper;
import org.springblade.modules.system.service.IDictService;
import org.springblade.modules.system.wrapper.DictWrapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

import static org.springblade.core.cache.constant.CacheConstant.DICT_CACHE;

/**
 * 字典表 服务实现类
 *
 * @author zjm
 * @since 2021-05-10
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {
    @Override
    public String getValue(String code, String dictKey) {
        return Func.toStr(baseMapper.getValue(code, dictKey), StringPool.EMPTY);
    }

    @Override
    public List<Dict> getList(String code) {
        return baseMapper.getList(code);
    }

    @Override
    public IPage<DictVO> parentList(Map<String, Object> dict, Query query) {
        IPage<Dict> page = this.page(Condition.getPage(query), Condition.getQueryWrapper(dict, Dict.class).lambda().eq(Dict::getParentId, CommonConstant.TOP_PARENT_ID).orderByAsc(Dict::getSort));
        return DictWrapper.build().pageVO(page);
    }

    @Override
    public IPage<DictVO> childList(Map<String, Object> dict, Long parentId, Query query) {
        dict.remove("parentId");
        IPage<Dict> page = this.page(Condition.getPage(query), Condition.getQueryWrapper(dict, Dict.class).lambda().eq(Dict::getParentId, parentId).orderByAsc(Dict::getSort));
        return DictWrapper.build().pageVO(page);
    }

    @Override
    public List<DictVO> tree() {
        return ForestNodeMerger.merge(baseMapper.tree());
    }

    @Override
    public List<DictVO> parentTree() {
        return ForestNodeMerger.merge(baseMapper.parentTree());
    }

    @Override
    public boolean submit(Dict dict) {
        LambdaQueryWrapper<Dict> lqw = Wrappers.<Dict>query().lambda().eq(Dict::getCode, dict.getCode()).eq(Dict::getDictKey, dict.getDictKey());
        Integer count = baseMapper.selectCount((Func.isEmpty(dict.getId())) ? lqw : lqw.notIn(Dict::getId, dict.getId()));
        if (count > 0) {
            throw new ServiceException("当前字典键值已存在!");
        }
        if (Func.isEmpty(dict.getParentId())) {
            dict.setParentId(BladeConstant.TOP_PARENT_ID);
        }
        dict.setIsDeleted(BladeConstant.DB_NOT_DELETED);
        CacheUtil.clear(DICT_CACHE);
        return saveOrUpdate(dict);
    }

    @Override
    public boolean removeDict(String ids) {
        Integer count = baseMapper.selectCount(Wrappers.<Dict>query().lambda().in(Dict::getParentId, Func.toLongList(ids)));
        if (count > 0) {
            throw new ServiceException("请先删除子节点！");
        }
        return removeByIds(Func.toLongList(ids));
    }

}

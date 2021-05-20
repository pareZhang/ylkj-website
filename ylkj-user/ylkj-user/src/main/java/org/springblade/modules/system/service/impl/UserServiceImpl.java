package org.springblade.modules.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.common.cache.SysCache;
import org.springblade.common.cache.UserCache;
import org.springblade.common.constant.CommonConstant;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.constant.RoleConstant;
import org.springblade.core.tool.utils.*;
import org.springblade.modules.system.Excel.UserExcel;
import org.springblade.modules.system.entity.Dept;
import org.springblade.modules.system.entity.User;
import org.springblade.modules.system.entity.UserOauth;
import org.springblade.modules.system.mapper.UserMapper;
import org.springblade.modules.system.service.IUserOauthService;
import org.springblade.modules.system.service.IUserService;
import org.springblade.modules.system.vo.UserVO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;


import javax.validation.constraints.NotEmpty;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 用户表 服务实现类
 *
 * @author zjm
 * @since 2021-05-10
 */
@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private IUserOauthService userOauthService;
    @Override
    public User userByAccount(String account) {
        return baseMapper.selectOne(Wrappers.<User>query().lambda().eq(User::getPhone,account).eq(User::getStatus, 0));
    }

    @Override
    public IPage<User> selectUserPage(IPage<User> page, User user, Long deptId) {
        List<Long> deptIdList = SysCache.getDeptChildIds(deptId);
        return page.setRecords(baseMapper.selectUserPage(page, user, deptIdList));
    }

    @Override
    public Map<String, Object> idCardCert(String name, String idCard) {
        String host = "https://idcardcert.market.alicloudapi.com";
        String path = "/idCardCert";
        String appcode = "a730ee68df694c328f1dbc81895d6eab";
        String urlSend = null;
        //拼接请求链接
        Map<String, Object> resultMap = new HashMap<>();
        try {
            urlSend = host + path + "?idCard=" + idCard + "&name=" + URLEncoder.encode(name, "UTF-8");
            URL url = new URL(urlSend);
            HttpURLConnection httpURLCon = (HttpURLConnection) url.openConnection();
            // 格式Authorization:APPCODE
            httpURLCon.setRequestProperty("Authorization", "APPCODE " + appcode);
            int httpCode = httpURLCon.getResponseCode();
            if (httpCode == 200) {
                String json = read(httpURLCon.getInputStream());
                resultMap.put("msg", "success");
                resultMap.put("data:", json);
            } else {
                Map<String, List<String>> map = httpURLCon.getHeaderFields();
                String error = map.get("X-Ca-Error-Message").get(0);
                if (httpCode == 400 && error.equals("Invalid AppCode `not exists`")) {
                    resultMap.put("msg", "AppCode错误");
                } else if (httpCode == 400 && error.equals("Invalid Url")) {
                    resultMap.put("msg", "请求的 Method、Path 或者环境错误");
                } else if (httpCode == 400 && error.equals("Invalid Param Location")) {
                    resultMap.put("msg", "参数错误");
                } else if (httpCode == 403 && error.equals("Unauthorized")) {
                    resultMap.put("msg", "服务未被授权（或URL和Path不正确）");
                } else if (httpCode == 403 && error.equals("Quota Exhausted")) {
                    resultMap.put("msg", "套餐包次数用完");
                } else {
                    resultMap.put("msg", "参数名错误 或 其他错误");
                    resultMap.put("error", error);
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 读取返回结果
     *
     * @param is
     * @return
     * @throws IOException
     */
    private static String read(InputStream is) throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        while ((line = br.readLine()) != null) {
            line = new String(line.getBytes(), "utf-8");
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    @Override
    public boolean submit(User user) {
        //判断是否已经存在
        Integer count = baseMapper.selectCount(Wrappers.<User>query().lambda().eq(User::getEmail,user.getEmail()).eq(User::getPhone,user.getPhone()));
        if (count>0){
            throw new ApiException(StringUtil.format("该用户已经存在",user.getPhone(),user.getEmail()));
        }
        //默认角色 单位和用户类型
        if (Func.isNotEmpty(user.getPassword())){
            user.setPassword(DigestUtil.encrypt(user.getPassword()));
            user.setRoleId(CommonConstant.DEFAULT_ROLE_ID);
            user.setDeptId(CommonConstant.DEFAULT_DEPT_ID);
            user.setUsertypeId(CommonConstant.DEFAULT_USERTYPE_ID);
        }
        return saveOrUpdate(user);
    }

    @Override
    public boolean removeUser(String userIds) {
        //不能自己删除自己（注销）
        if (Func.contains(Func.toLongArray(userIds), AuthUtil.getUserId())) {
            throw new ServiceException("不能删除本账号!");
        }
        return removeByIds(Func.toLongList(userIds));
    }

    @Override
    public boolean resetPassword(String userIds) {
        User user=new User();
        //重置密码为sjgl123456
        user.setPassword(DigestUtil.encrypt(CommonConstant.DEFAULT_PASSWORD));
        user.setUpdateTime(DateUtil.now());
        return this.update(user, Wrappers.<User>update().lambda().in(User::getId, Func.toLongList(userIds)));
    }

    @Override
    public boolean updatePassword(Long userId, String oldPassword, String newPassword, String newPassword1) {
        User user = getById(userId);
        if (!newPassword.equals(newPassword1)) {
            throw new ServiceException("请输入正确的确认密码!");
        }
        if (!user.getPassword().equals(DigestUtil.encrypt(oldPassword))) {
            throw new ServiceException("原密码不正确!");
        }
        //通过userId重新set密码
        return this.update(Wrappers.<User>update().lambda().set(User::getPassword, DigestUtil.encrypt(newPassword)).eq(User::getId, userId));
    }

    @Override
    public boolean grant(String userIds, String roleIds) {
        User user = new User();
        user.setRoleId(roleIds);
        return this.update(user, Wrappers.<User>update().lambda().in(User::getId, Func.toLongList(userIds)));
    }

    @Override
    public void importUser(List<UserExcel> data, Boolean isCovered) {
        data.forEach(userExcel -> {
            User user = Objects.requireNonNull(BeanUtil.copy(userExcel, User.class));
            // 设置部门ID
            user.setDeptId(SysCache.getDeptId(userExcel.getDeptName()));
            // 设置角色ID
            user.setRoleId(SysCache.getRoleIds(userExcel.getRoleName()));
            // 用户类型ID
            user.setUsertypeId(CommonConstant.DEFAULT_USERTYPE_ID);
            // 覆盖数据
            if (isCovered) {
                // 查询用户是否存在
                User oldUser = UserCache.getUser(userExcel.getAccount());
                if (oldUser != null && oldUser.getId() != null) {
                    user.setId(oldUser.getId());
                    updateById(user);
                    return;
                }
            }
            // 获取默认密码配置
            user.setPassword(DigestUtil.encrypt(CommonConstant.DEFAULT_PASSWORD));
            this.save(user);
        });
    }

    @Override
    public List<UserExcel> exportUser(QueryWrapper<User> queryWrapper) {
        List<UserExcel> userList = baseMapper.exportUser(queryWrapper);
        userList.forEach(user -> {
            user.setRoleName(StringUtil.join(SysCache.getRoleNames(user.getRoleId())));
            user.setDeptName(SysCache.getDeptName(user.getDeptId()));
        });
        return userList;
    }

    //TODO 设置默认角色和部门
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean registerGuest(User user, Long oauthId) {
        UserOauth userOauth = userOauthService.getById(oauthId);
        if (userOauth == null || userOauth.getId() == null) {
            throw new ApiException("第三方登陆信息错误!");
        }
        user.setRealName(user.getRealName());
        user.setAvatar(userOauth.getAvatar());
        //设置默认角色
        user.setRoleId(CommonConstant.DEFAULT_ROLE_ID);
        //设置默认部门
        user.setDeptId(CommonConstant.DEFAULT_DEPT_ID);
        user.setUsertypeId(CommonConstant.DEFAULT_USERTYPE_ID);

        boolean userTemp = this.save(user);
        userOauth.setUserId(user.getId());
        boolean oauthTemp = userOauthService.updateById(userOauth);
        return (userTemp && oauthTemp);
    }

    /**
     * 登录认证
     * @param account
     * @param password
     * @return
     */
    @Override
    public User oauthLogin(String account, String password) {
        return baseMapper.login(account, password);
    }

}

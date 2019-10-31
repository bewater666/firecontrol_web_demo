package com.orient.firecontrol_web_demo.service.user;

import com.orient.firecontrol_web_demo.config.common.StringUtil;
import com.orient.firecontrol_web_demo.config.exception.CustomException;
import com.orient.firecontrol_web_demo.config.jwt.JwtUtil;
import com.orient.firecontrol_web_demo.config.password.AesCipherUtil;
import com.orient.firecontrol_web_demo.config.shiro.UserRealm;
import com.orient.firecontrol_web_demo.dao.user.RoleDao;
import com.orient.firecontrol_web_demo.dao.user.UserDao;
import com.orient.firecontrol_web_demo.dao.user.UserRoleDao;
import com.orient.firecontrol_web_demo.model.common.Constant;
import com.orient.firecontrol_web_demo.model.common.ResultBean;
import com.orient.firecontrol_web_demo.model.user.Role;
import com.orient.firecontrol_web_demo.model.user.User;
import com.orient.firecontrol_web_demo.model.user.UserRole;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author bewater
 * @version 1.0
 * @date 2019/10/25 13:55
 * @func
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    UserRealm userRealm;


    /**
     * 人员管理业务代码
     * 超级管理员可以看到所有人员信息
     * 单位领导管理员可以看到该部门下的人员信息
     * @return
     */
    public ResultBean listUser(){
        //取出当前登录的用户信息
        String account = JwtUtil.getClaim(SecurityUtils.getSubject().getPrincipals().toString(), Constant.ACCOUNT);
        Integer enable = userDao.findOneByAccount(account).getEnable();
        if (enable==0){
            throw new CustomException("查询失败该账户已被禁用,请与管理员联系" +
                    "(Query failed this account has been disabled, please contact the administrator)");
        }
        List<Role> byUser = roleDao.findByUser(account);
        //因为在该项目中  我一个账号只设置了一个角色  故byUser这个list中就一个角色 取第一个就好
        Role role = byUser.get(0);
        if (role.getRoleName().equals("superadmin")){
            //若是超级管理员  那么查询出的用户列表就是全部的人员信息
            List<User> all = userDao.findAll();
            return new ResultBean(200, "查询成功(The query is successful)", all);
        }
        if (role.getRoleName().equals("admin")){
            //该角色是某单位的领导
            //需确定具体是哪个部门的领导  取出该账户下的organId
            Integer organId = userDao.findOneByAccount(account).getOrganId();
            List<User> byOrganId = userDao.findByOrganId(organId);
            return new ResultBean(200, "查询成功(The query is successful)", byOrganId);
        }
        //因为在该controller接口 已经约定只有superadmin admin  才可以使用该接口   故别的角色暂时不做判断
        return null;
    }

    /**
     * 将某个用户状态设置为不可用或可用
     * superadmin admin权限
     * @return
     */
    public ResultBean changUserStatus(Integer id){
        //注意这里 单位管理员和超级管理员不可以将自己的状态进行操作
        //获取当前账户
        String account = JwtUtil.getClaim(SecurityUtils.getSubject().getPrincipals().toString(), Constant.ACCOUNT);
        //获取当前账户的id
        Integer id1 = userDao.findOneByAccount(account).getId();
        if (id==id1){
            throw new CustomException("更改用户状态失败:不可以对自己状态进行更改" +
                    "(Failed to change user state: you cannot change your state)");
        }
        int i = userDao.changeUserStatus(id);
        if (i<=0){
            throw new CustomException( "更改用户状态失败(Failed to change user status)" );
        }
        Integer enable = userDao.findByUserId(id).getEnable();
        if (enable==1){
            return new ResultBean(200, "用户状态更改为启用状态(User status changed to enabled)");
        }
        if (enable==0){
            return new ResultBean(200, "用户状态更改为禁用状态(User status changed to disabled)");
        }
        return null;
    }

    /**
     * 保存用户角色
     * @param userRole
     * @return
     */
    @Transactional
    public ResultBean saveUserRole(UserRole userRole){
        //默认 我这里不允许 管理员修改自己的角色
        String account1 = JwtUtil.getClaim(SecurityUtils.getSubject().getPrincipals().toString(), Constant.ACCOUNT);
        Integer id = userDao.findOneByAccount(account1).getId();
        if (id==userRole.getUserId()){
            throw new CustomException("该用户角色不建议修改(单位领导)");
        }
        User byUserId1 = userDao.findByUserId(userRole.getUserId());
        if (byUserId1==null){
            throw new CustomException( "该用户不存在");
        }
        Role byRoleId = roleDao.findByRoleId(userRole.getRoleId());
        if (byRoleId==null){
            throw new CustomException("该角色不存在");
        }
        //先看这个userId在tb_user_role是否有对应的值
        List<UserRole> byUserId = userRoleDao.findByUserId(userRole.getUserId());
        if (byUserId.size()==0){ //没有  那就是给账户赋予角色
            int i = userRoleDao.addUser_Role(userRole);
            if (i<=0){
                throw new CustomException("保存账户角色信息失败");
            }
            //这边没有session  当角色权限改变时  动态刷新权限  还没有做到  日后再改
            return new ResultBean(200, "保存账户角色信息成功", null);
        }else{//有  那就是修改角色信息  将其修改掉即可  我这里一个账户对应一个角色
            int i = userRoleDao.updateUser_Role(userRole);
            if (i<=0){
                throw new CustomException( "保存账户角色信息失败");
            }
            return new ResultBean(200, "保存账户角色信息成功", null);
        }
    }

    /**
     * 查看该用户所具有的角色
     * superadmin    admin权限
     * @return
     */
    public ResultBean checkRoles(Integer userId){
        List<Role> byuserId = roleDao.findByuserId(userId);
        return new ResultBean(200, "查询成功", byuserId);
    }


    /**
     * 管理员新增用户操作
     * 超级管理员添加的是单位领导(默认) 赋予添加的员工admin角色  需要传organId
     * 单位领导添加的默认是该单位下的员工  不要要穿organId 从登录的账户中获得  员工的角色后期自己赋予
     * @return
     */
    @Transactional
    public ResultBean addUser(User user,Integer roleId){
        // 判断当前帐号是否存在
        User oneByAccount = userDao.findOneByAccount(user.getAccount());
        if (oneByAccount != null && StringUtil.isNotBlank(oneByAccount.getPassword())) {
            throw new CustomException("新增用户失败,该账号已存在");
        }
        // 密码以帐号+密码的形式进行AES加密
        if (user.getPassword().length() > Constant.PASSWORD_MAX_LEN) {
            throw new CustomException("密码不得超过8位");
        }
        user.setRegTime(new Date());
        String key = AesCipherUtil.enCrypto(user.getAccount() + user.getPassword());
        user.setPassword(key);
        String account = JwtUtil.getClaim(SecurityUtils.getSubject().getPrincipals().toString(), Constant.ACCOUNT); //获得当前登录的用户
        //根据账户获得它具有的角色列表
        List<Role> byUser = roleDao.findByUser(account);
        //我这里一个账户只有一个角色  所以取第一个就好
        if (byUser.get(0).getRoleName().equals("superadmin")){ //若是超级管理员 那这里添加某单位的领导
            //添加的用户默认可用
            user.setEnable(1);
            userDao.addUser(user);
            Integer userId = userDao.findOneByAccount(user.getAccount()).getId();
            UserRole userRole =new UserRole();
            userRole.setUserId(userId).setRoleId(2);//因为是超级管理员 所以我默认给他添加单位领导角色了
            userRoleDao.addUser_Role(userRole);
            return new ResultBean(HttpStatus.OK.value(), "新增成功(Insert Success)");
        }
        if (byUser.get(0).getRoleName().equals("admin")){
            //这里organId直接获取 前端传错了也没关系
            Integer organIDBy = userDao.findOrganIDBy(account);
            //将要插入的用户信息中的单位id和 操作人的单位id一致
            user.setOrganId(organIDBy);
            //添加的用户默认可用
            user.setEnable(1);
            userDao.addUser(user);
            Integer userId = userDao.findOneByAccount(user.getAccount()).getId();
            UserRole userRole =new UserRole();
            userRole.setUserId(userId).setRoleId(roleId);//这里 单位管理员可以添加 两个角色  角色id前端传入
            if (roleId<=2){
                throw new CustomException("给新增用户设置角色失败(设置权限等级过高)");
            }
            userRoleDao.addUser_Role(userRole);
            return new ResultBean(HttpStatus.OK.value(), "新增成功(Insert Success)");
        }
        return null;
    }

}

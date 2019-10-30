package com.orient.firecontrol_web_demo.service.organ;

import com.orient.firecontrol_web_demo.config.jwt.JwtUtil;
import com.orient.firecontrol_web_demo.dao.organization.OrganDao;
import com.orient.firecontrol_web_demo.dao.user.RoleDao;
import com.orient.firecontrol_web_demo.model.common.Constant;
import com.orient.firecontrol_web_demo.model.common.ResultBean;
import com.orient.firecontrol_web_demo.model.organization.Organization;
import com.orient.firecontrol_web_demo.model.organization.OrganizationDto;
import com.orient.firecontrol_web_demo.model.user.Role;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author bewater
 * @version 1.0
 * @date 2019/10/25 10:17
 * @func
 */
@Service
public class OrganService {
    @Autowired
    private OrganDao organDao;
    @Autowired
    private RoleDao roleDao;

    /**
     * @func  单位管理按钮的显示效果
     * 根据账户不同  查看单位管理显示不同  如超级管理员可以显示所有单位
     * 而其他单位领导只能显示各自管辖的单位
     * @return
     */
    public ResultBean organView(){
        String account = JwtUtil.getClaim(SecurityUtils.getSubject().getPrincipals().toString(), Constant.ACCOUNT); //获得当前登录的用户
        List<Role> byUser = roleDao.findByUser(account);
        for (Role role:
        byUser) {
            if (role.getRoleName().equals("superadmin")){ //若是角色是超级管理员 则显示全部的单位信息
                List<Organization> organizations = organDao.listAll();
                return new ResultBean(200, "查询成功",organizations);
            }
            if (role.getRoleName().equals("admin")){  //若角色是某个单位的领导  则显示自己管辖的单位信息
                Organization byAccount = organDao.findByAccount(account);
                return new ResultBean(200, "查询成功",byAccount);
            }
        }
        return null;
    }


    /**
     * 新建单位信息
     * 这里的业务逻辑 是新建单位  然后在这个单位下新建建筑  在这个建筑下添加设备一步步来的
     * 并不涉及到从之前的建筑来进行解绑松绑  我觉得是这样
     * 超级管理员权限
     * @param organization
     * @return
     */
    public ResultBean addOrgan(Organization organization){
        Organization byOrganName = organDao.findByOrganName(organization.getOrganizationName());
        if (byOrganName!=null){
            return new ResultBean(201, "新建单位失败(该单位已存在)", null);
        }
        int i = organDao.addOrgan(organization);
        if (i<=0){
            return new ResultBean(201, "新建单位失败", null);
        }
        return new ResultBean(200, "新建单位成功", null);
    }

    /**
     * 更新单位名称
     * superadmin admin权限
     * @param organizationDto
     * @return
     */
    public ResultBean updateOrgan(OrganizationDto organizationDto){
        Organization byId = organDao.findById(organizationDto.getId());
        if (byId==null){
            return new ResultBean(201, "更新单位失败(该单位不存在)", null);
        }
        int i = organDao.updateOrgan(organizationDto);
        if (i<=0){
            return new ResultBean(201, "更新单位名称失败", null);
        }
        return new ResultBean(200, "更新单位名称成功", null);
    }
}
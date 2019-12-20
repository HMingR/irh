package top.imuster.user.provider.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.core.dto.UserDto;
import top.imuster.common.core.utils.CusThreadLocal;
import top.imuster.common.core.utils.JwtTokenUtil;
import top.imuster.user.api.bo.ManagementDetails;
import top.imuster.user.api.pojo.AuthInfo;
import top.imuster.user.api.pojo.ManagementInfo;
import top.imuster.user.api.pojo.RoleInfo;
import top.imuster.user.provider.dao.ManagementInfoDao;
import top.imuster.user.provider.service.ManagementInfoService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * ManagementInfoService 实现类
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
@Service("managementInfoService")
public class ManagementInfoServiceImpl extends BaseServiceImpl<ManagementInfo, Long> implements ManagementInfoService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Resource
    private ManagementInfoDao managementInfoDao;

    @Override
    public BaseDao<ManagementInfo, Long> getDao() {
        return this.managementInfoDao;
    }

    @Override
    public ManagementInfo getManagementRoleByCondition(ManagementInfo condition){
        return managementInfoDao.selectManagementRoleByCondition(condition);
    }

    @Override
    public ManagementDetails loadManagementByName(String name){
        ManagementInfo managementInfo = new ManagementInfo();
        managementInfo.setName(name);
        managementInfo = managementInfoDao.selectManagementRoleByCondition(managementInfo);
        if(managementInfo == null) {
            throw new UsernameNotFoundException("用户名或者密码错误");
        }
        if(managementInfo.getType() == null || managementInfo.getType() <= 20){
            throw new RuntimeException("该账号已被冻结,请联系管理员");
        }
        return new ManagementDetails(managementInfo, getAuthList(managementInfo.getRoleList()));
    }

    @Override
    public String login(String name, String password) {
        try{
            Date date = new Date();
            String token;
            ManagementDetails managementDetails = loadManagementByName(name);
            if(!passwordEncoder.matches(password, managementDetails.getPassword())){
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(managementDetails, null, managementDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = JwtTokenUtil.generateToken(managementDetails.getUsername(), date);
            ManagementInfo managementInfo = managementDetails.getManagementInfo();

            //将用户的基本信息和登录时间放入本地线程中
            CusThreadLocal.put(GlobalConstant.USER_TOKEN_DTO, new UserDto(managementInfo.getId(), managementInfo.getName(), managementInfo.getDesc(), managementInfo.getType(), new Date()));
            return token;
        }catch (AuthenticationException a){
            this.LOGGER.error("管理员登录异常:{}" , a.getMessage());
            throw new RuntimeException();
        }
    }

    public List<AuthInfo> getAuthList(List<RoleInfo> roleInfoList){
        ArrayList<AuthInfo> authList = new ArrayList<>();
        Iterator<RoleInfo> iterator = roleInfoList.iterator();
        while(iterator.hasNext()){
            List<AuthInfo> authInfoList = iterator.next().getAuthInfoList();
            for (AuthInfo authInfo : authInfoList) {
                authList.add(authInfo);
            }
        }
        return authList;
    }
}
package top.imuster.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.imuster.auth.utils.JwtTokenUtil;
import top.imuster.domain.base.BaseDao;
import top.imuster.service.base.BaseServiceImpl;
import top.imuster.user.bo.ManagementDetails;
import top.imuster.user.dao.ManagementInfoDao;
import top.imuster.user.pojo.AuthInfo;
import top.imuster.user.pojo.ManagementInfo;
import top.imuster.user.pojo.RoleInfo;
import top.imuster.user.service.ManagementInfoService;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

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
    public UserDetails loadManagementByName(String name){
        ManagementInfo managementInfo = new ManagementInfo();
        managementInfo.setName(name);
        managementInfo = managementInfoDao.selectManagementRoleByCondition(managementInfo);
        if(managementInfo == null) {
            throw new UsernameNotFoundException("用户名或者密码错误");
        }
        if(managementInfo.getType() <= 20){
            throw new RuntimeException("该账号已被冻结,请联系管理员");
        }
        return new ManagementDetails(managementInfo, getAuthList(managementInfo.getRoleList()));
    }

    @Override
    public String login(String name, String password) {
        String token = null;
        try{
            UserDetails userDetails = loadManagementByName(name);
            if(!passwordEncoder.matches(password, userDetails.getPassword())){
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        }catch (AuthenticationException a){
            this.LOGGER.error("管理员登录异常:{}" , a.getMessage());
            throw new RuntimeException();
        }
        return token;
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
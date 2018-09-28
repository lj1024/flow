package com.lwj.flow.shiro.realm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.lwj.easymybatis.common.Criteria;
import com.lwj.flow.login.entity.Resources;
import com.lwj.flow.login.entity.User;
import com.lwj.flow.login.mapper.ResourcesMapper;
import com.lwj.flow.login.mapper.UserMapper;

/**
 * 
 * @author luwja
 *
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserMapper userService;
    

    @Autowired
    private ResourcesMapper resourcesService;

    
    
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user= (User) SecurityUtils.getSubject().getPrincipal();//User{id=1, username='admin', password='3ef7164d1f6167cb9f2658c07d3c2f0a', enable=1}
        Criteria criteria = new Criteria();
        criteria.putWhere("id", user.getId());
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",user.getId());
        //根据用户id获取用户权限
        List<Resources> userResources =  resourcesService.queryResourcesByUser(criteria);
        // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for(Resources resources: userResources){
            info.addStringPermission(resources.getResurl());
        }
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户的输入的账号.
        String username = (String)token.getPrincipal();
        Criteria criteria = new Criteria();
        criteria.putWhere("username", username);
        User user = userService.read(criteria);
        if(user == null) throw new UnknownAccountException();
        if (0 == user.getStatus()) {
            throw new LockedAccountException(); // 帐号锁定
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user, //用户
                user.getPassword(), //密码
                ByteSource.Util.bytes(username),
                getName()  //realm name
        );
        return authenticationInfo;
    }

    /**
     * 指定principalCollection 清除
     */
    public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {

        super.clearCachedAuthorizationInfo(principalCollection);
    }
    
    
    
    

}

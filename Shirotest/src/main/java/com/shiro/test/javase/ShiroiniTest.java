package com.shiro.test.javase;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class ShiroiniTest {
    public static void main(String[] args) {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        /*至少一个匹配策略*/
        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        securityManager.setAuthenticator(authenticator);

        /*设置授权*/
        ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
        /*WildcardPermissionResolver-解析字符串*/
        authorizer.setPermissionResolver(new WildcardPermissionResolver());
        securityManager.setAuthorizer(authorizer);


        //dataSource=org.springframework.jdbc.datasource.DriverManagerDataSource
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        //dataSource.driverClassName=com.mysql.jdbc.Driver
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        //dataSource.url=jdbc:mysql://localhost:3306/shiro
        dataSource.setUrl("jdbc:mysql://localhost:3306/shiro");
        //dataSource.username=root
        dataSource.setUsername("root");
        //dataSource.password=99999999
        dataSource.setPassword("99999999");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        MyRealm2 myRealm2 = new MyRealm2();
        myRealm2.setJdbcTemplate(jdbcTemplate);

        /*设置数据源*/
        securityManager.setRealm(myRealm2);
        /*绑定上下文*/
        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("admin@shiro.com", "admin");
        try {
            subject.login(token);
            System.out.println(subject.hasRole("admin"));
            System.out.println("登陆成功");
        } catch (AuthenticationException e) {
            e.printStackTrace();
            System.out.println("用户名或密码错误，登陆失败");
        }
    }
}

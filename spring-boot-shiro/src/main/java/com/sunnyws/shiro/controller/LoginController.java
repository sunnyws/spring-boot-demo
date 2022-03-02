package com.sunnyws.shiro.controller;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.sunnyws.shiro.entity.SysUser;
import com.sunnyws.shiro.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * This class is for LoginController
 *
 * @author qinlang
 * @date 2021/12/27
 */
@Slf4j
@Controller
public class LoginController {

    @Value("${chinaictc.login_url}")
    private String login_url;

    @Value("${chinaictc.client_id}")
    private String client_id;

    @Value("${chinaictc.redirect_uri}")
    private String redirect_uri;

    @Value("${chinaictc.client_secret}")
    private String client_secret;

    @Value("${chinaictc.response_type}")
    private String response_type;

    @Value("${chinaictc.token_url}")
    private String token_url;

    @Value("${chinaictc.grant_type}")
    private String grant_type;


    @Value("${chinaictc.scope}")
    private String scope;


    @Resource
    private LoginService loginService;
    /**
     * POST登录
     */
    @PostMapping(value = "/login")
    @ResponseBody
    public String login(SysUser user) {
        if (StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword())) {
            return "请输入用户名和密码！";
        }
        //用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                user.getUserName(),
                user.getPassword()
        );
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);
//            subject.checkRole("admin");
//            subject.checkPermissions("queryUser", "addUser");
        } catch (UnknownAccountException e) {
            log.error("用户名不存在！", e);
            return "用户名不存在！";
        } catch (AuthenticationException e) {
            log.error("账号或密码错误！", e);
            return "账号或密码错误！";
        } catch (AuthorizationException e) {
            log.error("没有权限！", e);
            return "没有权限";
        }
        return "login success";
    }

    /**
    * This method is for  跳转到第三方登录地址
    **/
    @RequestMapping(value = "/toOauthLogin")
    public String otherLogin(){
        String builder = UrlBuilder.ofHttp(login_url, CharsetUtil.CHARSET_UTF_8)
                                    .addQuery("client_id",client_id)
                                    .addQuery("redirect_uri",redirect_uri)
                                    .addQuery("response_type",response_type)
                                    .build();
        return  "redirect:"+builder;
    }


    /**
     * This method is for  携带code 请求获取token  地址与redirect_uri相同 用于接收认证中心回调
     *
     **/
    /**
    * 返回参数  示例
    * {
     * 	"access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJsaWNlbnNlIjoibWFkZSBieSBjaGluYWljdGMiLCJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbIkFsbCJdLCJ4LXVzZXItaWQiOiJlOWNhMjNkNjhkODg0ZDRlYmIxOWQwNzg4OTcyN2RhZSIsImV4cCI6MTY0MDY4MzM1OSwieC11c2VyLW5hbWUiOiJhZG1pbiIsImp0aSI6IjhlMDYyNTYwLTA1NDAtNGY2ZS1iNzRjLWY4NDM0NGVhYTEzNyIsImNsaWVudF9pZCI6IjE0NzUzODU4NjEwNDcxNDAzNTQifQ.O8zN8XsEIruNVD6wjYsJkfN-HKpNUG3KdnqbqBnGo8Xokn7lQocPLEfTT9OrsOr7lATkSuBfdy9CFIJLXw61STvEdUoC9Iwd_eNn41FPf82iZW9D4dLfcTommHO1GpJjatB50Uc7DZ7tkGNUQ-mU7QOwOOaiEZEON6nX6qHpor3v9N5oDBagQz3XjrnkYG34KnwErq9Kc3JFDPMhpTY5L3oUUQ-uMT0pdUaPyUOpsJkdDqMSmDgcbCWgNnY_rS_f2WDNkzoA4FW9LALGzbDHxWWGbDGJZ664Gkbq2oVHFO4v1gCqo0vErAKwZbVIt_RkvwqGuAoEKtHxmE_cJktO1Q",
     * 	"token_type": "bearer",
     * 	"refresh_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJsaWNlbnNlIjoibWFkZSBieSBjaGluYWljdGMiLCJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbIkFsbCJdLCJhdGkiOiI4ZTA2MjU2MC0wNTQwLTRmNmUtYjc0Yy1mODQzNDRlYWExMzciLCJ4LXVzZXItaWQiOiJlOWNhMjNkNjhkODg0ZDRlYmIxOWQwNzg4OTcyN2RhZSIsImV4cCI6MTY0MDY4MzM1OSwieC11c2VyLW5hbWUiOiJhZG1pbiIsImp0aSI6IjczNzFiZTgyLWQwNDgtNDkzOC1iNTIyLWM1OTZiMjNjNDZmOSIsImNsaWVudF9pZCI6IjE0NzUzODU4NjEwNDcxNDAzNTQifQ.KJ-T4yD_Gn8Y6A0CWHiGko6i-L9VDLWYZIOuiZij8V0mnH2SWJ6Awzji5--jqM221NUNLb4XO9hDne_5Tf1J-z1Uoku1K_tQwOrU0GAWvmlXvyO5mMkmbGh-nbHVXLmXvGBbqD3AMdZ_NjU6z7mCFRhN05x9kbSUEPMeJPMWaYVjdDGtP3Q3IeNW7GtnIf58UiG6X_QO4a-Sow-nR8gsezdQIh8eMz2e5uDxWlkQCTjB0VlSQlSlUZEGEvmOK3tRxAz3HRPn_GW4kp7YzIzsvD4J2qNhVsCDZD2PPkL-ojVkuBxb7PIYbQDIpF9d6XIlOJ0FtjmTUgYfLOEOlEgwLA",
     * 	"expires_in": 86399,
     * 	"scope": "All",
     * 	"jti": "8e062560-0540-4f6e-b74c-f84344eaa137",
     * 	"license": "made by chinaictc",
     * 	"x-user-name": "admin",
     * 	"x-user-id": "e9ca23d68d884d4ebb19d07889727dae",   #用户id
     * 	"x-user-center-info": {
     * 		"multi_depart": 0,
     * 		"departs": [],
     * 		"token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NDA2ODMzNTksInVzZXJuYW1lIjoiYWRtaW4ifQ.jKFVzTGVt7Q7HzTvyr0HhvYa4DhTTM76jkNWnV4PlhQ",   #token
     * 		"userInfo": {
     * 			"id": "e9ca23d68d884d4ebb19d07889727dae",
     * 			"username": "admin",  #账号
     * 			"realname": "管理员",
     * 			"avatar": "http://198.168.31.221:8180/sysNews/20210918/202109181549182128.jpg",
     * 			"birthday": "2018-12-04",
     * 			"sex": 1,
     * 			"email": "jeecg@163.com",
     * 			"phone": "18611111111",
     * 			"orgCode": "产品部",
     * 			"status": 1,
     * 			"delFlag": 0,
     * 			"workNo": "00001",
     * 			"post": "总经理",
     * 			"telephone": "",
     * 			"createTime": "2019-06-21 17:54:10",
     * 			"updateBy": "admin",
     * 			"updateTime": "2021-10-14 09:32:40",
     * 			"activitiSync": 1,
     * 			"userIdentity": 2,
     * 			"departIds": "9367782625334e4c86400928f057d414,69e19762c6ff4a61998f3e70d9e94a49"
     *        }
     *     }
     * }
    **/
    @RequestMapping(value = "/getOauthToken")
    @ResponseBody
    public String  getCode(HttpServletRequest request){
        //根据认证中心回调 获取code
        String code = request.getParameter("code");
        log.info("获取到的code为：{}",code);
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("grant_type", grant_type);
        paramMap.put("scope", scope);
        paramMap.put("client_secret", client_secret);
        paramMap.put("code", code);
        paramMap.put("redirect_uri", redirect_uri);
        paramMap.put("client_id", client_id);
        String result= HttpRequest.post(token_url)
                .contentType("application/x-www-form-urlencoded")
                .form(paramMap)//表单内容
                .timeout(20000)//超时，毫秒
                .execute().body();
        log.info("返回结果为：{}",result);
        JSONObject json = JSONUtil.parseObj(result);
        //认证中心  用户账号
        String  account = json.getStr("x-user-name");
        log.info("用户账号{}",account);
        //认证中心  用户id
        String  userId = json.getStr("x-user-id");
        log.info("用户id{}",userId);
        //认证中心  用户其他信息
        JSONObject  userInfo = json.getJSONObject("x-user-center-info");
        log.info("用户信息{}",userInfo);
        //认证中心  token  访问数字底座接口 请求数据时时需要header中携带token （X-Access-Token：token）
        String  token = userInfo.getStr("token");
        log.info("token为{}",token);

        //TODO  根据认证中心返回的用户信息  查找用户  关联用户或者注册用户
        SysUser user = loginService.findByName(account);
        log.info("获取到的用户为:{}",user);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                user.getUserName(),
                user.getPassword()
        );
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);
        } catch (UnknownAccountException e) {
            log.error("用户名不存在！", e);
            return "用户名不存在！";
        } catch (AuthenticationException e) {
            log.error("账号或密码错误！", e);
            return "账号或密码错误！";
        } catch (AuthorizationException e) {
            log.error("没有权限！", e);
            return "没有权限";
        }


        return "login success";
    }

//
//    @PostMapping(value = "/otherLogin")
//    public String QQsuccess(HttpServletRequest request, HttpServletResponse response){
//        response.setContentType("text/html;charset=utf-8");
//        try {
//            AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);
//            String accessToken = null, openID = null;
//            long tokenExpireIn = 0L;
//            if (accessTokenObj.getAccessToken().equals("")) {
//                System.out.println("没有获取到响应参数");
//                try {
//                    response.sendRedirect("QQlogin");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }else{
//                accessToken = accessTokenObj.getAccessToken();
//                tokenExpireIn = accessTokenObj.getExpireIn();
//                // 利用获取到的accessToken 去获取当前用的openid -------- start
//                OpenID openIDObj =  new OpenID(accessToken);
//                openID = openIDObj.getUserOpenID();
//                List<UUser> users = userService.findByqqOpenId(openID);
//                String touser = users.get(0).getUsername();
//                String topwd = users.get(0).getPassword();
//                if (users.size()>0){
//                    System.out.println("直接登录"+users.get(0).getUsername()+topwd);
//                    UsernamePasswordLoginTypeToken token = new UsernamePasswordLoginTypeToken(touser,topwd,false,"1",topwd);
//                    HttpSession session = request.getSession();
//                    session.setAttribute("username",users.get(0).getUsername());
//                    Subject currentUser = SecurityUtils.getSubject();
//                    if(!currentUser.isAuthenticated()) {
//                        System.out.println("验证角色和权限");
//                        currentUser.login(token);//验证角色和权限
//                    }
//                    System.out.println("admin/index----->");
//                    return "redirect:/admin/index";
//                }
//                System.out.println("openid:" + openID);
//                request.getSession().setAttribute("qqOpenid", openID);
//                // 利用获取到的accessToken 去获取当前用户的openid --------- end
//                UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
//                UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
//                if (userInfoBean.getRet() == 0) {
//                    System.out.println(userInfoBean.getNickname());
//                    System.out.println("<image src=" + userInfoBean.getAvatar().getAvatarURL100() + "><br/>");
//                } else {
//                    System.out.println("很抱歉，没能正确获取到您的信息，原因是： " + userInfoBean.getMsg());
//                }
//            }
//        } catch (QQConnectException e) {
//            e.printStackTrace();
//        }
//        return "modules/qqLogin/index";
//    }
}

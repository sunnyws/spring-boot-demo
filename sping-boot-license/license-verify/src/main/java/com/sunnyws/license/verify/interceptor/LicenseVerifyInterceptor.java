package com.sunnyws.license.verify.interceptor;

import cn.hutool.json.JSONUtil;
import com.sunnyws.license.verify.annotion.VLicense;
import com.sunnyws.license.verify.param.Result;
import com.sunnyws.license.verify.config.GlobalState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * <p>License验证拦截器</p>
 *
 * @author appleyk
 * @version V.0.2.1
 * @blob https://blog.csdn.net/appleyk
 * @date created on 00:32 上午 2020/8/22
 */
@Slf4j
public class LicenseVerifyInterceptor implements HandlerInterceptor  {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            VLicense annotation = method.getAnnotation(VLicense.class);
            if (annotation != null) {
                if(GlobalState.globalState){
                    return true;
                }else{
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().write(JSONUtil.parseObj(Result.error("您的证书无效，请核查服务器是否取得授权或重新申请证书！")).toString());
                    return false;
                }
            }
        }
        return  true;
    }

}

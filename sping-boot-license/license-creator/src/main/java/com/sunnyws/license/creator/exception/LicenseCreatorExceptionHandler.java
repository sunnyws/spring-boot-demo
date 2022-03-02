package com.sunnyws.license.creator.exception;

import de.schlichtherle.license.LicenseContentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;


@RestControllerAdvice(annotations = RestController.class)
@Slf4j
public class LicenseCreatorExceptionHandler {

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(LicenseCreatorException.class)
	public Result handleRRException(LicenseCreatorException e){
		log.error(e.getMessage(), e);
		return Result.error(e.getMessage());
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public Result handlerNoFoundException(Exception e) {
		log.error(e.getMessage(), e);
		return Result.error("路径不存在，请检查路径是否正确");
	}

	@ResponseBody
	@ExceptionHandler(LicenseContentException.class)
	public Result handlerLicenseContentException(Exception e) {
		log.error(e.getMessage(), e);
		return Result.error(e.getMessage());
	}
	

	@ExceptionHandler(Exception.class)
	public Result handleException(Exception e){
		log.error(e.getMessage(), e);
		return Result.error("操作失败:"+e.getMessage());
	}

	/**
	 * @Author 政辉
	 * @param e
	 * @return
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Result HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
		StringBuffer sb = new StringBuffer();
		sb.append("不支持");
		sb.append(e.getMethod());
		sb.append("请求方法，");
		sb.append("支持以下");
		String [] methods = e.getSupportedMethods();
		if(methods!=null){
			for(String str:methods){
				sb.append(str);
				sb.append("、");
			}
		}
		log.error(sb.toString(), e);
		return Result.error(sb.toString());
	}



}

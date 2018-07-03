package cn.elmall.search.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class GlobalExceptionResolver implements HandlerExceptionResolver {

	/**
	 * 全局异常处理
	 * 
	 * 1.书写当前继承HandlerExceptionResoler的类 2.在springmvc.xml中配置一下：<bean class=
	 * "cn.elmall.search.exception.GlobalExceptionResolver"/>
	 * 3.在classpath添加log4j.properties的日志文件
	 * 解释：log4j中的日志级别：debugger->info->error依次变高（意思为：如果配置低的，高的也会输出，如果配置高的，低的就不会输出）
	 * 
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);
		logger.error("异常信息", ex);
		System.out.println(ex);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("error/exception");
		return modelAndView;
	}

}

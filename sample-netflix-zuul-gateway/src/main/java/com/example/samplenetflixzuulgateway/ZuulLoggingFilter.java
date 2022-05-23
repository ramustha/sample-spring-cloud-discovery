package com.example.samplenetflixzuulgateway;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class ZuulLoggingFilter extends ZuulFilter {
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  public String filterType() {
    return FilterConstants.PRE_TYPE; //intercept all the request before execution
  }

  @Override
  public int filterOrder() {
    return 0;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() throws ZuulException {
    RequestContext context = RequestContext.getCurrentContext();

    context.addZuulResponseHeader("X-sample", "1234");

    HttpServletRequest request = context.getRequest();
    logger.info("Authorization -> {}", request.getHeader("Authorization"));
    logger.info("request -> {} request uri-> {}", request, request.getRequestURI());
    return null;
  }
}

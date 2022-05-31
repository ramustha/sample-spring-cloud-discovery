package com.example.samplenetflixzuulgateway;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class ZuulPostFilter extends ZuulFilter {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  public String filterType() {
    return FilterConstants.POST_TYPE; //intercept all the request after execution
  }

  @Override
  public int filterOrder() {
    return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1; // run before PreDecoration
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Autowired
  private ProxyRequestHelper helper;

  @Override
  public Object run() throws ZuulException {
    RequestContext context = RequestContext.getCurrentContext();

    context.addZuulResponseHeader("X-post-response", "post-response");
    context.addZuulRequestHeader("X-post-request", "post-request");

    HttpServletRequest request = context.getRequest();
    logger.info("POST_FILTER -> {}", helper.buildZuulRequestURI(request));
    return null;
  }
}

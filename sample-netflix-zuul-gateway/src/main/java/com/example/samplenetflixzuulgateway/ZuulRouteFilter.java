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
public class ZuulRouteFilter extends ZuulFilter {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  public String filterType() {
    return FilterConstants.ROUTE_TYPE;
  }

  @Override
  public int filterOrder() {
    return FilterConstants.SIMPLE_HOST_ROUTING_FILTER_ORDER - 1;
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

    HttpServletRequest request = context.getRequest();
    logger.info("ROUTE_TYPE -> {}", helper.buildZuulRequestURI(request));

    context.setRouteHost(null); // prevent SimpleHostRoutingFilter from running
    return null;
  }
}

package com.example.samplenetflixzuulgateway;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

public class RibbonEurekaClientConfig {

  @Autowired
  DiscoveryClient discoveryClient;

  @Bean
  public IPing ribbonPing(IClientConfig config) {
    return new PingUrl();
  }

  @Bean
  public IRule ribbonRule(IClientConfig config) {
    return new AvailabilityFilteringRule();
  }

  @Bean
  public ServerList<Server> getServerList(IClientConfig config) {

    return new ServerList<>() {

      @Override
      public List<Server> getInitialListOfServers() {
        return new ArrayList<>();
      }

      @Override
      public List<Server> getUpdatedListOfServers() {
        List<Server> serverList = new ArrayList<>();

        List<ServiceInstance> list = discoveryClient.getInstances(config.getClientName());
        for (ServiceInstance instance : list) {
          serverList.add(new Server(instance.getHost(), instance.getPort()));
        }
        return serverList;
      }
    };
  }
}

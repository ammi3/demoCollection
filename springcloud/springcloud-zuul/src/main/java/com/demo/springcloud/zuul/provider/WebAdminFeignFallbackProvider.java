package com.demo.springcloud.zuul.provider;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.client.ClientHttpResponse;

public class WebAdminFeignFallbackProvider implements FallbackProvider {
    @Override
    public String getRoute() {
        return "springcloud-web-admin-feign";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return null;
    }
}

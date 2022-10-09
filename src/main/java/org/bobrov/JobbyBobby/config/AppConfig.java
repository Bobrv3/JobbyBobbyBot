package org.bobrov.JobbyBobby.config;

import org.bobrov.JobbyBobby.proxy.HHProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackageClasses = HHProxy.class)
public class AppConfig {

}

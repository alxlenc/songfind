package alx.music.songfind.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class ActuatorSecurityConfiguration {

  @Configuration
  @Order(2)
  @Profile("prod")
  public static class ActuatorProdConfigurationAdapter extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.requestMatchers()
          .antMatchers("/actuator/health/liveness",
              "/actuator/health/readiness",
              "/actuator/auditevents",
              "/actuator/mappings")
          .and()
          .authorizeRequests().anyRequest().permitAll();
    }
  }

}

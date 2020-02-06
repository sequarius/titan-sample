package com.sequarius.titan.sample.system.message;

import com.sequarius.titan.sample.common.message.YamlPropertySourceFactory;
import com.sequarius.titan.sample.common.util.Constant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * project titan-sample
 *
 * @author Sequarius *
 * @since 03/02/2020
 */
@Configuration
@PropertySource(value = Constant.MESSAGE_CONFIG_LOCATION, factory = YamlPropertySourceFactory.class)
@ConfigurationProperties("message.system")
@Data
@Slf4j
public class SystemMessage {
    private String loginPasswordError;
    private String loginAccountLock;
    private String loginFailed;
    private String loginSuccess;
}

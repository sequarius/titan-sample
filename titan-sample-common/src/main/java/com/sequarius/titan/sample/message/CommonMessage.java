package com.sequarius.titan.sample.message;

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
@PropertySource(value = "classpath:message.yaml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties("message.common")
@Data
@Slf4j
public class CommonMessage {
    private String entityNotFound;
    private String entityAddSuccess;
    private String entityUpdateSuccess;
    private String entityDeleteSuccess;
    private String entityAddFailed;
    private String entityUpdateFailed;
    private String entityDeleteFailed;

    public String getEntityUpdateSuccess(String entityName) {
        try {
            return String.format(entityUpdateSuccess, entityName);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return entityUpdateSuccess;
        }
    }

    public String getEntityNotFound(String entityName) {
        try {
            return String.format(entityNotFound, entityName);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return entityNotFound;
        }
    }


    public String getEntityAddSuccess(String entityName) {
        try {
            return String.format(entityAddSuccess, entityName);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return entityAddSuccess;
        }
    }

    public String getEntityDeleteSuccess(String entityName, Integer deleteCount) {
        try {
            return String.format(entityDeleteSuccess, deleteCount, entityName);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return entityDeleteSuccess;
        }
    }

    public String getEntityAddFailed(String entityName) {
        try {
            return String.format(entityAddFailed, entityName);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return entityAddFailed;
        }
    }

    public String getEntityUpdateFailed(String entityName) {
        try {
            return String.format(entityUpdateFailed, entityName);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return entityUpdateFailed;
        }
    }

    public String getEntityDeleteFailed(String entityName) {
        try {
            return String.format(entityDeleteFailed, entityName);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return entityDeleteFailed;
        }
    }
}

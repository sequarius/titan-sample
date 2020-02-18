package com.sequarius.titan.sample.common.message;

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
@ConfigurationProperties("message.common")
@Data
@Slf4j
public class CommonMessage {
    private String entityNotFound;
    private String entitySaveSuccess;
    private String entityUpdateSuccess;
    private String entityRemoveSuccess;
    private String entitySaveFailed;
    private String entityUpdateFailed;
    private String entityRemoveFailed;

    private String requireToLogin;
    private String serviceError;
    private String loginEmptyUsernameError;
    private String loginUserNotFound;

    private String emptyId;


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


    public String getEntitySaveSuccess(String entityName) {
        try {
            return String.format(entitySaveSuccess, entityName);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return entitySaveSuccess;
        }
    }

    public String getEntityRemoveSuccess(String entityName, Integer deleteCount) {
        try {
            return String.format(entityRemoveSuccess, deleteCount, entityName);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return entityRemoveSuccess;
        }
    }

    public String getEntitySaveFailed(String entityName) {
        try {
            return String.format(entitySaveFailed, entityName);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return entitySaveFailed;
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

    public String getEntityRemoveFailed(String entityName) {
        try {
            return String.format(entityRemoveFailed, entityName);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return entityRemoveFailed;
        }
    }
}

package com.sequarius.titan.sample.system.support;


import com.sequarius.titan.sample.domain.SysPermissionDO;
import com.sequarius.titan.sample.system.service.SysPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * project erp-bundle
 *
 * @author Sequarius *
 * @since 02/03/2020
 */
@Configuration
@Slf4j
public class PermissionScannerConfig implements ApplicationContextAware {

    @Resource
    private SysPermissionService sysPermissionService;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Reflections reflections = new Reflections("com.sequarius.titan.sample",new MethodAnnotationsScanner());
        Set<Method> methods = reflections.getMethodsAnnotatedWith(RequiresPermissions.class);
        for (Method method : methods) {
            RequiresPermissions permissions = method.getAnnotation(RequiresPermissions.class);
            if (permissions == null) {
                continue;
            }
            String group = getGroupName(method.getDeclaringClass());
            ApiOperation operation = method.getAnnotation(ApiOperation.class);
            String describe = operation != null?operation.value().replaceAll("列表",""):null;

            for (String permission : permissions.value()) {
                updatePermission(group, describe, permission);
            }
        }
    }

    private void updatePermission(String group, String describe, String permission) {
        SysPermissionDO permissionDO = sysPermissionService.findSysPermission(permission.trim());
        boolean shouldBeSave =false;
        if(permissionDO==null){
            permissionDO = new SysPermissionDO();
            permissionDO.setPermission(permission);
            shouldBeSave = true;
        }
        if(StringUtils.isEmpty(permissionDO.getGroup())){
            permissionDO.setGroup(group);
            shouldBeSave=true;
        }
        if (StringUtils.isEmpty(permissionDO.getDescription())){
            permissionDO.setDescription(describe);
            shouldBeSave = true;
        }
        if (shouldBeSave) {
            sysPermissionService.saveSysPermissionDO(permissionDO);
            log.info("save permission == {}",permissionDO);
        }
    }

    private String getGroupName(Class<?> clazz) {
        Api annotation = clazz.getAnnotation(Api.class);
        if (annotation != null && !StringUtils.isEmpty(annotation.tags())) {
            return String.join(",", annotation.tags()).replaceAll("API", "").trim();
        }

        try {
            Field nameFiled = clazz.getField("ENTITY_NAME");
            nameFiled.setAccessible(true);
            String group = (String) nameFiled.get(clazz);
            return group.trim();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.warn(e.getMessage(), e);
        }

        return null;
    }


}

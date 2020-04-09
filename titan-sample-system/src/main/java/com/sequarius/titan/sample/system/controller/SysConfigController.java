package com.sequarius.titan.sample.system.controller;

import com.sequarius.titan.sample.common.domain.Page;
import com.sequarius.titan.sample.common.domain.PageData;
import com.sequarius.titan.sample.common.domain.Response;
import com.sequarius.titan.sample.common.message.CommonMessage;
import com.sequarius.titan.sample.system.domain.SysConfigRequestDTO;
import com.sequarius.titan.sample.system.domain.SysConfigResponseDTO;
import com.sequarius.titan.sample.system.service.SysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 系统配置Controller
 *
 * @author titan-generator
 * @since 2020-03-19
 */
@RestController
@Api(tags = "系统配置API", description = "系统配置相关操作")
@RequestMapping("/system")
@Slf4j
public class SysConfigController {

    public static final String ENTITY_NAME = "系统配置";

    @Resource
    private CommonMessage commonMessage;

    @Resource
    private SysConfigService sysConfigService;

    @GetMapping("/sysConfigs")
    @ApiOperation("查看系统配置列表")
    @RequiresPermissions("system:sysConfig:view")
    public Response<PageData<SysConfigResponseDTO>> list(@Valid Page page, String keyword) {
        return Response.success(sysConfigService.listSysConfigs(page, keyword));
    }

    @GetMapping("/sysConfig")
    @ApiOperation("通过key查找对应的公开配置")
    public Response<PageData<String>> getSysConfig(String key) {
        String sysConfig = sysConfigService.getSysConfig(key);
        if(sysConfig == null){
            return Response.fail(commonMessage.getEntityNotFound(key));
        }
        return Response.success(sysConfig);
    }

    @GetMapping("/sysConfig/{id}")
    @ApiOperation("查看系统配置")
    @RequiresPermissions("system:sysConfig:view")
    public Response<SysConfigResponseDTO> findSysConfig(@PathVariable Long id) {
        SysConfigResponseDTO sysConfig = sysConfigService.findSysConfig(id);
        if (sysConfig == null) {
            return Response.fail(commonMessage.getEntityNotFound(ENTITY_NAME));
        }
        return Response.success(sysConfig);
    }

    @DeleteMapping("/sysConfig")
    @ApiOperation("删除系统配置")
    @RequiresPermissions("system:sysConfig:remove")
    public Response<SysConfigResponseDTO> removeSysConfig(RequestEntity<List<Long>> ids) {
        if (ids.getBody() == null || ids.getBody().isEmpty()) {
            return Response.fail(commonMessage.getEmptyId());
        }
        Integer result = sysConfigService.removeSysConfig(ids.getBody());
        if (result < 1) {
            return Response.fail(commonMessage.getEntityRemoveFailed(ENTITY_NAME));
        }
        return Response.success(commonMessage.getEntityRemoveSuccess(ENTITY_NAME, result));
    }

    @PostMapping("/sysConfig")
    @ApiOperation("新增系统配置")
    @RequiresPermissions("system:sysConfig:save")
    public Response<String> addSysConfig(@Valid @RequestBody SysConfigRequestDTO requestDTO) {
        if (sysConfigService.saveSysConfig(requestDTO) > 0) {
            return Response.success(commonMessage.getEntitySaveSuccess(ENTITY_NAME));
        }
        return Response.fail(commonMessage.getEntitySaveFailed(ENTITY_NAME));
    }

    @PutMapping("/sysConfig")
    @ApiOperation("更新系统配置")
    @RequiresPermissions("system:sysConfig:update")
    public Response<String> updateSysConfig(@Valid @RequestBody SysConfigRequestDTO requestDTO) {
        if (requestDTO.getId() == null) {
            return Response.fail(commonMessage.getEmptyId());
        }
        Integer result = sysConfigService.updateSysConfig(requestDTO);
        if (result > 0) {
            return Response.success(commonMessage.getEntityUpdateSuccess(ENTITY_NAME));
        }

        if (result == -1) {
            return Response.fail(commonMessage.getEntityNotFound(ENTITY_NAME));
        }
        return Response.fail(commonMessage.getEntityUpdateFailed(ENTITY_NAME));
    }
}

package com.sequarius.titan.sample.system.controller;

import com.sequarius.titan.sample.common.domain.Page;
import com.sequarius.titan.sample.common.domain.PageData;
import com.sequarius.titan.sample.common.domain.Response;
import com.sequarius.titan.sample.common.domain.TreeNodeDTO;
import com.sequarius.titan.sample.common.message.CommonMessage;
import com.sequarius.titan.sample.system.domain.SysPermissionRequestDTO;
import com.sequarius.titan.sample.system.domain.SysPermissionResponseDTO;
import com.sequarius.titan.sample.system.service.SysPermissionService;
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
 * 权限Controller
 *
 * @author titan-generator
 * @since 2020-03-02
 */
@RestController
@Api(tags = "权限API", description = "权限相关操作")
@RequestMapping("/system")
@Slf4j
public class SysPermissionController {

    public static final String ENTITY_NAME = "权限";

    @Resource
    private CommonMessage commonMessage;

    @Resource
    private SysPermissionService sysPermissionService;

    @GetMapping("/sysPermissions")
    @ApiOperation("查看权限列表")
    @RequiresPermissions("system:sysPermission:view")
    public Response<PageData<SysPermissionResponseDTO>> list(@Valid Page page, String keyword) {
        return Response.success(sysPermissionService.listSysPermissions(page, keyword));
    }

    @GetMapping("permissionTree")
    @ApiOperation("查看权限树")
    @RequiresPermissions("system:sysPermission:view")
    public Response<List<TreeNodeDTO>> getPermissionTree(String keyword){
        List<TreeNodeDTO> treeNodeDTOS = sysPermissionService.getPermissionTree(keyword);
        return Response.success(treeNodeDTOS);
    }

    @GetMapping("/sysPermission/{id}")
    @ApiOperation("查看权限")
    @RequiresPermissions("system:sysPermission:view")
    public Response<SysPermissionResponseDTO> findSysPermission(@PathVariable Long id) {
        SysPermissionResponseDTO sysPermission = sysPermissionService.findSysPermission(id);
        if (sysPermission == null) {
            return Response.fail(commonMessage.getEntityNotFound(ENTITY_NAME));
        }
        return Response.success(sysPermission);
    }

    @DeleteMapping("/sysPermission")
    @ApiOperation("删除权限")
    @RequiresPermissions("system:sysPermission:remove")
    public Response<SysPermissionResponseDTO> removeSysPermission(RequestEntity<List<Long>> ids) {
        if (ids.getBody() == null || ids.getBody().isEmpty()) {
            return Response.fail(commonMessage.getEmptyId());
        }
        Integer result = sysPermissionService.removeSysPermission(ids.getBody());
        if (result < 1) {
            return Response.fail(commonMessage.getEntityRemoveFailed(ENTITY_NAME));
        }
        return Response.success(commonMessage.getEntityRemoveSuccess(ENTITY_NAME, result));
    }

    @PostMapping("/sysPermission")
    @ApiOperation("新增权限")
    @RequiresPermissions("system:sysPermission:save")
    public Response<String> addSysPermission(@Valid @RequestBody SysPermissionRequestDTO requestDTO) {
        if (sysPermissionService.saveSysPermission(requestDTO) > 0) {
            return Response.success(commonMessage.getEntitySaveSuccess(ENTITY_NAME));
        }
        return Response.fail(commonMessage.getEntitySaveFailed(ENTITY_NAME));
    }

    @PutMapping("/sysPermission")
    @ApiOperation("更新权限")
    @RequiresPermissions("system:sysPermission:update")
    public Response<String> updateSysPermission(@Valid @RequestBody SysPermissionRequestDTO requestDTO) {
        if (requestDTO.getId() == null) {
            return Response.fail(commonMessage.getEmptyId());
        }
        Integer result = sysPermissionService.updateSysPermission(requestDTO);
        if (result > 0) {
            return Response.success(commonMessage.getEntityUpdateSuccess(ENTITY_NAME));
        }

        if (result == -1) {
            return Response.fail(commonMessage.getEntityNotFound(ENTITY_NAME));
        }
        return Response.fail(commonMessage.getEntityUpdateFailed(ENTITY_NAME));
    }
}

package com.sequarius.titan.sample.system.controller;

import com.sequarius.titan.sample.common.domain.Page;
import com.sequarius.titan.sample.common.domain.PageData;
import com.sequarius.titan.sample.common.domain.Response;
import com.sequarius.titan.sample.common.domain.TreeNodeDTO;
import com.sequarius.titan.sample.common.message.CommonMessage;
import com.sequarius.titan.sample.domain.SysRoleDO;
import com.sequarius.titan.sample.system.domain.SysRoleRequestDTO;
import com.sequarius.titan.sample.system.domain.SysRoleResponseDTO;
import com.sequarius.titan.sample.system.message.SystemMessage;
import com.sequarius.titan.sample.system.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.RequestEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 角色Controller
 *
 * @author titan-generator
 * @since 2020-03-02
 */
@RestController
@Api(tags = "角色API", description = "角色相关操作")
@RequestMapping("/system")
@Slf4j
public class SysRoleController {

    public static final String ENTITY_NAME = "角色";

    @Resource
    private CommonMessage commonMessage;

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SystemMessage systemMessage;

    @GetMapping("/sysRoles")
    @ApiOperation("查看角色列表")
    @RequiresPermissions("system:sysRole:view")
    public Response<PageData<SysRoleResponseDTO>> list(@Valid Page page, String keyword) {
        return Response.success(sysRoleService.listSysRoles(page, keyword));
    }


    @GetMapping("/sysRoleTree")
    @ApiOperation("查看角色列表树")
    @RequiresPermissions("system:sysRole:view")
    public Response<List<TreeNodeDTO>> list(String keyword) {
        return Response.success(sysRoleService.roleTree(keyword));
    }

    @GetMapping("/sysRole/{id}")
    @ApiOperation("查看角色")
    @RequiresPermissions("system:sysRole:view")
    public Response<SysRoleResponseDTO> findSysRole(@PathVariable Long id) {
        SysRoleResponseDTO sysRole = sysRoleService.findSysRole(id);
        if (sysRole == null) {
            return Response.fail(commonMessage.getEntityNotFound(ENTITY_NAME));
        }
        return Response.success(sysRole);
    }

    @DeleteMapping("/sysRole")
    @ApiOperation("删除角色")
    @RequiresPermissions("system:sysRole:remove")
    public Response<SysRoleResponseDTO> removeSysRole(RequestEntity<List<Long>> ids) {
        if (ids.getBody() == null || ids.getBody().isEmpty()) {
            return Response.fail(commonMessage.getEmptyId());
        }
        Integer result = sysRoleService.removeSysRole(ids.getBody());
        if (result < 1) {
            return Response.fail(commonMessage.getEntityRemoveFailed(ENTITY_NAME));
        }
        return Response.success(commonMessage.getEntityRemoveSuccess(ENTITY_NAME, result));
    }

    @PostMapping("/sysRole")
    @ApiOperation("新增角色")
    @RequiresPermissions("system:sysRole:save")
    public Response<String> addSysRole(@Valid @RequestBody SysRoleRequestDTO requestDTO) {
        if (sysRoleService.findSysRoleDo(requestDTO.getRole()) != null) {
            return Response.fail(commonMessage.getEntityExisted("角色名称", requestDTO.getRole()));
        }

        if (sysRoleService.saveSysRole(requestDTO) > 0) {
            return Response.success(commonMessage.getEntitySaveSuccess(ENTITY_NAME));
        }
        return Response.fail(commonMessage.getEntitySaveFailed(ENTITY_NAME));
    }

    @PutMapping("/sysRole")
    @ApiOperation("更新角色")
    @RequiresPermissions("system:sysRole:update")
    public Response<String> updateSysRole(@Valid @RequestBody SysRoleRequestDTO requestDTO) {
        if (requestDTO.getId() == null) {
            return Response.fail(commonMessage.getEmptyId());
        }

        String circleMessage = sysRoleService.findCircleDependency(requestDTO);

        if (!StringUtils.isEmpty(circleMessage)) {
            return Response.fail(String.format(systemMessage.getCircleRoleDependency(), circleMessage));
        }

        SysRoleDO sysRoleDo = sysRoleService.findSysRoleDo(requestDTO.getRole());
        if (sysRoleDo != null && !sysRoleDo.getId().equals(requestDTO.getId())) {
            return Response.fail(commonMessage.getEntityExisted("角色名称", requestDTO.getRole()));
        }

        Integer result = sysRoleService.updateSysRole(requestDTO);
        if (result > 0) {
            return Response.success(commonMessage.getEntityUpdateSuccess(ENTITY_NAME));
        }

        if (result == -1) {
            return Response.fail(commonMessage.getEntityNotFound(ENTITY_NAME));
        }
        return Response.fail(commonMessage.getEntityUpdateFailed(ENTITY_NAME));
    }
}

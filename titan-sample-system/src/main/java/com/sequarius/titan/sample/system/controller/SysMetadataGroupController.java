package com.sequarius.titan.sample.system.controller;

import com.sequarius.titan.sample.common.domain.Page;
import com.sequarius.titan.sample.common.domain.PageData;
import com.sequarius.titan.sample.common.domain.Response;
import com.sequarius.titan.sample.common.message.CommonMessage;
import com.sequarius.titan.sample.domain.SysMetadataGroupDO;
import com.sequarius.titan.sample.system.domain.SysMetadataGroupRequestDTO;
import com.sequarius.titan.sample.system.domain.SysMetadataGroupResponseDTO;
import com.sequarius.titan.sample.system.service.SysMetadataGroupService;
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
 * 元数据集Controller
 *
 * @author titan-generator
 * @since 2020-03-20
 */
@RestController
@Api(tags = "元数据集API", description = "元数据集相关操作")
@RequestMapping("/system")
@Slf4j
public class SysMetadataGroupController {

    public static final String ENTITY_NAME = "元数据集";

    @Resource
    private CommonMessage commonMessage;

    @Resource
    private SysMetadataGroupService sysMetadataGroupService;

    @GetMapping("/sysMetadataGroups")
    @ApiOperation("查看元数据集列表")
    @RequiresPermissions("system:sysMetadataGroup:view")
    public Response<PageData<SysMetadataGroupResponseDTO>> list(@Valid Page page, String keyword) {
        return Response.success(sysMetadataGroupService.listSysMetadataGroups(page, keyword));
    }

    @GetMapping("/sysMetadataGroup/{id}")
    @ApiOperation("查看元数据集")
    @RequiresPermissions("system:sysMetadataGroup:view")
    public Response<SysMetadataGroupResponseDTO> findSysMetadataGroup(@PathVariable Long id) {
        SysMetadataGroupResponseDTO sysMetadataGroup = sysMetadataGroupService.findSysMetadataGroup(id);
        if (sysMetadataGroup == null) {
            return Response.fail(commonMessage.getEntityNotFound(ENTITY_NAME));
        }
        return Response.success(sysMetadataGroup);
    }

    @DeleteMapping("/sysMetadataGroup")
    @ApiOperation("删除元数据集")
    @RequiresPermissions("system:sysMetadataGroup:remove")
    public Response<SysMetadataGroupResponseDTO> removeSysMetadataGroup(RequestEntity<List<Long>> ids) {
        if (ids.getBody() == null || ids.getBody().isEmpty()) {
            return Response.fail(commonMessage.getEmptyId());
        }
        Integer result = sysMetadataGroupService.removeSysMetadataGroup(ids.getBody());
        if (result < 1) {
            return Response.fail(commonMessage.getEntityRemoveFailed(ENTITY_NAME));
        }
        return Response.success(commonMessage.getEntityRemoveSuccess(ENTITY_NAME, result));
    }

    @PostMapping("/sysMetadataGroup")
    @ApiOperation("新增元数据集")
    @RequiresPermissions("system:sysMetadataGroup:save")
    public Response<String> addSysMetadataGroup(@Valid @RequestBody SysMetadataGroupRequestDTO requestDTO) {
        SysMetadataGroupDO sysMetadataGroup = sysMetadataGroupService.findSysMetadataGroup(requestDTO.getKey());
        if(sysMetadataGroup!=null){
            return Response.fail(commonMessage.getEntityExisted(ENTITY_NAME,requestDTO.getKey()));
        }
        if (sysMetadataGroupService.saveSysMetadataGroup(requestDTO) > 0) {
            return Response.success(commonMessage.getEntitySaveSuccess(ENTITY_NAME));
        }
        return Response.fail(commonMessage.getEntitySaveFailed(ENTITY_NAME));
    }

    @PutMapping("/sysMetadataGroup")
    @ApiOperation("更新元数据集")
    @RequiresPermissions("system:sysMetadataGroup:update")
    public Response<String> updateSysMetadataGroup(@Valid @RequestBody SysMetadataGroupRequestDTO requestDTO) {
        if (requestDTO.getId() == null) {
            return Response.fail(commonMessage.getEmptyId());
        }
        SysMetadataGroupDO sysMetadataGroup = sysMetadataGroupService.findSysMetadataGroup(requestDTO.getKey());
        Integer result = sysMetadataGroupService.updateSysMetadataGroup(requestDTO);
        if (result > 0) {
            return Response.success(commonMessage.getEntityUpdateSuccess(ENTITY_NAME));
        }

        if (result == -1) {
            return Response.fail(commonMessage.getEntityNotFound(ENTITY_NAME));
        }
        return Response.fail(commonMessage.getEntityUpdateFailed(ENTITY_NAME));
    }
}

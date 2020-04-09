package com.sequarius.titan.sample.system.controller;

import com.sequarius.titan.sample.common.domain.Page;
import com.sequarius.titan.sample.common.domain.PageData;
import com.sequarius.titan.sample.common.domain.Response;
import com.sequarius.titan.sample.common.message.CommonMessage;
import com.sequarius.titan.sample.domain.SysMetadataDO;
import com.sequarius.titan.sample.system.domain.MetaDataSelectResponseDTO;
import com.sequarius.titan.sample.system.domain.SysMetadataRequestDTO;
import com.sequarius.titan.sample.system.domain.SysMetadataResponseDTO;
import com.sequarius.titan.sample.system.service.SysMetadataService;
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
 * 元数据Controller
 *
 * @author titan-generator
 * @since 2020-03-20
 */
@RestController
@Api(tags = "元数据API", description = "元数据相关操作")
@RequestMapping("/system")
@Slf4j
public class SysMetadataController {

    public static final String ENTITY_NAME = "元数据";

    @Resource
    private CommonMessage commonMessage;

    @Resource
    private SysMetadataService sysMetadataService;

    @GetMapping("/sysMetadatas")
    @ApiOperation("查看元数据列表")
    @RequiresPermissions("system:sysMetadata:view")
    public Response<PageData<SysMetadataResponseDTO>> list(@Valid Page page, String keyword, Long groupId) {
        return Response.success(sysMetadataService.listSysMetadatas(page, keyword, groupId));
    }

    @GetMapping("/sysMetadataSource")
    @ApiOperation("根据GroupKey查看元数据列表")
    public Response<MetaDataSelectResponseDTO> list(String keyword, String groupKey) {
        MetaDataSelectResponseDTO responseDTO = sysMetadataService.listSysMetadatas(keyword, groupKey);
        return Response.success(responseDTO);
    }

    @GetMapping("/sysMetadata/{id}")
    @ApiOperation("查看元数据")
    @RequiresPermissions("system:sysMetadata:view")
    public Response<SysMetadataResponseDTO> findSysMetadata(@PathVariable Long id) {
        SysMetadataResponseDTO sysMetadata = sysMetadataService.findSysMetadata(id);
        if (sysMetadata == null) {
            return Response.fail(commonMessage.getEntityNotFound(ENTITY_NAME));
        }
        return Response.success(sysMetadata);
    }

    @DeleteMapping("/sysMetadata")
    @ApiOperation("删除元数据")
    @RequiresPermissions("system:sysMetadata:remove")
    public Response<SysMetadataResponseDTO> removeSysMetadata(RequestEntity<List<Long>> ids) {
        if (ids.getBody() == null || ids.getBody().isEmpty()) {
            return Response.fail(commonMessage.getEmptyId());
        }
        Integer result = sysMetadataService.removeSysMetadata(ids.getBody());
        if (result < 1) {
            return Response.fail(commonMessage.getEntityRemoveFailed(ENTITY_NAME));
        }
        return Response.success(commonMessage.getEntityRemoveSuccess(ENTITY_NAME, result));
    }

    @PostMapping("/sysMetadata")
    @ApiOperation("新增元数据")
    @RequiresPermissions("system:sysMetadata:save")
    public Response<String> addSysMetadata(@Valid @RequestBody SysMetadataRequestDTO requestDTO) {
        if(!StringUtils.isEmpty(requestDTO.getGroupNo())&&sysMetadataService.findMetadata(requestDTO.getGroupId(),
                requestDTO.getGroupNo())!=null){
            return Response.fail(commonMessage.getEntityExisted(ENTITY_NAME,requestDTO.getGroupNo()));
        }
        if (sysMetadataService.saveSysMetadata(requestDTO) > 0) {
            return Response.success(commonMessage.getEntitySaveSuccess(ENTITY_NAME));
        }
        return Response.fail(commonMessage.getEntitySaveFailed(ENTITY_NAME));
    }

    @PutMapping("/sysMetadata")
    @ApiOperation("更新元数据")
    @RequiresPermissions("system:sysMetadata:update")
    public Response<String> updateSysMetadata(@Valid @RequestBody SysMetadataRequestDTO requestDTO) {
        if (requestDTO.getId() == null) {
            return Response.fail(commonMessage.getEmptyId());
        }
        SysMetadataDO metadata = sysMetadataService.findMetadata(requestDTO.getGroupId(),
                requestDTO.getGroupNo());
        if(metadata !=null && !metadata.getId().equals(requestDTO.getId())){
            return Response.fail(commonMessage.getEntityExisted(ENTITY_NAME,requestDTO.getGroupNo()));
        }
        Integer result = sysMetadataService.updateSysMetadata(requestDTO);
        if (result > 0) {
            return Response.success(commonMessage.getEntityUpdateSuccess(ENTITY_NAME));
        }

        if (result == -1) {
            return Response.fail(commonMessage.getEntityNotFound(ENTITY_NAME));
        }
        return Response.fail(commonMessage.getEntityUpdateFailed(ENTITY_NAME));
    }
}

package com.sequarius.titan.sample.system.controller;

import com.sequarius.titan.sample.common.domain.Page;
import com.sequarius.titan.sample.common.domain.PageData;
import com.sequarius.titan.sample.common.domain.Response;
import com.sequarius.titan.sample.common.message.CommonMessage;
import com.sequarius.titan.sample.system.domain.SysUserRequestDTO;
import com.sequarius.titan.sample.system.domain.SysUserResponseDTO;
import com.sequarius.titan.sample.system.message.SystemMessage;
import com.sequarius.titan.sample.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.RequestEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户Controller
 *
 * @author titan-generator
 * @since 2020-03-02
 */
@RestController
@Api(tags = "用户API", description = "用户相关操作")
@RequestMapping("/system")
@Slf4j
public class SysUserController {

    public static final String ENTITY_NAME = "用户";

    @Resource
    private CommonMessage commonMessage;

    @Resource
    private SystemMessage systemMessage;

    @Resource
    private SysUserService sysUserService;

    @GetMapping("/sysUsers")
    @ApiOperation("查看用户列表")
    @RequiresPermissions("system:sysUser:view")
    public Response<PageData<SysUserResponseDTO>> list(@Valid Page page, String keyword,
                                                       @RequestParam(required = false,name = "ids") String idQuery) {
        Set<Long> ids;
        if(StringUtils.isEmpty(idQuery)){
            ids = new HashSet<>();
        }else{
            ids = Arrays.stream(idQuery.split(",")).map(Long::valueOf).collect(Collectors.toSet());
        }

        return Response.success(sysUserService.listSysUsers(page, keyword,ids));
    }

    @GetMapping("/sysUser/{id}")
    @ApiOperation("查看用户")
    @RequiresPermissions("system:sysUser:view")
    public Response<SysUserResponseDTO> findSysUser(@PathVariable Long id) {
        SysUserResponseDTO sysUser = sysUserService.findSysUser(id);
        if (sysUser == null) {
            return Response.fail(commonMessage.getEntityNotFound(ENTITY_NAME));
        }
        return Response.success(sysUser);
    }

    @DeleteMapping("/sysUser")
    @ApiOperation("删除用户")
    @RequiresPermissions("system:sysUser:remove")
    public Response<SysUserResponseDTO> removeSysUser(RequestEntity<List<Long>> ids) {
        if (ids.getBody() == null || ids.getBody().isEmpty()) {
            return Response.fail(commonMessage.getEmptyId());
        }
        Integer result = sysUserService.removeSysUser(ids.getBody());
        if (result == -1){
            return Response.fail(systemMessage.getHasEmployeeBind());
        }
        if (result < 1) {
            return Response.fail(commonMessage.getEntityRemoveFailed(ENTITY_NAME));
        }
        return Response.success(commonMessage.getEntityRemoveSuccess(ENTITY_NAME, result));
    }

    @PostMapping("/sysUser")
    @ApiOperation("新增用户")
    @RequiresPermissions("system:sysUser:save")
    public Response<String> addSysUser(@Valid @RequestBody SysUserRequestDTO requestDTO) {
        if (StringUtils.isEmpty(requestDTO.getPassword())) {
            return Response.fail(systemMessage.getPasswordRequired());
        }
        SysUserResponseDTO sysUser = sysUserService.findSysUser(requestDTO.getUsername());
        if (sysUser != null) {
            return Response.fail(String.format(commonMessage.getEntityExisted( "用户名" ,requestDTO.getUsername())));
        }
        if (sysUserService.saveSysUser(requestDTO) > 0) {
            return Response.success(commonMessage.getEntitySaveSuccess(ENTITY_NAME));
        }
        return Response.fail(commonMessage.getEntitySaveFailed(ENTITY_NAME));
    }

    @PutMapping("/sysUser")
    @ApiOperation("更新用户")
    @RequiresPermissions("system:sysUser:update")
    public Response<String> updateSysUser(@Valid @RequestBody SysUserRequestDTO requestDTO) {
        if (requestDTO.getId() == null) {
            return Response.fail(commonMessage.getEmptyId());
        }
        Integer result = sysUserService.updateSysUser(requestDTO);
        if (result > 0) {
            return Response.success(commonMessage.getEntityUpdateSuccess(ENTITY_NAME));
        }

        if (result == -1) {
            return Response.fail(commonMessage.getEntityNotFound(ENTITY_NAME));
        }
        return Response.fail(commonMessage.getEntityUpdateFailed(ENTITY_NAME));
    }
}

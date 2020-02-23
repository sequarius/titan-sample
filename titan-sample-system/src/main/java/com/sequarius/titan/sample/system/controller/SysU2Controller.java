package com.sequarius.titan.sample.system.controller;

import com.sequarius.titan.sample.common.Page;
import com.sequarius.titan.sample.common.PageData;
import com.sequarius.titan.sample.common.Response;
import com.sequarius.titan.sample.common.message.CommonMessage;
import com.sequarius.titan.sample.system.domain.SysU2RequestDTO;
import com.sequarius.titan.sample.system.domain.SysU2ResponseDTO;
import com.sequarius.titan.sample.system.service.SysU2Service;
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
 * 用户22Controller
 *
 * @author titan-generator
 * @since 2020-02-23
 */
@RestController
@Api(tags = "用户22API", description = "用户22相关操作")
@RequestMapping("/system")
@Slf4j
public class SysU2Controller {

    public static final String ENTITY_NAME = "用户22";

    @Resource
    private CommonMessage commonMessage;

    @Resource
    private SysU2Service sysU2Service;

    @GetMapping("/sysU2s")
    @ApiOperation("查看用户22列表")
    @RequiresPermissions("system:sysU2:view")
    public Response<PageData<SysU2ResponseDTO>> list(@Valid Page page, String keyword) {
        return Response.success(sysU2Service.listSysU2s(page, keyword));
    }

    @GetMapping("/sysU2/{id}")
    @ApiOperation("查看用户22")
    @RequiresPermissions("system:sysU2:view")
    public Response<SysU2ResponseDTO> findSysU2(@PathVariable Long id) {
        SysU2ResponseDTO sysU2 = sysU2Service.findSysU2(id);
        if (sysU2 == null) {
            return Response.fail(commonMessage.getEntityNotFound(ENTITY_NAME));
        }
        return Response.success(sysU2);
    }

    @PostMapping("/sysU2")
    @ApiOperation("新增用户22")
    @RequiresPermissions("system:sysU2:save")
    public Response<String> addSysU2(@Valid @RequestBody SysU2RequestDTO requestDTO) {
        if (sysU2Service.saveSysU2(requestDTO) > 0) {
            return Response.success(commonMessage.getEntitySaveSuccess(ENTITY_NAME));
        }
        return Response.fail(commonMessage.getEntitySaveFailed(ENTITY_NAME));
    }
}

package com.sequarius.titan.sample.system.controller;

import com.sequarius.titan.sample.common.Page;
import com.sequarius.titan.sample.common.Response;
import com.sequarius.titan.sample.common.message.CommonMessage;
import com.sequarius.titan.sample.system.domain.UserRequestDTO;
import com.sequarius.titan.sample.system.domain.UserResponseDTO;
import com.sequarius.titan.sample.system.service.UserService;
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
 * project titan-sample
 *
 * @author Sequarius *
 * @since 30/01/2020
 */
@RestController
@Api(tags = "用户API", description = "用户相关操作")
@RequestMapping("/system")
@Slf4j
public class UserController {

    public static final String ENTITY_NAME = "用户";

    @Resource
    private CommonMessage commonMessage;

    @Resource
    private UserService userService;

    @GetMapping("/users")
    @ApiOperation("查看用户列表")
    @RequiresPermissions("system:user:view")
    public Response<List<UserResponseDTO>> list(@Valid Page page,String keyword) {
        return Response.success(userService.listUsers(page, keyword));
    }

    @GetMapping("/user/{id}")
    @ApiOperation("查看用户")
    @RequiresPermissions("system:user:view")
    public Response<UserResponseDTO> findUser(@PathVariable Long id) {
        UserResponseDTO user = userService.findUser(id);
        if (user == null) {
            return Response.fail(commonMessage.getEntityNotFound(ENTITY_NAME));
        }
        return Response.success(user);
    }

    @DeleteMapping("/user")
    @ApiOperation("删除用户")
    @RequiresPermissions("system:user:remove")
    public Response<UserResponseDTO> removeUser(RequestEntity<List<Long>> ids) {
        Integer result = userService.removeUser(ids.getBody());
        if (result < 1) {
            return Response.fail(commonMessage.getEntityRemoveFailed(ENTITY_NAME));
        }
        return Response.success(commonMessage.getEntityRemoveSuccess(ENTITY_NAME, result));
    }

    @PostMapping("/user")
    @ApiOperation("新增用户")
    @RequiresPermissions("system:user:save")
    public Response<String> addUser(@Valid @RequestBody UserRequestDTO requestDTO) {
        if (userService.saveUser(requestDTO) > 0) {
            return Response.success(commonMessage.getEntitySaveSuccess(ENTITY_NAME));
        }
        return Response.fail(commonMessage.getEntitySaveFailed(ENTITY_NAME));
    }

    @PutMapping("/user")
    @ApiOperation("更新用户")
    @RequiresPermissions("system:user:update")
    public Response<String> updateUser(@RequestBody UserRequestDTO requestDTO) {
        Integer result = userService.updateUser(requestDTO);
        if (result > 0) {
            return Response.success(commonMessage.getEntityUpdateSuccess(ENTITY_NAME));
        }

        if (result == -1) {
            return Response.fail(commonMessage.getEntityNotFound(ENTITY_NAME));
        }
        return Response.fail(commonMessage.getEntityUpdateFailed(ENTITY_NAME));
    }

}

package com.sequarius.titan.sample.system.controller;

import com.sequarius.common.Page;
import com.sequarius.common.Response;
import com.sequarius.titan.sample.system.domain.UserRequestDTO;
import com.sequarius.titan.sample.system.domain.UserResponseDTO;
import com.sequarius.titan.sample.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
    @Resource
    private UserService userService;

    @GetMapping("/users")
    @ApiOperation("获取用户列表")
    public Response<List<UserResponseDTO>> list() {
        return Response.success(userService.listUsers(new Page(0, 10), "xdsjsd"));
    }

    @GetMapping("/user/{id}")
    @ApiOperation("通过id获取用户")
    public Response<UserResponseDTO> findUser(@PathVariable Long id) {
        UserResponseDTO user = userService.findUser(id);
        if (user == null) {
            return Response.fail("未找到用户!");
        }
        return Response.success(user);
    }

    @DeleteMapping("/user")
    @ApiOperation("通过id删除用户")
    public Response<UserResponseDTO> removeUser(RequestEntity<List<Long>> ids) {
        Integer result = userService.deleteUser(ids.getBody());
        if (result < 1) {
            return Response.fail("删除失败，用户可能已被其他用户删除");
        }
        return Response.success(String.format("已成功删除 %d 个用户", result));
    }

    @PostMapping("/user")
    @ApiOperation("新增用户")
    public Response<String> addUser(@Valid @RequestBody UserRequestDTO requestDTO) {
        if (userService.addUser(requestDTO) > 0) {
            return Response.success("新增用户成功!");
        }
        return Response.fail("新建用户失败，请稍候再试！");
    }

    @PutMapping("/user")
    @ApiOperation("更新用户")
    public Response<String> updateUser(@RequestBody UserRequestDTO requestDTO) {
        Integer result = userService.updateUser(requestDTO);
        if (result > 0) {
            return Response.success("更新用户成功!");
        }

        if (result == -1) {
            return Response.fail("未找到更新用户，该用户可能已经被其他用户删除，请刷新列表后重试！");
        }

        return Response.fail("更新用户失败，该用户可能已被其他用户操作，请刷新列表后重试！");
    }

}

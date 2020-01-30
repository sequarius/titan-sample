package com.sequarius.titan.sample.system.controller;

import com.sequarius.common.Page;
import com.sequarius.titan.sample.system.domain.UserResponseDTO;
import com.sequarius.titan.sample.system.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * project titan-sample
 *
 * @author Sequarius *
 * @since 30/01/2020
 */
@RestController
@RequestMapping("/system")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/user")
    public List<UserResponseDTO> list(){
        return userService.listUsers(new Page(0,10),"xdsjsd");
    }
}

package com.zsc.springboot.controller;


import com.zsc.springboot.commons.Result;
import com.zsc.springboot.commons.swagger2.SwaggerParamType;
import com.zsc.springboot.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author shichen
 * @since 2018-12-17
 */
@Slf4j
@RestController
@RequestMapping("/collections-framework/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 根据用户id获取用户详细信息
     *
     * @param uid
     * @return
     */
    @ApiOperation(value="根据用户id获取用户详细信息", notes="必须是用户uid")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType= SwaggerParamType.PATH, name = "uid", value = "用户id", required = true, dataType = "Long", defaultValue = "1")
    })
    @GetMapping("{uid}")
    public Result<Object> user(@PathVariable long uid) {
        return new Result<>(userService.getById(uid));
    }


}

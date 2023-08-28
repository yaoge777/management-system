package com.example.backend.controller;

import com.example.backend.entity.Menu;
import com.example.backend.entity.Result;
import com.example.backend.entity.Role;
import com.example.backend.service.IRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yaoge
 * @since 2023-08-14
 */
@RestController

@RequestMapping("/sys/roleMenu")
public class RoleMenuController {


    @Autowired
    private IRoleMenuService roleMenuService;

    @PostMapping("/query")
    public Result<Object> getSelectMenu(@RequestBody Role role){
        List<Menu> menus = roleMenuService.getMenuByRole(role);
        if(menus.isEmpty()) return Result.fail();
        return Result.success(menus);
    }
}

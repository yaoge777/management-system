package com.example.backend.controller;

import com.example.backend.entity.Menu;
import com.example.backend.entity.Result;
import com.example.backend.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

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

@RequestMapping("/sys/menu")
public class MenuController {
    @Autowired
    private IMenuService menuService;

    @GetMapping
    public Result<Object> getAllMenus(){
        List<Menu> menus = menuService.getAllMenu();
        if(menus.isEmpty()) return Result.fail();
        return Result.success(menus);
    }


}

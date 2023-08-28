package com.example.backend.controller;

import com.example.backend.entity.Result;
import com.example.backend.entity.Role;
import com.example.backend.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yaoge
 * @since 2023-08-14
 */
@RestController
@RequestMapping("/sys/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @GetMapping("/rolelist")
    public Result<Object> getRoleList(){
        List<Role> list = roleService.getRoleList();
        if(list.isEmpty()) return Result.fail();
        Map<String, Object> map = new HashMap<>();
        map.put("data", list);
        return Result.success(map);
    }

    @GetMapping("/all")
    public Result<Object> getAllRole(@RequestParam Long PageNumber,
                                     @RequestParam Long PageSize){
        List<Role> list = roleService.getAllRole(PageNumber, PageSize);
        if(list.isEmpty()) return Result.fail();
        Map<String, Object> map = new HashMap<>();
        map.put("data", list);
        return Result.success(map);
    }


    @PutMapping("/add")
    public Result<Object> addRole(@RequestBody Role role){
        boolean r = roleService.insertRole(role);
        if(r) return Result.success();
        return Result.fail();
    }

    @PostMapping("/edit")
    public Result<Object> updateRole(@RequestBody Role role){
        boolean r = roleService.updateRole(role);
        if(r) return Result.success();
        return Result.fail();
    }

    @GetMapping("/query")
    public Result<Object> queryRole(@RequestParam String roleName,
                                               @RequestParam Long PageSize,
                                               @RequestParam Long PageNumber){
        List<Role> list = roleService.queryRole(roleName, PageNumber, PageSize);
        if(list.isEmpty()) return Result.fail();
        Map<String, List<Role>> map = new HashMap<>();
        map.put("data", list);
        return Result.success(map);
    }

    @DeleteMapping("/delete")
    public Result deleteRole(@RequestBody Role role){
        boolean b = roleService.deleteRole(role);
        if(b) return Result.success();
        return Result.fail();
    }

}

package com.example.backend.controller;

import com.example.backend.entity.Result;
import com.example.backend.entity.User;
import com.example.backend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
@RequestMapping("/sys/user")
public class UserController {
    @Autowired
    private IUserService userService;


    @PostMapping("/login")
    public Result login(@RequestBody User user){

        Map<String, Object> data = userService.login(user);
        System.out.println(user);
        if(data == null) return Result.fail();
        return Result.success(data);
    }
    @GetMapping("/all")
    public Result getAllUser(@RequestParam Long PageNumber, @RequestParam Long PageSize) {
        List<User> list = userService.getAllUser(PageNumber,PageSize);
        List<User> users = userService.list();
        if(list.size() == 0) return Result.fail();
        Map<String, Object> mapper = new HashMap<>();
        mapper.put("data", list);
        mapper.put("total", users.size());
        return Result.success(mapper);
    }

    @GetMapping("/query")
    public Result queryUser(@RequestParam(required = false) String username,
                            @RequestParam(required = false) String email,
                            @RequestParam(required = false) String phone,
                            @RequestParam Long PageNumber, @RequestParam Long PageSize){
        List<User> list = userService.queryUser(username, email, phone, PageNumber, PageSize);
        if(list.isEmpty()) return Result.fail();
        Map<String, Object> mapper = new HashMap<>();
        mapper.put("data", list);
        mapper.put("total", list.size());
        return Result.success(mapper);
    }

    @GetMapping("/{id}")
    public Result getUserById(@PathVariable Integer id){
        User user = userService.getUserById(id);
        if(user == null) return Result.fail();
        Map<String, Object> mapper = new HashMap<>();
        mapper.put("data", user);
        return Result.success(mapper);
    }

    @PostMapping("/edit")
    public Result updateUser(@RequestBody User user){
        if(userService.updateUser(user)) return Result.success();
        return Result.fail();
    }

    @PutMapping("/add")
    public Result addUser(@RequestBody User user){
        if(userService.addUser(user)) return Result.success();
        return Result.fail();
    }

    @DeleteMapping("/delete")
    public Result deleterUser(@RequestBody User user){
        if(userService.deleteUser(user)) return Result.success();
        return Result.fail();
    }

}

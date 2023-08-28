package com.example.backend;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.sql.Types;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CodeGenerator {

    public static void main(String[] args) {
        String tables = "x_user,x_role,x_menu,x_user_role,x_role_menu";
        FastAutoGenerator.create("jdbc:mysql:///d1", "root", "yzr980812")
                .globalConfig(builder -> {
                    builder.author("yaoge") // 设置作者
                            .outputDir("/Users/richardyang/Documents/html/vue3-p2/backend/src/main/java"); // 指定输出目录
                })

                .packageConfig(builder -> {
                    builder.parent("com.example") // 设置父包名
                            .moduleName("backend") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "/Users/richardyang/Documents/html/vue3-p2/backend/src/main/resources/mapper/system")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables) // 设置需要生成的表名
                            .addTablePrefix("x_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();


    }
}

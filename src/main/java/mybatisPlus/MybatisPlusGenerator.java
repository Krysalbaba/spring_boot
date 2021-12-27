package mybatisPlus;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

public class MybatisPlusGenerator {


    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://192.168.1.88:3306/project?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("nie") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .disableOpenDir()//创建完成不启动文件夹
                            .outputDir("D:/Work/IDEA/demo/spring_boot/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.java.nie") // 设置父包名
                            .controller("controller")
                            .service("service")
                            .serviceImpl("service.impl")
                            .entity("domain")
                            .mapper("mapper")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D:/Work/IDEA/demo/spring_boot/src/main/resources/mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("t_user") // 设置需要生成的表名
                            .entityBuilder()//实体类配置
                            .enableLombok()//开启lombok注解
                            .controllerBuilder()//controller配置
                            .enableRestStyle()//开启restcontroller注解
                            .mapperBuilder()   //配置mapper 策略
                            .enableBaseColumnList()
                            .enableBaseResultMap()
                            .build(); //注入
                })
                .execute();
    }

}

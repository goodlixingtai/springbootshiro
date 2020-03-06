package com.yonyou.springbootshiro.controller;

import com.yonyou.springbootshiro.entity.SysUser;
import com.yonyou.springbootshiro.repository.UserRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lxt
 * @version 1.0
 * @date 2020-03-05 22:58
 */
@RestController
@RequestMapping("user")
public class UserHandler {
    @Autowired
    UserRepository userRepository;

   /* 其中@ApiOperation是接口的描述信息
    @ApiImplicitParam针对具体的参数添加描述
    @ApiImplicitParams则是多个@ApiImplicitParam
    更多注解使用，可以参考官方文档。
    需要注意的是，
    使用@PathVariable需要设置paramType="path"，
    而使用@RequestParam时，
    需要设置paramType="query"，
    不然可能会导致好好的接口无法接受到请求参数。*/

    @ApiOperation(value = "用户" ,notes = "查询书籍信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",paramType = "path",value = "用户id",required = true,dataType = "Long" )
    })
    @GetMapping("/findById/{id}")
    public SysUser findById(@PathVariable(value = "id") Long id){
        SysUser user =userRepository.findById(id).get();
         return   user;

    }

}

package com.yonyou.springbootshiro.repository;


import com.yonyou.springbootshiro.entity.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;

/**
 * @author lxt
 * @version 1.0
 * @date 2020-03-05 22:14
 */
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Test
    void  find(){
      //  userRepository.selectBySysUser("赵云");
        /*System.out.println(userRepository.selectBySysUser("赵云"));*/
        SysUser sysUser=new SysUser();
        sysUser.setName("赵云");
        Example<SysUser> example1 = Example.of(sysUser);

       /* userRepository.findAll(example1);*/
        System.out.println(userRepository.findByName("admin"));
    }
}

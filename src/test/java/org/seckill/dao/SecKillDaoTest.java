package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SecKill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 配置spring和junit的整合，junit启动时加载spring ioc容器
 * <p>User: yeyaohui
 * <p>Date: Jun 23, 2016
 * <p>Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SecKillDaoTest {
    //注入DAO实现类依赖
    @Resource
    private SecKillDao secKillDao;

    @Test
    public void testReduceNumber() throws Exception {
        Date killTime = new Date();
        int result = secKillDao.reduceNumber(1000L,killTime);
        System.out.println(result);

    }

    @Test
    public void testQueryById() throws Exception {
        long id = 1000;
        SecKill secKill = secKillDao.queryById(id);
        System.out.println(secKill.getName());
    }

    @Test
    public void testQueryAll() throws Exception {
        List<SecKill> secKillList = secKillDao.queryAll(0,1000);

        for(SecKill row : secKillList){
            System.out.println(row.toString());
        }
    }
}

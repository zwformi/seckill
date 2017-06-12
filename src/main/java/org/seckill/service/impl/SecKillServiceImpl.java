package org.seckill.service.impl;

import org.seckill.dao.SecKillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SecKillExecution;
import org.seckill.entity.SecKill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SecKillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SecKillCloseException;
import org.seckill.exception.SecKillException;
import org.seckill.service.SecKillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * 业务接口实现
 * <p>User: yeyaohui
 * <p>Date: Jun 25, 2016
 * <p>Version: 1.0
 */
@Service
public class SecKillServiceImpl implements SecKillService{
    private Logger logger = LoggerFactory.getLogger(SecKillService.class);

    @Autowired
    private SecKillDao secKillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;

    //混淆字符，用于混淆MD5
    private final String salt = "sdlkjs#$#$dfowierlkjafdmv232k3j@@##$";

    @Override
    public List<SecKill> getSecKillList() {
        return secKillDao.queryAll(0,4);
    }

    @Override
    public SecKill getById(long secKillId) {
        return secKillDao.queryById(secKillId);
    }

    @Override
    public Exposer exportSecKillUrl(long secKillId) {
        SecKill secKill = secKillDao.queryById(secKillId);

        if(null == secKill){
            return  new Exposer(false,secKillId);
        }

        Date startTime = secKill.getStartTime();
        Date endTime = secKill.getEndTime();
        Date nowTime = new Date();

        if(nowTime.getTime() < startTime.getTime()
                || nowTime.getTime() > endTime.getTime()){
            return new Exposer(false,secKillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
        }

        //转化特定字符串的过程，不可逆
        String md5 = getMD5(secKillId);

        return new Exposer(true,md5,secKillId);

    }

    @Override
    @Transactional
    /**
     * 使用注解控制事务方法的优化：
     * 1、开发团队达到一致约定，明确标注事务方法的编程风格
     * 2、保证事务方法的执行时间尽可能短，不要穿插其他网络操作RPC/HTTP请求，或者剥离到事务方法外部
     * 3、不是所有的方法都需要事务，如只有一条修改操作，只读操作就不需要事务控制
     */
    public SecKillExecution executeSecKill(long secKillId, long userPhone, String md5)
            throws SecKillException, RepeatKillException, SecKillCloseException {
        if(null == md5 || !md5.equals(getMD5(secKillId))){
            throw new SecKillException("seckill datarewirte");
        }

       try{
           //执行秒杀逻辑，减库存，记录购买行为
           Date nowTime = new Date();
           //减库存
           int updateCount = secKillDao.reduceNumber(secKillId,nowTime);

           if(updateCount <= 0){
               //没有更新到记录，秒杀结束
               throw new SecKillCloseException("seckill is Closed");
           }else{
               //记录购买行为
               int insertCount = successKilledDao.insertSuccessKilled(secKillId,userPhone);

               //唯一：secKillId,userPhone
               if(insertCount <= 0){
                   //重复秒杀
                   throw new RepeatKillException("seckill repeated");
               }else{
                   //秒杀成功
                   SuccessKilled successKilled = successKilledDao.queryByIdWithSecKill(secKillId,userPhone);

                   return new SecKillExecution(secKillId, SecKillStatEnum.SUCCESS,successKilled);
               }
           }
       }catch(SecKillCloseException e1){
           throw e1;
       }catch(RepeatKillException e2){
           throw e2;
       }catch (Exception e){
            logger.error(e.getMessage(),e);
           //所有编译期异常，转化为运行期异常
           throw new SecKillException("seckill inner error:" + e.getMessage());
       }
    }

    /**
     * 生成MD5
     * @param secKillId
     * @return
     */
    private String getMD5(long secKillId){
        String base = secKillId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}

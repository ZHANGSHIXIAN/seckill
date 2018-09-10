package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"
})
public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;


    @Test
    public void getSeckillList() {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}",list);
    }

    @Test
    public void getById() {
        long id=1000;
        Seckill seckill = seckillService.getById(id);
        logger.info("seckill={}",seckill);
    }

    @Test
    public void testSeckillLogic() {
        long id=1001;
        Exposer exposer= seckillService.exportSeckillUrl(id);
        logger.info("exposer={}", exposer);
        if (exposer.isExposed()){
            long userPhone=13143065607L;
            String md5=exposer.getMd5();
            try {
                SeckillExecution execution =  seckillService.executeSeckill(id, userPhone, md5);
                logger.info("result={}",execution);
            }catch (RepeatKillException e){
                logger.error(e.getMessage(), e);
            }catch (SeckillCloseException e){
                logger.error(e.getMessage(), e);
            }
        }else {
            logger.warn("exposer={}",exposer);
        }

        //21:25:10.495 [main]
        // INFO  o.seckill.service.SeckillServiceTest
        // - exposer=Exposer{exposed=true,
        // md5='083c0e80fa55f41419a4c835dcd4fa6b',
        // seckillId=1000, now=0, start=0, end=0}
    }


    @Test
    public void executeSeckillProcedure() throws SeckillExecution {
        long seckillId=1001;
        long phone =13414754761L;
        Exposer exposer=seckillService.exportSeckillUrl(seckillId);
        if (exposer.isExposed()){
            String md5=exposer.getMd5();
            SeckillExecution execution=seckillService.executeSeckillProcedure(seckillId, phone, md5);
            logger.info(execution.getStateInfo());
        }




    }
}
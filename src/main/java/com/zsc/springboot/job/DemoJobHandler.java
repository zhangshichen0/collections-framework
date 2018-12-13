package com.zsc.springboot.job;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author shichen
 * @create 2018-12-07
 * @desc
 */
@Slf4j
@JobHandler(value="demoJobHandler")
@Component
public class DemoJobHandler extends IJobHandler {

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        Transaction transaction = Cat.newTransaction("DemoJobHandler", "executeDemoJob");
        log.info("【定时任务执行】param {}, current time {}", param, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        transaction.setStatus(Transaction.SUCCESS);
        transaction.complete();
        return ReturnT.SUCCESS;
    }
}

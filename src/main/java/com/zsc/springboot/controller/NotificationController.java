package com.zsc.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.zsc.springboot.commons.Result;
import com.zsc.springboot.utils.CfThreadFactory;
import com.zsc.springboot.utils.DeferredResultWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author shichen
 * @create 2018-12-07
 * @desc 此类按照restful规范开发
 */
@Slf4j
@RestController
@RequestMapping(value = "/collections-framework/api/notification", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class NotificationController {

    private final Map<String, DeferredResultWrapper> deferredResults = new ConcurrentHashMap<>();

    private final ScheduledExecutorService executorService;

    public NotificationController() {
        executorService = Executors.newSingleThreadScheduledExecutor(CfThreadFactory.create("NotificationController", true));
        executorService.scheduleAtFixedRate(() -> deferredResults.values().stream().forEach(deferredResultWrapper -> {
            Result<Object> result = new Result<>(true);
            JSONObject json = new JSONObject();
            json.put("count", 1);
            result.setData(json);
            deferredResultWrapper.setResult(result);
            log.info("【异步处理长轮询结果】biz thread process completed");
        }), 10, 10, TimeUnit.SECONDS);
    }

    /**
     * 获取通知
     *
     * @param uid
     * @return
     */
    @GetMapping
    public DeferredResult<Result<Object>> pollNotification(long uid) {
        log.info("【长轮询】servlet thread enter");
        DeferredResultWrapper deferredResultWrapper = new DeferredResultWrapper();

        deferredResultWrapper.onTimeout(() -> {
            log.info("【获取变化通知】timeout, uid {}", uid);
        });

        deferredResultWrapper.onCompletion(() -> {
            deferredResults.remove(String.valueOf(uid), deferredResultWrapper);
        });

        deferredResults.put(String.valueOf(uid), deferredResultWrapper);
        log.info("【长轮询】servlet thread released");
        return deferredResultWrapper.getResult();
    }


}

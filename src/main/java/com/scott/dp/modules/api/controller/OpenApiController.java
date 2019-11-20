package com.scott.dp.modules.api.controller;

import com.scott.dp.common.support.config.AnRateLimiter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName :OpenApiController
 * @Description :DOTO
 * @Author :Mr.薛
 * @Data :2019/11/20  14:02
 * @Version :V1.0
 * @Status : 编写
 **/
@Api(value = "开源接口", description = "开源接口测试")
@RestController
@RequestMapping(value = "/openApi")
public class OpenApiController {

    @ApiOperation(value = "开源测试")
    @AnRateLimiter(timeout = 1, timeunit = TimeUnit.SECONDS)
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return "Test OK!";
    }

}

package com.zsc.springboot.test.controller;

import com.zsc.springboot.test.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author shichen
 * @create 2018-12-17
 * @desc
 */
@Slf4j
public class UserControllerTest extends BaseTest {

    @Autowired
    private MockMvc mvc;

    /**
     * 测试翻页
     */
    @Test
    public void testList() throws Exception {
        MockHttpServletResponse response =
                mvc.perform(MockMvcRequestBuilders.get("/collections-framework/api/user/list/1/10/10")
//                        .header("x-app-id","10002")
//                        .header("Authorization","1v773706325951a1a900042203414720e768a502c4af00d53504932996473d38245390b3250ec87bcc7e49a16b1ab01c98")
                        //.param("commodityId","1281051921")
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn().getResponse();

        System.out.println("======================== 打印结果 ========================");
        System.out.println(response.getContentType());
        System.out.println(response.getCharacterEncoding());
        System.out.println(response.getStatus());
        System.out.println(response.getContentAsString());
        System.out.println("======================== 打印结果 ========================");
    }
}

package com.microtask.msghandler.controller;


import com.microtask.msghandler.MessageHandlerTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = MessageHandlerTest.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc(addFilters = false)
//@ActiveProfiles("test")
//public class MessageControllerTest {
//    @Autowired
//    private MessagesController controller;
//
//    private MockMvc mockMvc;
//
//    @Value("${test.expected.header.name}")
//    private String expectedHeader;
//    @Value("${test.expected.header.value}")
//    private String expectedHeaderVal;
//
//    @Before
//    public void setup() {
//        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
//    }
//
//    @Test
//    public void testSayHelloWorld() throws Exception{
//        this.mockMvc.perform(MockMvcRequestBuilders
//                        .post("/")
////                        .header()
//                        .content(MediaType.TEXT_PLAIN_VALUE)
//                        .content("test_message"))
////                        .param("msg", "test"))
//                .andExpect(status().isOk())
////                .andExpect(model().attribute("welcome",
////                        "Welcome , Geeks to the world of programming!!!"))
////                .andExpect(view().name("welcome-page"))
//                .andDo(MockMvcResultHandlers.print());
//    }
//}


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration
//@WebAppConfiguration
//
@SpringBootTest//(classes = MessageHandlerTest.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ExtendWith(SpringExtension.class)
//@AutoConfigureMockMvc(addFilters = false)
//@WebMvcTest
//@ContextConfiguration(classes = SecurityConfig.class)
//@WebAppConfiguration
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ContextConfiguration
//@WebMvcTest
@ActiveProfiles("test")
public class MessageControllerTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @Value("${test.expected.header.name}")
    private String expectedHeader;
    @Value("${test.expected.header.value}")
    private String expectedHeaderVal;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void testSayHelloWorld() throws Exception{

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/")
                        .header(expectedHeader, expectedHeaderVal)
                        .content(MediaType.TEXT_PLAIN_VALUE)
                        .content("test_message"))
//                        .param("msg", "test"))
                .andExpect(status().isOk())
//                .andExpect(model().attribute("welcome",
//                        "Welcome , Geeks to the world of programming!!!"))
//                .andExpect(view().name("welcome-page"))
                .andDo(MockMvcResultHandlers.print());
    }
}

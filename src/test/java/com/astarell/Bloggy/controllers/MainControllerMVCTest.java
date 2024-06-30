package com.astarell.Bloggy.controllers;

import com.astarell.Bloggy.repositories.PostRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@TestPropertySource(properties = {"main_page_posts_quantity=12", "recent_posts_quantity=5"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MainControllerMVCTest {

    private WebApplicationContext context;
    @Autowired
    private MockMvc mvc;

    @MockBean
    private PostRepository postRepo;

    @Value("${recent_posts_quantity}")
    private int recent_posts_quantity;
    @Value("${main_page_posts_quantity}")
    private int main_page_posts_quantity;

    @Before
    public void setup(){
        this.mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void getMainPage_ThenSuccess() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("posts", postRepo.getRecentPosts(main_page_posts_quantity)))
                .andExpect(model().attribute("recentPosts", postRepo.getRecentPosts(recent_posts_quantity)))
                .andExpect(view().name("main-pages/main"))
                .andExpect(content().string(containsString("All posts")));
    }

    @Test
    public void getMainSearchPage_ThenSuccess() throws Exception {
        mvc.perform(get("/search"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("posts", postRepo.getRecentPosts(main_page_posts_quantity)))
                .andExpect(view().name("main-pages/main"))
                .andExpect(content().string(containsString("All posts")));
    }

/*    @Test
    @WithAnonymousUser
    public void getPostDetails_AndPostIsPresent_ThenSuccess() throws Exception {
        mvc.perform(get("/details/{postId}", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("post", postRepo.findById(1L)))
                .andExpect(view().name("main-pages/main-details"));
    }*/
}
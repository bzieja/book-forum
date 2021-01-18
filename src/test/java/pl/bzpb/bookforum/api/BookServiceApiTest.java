package pl.bzpb.bookforum.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.bzpb.bookforum.dao.BookRepo;
import pl.bzpb.bookforum.dao.entity.Book;
import pl.bzpb.bookforum.services.BookService;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "ADMIN")
class BookServiceApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    //W ten sposób wszystko w teście jest wywoływane w jednej transakcji a na koniec testu zostanie ona zrollbackowana
    @Transactional
    void testGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/book"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].isbn", Matchers.is(1234)));
    }

    @Test
    @Transactional
    void testPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/book")
                .content("{\"isbn\": 3333,\"title\": \"TEST\",\"dateOfPublication\": \"1998-01-01\",\"author\": \"Test\" }")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @Transactional
    void testDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/book/{id}", 1234))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    void testPut() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/book/{id}", 1234)
                .content("{\"isbn\": \"\" ,\"title\": \"Zmiana\",\"dateOfPublication\": \"1999-01-01\",\"author\": \"Autora\" }")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
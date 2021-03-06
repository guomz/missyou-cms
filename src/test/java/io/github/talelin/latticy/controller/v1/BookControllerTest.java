package io.github.talelin.latticy.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import io.github.talelin.latticy.dto.book.CreateOrUpdateBookDTO;
import io.github.talelin.latticy.mapper.BookMapper;
import io.github.talelin.latticy.model.BookDO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
@Rollback
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookMapper bookMapper;

    private Long id;
    private String title = "ειδΉε€";
    private String author = "pedro";
    private String image = "ειδΉε€.png";
    private String summary = "ειδΉε€οΌζ―ε¨ζ°δΌ¦εθ΄ΉηζΈδΈθ΅·εε?ηζ­ζ²";

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getBook() throws Exception {
        BookDO bookDO = new BookDO();
        bookDO.setTitle(title);
        bookDO.setAuthor(author);
        bookDO.setImage(image);
        bookDO.setSummary(summary);
        bookMapper.insert(bookDO);

        this.id = bookDO.getId();
        this.mvc.perform(get("/v1/book/" + this.id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.title").value(title));
    }

    @Test
    public void getBooks() throws Exception {
        BookDO bookDO = new BookDO();
        bookDO.setTitle(title);
        bookDO.setAuthor(author);
        bookDO.setImage(image);
        bookDO.setSummary(summary);
        bookMapper.insert(bookDO);
        this.id = bookDO.getId();

        mvc.perform(get("/v1/book"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    public void searchBook() throws Exception {
        BookDO bookDO = new BookDO();
        bookDO.setTitle(title);
        bookDO.setAuthor(author);
        bookDO.setImage(image);
        bookDO.setSummary(summary);
        bookMapper.insert(bookDO);
        this.id = bookDO.getId();

        mvc.perform(get("/v1/book/search?q=ει"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$").isArray());
    }

    @Test
    public void createBook() throws Exception {
        CreateOrUpdateBookDTO dto = new CreateOrUpdateBookDTO();
        dto.setAuthor(author);
        dto.setImage(image);
        dto.setSummary(summary);
        dto.setTitle(title);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        String content = mapper.writeValueAsString(dto);

        mvc.perform(post("/v1/book/")
                .contentType(MediaType.APPLICATION_JSON).content(content))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.message").value("ζ°ε»ΊεΎδΉ¦ζε"));
    }

    @Test
    public void updateBook() throws Exception {
        BookDO bookDO = new BookDO();
        bookDO.setTitle(title);
        bookDO.setAuthor(author);
        bookDO.setImage(image);
        bookDO.setSummary(summary);
        bookMapper.insert(bookDO);
        this.id = bookDO.getId();

        CreateOrUpdateBookDTO dto = new CreateOrUpdateBookDTO();
        dto.setAuthor(author);
        dto.setImage(image);
        dto.setSummary(summary);
        dto.setTitle(title + "lol");

        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        String content = mapper.writeValueAsString(dto);

        mvc.perform(put("/v1/book/" + this.id)
                .contentType(MediaType.APPLICATION_JSON).content(content))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.message").value("ζ΄ζ°εΎδΉ¦ζε"));
    }

    @Test
    public void deleteBook() throws Exception {
        BookDO bookDO = new BookDO();
        bookDO.setTitle(title);
        bookDO.setAuthor(author);
        bookDO.setImage(image);
        bookDO.setSummary(summary);
        bookMapper.insert(bookDO);
        this.id = bookDO.getId();

        mvc.perform(delete("/v1/book/" + this.id))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.message").value("ε ι€εΎδΉ¦ζε"));
    }


    @After
    public void tearDown() throws Exception {
    }
}
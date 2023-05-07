package library.deepaklibraryportal.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import library.deepaklibraryportal.Model.Book;
import library.deepaklibraryportal.Model.BorrowedBooksDates;
import library.deepaklibraryportal.Model.Student;
import library.deepaklibraryportal.Service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = BookController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private Book book = new Book();

    private BorrowedBooksDates borrowDetail = new BorrowedBooksDates();

    private Student student = new Student();

    @BeforeEach
    public void init(){
        book.setBookName("Testing");
        book.setId(2);
        student.setId(10);
        borrowDetail.setBorrowDate(new Date());
        borrowDetail.setReturnDate(new Date());
        borrowDetail.setBook(book);
        borrowDetail.setStudent(student);
    }

    @Test
    void add() throws Exception {
        given(bookService.saveBook(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/book/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)));
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void getAllBooks() throws Exception {
        given(bookService.saveBook(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(get("/book/getAll")
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void getAllBooksByStudentId() throws Exception {
        given(bookService.saveBook(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(get("/book/borrowedBooks/10")
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void addBook() throws Exception {
        given(bookService.saveBook(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/book/borrowBook2/10/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(borrowDetail)));
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void returnBook() throws Exception {
        given(bookService.saveBook(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/book/returnBook/10/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(borrowDetail)));
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void allBorrowedBooks() throws Exception {
        given(bookService.saveBook(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(get("/book/allBorrowedBooks")
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void allOverDueBooks() throws Exception {
        given(bookService.saveBook(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(get("/book/allOverDueBooks")
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}
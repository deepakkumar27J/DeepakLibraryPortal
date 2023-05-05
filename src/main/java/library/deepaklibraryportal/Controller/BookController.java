package library.deepaklibraryportal.Controller;

import library.deepaklibraryportal.Model.Book;
import library.deepaklibraryportal.Model.BorrowedBooksDates;
import library.deepaklibraryportal.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/book")
@CrossOrigin
public class BookController {

    @Autowired
    private BookService bookService;

    private long bookId;

    @PostMapping("/create")
    public String add(@RequestBody Book book) {
        bookService.saveBook(book);
        return "Book has been created";
    }

    @GetMapping("/getAll")
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/getBook")
    public Book getBookDetail(@RequestParam int id){
        return bookService.bookDetail(id);
    }

    @GetMapping("/borrowedBooks/{studentId}")
    public ResponseEntity<Collection<BorrowedBooksDates>> getAllBooksByStudentId(@PathVariable(value="studentId") int studentId) throws Exception {
        Collection<BorrowedBooksDates> books = bookService.booksByStudentId(studentId);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping("/borrowBook/{studentId}/{bookId}")
    public ResponseEntity<Book> addBook (@PathVariable(value="studentId") int studentId, @PathVariable(value="bookId") int bookId) throws Exception {
        Book book = bookService.borrowBook(studentId, bookId);

        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping("/borrowBook2/{studentId}/{bookId}")
    public ResponseEntity<String> addBook (@PathVariable(value="studentId") int studentId, @PathVariable(value="bookId") int bookId, @RequestBody BorrowedBooksDates borrowingDetail) throws Exception {
        bookService.borrowBookOne(studentId, bookId, borrowingDetail);

        return new ResponseEntity<>("Successfully borrowed!", HttpStatus.OK);
    }

    @PostMapping("/returnBook/{studentId}/{bookId}")
    public ResponseEntity<String> returnBook (@PathVariable(value="studentId") int studentId, @PathVariable(value="bookId") int bookId, @RequestBody BorrowedBooksDates borrowingDetail) throws Exception {
        String response = bookService.returnBook(studentId, bookId, borrowingDetail);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/allBorrowedBooks")
    public ResponseEntity<Collection<BorrowedBooksDates>> allBorrowedBooks (){
        Collection<BorrowedBooksDates> books = bookService.allBorrowedBooks();

        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/allOverDueBooks")
    public ResponseEntity<Collection<BorrowedBooksDates>> allOverDueBooks (){
        Collection<BorrowedBooksDates> books = bookService.allOverDueBooks();

        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}

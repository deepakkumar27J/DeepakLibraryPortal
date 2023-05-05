package library.deepaklibraryportal.Service;

import library.deepaklibraryportal.Model.Book;
import library.deepaklibraryportal.Model.BorrowedBooksDates;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface BookService {
    public Book saveBook(Book book);
    public List<Book> getAllBooks();

    public Book bookDetail(int id);

    public Collection<BorrowedBooksDates> booksByStudentId(long studentId) throws Exception;

    public Book borrowBook(int studentId, int bookId) throws Exception;

    public void borrowBookOne(int studentId, int bookId, BorrowedBooksDates borrowingDetail) throws Exception;

    public String returnBook(int studentId, int bookId, BorrowedBooksDates borrowingDetail) throws Exception;

    public Collection<BorrowedBooksDates> allBorrowedBooks();

    public Collection<BorrowedBooksDates> allOverDueBooks();
}

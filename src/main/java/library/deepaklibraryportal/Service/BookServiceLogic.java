package library.deepaklibraryportal.Service;

import library.deepaklibraryportal.Model.Book;
import library.deepaklibraryportal.Model.BorrowedBooksDates;
import library.deepaklibraryportal.Model.Student;
import library.deepaklibraryportal.Repository.BookRepositroy;
import library.deepaklibraryportal.Repository.BorrowHistory;
import library.deepaklibraryportal.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceLogic implements BookService{

    @Autowired
    private BookRepositroy bookRepository;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private BorrowHistory borrowHistory;
    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book bookDetail(int id) {
        Book bookResponse= bookRepository.findById(id);
        return bookResponse;
    }

    @Override
    public Collection<BorrowedBooksDates> booksByStudentId(long studentId) throws Exception {
        Student _student = studentRepository.findStudentById((int)studentId);
        if(!studentRepository.existsById((int)studentId)) {
            throw new Exception("No book found for student with Id "+studentId);
        }
        Collection<BorrowedBooksDates> books = _student.getBorrowedBooks();
        Iterator<BorrowedBooksDates> i = books.iterator();

        while(i.hasNext()) {
            BorrowedBooksDates e = i.next();
            if (e.getReturnedDate()!=null) {
                i.remove();
            }
        }
        return books;
    }

    @Override
    public Book borrowBook(int studentId, int bookId) throws Exception {
        return new Book();
    }

    @Override
    public void borrowBookOne(int studentId, int bookId, BorrowedBooksDates borrowingDetail) throws Exception {
        studentRepository.findById(studentId).map(student->{
            boolean ifBookExist = bookRepository.existsById((long) bookId);
            if(ifBookExist==false) {
                try {
                    throw new Exception("No valid book found for id "+bookId);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            Book _book = bookDetail(Math.toIntExact(bookId));
            BorrowedBooksDates _borrowedOne =  borrowHistory.findByBookIdAndStudentId(bookId,studentId);
            if(_borrowedOne.getReturnedDate()!=null){
                _borrowedOne.setBorrowDate(borrowingDetail.getBorrowDate());
                _borrowedOne.setReturnDate(borrowingDetail.getReturnDate());
                _borrowedOne.setReturnedDate(null);
                borrowHistory.save(_borrowedOne);
                return "{\"already\":\"true\"}";
            }
            else {
            BorrowedBooksDates newBorrow1 = new BorrowedBooksDates();
            newBorrow1.setStudent(student);
            newBorrow1.setBook(_book);
            newBorrow1.setBorrowDate(borrowingDetail.getBorrowDate());
            newBorrow1.setReturnDate(borrowingDetail.getReturnDate());
            borrowHistory.save(newBorrow1);
            student.setBorrowedBooks(student.getBorrowedBooks()
                    .stream()
                    .map(borrow-> {
                        System.out.print(student.getId());
                        System.out.print(_book.getId());
                        BorrowedBooksDates newBorrow = new BorrowedBooksDates();
                        newBorrow.setStudent(student);
                        newBorrow.setBook(_book);
                        newBorrow.setBorrowDate(borrow.getBorrowDate());
                        newBorrow.setReturnDate(borrow.getReturnDate());
                        return newBorrow;
                    })
                    .collect(Collectors.toList())
            );}
            return _book;
        }).orElseThrow(()-> new Exception("No student with id = "+studentId));
    }

    @Override
    public String returnBook(int studentId, int bookId, BorrowedBooksDates borrowingDetail) throws Exception {
        String response = studentRepository.findById(studentId).map(student->{
            boolean ifBookExist = bookRepository.existsById((long) bookId);
            if(ifBookExist==false) {
                try {
                    throw new Exception("No valid book found for id "+bookId);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            Book _book = bookDetail(Math.toIntExact(bookId));

            BorrowedBooksDates _borrowedOne =  borrowHistory.findByBookIdAndStudentId(bookId,studentId);
            if(_borrowedOne.getReturnedDate()!=null){
                return "{\"already\":\"true\"}";
            }
            _borrowedOne.setReturnedDate(borrowingDetail.getReturnedDate());
            borrowHistory.save(_borrowedOne);
            if(borrowingDetail.getReturnedDate().after(_borrowedOne.getReturnDate())) {
                // Create Invoice
                String uri = "http://localhost:8081/invoice";
                RestTemplate restTemplate = new RestTemplate();
                Invoice _invoice = new Invoice(20, "OverDue", student.getEmailId());
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity entity = new HttpEntity(_invoice, headers);
                ResponseEntity<String> out = restTemplate.exchange(uri, HttpMethod.POST, entity
                        , String.class);
                System.out.print(out.getBody());
                // Invoice Created
                return out.getBody();
            }
            return "{\"return\":\"true\"}";
        }).orElseThrow(()-> new Exception("No student with id = "+studentId));

        return response;
    }

    @Override
    public Collection<BorrowedBooksDates> allBorrowedBooks() {
        Collection<BorrowedBooksDates> books = borrowHistory.findAll();
        Iterator<BorrowedBooksDates> i = books.iterator();

        while(i.hasNext()) {
            BorrowedBooksDates e = i.next();
            if (e.getReturnedDate()!=null) {
                i.remove();
            }
        }
        return books;
    }

    @Override
    public Collection<BorrowedBooksDates> allOverDueBooks() {
        Collection<BorrowedBooksDates> books = borrowHistory.findAll();
        Iterator<BorrowedBooksDates> i = books.iterator();

        while(i.hasNext()) {
            BorrowedBooksDates e = i.next();
            if (e.getReturnDate().after(new Date())) {
                i.remove();
            }
        }
        return books;
    }
}

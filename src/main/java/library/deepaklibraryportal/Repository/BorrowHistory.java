package library.deepaklibraryportal.Repository;

import library.deepaklibraryportal.Model.BorrowedBooks;
import library.deepaklibraryportal.Model.BorrowedBooksDates;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowHistory extends JpaRepository<BorrowedBooksDates, BorrowedBooks> {
    BorrowedBooksDates findByBookIdAndStudentId(int bookId, int studentId);
}

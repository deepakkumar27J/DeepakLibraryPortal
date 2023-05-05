package library.deepaklibraryportal.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BorrowedBooks implements Serializable {

    @Column (name = "student_id")
    Long studentId;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    @Column(name = "book_id")
    Long bookId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BorrowedBooks that)) return false;
        return Objects.equals(getStudentId(), that.getStudentId()) && Objects.equals(getBookId(), that.getBookId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStudentId(), getBookId());
    }
}

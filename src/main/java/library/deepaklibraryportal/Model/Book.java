package library.deepaklibraryportal.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Book {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @JsonIgnore
    Collection<BorrowedBooksDates> borrowedBooks = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookName() {
        return bookName;
    }

    @Column(
            name = "book_name"
    )
    private String bookName;

    public Book() {
    }

    public Collection<BorrowedBooksDates> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(Collection<BorrowedBooksDates> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }
}

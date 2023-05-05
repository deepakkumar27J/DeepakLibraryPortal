package library.deepaklibraryportal.Repository;

import library.deepaklibraryportal.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepositroy extends JpaRepository<Book,Integer> {
    Book findById(long id);
    boolean existsById(long id);
}

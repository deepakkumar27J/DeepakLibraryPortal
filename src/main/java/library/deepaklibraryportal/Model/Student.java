package library.deepaklibraryportal.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.*;

@Entity
public class Student {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonIgnore
    private Collection<BorrowedBooksDates> borrowedBooks = new ArrayList<>();
    @Column(
            name = "year_intake"
    )
    private Integer yearIntake;
    @Column(
            name = "cgpa"
    )
    private float cgpa;
    @Column(
            name = "dues_clear"
    )
    private boolean DuesClear;
    @Column(
            name = "first_name"
    )
    private String firstName;
    @Column(
            name = "last_name"
    )
    private String lastName;
    @Column(
            name = "email_id"
    )
    private String emailId;
    @Column(
            name = "dob"
    )
    private Date dob;
    @Column(
            name = "password"
    )
//    @JsonIgnore
    private String password;
    @Column(
            name = "phone_number"
    )
    private String phoneNumber;

    public long getId() {
        return this.id;
    }

    public Integer getYearIntake() {
        return this.yearIntake;
    }

    public float getCgpa() {
        return this.cgpa;
    }

    public boolean isDuesClear() {
        return this.DuesClear;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmailId() {
        return this.emailId;
    }

    public Date getDob() {
        return this.dob;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public void setYearIntake(final Integer yearIntake) {
        this.yearIntake = yearIntake;
    }

    public void setCgpa(final float cgpa) {
        this.cgpa = cgpa;
    }

    public void setDuesClear(final boolean DuesClear) {
        this.DuesClear = DuesClear;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public void setEmailId(final String emailId) {
        this.emailId = emailId;
    }

    public void setDob(final Date dob) {
        this.dob = dob;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Student() {
    }

    public Collection<BorrowedBooksDates> getBorrowedBooks() {
        return borrowedBooks;
    }

    public Collection<BorrowedBooksDates> setBorrowedBooks(Collection<BorrowedBooksDates> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
        return this.getBorrowedBooks();
    }
}

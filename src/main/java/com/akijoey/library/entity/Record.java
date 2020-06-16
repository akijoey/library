package com.akijoey.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "record")
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name="uid", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name="isbn", referencedColumnName = "isbn")
    private Book book;

    @Column(name = "borrowing")
    private Date borrowing;

    @Column(name = "returning")
    private Date returning;

    @Column(name = "state")
    private boolean state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getBorrowing() {
        return borrowing;
    }

    public void setBorrowing(Date borrowing) {
        this.borrowing = borrowing;
    }

    public Date getReturning() {
        return returning;
    }

    public void setReturning(Date returning) {
        this.returning = returning;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}

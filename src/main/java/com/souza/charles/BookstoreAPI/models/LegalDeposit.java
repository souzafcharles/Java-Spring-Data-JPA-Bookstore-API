package com.souza.charles.BookstoreAPI.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tb_legal_deposit")
public class LegalDeposit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String depositCode;

    @Column(nullable = false, unique = true)
    private String country;

    private Book book;

    public LegalDeposit() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDepositCode() {
        return depositCode;
    }

    public void setDepositCode(String depositCode) {
        this.depositCode = depositCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LegalDeposit that = (LegalDeposit) o;
        return Objects.equals(id, that.id) && Objects.equals(depositCode, that.depositCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, depositCode);
    }
}

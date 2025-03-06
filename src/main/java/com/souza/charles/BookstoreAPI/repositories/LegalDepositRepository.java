package com.souza.charles.BookstoreAPI.repositories;

import com.souza.charles.BookstoreAPI.models.LegalDeposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LegalDepositRepository extends JpaRepository<LegalDeposit, UUID> {
}
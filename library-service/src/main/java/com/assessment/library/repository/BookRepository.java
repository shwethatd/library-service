package com.assessment.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assessment.library.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}

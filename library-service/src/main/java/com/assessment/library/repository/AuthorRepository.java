package com.assessment.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assessment.library.model.Author;

public interface AuthorRepository extends JpaRepository<Author,Long>{

}

package com.assessment.library.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.assessment.library.model.Author;
import com.assessment.library.model.Book;

public interface BookService {

	public Book addBook(Book newBook);
	public Book updateBookName(Map<String, String> update, Long id);
	public Book findBook(Long id);
	public List<Book> findAll(String sortBy,String direction);
	public Book updateAuthors(List<Author> authors, Long id);
	public void deleteBook(Long id); 
	
}

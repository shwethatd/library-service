package com.assessment.library;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.assessment.library.model.Author;
import com.assessment.library.model.Book;
import com.assessment.library.service.BookService;

@SpringBootApplication //(exclude = {SecurityAutoConfiguration.class })
public class LibraryServiceApplication implements CommandLineRunner {

	@Autowired
	BookService bookService;
	
	public static void main(String[] args) {
		SpringApplication.run(LibraryServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Author author = new Author(221l, "Kathy Sierra", null);
		List<Author> authors = new ArrayList<Author>();
		authors.add(author);
		Book book = new Book(1234567l, "ISBNA12", "Head First Java", new BigDecimal(1500.00), "Java Programming book", authors);
		bookService.addBook(book);
		
		Author author1 = new Author(231l, "John Sierra", null);
		List<Author> authors1 = new ArrayList<Author>();
		authors1.add(author1);
		Book book1 = new Book(1234568l, "ISBNB13", " First Java", new BigDecimal(1500.00), "Java Programming book", authors1);
		bookService.addBook(book1);
	}

}

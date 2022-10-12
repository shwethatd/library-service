package com.assessment.library.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.library.model.Author;
import com.assessment.library.model.Book;
import com.assessment.library.service.BookService;

@RestController
@RequestMapping("/api")
public class BookController {

	@Autowired
	BookService bookService;

	// add new book
	@RequestMapping(value = "/book", method = RequestMethod.POST, consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Book addBook(@RequestBody Book newBook) {
		return bookService.addBook(newBook);
	}

	// update book name only
	@PatchMapping("/book/name/{id}")
	public Book updateBookName(@RequestBody Map<String, String> update, @PathVariable Long id) {

		return bookService.updateBookName(update, id);

	}

	// Find
	@GetMapping("/book/{id}")
	public Book findOne(@PathVariable Long id) {
		return bookService.findBook(id);
	}

	// Find
	@GetMapping("/book")
	public List<Book> findAll(@RequestParam String sortBy, @RequestParam String direction) {
		return bookService.findAll(sortBy,direction);
	}

	// update author only
	@PatchMapping("/book/author/{id}")
	public Book updateAuthor(@RequestBody List<Author> authors, @PathVariable Long id) {

		return bookService.updateAuthors(authors, id);

	}

	// delete book
	@DeleteMapping("/book/{id}")
	public void deleteBook(@PathVariable Long id) {
		bookService.deleteBook(id);

	}
}

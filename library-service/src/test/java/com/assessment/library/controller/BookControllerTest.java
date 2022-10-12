package com.assessment.library.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.assessment.library.model.Author;
import com.assessment.library.model.Book;
import com.assessment.library.service.BookService;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

	@InjectMocks
	BookController bookController = new BookController();
	@Mock
	BookService bookService;

	static Book book;
	static List<Author> authors;

	@BeforeAll
	public static void init() {
		//MockitoAnnotations.openMocks(this);

		Author author = new Author(221l, "Kathy Sierra", null);
		authors = new ArrayList<Author>();
		authors.add(author);
		book = new Book(1234567l, "ISBNA12", "Head First Java", new BigDecimal(1500.00), "Java Programming book",
				authors);

	}

	@Test
	public void addBookTest() {

		Mockito.when(bookService.addBook(Mockito.any())).thenReturn(book);
		Book newBook = bookController.addBook(book);
		assertEquals("Head First Java", newBook.getName());

	}
	
	@Test
	public void updateBookNameTest() {
		Map<String, String> update = new HashMap<String,String>();
		update.put("bookName", "Java in Action");
		
		Mockito.when(bookService.updateBookName(Mockito.anyMap(),Mockito.anyLong())).thenReturn(book);
		Book newBook = bookController.updateBookName(update,1234567l);
		assertEquals("Head First Java", newBook.getName());
	}

	@Test
	public void findBookTest() {
		Mockito.when(bookService.findBook(Mockito.anyLong())).thenReturn(book);
		Book newBook = bookController.findOne(1234567l);
		assertEquals(1234567, newBook.getId());
	}
	
	@Test
	public void findAllTest() {
		List<Book> books =new ArrayList<Book>();
		books.add(book);
		Mockito.when(bookService.findAll(Mockito.anyString(),Mockito.anyString())).thenReturn(books);
		List<Book> booksList = bookController.findAll("name","ASC");
		assertEquals(1, booksList.size());
	}
	
	@Test
	public void deleteBookTest() {
		Mockito.doNothing().when(bookService).deleteBook(Mockito.anyLong());
		bookController.deleteBook(1234567l);

	}
}

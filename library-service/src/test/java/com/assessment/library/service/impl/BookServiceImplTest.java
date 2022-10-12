package com.assessment.library.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import com.assessment.library.exception.BadRequestException;
import com.assessment.library.exception.BookNotFoundException;
import com.assessment.library.model.Author;
import com.assessment.library.model.Book;
import com.assessment.library.repository.BookRepository;
import com.assessment.library.service.BookService;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

	@InjectMocks
	BookService bookService = new BookServiceImpl();

	@Mock
	BookRepository repository;

	static Book book;

	static List<Author> authors;

	@BeforeAll
	public static void init() {
		Author author = new Author(221l, "Kathy Sierra", null);
		authors = new ArrayList<Author>();
		authors.add(author);
		book = new Book(1234567l, "ISBNA12", "Head First Java", new BigDecimal(1500.00), "Java Programming book",
				authors);

	}

	@Test
	public void updateBookName() {

		Book b1 = new Book(1234567l, "ISBNA12", "Java 8 in Action", new BigDecimal(1500.00), "Java Programming book",
				authors);
		Map<String, String> update = new HashMap<String,String>();
		update.put("name", "Java 8 in Action");
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(book));
		Mockito.when(repository.save(Mockito.any())).thenReturn(b1);
		Book newBook= bookService.updateBookName(update, 1234567l);
		assertEquals("Java 8 in Action", newBook.getName());

	}
	
	@Test
	public void updateBookNameNotFound() {

		Map<String, String> update = new HashMap<String,String>();
		update.put("name", "Java 8 in Action");
		Mockito.when(repository.findById(Mockito.anyLong())).thenThrow( new BookNotFoundException("Not Found Book id " + book.getId()));
		assertThrows(BookNotFoundException.class,() -> bookService.updateBookName(update, 1234567l));

	}

	@Test
	public void findBook() {
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(book));
		Book newBook= bookService.findBook(Mockito.anyLong());
		assertEquals("ISBNA12", newBook.getIsbn());
	}

	@Test
	public void findBookeNotFound() {
		Mockito.when(repository.findById(Mockito.anyLong())).thenThrow( new BookNotFoundException("Not Found Book id " + book.getId()));
		assertThrows(BookNotFoundException.class,() -> bookService.findBook(1234567l));

	}
	
	@Test
	public void findAll() {
		List<Book> books =new ArrayList<Book>();
		books.add(book);
		Mockito.when(repository.findAll()).thenReturn(books);
		List<Book> booksList = bookService.findAll(null, null);
		assertEquals(1, booksList.size());

	}
	
	@Test
	public void findAllSorted() {
		List<Book> books =new ArrayList<Book>();
		books.add(book);
		Sort sort = Sort.by("name").ascending();
				
		Mockito.when(repository.findAll(sort)).thenReturn(books);
		List<Book> booksList = bookService.findAll("name", "ASC");
		assertEquals(1, booksList.size());

	}

	@Test
	public void addBook() {
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(book));
		Mockito.when(repository.save(Mockito.any())).thenReturn(book);
		Book newBook = bookService.addBook(book);
		assertEquals("ISBNA12", newBook.getIsbn());
	}
	
	@Test
	public void updateAuthors() {
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(book));
		Mockito.when(repository.save(Mockito.any())).thenReturn(book);

		List<Author> authors = new ArrayList<Author>();
		Author author= new Author(443l,"Vekat Subramanyam",null);
		authors.add(author);
		 Book updatedBook=bookService.updateAuthors(authors, 1234567l);
		assertEquals("Vekat Subramanyam", updatedBook.getAuthors().get(0).getName());
	}
	
	@Test
	public void updateAuthorsBadRequest() {
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(book));

		List<Author> authors = new ArrayList<Author>();
		 assertThrows(BadRequestException.class,() -> bookService.updateAuthors(authors, 1234567l));
	}
	
	@Test
	public void updateAuthorseNotFound() {

		List<Author> authors = new ArrayList<Author>();
		Author author= new Author(443l,"Vekat Subramanyam",null);
		authors.add(author);
		Mockito.when(repository.findById(Mockito.anyLong())).thenThrow( new BookNotFoundException("Not Found Book id " + book.getId()));
		assertThrows(BookNotFoundException.class,() -> bookService.updateAuthors(authors, 1234567l));

	}
	
	@Test
	public void deleteBook() {

		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(book));
		Mockito.doNothing().when(repository).deleteById(Mockito.anyLong());
		bookService.deleteBook(1234567l);

	}
	
	@Test
	public void deleteBookNotFound() {

		Mockito.when(repository.findById(Mockito.anyLong())).thenThrow( new BookNotFoundException("Not Found Book id " + book.getId()));
		assertThrows(BookNotFoundException.class,() -> bookService.deleteBook(1234567l));


	}
}

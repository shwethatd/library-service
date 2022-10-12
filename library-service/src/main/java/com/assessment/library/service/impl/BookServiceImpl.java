package com.assessment.library.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.assessment.library.exception.BadRequestException;
import com.assessment.library.exception.BookNotFoundException;
import com.assessment.library.model.Author;
import com.assessment.library.model.Book;
import com.assessment.library.repository.BookRepository;
import com.assessment.library.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;

	@Override
	public Book addBook(Book newBook) {
		return bookRepository.findById(newBook.getId()).map(x -> {
			x.setName(newBook.getName());
			x.setAuthors(newBook.getAuthors());
			x.setPrice(newBook.getPrice());
			return bookRepository.save(x);
		}).orElseGet(() -> {
			return bookRepository.save(newBook);
		});
	}

	@Override
	public Book updateBookName(Map<String, String> update, Long id) {
		return bookRepository.findById(id).map(x -> {

			String name = update.get("bookName");
			if (!StringUtils.isEmpty(name)) {
				x.setName(name);

				return bookRepository.save(x);
			} else {
				throw new BadRequestException("Bad Request");
			}

		}).orElseGet(() -> {
			throw new BookNotFoundException("Not Found Book id " + id);
		});

	}

	@Override
	public Book findBook(Long id) {
		return bookRepository.findById(id)
				.orElseThrow(() -> new BookNotFoundException("Book not found with ID  " + id));
	}

	@Override
	public List<Book> findAll(String sortBy, String direction) {
		if (sortBy != null && (sortBy.equalsIgnoreCase("name") || sortBy.equalsIgnoreCase("authors"))) {

			Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
					: Sort.by(sortBy).descending();
			return bookRepository.findAll(sort);
		} else {

			return bookRepository.findAll();
		}
	}

	@Override
	public Book updateAuthors(List<Author> authors, Long id) {
		return bookRepository.findById(id).map(x -> {
			if (!CollectionUtils.isEmpty(authors)) {
				x.setAuthors(authors);
				return bookRepository.save(x);
			} else {
				throw new BadRequestException("Bad Request");
			}

		}).orElseGet(() -> {
			throw new BookNotFoundException("Book not found with ID  " + id);
		});

	}

	@Override
	public void deleteBook(Long id) {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new BookNotFoundException(String.format("Book not found with ID %d", id)));

		bookRepository.deleteById(book.getId());

	}

}

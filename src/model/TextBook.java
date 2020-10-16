package model;

import java.io.Serializable;
import java.util.Arrays;

public class TextBook implements Serializable {

	private String bookTitle;
	private String isbn;
	private Name[] authors;
	private double price;

	public TextBook(String bookTitle, String isbn, Name[] authors, double price) {
		super();
		this.bookTitle = bookTitle;
		this.isbn = isbn;
		this.authors = authors;
		this.price = price;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Name[] getAuthors() {
		return authors;
	}

	public void setAuthor(Name[] authors) {
		this.authors = authors;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "TextBook [bookTitle=" + bookTitle + ", isbn=" + isbn + ", authors=" + Arrays.toString(authors)
				+ ", price=" + price + "]";
	}

}

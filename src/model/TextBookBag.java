package model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.function.Predicate;

public class TextBookBag implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8944913004237146023L;
	private TextBook[] textBookArray;
	private int nElems;

	public TextBookBag(int maxSize) {

		textBookArray = new TextBook[maxSize];
	}

	public void insert(TextBook textbook) {

		textBookArray[nElems++] = textbook;
	}

	public TextBook findByIsbn(String isbn) {
		for (int i = 0; i < nElems; i++) {

			if (textBookArray[i].getIsbn().contentEquals(isbn)) {

				return textBookArray[i];

			}
		}
		return null;

	}

	public TextBook removeByIsbn(String isbn) {
		int matchCount = 0;
		int i;
		for (i = 0; i < nElems; i++) {

			if (textBookArray[i].getIsbn().contentEquals(isbn)) {

				break;
			}
		}

		if (i == nElems) {
			return null;
		}

		TextBook temp = textBookArray[i];
		for (int j = i; j < nElems; j++) {

			textBookArray[j] = textBookArray[j + 1];

		}
		nElems--;
		return temp;
	}

	public TextBook[] getTextBookArray() {
		return textBookArray;
	}

	public void setTextBookArray(TextBook[] textBookArray) {
		this.textBookArray = textBookArray;
	}

	public int getnElems() {
		return nElems;
	}

}

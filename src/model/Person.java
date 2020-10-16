package model;

import java.io.Serializable;

import javafx.scene.control.Button;

public abstract class Person implements Serializable {

	private Name name;
	private String id;

	private static int idCounter = 1;

	public Person(Name name) {
		this.name = name;
		id = String.format("%08d", idCounter++);
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {

		this.id = id;
	}

}

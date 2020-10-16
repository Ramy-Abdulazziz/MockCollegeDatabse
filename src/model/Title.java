package model;

import java.io.Serializable;

public enum Title implements Serializable {

	PROF, ASSOCIATE_PROF, ASSISTANT_PROF, INSTRUCTOR;

	public static Title getEnum(String string) {

		switch (string) {

		case "PROF":
			return PROF;
		case "ASSOCIATE PROF":
			return ASSOCIATE_PROF;
		case "ASSISTANT PROF":
			return ASSISTANT_PROF;
		case "INSTRUCTOR":
			return INSTRUCTOR;
		}
		return null;
	}

	public String toString() {
		switch (this) {
		case PROF:
			return "PROF";

		case ASSOCIATE_PROF:
			return "ASSOCIATE PROF";
		case ASSISTANT_PROF:
			return "ASSISTANT PROF";
		case INSTRUCTOR:
			return "INSTRUCTOR";

		}
		return null;
	}
}

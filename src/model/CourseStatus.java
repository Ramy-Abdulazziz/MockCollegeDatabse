package model;

import java.io.Serializable;

public enum CourseStatus implements Serializable{
	TAKEN, TAKING, TO_TAKE, TAUGHT, TEACHING;

	public static CourseStatus getEnum(String statusStr) {
		
		switch (statusStr) {

		case "TAKEN":
			return TAKEN;
		case "TAKING":
			return TAKING;
		case "TO TAKE":
			return TO_TAKE;
		case "TAUGHT":
			return TAUGHT;
		case "TEACHING":
			return TEACHING;
		}
		return null;
		
		
		
	}
}

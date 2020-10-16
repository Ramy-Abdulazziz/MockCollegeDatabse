package helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class FileReadUtilities {

	public static String[] readFile(String fileName) throws FileNotFoundException {

		File file = new File(fileName);
		Scanner scanner = new Scanner(file);
		String[] array = new String[40000];
		int count = 0;
		while (scanner.hasNextLine()) {

			String string = scanner.nextLine();
			array[count++] = string;

		}
		String[] data = Arrays.copyOf(array, count);
		scanner.close();
		return data;

	}
}
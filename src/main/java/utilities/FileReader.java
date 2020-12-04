package utilities;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileReader {
  public static List<String> readToList(String fileName) {
    List<String> input = new ArrayList<>();

    try {
      InputStream resource = new ClassPathResource(fileName).getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(resource));
      input = reader.lines()
          .collect(Collectors.toList());
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    return input;
  }

  public static List<String> readToListSeparatedByEmptyLine(String fileName) {
    List<String> input = new ArrayList<>();

    try {
      InputStream resource = new ClassPathResource(fileName).getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(resource));

      String currentLine = null;
      StringBuilder passport = new StringBuilder();
      while ((currentLine = reader.readLine()) != null) {
        if(!"".equals(currentLine)) {
          passport.append(currentLine);
          passport.append(" ");
        }
        else {
          input.add(passport.toString());
          passport = new StringBuilder();
        }
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    return input;
  }

}

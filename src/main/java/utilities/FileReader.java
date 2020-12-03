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

}

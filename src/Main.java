import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        List<File> folderList = Arrays.asList(
                new File("C://Games//src"),
                new File("C://Games//res"),
                new File("C://Games//savegames"),
                new File("C://Games//temp"),
                new File("C://Games//src//main"),
                new File("C://Games//src//test"),
                new File("C://Games//res//drawables"),
                new File("C://Games//res//vectors"),
                new File("C://Games//res//icons")
        );
        List<File> fileList = Arrays.asList(
                new File("C://Games//src//main//Main.java"),
                new File("C://Games//src//main//Utils.java"),
                new File("C://Games//temp//temp.txt")
        );

        folderList.stream().forEach(folder -> {
            if (folder.mkdir()) {
                folder.setLastModified(System.currentTimeMillis());
                Path file1 = Paths.get(folder.toURI());
                BasicFileAttributes attr = null;
                try {
                    attr = Files.readAttributes(file1, BasicFileAttributes.class);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                FileTime lastModifiedTime = attr.lastModifiedTime();
                sb.append("\nКаталог " + folder + " создан\n" + lastModifiedTime);

            } else sb.append("Каталог " + folder + " не создан\n");
        });
        fileList.stream().forEach(file -> {
            try {
                if (file.createNewFile()) {
                    file.setLastModified(System.currentTimeMillis());
                    Path pf1 = Paths.get(file.toURI());
                    BasicFileAttributes attr1 = Files.readAttributes(pf1, BasicFileAttributes.class);
                    FileTime lastModifiedTime1 = attr1.lastModifiedTime();
                    sb.append("\nФайл " + file + " создан\n" + lastModifiedTime1);
                } else sb.append("Файл " + file + " не создан\n");
            } catch (IOException ex) {
                sb.append(ex.getMessage() + '\n');
            }
        });
        try (FileWriter log = new FileWriter("C://Games//temp//temp.txt", false)) {
            log.write(sb.toString());
            log.flush();
        } catch (IOException ex) {
            sb.append(ex.getMessage() + '\n');
        }
        try (BufferedReader br = new BufferedReader(new FileReader("C://Games//temp//temp.txt"))) {
            String s;
            while ((s = br.readLine()) != null) System.out.println(s);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
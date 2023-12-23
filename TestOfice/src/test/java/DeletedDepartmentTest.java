import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class DeletedDepartmentTest {

    @BeforeEach
    public void setUp(){
    }

    @Test
    public void testDeleteDeparment(){
        String jarPath = "C:/_GIT/JDBC_Project/JDBC_Project/target/JDBC_Project-1.0-SNAPSHOT.jar";
        ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", jarPath);
        processBuilder.redirectErrorStream(true); // Перенаправляем stderr в stdout

        try {
            Process process = processBuilder.start();

            // Чтение вывода приложения для ассертов или логирования
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                line = reader.readLine();
                output.append(line);
                InputStream in = new ByteArrayInputStream("4n".getBytes());
                System.setIn(in);
                in = new ByteArrayInputStream("1n".getBytes());
                System.setIn(in);
            }
                // Имитируем System.in с вводом "1" + псевдонажатием Enter (новая строка)

                System.out.println(new String(line.getBytes(), "UTF-8"));
                System.setIn(System.in);

            // Ожидание завершения процесса и проверка кода выхода
            int exitCode = process.waitFor();

            // Здесь можно добавить какие-либо ассерты по содержимому output

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            fail("Произошла ошибка во время выполнения процесса");
        }
    }


    @AfterEach
    public void tearDown() {

    }
}

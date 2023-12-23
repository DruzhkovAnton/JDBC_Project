import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.fail;


public class DeletedDepartmentTest {
    private Process app;
    @BeforeEach
        public void setUp() throws Exception {
            // Запустите приложение
         app = Runtime.getRuntime().exec("java -jar JDBC_Project-1.0-SNAPSHOT.jar");
            // Подключение к базе данных
        dbcon.creatDb();
    }

    @Test
    public void testDeleteDeparment() throws InterruptedException {
        int departmentIdToDelete = 2;
        int rowEmployee2 = 0;
        int rowEmployee1 = 0;
        rowEmployee1 = dbcon.areEmployeesPresent();
        // Удалите деп
        dbcon.deleteDepartment(departmentIdToDelete);

        // Подождите некоторое время для обработки
        Thread.sleep(5000);

        rowEmployee2 = dbcon.areEmployeesPresent();

        // Выполните проверку содержимого базы
        Assertions.assertEquals(rowEmployee1,rowEmployee2+1);
    }




    @AfterEach
    public void tearDown() throws SQLException, InterruptedException {
    app.destroy();
    app.waitFor();
    }
}

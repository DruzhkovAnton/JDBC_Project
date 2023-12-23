import java.sql.*;

public class dbcon {

    public static void creatDb() {

            try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
                Statement stm = con.createStatement();
                stm.executeUpdate("DROP TABLE Department IF EXISTS");
                stm.executeUpdate("CREATE TABLE Department(ID INT PRIMARY KEY, NAME VARCHAR(255))");
                stm.executeUpdate("INSERT INTO Department VALUES(1,'Accounting')");
                stm.executeUpdate("INSERT INTO Department VALUES(2,'IT')");
                stm.executeUpdate("INSERT INTO Department VALUES(3,'HR')");

                stm.executeUpdate("DROP TABLE Employee IF EXISTS");
                stm.executeUpdate("CREATE TABLE Employee(ID INT PRIMARY KEY, NAME VARCHAR(255), DepartmentID INT)");
                stm.executeUpdate("INSERT INTO Employee VALUES(1,'Pete',1)");
                stm.executeUpdate("INSERT INTO Employee VALUES(2,'Ann',1)");

                stm.executeUpdate("INSERT INTO Employee VALUES(3,'Liz',2)");
                stm.executeUpdate("INSERT INTO Employee VALUES(4,'Tom',2)");

                stm.executeUpdate("INSERT INTO Employee VALUES(5,'Todd',3)");

            } catch (SQLException e) {
                System.out.println(e);

        }
    }

    public static void deleteDepartment(int departmentIdToDelete) {
        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            System.out.println("------------------------------------");
            System.out.println("удаляем департамент с ид = "+departmentIdToDelete);
            PreparedStatement stm = con.prepareStatement("DELETE FROM Department WHERE ID=?");
            stm.setInt(1, departmentIdToDelete);
            stm.executeUpdate();
            System.out.println("------------------------------------");
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static  int areEmployeesPresent() {
        int result = 0;
        try(Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")){
            Statement stm = con.createStatement();
            ResultSet rs= stm.executeQuery("Select Employee.ID, Employee.Name,Department.Name as DepName from Employee left join Department on Employee.DepartmentID=Department.ID");
            //ResultSet rs= stm.executeQuery("Select Employee.ID, Employee.Name,Employee.DepartmentID as DepName from Employee");
            System.out.println("------------------------------------");
            ResultSetMetaData metaData= rs.getMetaData();
            while(rs.next()){
                result++;
                System.out.println(rs.getInt("ID")+"\t"+rs.getString("NAME")+"\t"+rs.getString("DepName"));
            }
            System.out.println("------------------------------------");
        }catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }
}



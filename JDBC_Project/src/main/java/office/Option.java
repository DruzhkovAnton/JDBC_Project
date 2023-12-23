package office;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.Scanner;

public enum Option {
    ExOne {
        String getText() {
            return this.ordinal() + ".Выполнить шаги по заданию 1";
        }

        void action() {
            PreparedStatement pstmtSelect = null;
            PreparedStatement pstmtUpdate = null;
            PreparedStatement pstmtCountIT = null;
            ResultSet rs = null;
            int countUpdated = 0;
            try(Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")){
                String selectSQL = "SELECT id FROM Employee WHERE Name = ?";
                pstmtSelect = con.prepareStatement(selectSQL);
                pstmtSelect.setString(1, "Ann");

                rs= pstmtSelect.executeQuery();
                System.out.println("------------------------------------");
                if (rs.next() && rs.isLast()) {
                    int employeeId = rs.getInt("ID");
                    String updateSQL = "UPDATE Employee SET DepartmentID = ? WHERE id = ?";
                    selectSQL = "SELECT ID FROM Department WHERE NAME = 'HR'";
                    //pstmtSelect = con.prepareStatement(selectSQL);
                    rs= con.createStatement().executeQuery(selectSQL);
                    rs.next();
                    int hr = rs.getInt("ID");
                    pstmtUpdate = con.prepareStatement(updateSQL);
                    pstmtUpdate.setInt(1, hr);
                    pstmtUpdate.setInt(2, employeeId);

                    pstmtUpdate.executeUpdate();
                    System.out.println("Департамент для сотрудника Ann - ID = " + employeeId + " было обнавлено на HR.");

                    selectSQL = "SELECT ID, Name FROM employee";
                    pstmtSelect = con.prepareStatement(selectSQL);
                    rs = pstmtSelect.executeQuery();
                    while (rs.next()) {
                        employeeId = rs.getInt("ID");
                        String name = rs.getString("Name");
                        if (Character.isLowerCase(name.charAt(0))) {
                            String updatedName = Character.toUpperCase(name.charAt(0)) + name.substring(1);
                            updateSQL = "UPDATE employee SET Name = ? WHERE id = ?";
                            pstmtUpdate = con.prepareStatement(updateSQL);
                            pstmtUpdate.setString(1, updatedName);
                            pstmtUpdate.setInt(2, employeeId);

                            pstmtUpdate.executeUpdate();
                            countUpdated++;
                            System.out.println("Колличество исправленных имен: " + countUpdated);

                        }

                    }
                    String countITSQL = "SELECT COUNT(*) AS total FROM employee WHERE DepartmentID in (SELECT ID FROM Department WHERE NAME = 'IT')";
                    pstmtCountIT = con.prepareStatement(countITSQL);
                    ResultSet rsCountIT = pstmtCountIT.executeQuery();

                    if (rsCountIT.next()) {
                        int countIT = rsCountIT.getInt("total");
                        System.out.println("Колличество сотрудников в ИТ: " + countIT);
                    }
                }
                System.out.println("------------------------------------");
            }catch (SQLException e) {
                System.out.println(e);
            }

        }
    },
    AddEmployee {
        String getText() {
            return this.ordinal() + ".Добавить сотрудника";
        }

        void action() {
            System.out.println("Введите его id:");
            int id=sc.nextInt();
            System.out.println("Введите его имя:");
            String name=sc.next();
            System.out.println("Введите id отдела:");
            int depid=sc.nextInt();
            Service.addEmployee(new Employee(id,name,depid));
        }
    },
    DeleteEmployee {
        String getText() {
            return this.ordinal() + ".Удалить сотрудника";
        }

        void action() {
            System.out.println("Введите его id:");
            int id=sc.nextInt();
            Service.removeEmployee(new Employee(id,"",0));
        }
    },
    AddDepartment {
        String getText() {
            return this.ordinal() + ".Добавить отдел";
        }

        void action() {
            System.out.println("Введите его id:");
            int id=sc.nextInt();
            System.out.println("Введите его название:");
            String name=sc.next();
            Service.addDepartment(new Department(id,name));
        }
    },
    DeleteDepartment {
        String getText() {
            return this.ordinal() + ".Удалить отдел";
        }

        void action() {
            System.out.println("Введите его id:");
            int id=sc.nextInt();
            Service.removeDepartment(new Department(id,""));
        }
    },
    CLEAR_DB {
        String getText() {
            return this.ordinal() + ".Сбросить базу данных";
        }

        void action() {
            Service.createDB();
        }

    },
    PRINT_DEPS {
        String getText() {
            return this.ordinal() + ".Вывести на экран все отделы";
        }

        void action() {
            try(Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")){
                PreparedStatement stm = con.prepareStatement(
                        //"Select ID, NAME as txt from Department",
                        "Select ID, NAME as txt from Department where name like ?",
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE
                );
                String str="%";
                //ResultSet rs= stm.executeQuery("Select ID, NAME as txt from Department");
                stm.setString(1,str);
                ResultSet rs=stm.executeQuery();
                System.out.println("------------------------------------");
                while(rs.next()){
                    System.out.println(rs.getInt("ID")+"\t"+rs.getString("name"));
                }
                System.out.println("------------------------------------");
            }catch (SQLException e) {
                System.out.println(e);
            }
        }
    },
    PRINT_EMPLOYEES {
        String getText() {
            return this.ordinal() + ".Вывести на экран всех сотрудников";
        }

        void action() {
            try(Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")){
                Statement stm = con.createStatement();
                ResultSet rs= stm.executeQuery("Select Employee.ID, Employee.Name,Department.Name as DepName from Employee join Department on Employee.DepartmentID=Department.ID");
                //ResultSet rs= stm.executeQuery("Select Employee.ID, Employee.Name,Employee.DepartmentID as DepName from Employee");
                System.out.println("------------------------------------");
                ResultSetMetaData metaData= rs.getMetaData();
                while(rs.next()){
                    System.out.println(rs.getInt("ID")+"\t"+rs.getString("NAME")+"\t"+rs.getString("DepName"));
                }
                System.out.println("------------------------------------");
            }catch (SQLException e) {
                System.out.println(e);
            }
        }   
    },
    EXIT {
        String getText() {
            return this.ordinal() + ".Выход";
        }

        void action() {
            System.out.println("выход");
        }
    },;
    
    Scanner sc = new Scanner(System.in);
    abstract String getText();
    abstract void action();
}

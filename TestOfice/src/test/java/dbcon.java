import java.sql.*;

public class dbcon {
    public static void selectFromDepartment(){
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

}

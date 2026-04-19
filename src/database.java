import java.sql.Connection;
import java.sql.DriverManager;

public class database {
    public static Connection connect(){
        try{
            return DriverManager.getConnection("jdbc:sqlite:C:\\Users\\БТ\\Documents\\movies");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}

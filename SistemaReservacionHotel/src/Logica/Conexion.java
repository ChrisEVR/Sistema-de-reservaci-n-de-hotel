package Logica;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {
    public String db = "basereserva";
    public String url = "jdbc:mysql://localhost:3306/" + db + "?useLegacyDatetimeCode=false&serverTimezone=UTC";
    public String user = "root";
    public String pass = "36515255aA@!";
    
    public Conexion()
    {
        
    }
    
    public Connection conectar()
    {
        Connection link = null;
        
        try
        {
            Class.forName("org.gjt.mm.mysql.Driver");
            link = DriverManager.getConnection(this.url, this.user, this.pass);
            if(link!=null){
                System.out.println("conexion exitosa");
            }
        }
        catch(ClassNotFoundException | SQLException e) 
        {
            JOptionPane.showMessageDialog(null,e,"Error",JOptionPane.ERROR_MESSAGE);
        }
        
        return link;
    }
}

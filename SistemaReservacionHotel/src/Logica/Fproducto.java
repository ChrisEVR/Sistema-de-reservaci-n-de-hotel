package Logica;

import Datos.Vhabitacion;
import Datos.Vproducto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Fproducto {
    
    private Conexion mysql = new Conexion();
    private Connection cn = mysql.conectar();
    private String sSQL = "";
    public Integer totalRegistros;
    
    public DefaultTableModel mostrar(String buscar)
    {
        DefaultTableModel modelo;
        
        String [] titulos = {"ID", "Producto", "Descripción", "Unidad de Medida", "Precio de Venta"};
        String [] registro = new String [5];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);
        
        sSQL = "SELECT * FROM producto WHERE nombre LIKE '%" + buscar + "%' ORDER BY idproducto DESC";
        
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            
            while(rs.next())
            {
                registro [0] = rs.getString("idproducto");
                registro [1] = rs.getString("nombre");
                registro [2] = rs.getString("descripcion");
                registro [3] = rs.getString("unidad_medida");
                registro [4] = rs.getString("precio_venta");
                
                totalRegistros++;
                modelo.addRow(registro);
            }
            return modelo;
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, e,"Error",JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public boolean insertar (Vproducto dts)
    {
        sSQL = "INSERT INTO producto (nombre, descripcion, unidad_medida, precio_venta)" + 
                "VALUES (?, ?, ?, ?)";
        try 
        {
            
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setString(1, dts.getNombre());
            pst.setString(2, dts.getDescripcion());
            pst.setString(3, dts.getUnidad_medida());
            pst.setDouble(4, dts.getPrecio_venta());
            
            int n = pst.executeUpdate();
            
            if(n != 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, e,"Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean editar (Vproducto dts)
    {
        sSQL = "UPDATE producto SET nombre = ?, descripcion = ?, unidad_medida = ?, precio_venta = ? " +
                "WHERE idproducto = ?";
        
        try 
        {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setString(1, dts.getNombre());
            pst.setString(2, dts.getDescripcion());
            pst.setString(3, dts.getUnidad_medida());
            pst.setDouble(4, dts.getPrecio_venta());
            pst.setInt(5, dts.getIdproducto());
            
            int n = pst.executeUpdate();
            
            if(n != 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, e,"Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean eliminar (Vproducto dts)
    {
        sSQL = "DELETE FROM producto WHERE idproducto = ?";
        try 
        {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            
            pst.setInt(1, dts.getIdproducto());
            
            int n = pst.executeUpdate();
            
            if(n != 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, e,"Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}

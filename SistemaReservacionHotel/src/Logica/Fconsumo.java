package Logica;

import Datos.Vconsumo;
import Datos.Vhabitacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Fconsumo {
    
    private Conexion mysql = new Conexion();
    private Connection cn = mysql.conectar();
    private String sSQL = "";
    public Integer totalRegistros;
    public Double totalConsumo;
    
    public DefaultTableModel mostrar(String buscar)
    {
        DefaultTableModel modelo;
        
        String [] titulos = {"ID", "Idreserva", "Idproducto", "Producto", "Cantidad", "Precio de Venta", "Estado"};
        
        String [] registro = new String [7];
        
        totalRegistros = 0;
        totalConsumo = 0.0;
        
        modelo = new DefaultTableModel(null, titulos);
        
        sSQL = "SELECT c.idconsumo, c.idreserva, c.idproducto, p.nombre, c.cantidad, c.precio_venta, "
                + "c.estado FROM consumo c INNER JOIN producto p ON c.idproducto = p.idproducto"
                + " WHERE c.idreserva = " + buscar + " ORDER BY c.idconsumo DESC";
        
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            
            while(rs.next())
            {
                registro [0] = rs.getString("idconsumo");
                registro [1] = rs.getString("idreserva");
                registro [2] = rs.getString("idproducto");
                registro [3] = rs.getString("nombre");
                registro [4] = rs.getString("cantidad");
                registro [5] = rs.getString("precio_venta");
                registro [6] = rs.getString("estado");
                
                totalRegistros++;
                totalConsumo = totalConsumo + (rs.getDouble("cantidad") * rs.getDouble("precio_venta"));
                
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
    
    public boolean insertar (Vconsumo dts)
    {
        sSQL = "INSERT INTO consumo (idreserva, idproducto, cantidad, precio_venta, estado)" + 
                " VALUES (?, ?, ?, ?, ?)";
        try 
        {
            
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, dts.getIdreserva());
            pst.setInt(2, dts.getIdproducto());
            pst.setDouble(3, dts.getCantidad());
            pst.setDouble(4, dts.getPrecio_venta());
            pst.setString(5, dts.getEstado());
            
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
    
    public boolean editar (Vconsumo dts)
    {
        sSQL = "UPDATE consumo SET idreserva = ?, idproducto = ?, cantidad = ?, precio_venta = ?, estado = ?" +
                " WHERE idconsumo = ?";
        
        try 
        {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, dts.getIdreserva());
            pst.setInt(2, dts.getIdproducto());
            pst.setDouble(3, dts.getCantidad());
            pst.setDouble(4, dts.getPrecio_venta());
            pst.setString(5, dts.getEstado());
            pst.setInt(6, dts.getIdconsumo());
            
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
    
    public boolean eliminar (Vconsumo dts)
    {
        sSQL = "DELETE FROM consumo WHERE idconsumo = ?";
        try 
        {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            
            pst.setInt(1, dts.getIdconsumo());
            
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

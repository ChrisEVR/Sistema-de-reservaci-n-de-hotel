package Logica;

import Datos.Vhabitacion;
import Datos.Vpago;
import Datos.Vproducto;
import Datos.Vreserva;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Fpago {
    
    private Conexion mysql = new Conexion();
    private Connection cn = mysql.conectar();
    private String sSQL = "";
    public Integer totalRegistros;
    
    public DefaultTableModel mostrar(String buscar)
    {
        DefaultTableModel modelo;
        
        String [] titulos = {"ID", "Idreserva", "Comprobante", "Número", "IVA", "Total", "Fecha de emisión", "Fecha de Pago"};
        
        String [] registro = new String [8];
        
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);
        
        sSQL = "SELECT * FROM pago WHERE idreserva =" + buscar + " ORDER BY idpago DESC";
        
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            
            while(rs.next())
            {
                registro [0] = rs.getString("idpago");
                registro [1] = rs.getString("idreserva");
                registro [2] = rs.getString("tipo_comprobante");
                registro [3] = rs.getString("num_comprobante");
                registro [4] = rs.getString("iva");
                registro [5] = rs.getString("total_pago");
                registro [6] = rs.getString("fecha_emision");
                registro [7] = rs.getString("fecha_pago");
                
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
    
    public boolean insertar (Vpago dts)
    {
        sSQL = "INSERT INTO pago (idreserva, tipo_comprobante, num_comprobante, iva, total_pago, fecha_emision, fecha_pago) " + 
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try 
        {
            
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, dts.getIdreserva());
            pst.setString(2, dts.getTipo_comprobante());
            pst.setString(3, dts.getNum_comprobante());
            pst.setDouble(4, dts.getIVA());
            pst.setDouble(5, dts.getTotal_pago());
            pst.setDate(6, dts.getFecha_emision());
            pst.setDate(7, dts.getFecha_pago());
            
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
    
    public boolean editar (Vpago dts)
    {
        sSQL = "UPDATE pago SET idreserva = ?, tipo_comprobante = ?, num_comprobante = ?, iva = ?, total_pago = ?, fecha_emision = ?, fecha_pago = ? " +
                "WHERE idpago = ?";
        
        try 
        {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, dts.getIdreserva());
            pst.setString(2, dts.getTipo_comprobante());
            pst.setString(3, dts.getNum_comprobante());
            pst.setDouble(4, dts.getIVA());
            pst.setDouble(5, dts.getTotal_pago());
            pst.setDate(6, dts.getFecha_emision());
            pst.setDate(7, dts.getFecha_pago());
            pst.setInt(8, dts.getIdpago());
            
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
    
    public boolean eliminar (Vpago dts)
    {
        sSQL = "DELETE FROM pago WHERE idpago = ?";
        try 
        {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            
            pst.setInt(1, dts.getIdpago());
            
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

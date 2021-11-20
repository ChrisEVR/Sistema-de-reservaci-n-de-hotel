package Logica;

import Datos.Vhabitacion;
import Datos.Vproducto;
import Datos.Vreserva;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Freserva {
    
    private Conexion mysql = new Conexion();
    private Connection cn = mysql.conectar();
    private String sSQL = "";
    public Integer totalRegistros;
    
    public DefaultTableModel mostrar(String buscar)
    {
        DefaultTableModel modelo;
        
        String [] titulos = {"ID", "Idhabitacion", "Número", "Idcliente", "Cliente", "Idtrabajador", "Trabajador", "Tipo de Reserva", "Fecha de Reserva", "Fecha de Ingreso", "Fecha de Salida", "Costo", "Estado"};
        String [] registro = new String [13];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);
        
        sSQL = "SELECT r.idreserva, r.idhabitacion, h.numero, r.idcliente, "
                + "(SELECT nombre FROM persona WHERE idpersona = r.idcliente) AS clienten, "
                + "(SELECT apaterno FROM persona WHERE idpersona = r.idcliente) AS clienteap, "
                + "r.idtrabajador, (SELECT nombre FROM persona WHERE idpersona = r.idtrabajador) AS trabajadorn, "
                + "(SELECT apaterno FROM persona WHERE idpersona = r.idtrabajador) AS trabajadorap, "
                + "r.tipo_reserva, r.fecha_reserva, r.fecha_ingresa, r.fecha_salida, "
                + "r.costo_alojamiento, r.estado FROM reserva r INNER JOIN habitacion h ON r.idhabitacion = h.idhabitacion WHERE r.fecha_reserva like '%" + buscar + "%' ORDER BY idreserva DESC";
        
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            
            while(rs.next())
            {
                registro [0] = rs.getString("idreserva");
                registro [1] = rs.getString("idhabitacion");
                registro [2] = rs.getString("numero");
                registro [3] = rs.getString("idcliente");
                registro [4] = rs.getString("clienten") + " " + rs.getString("clienteap");
                registro [5] = rs.getString("idtrabajador");
                registro [6] = rs.getString("trabajadorn") + " " + rs.getString("trabajadorap");
                registro [7] = rs.getString("tipo_reserva");
                registro [8] = rs.getString("fecha_reserva");
                registro [9] = rs.getString("fecha_ingresa");
                registro [10] = rs.getString("fecha_salida");
                registro [11] = rs.getString("costo_alojamiento");
                registro [12] = rs.getString("estado");
                
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
    
    public boolean insertar (Vreserva dts)
    {
        sSQL = "INSERT INTO reserva (idhabitacion, idcliente, idtrabajador, tipo_reserva, fecha_reserva, fecha_ingresa, fecha_salida, costo_alojamiento, estado) " + 
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try 
        { 
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, dts.getIdhabitacion());
            pst.setInt(2, dts.getIdcliente());
            pst.setInt(3, dts.getIdtrabajador());
            pst.setString(4, dts.getTipo_reserva());
            pst.setDate(5, dts.getFecha_reserva());
            pst.setDate(6, dts.getFecha_ingresa());
            pst.setDate(7, dts.getFecha_salida());
            pst.setDouble(8, dts.getCosto_alojamiento());
            pst.setString(9, dts.getEstado());
            
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
    
    public boolean editar (Vreserva dts)
    {
        sSQL = "UPDATE reserva SET idhabitacion = ?, idcliente = ?, idtrabajador = ?, tipo_reserva = ?, fecha_reserva = ?, fecha_ingresa = ?, fecha_salida = ?, costo_alojamiento = ?, estado = ? " +
                "WHERE idreserva = ?";
        
        try 
        {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, dts.getIdhabitacion());
            pst.setInt(2, dts.getIdcliente());
            pst.setInt(3, dts.getIdtrabajador());
            pst.setString(4, dts.getTipo_reserva());
            pst.setDate(5, dts.getFecha_reserva());
            pst.setDate(6, dts.getFecha_ingresa());
            pst.setDate(7, dts.getFecha_salida());
            pst.setDouble(8, dts.getCosto_alojamiento());
            pst.setString(9, dts.getEstado());
            
            pst.setInt(10, dts.getIdreserva());
            
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
    
    public boolean eliminar (Vreserva dts)
    {
        sSQL = "DELETE FROM reserva WHERE idreserva = ?";
        try 
        {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            
            pst.setInt(1, dts.getIdreserva());
            
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
    
    public boolean pagar (Vreserva dts)
    {
        sSQL = "UPDATE reserva SET estado = 'Pagada' " +
                "WHERE idreserva = ?";
        
        try 
        {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            
            pst.setInt(1, dts.getIdreserva());
            
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

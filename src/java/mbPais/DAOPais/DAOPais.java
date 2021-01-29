/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mbPais.DAOPais;

import conexion.conexiondbms;
import mbPais.dominio.Pais;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * @author clog10
 */

public class DAOPais {
    
    
    
    public ArrayList<Pais> damePaises() throws SQLException{
        ArrayList<Pais> lst = new ArrayList<>();
        conexiondbms cn = new conexiondbms();
        Connection c = cn.conectar();
        String sql = "Select * from scpais.pais";
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery(sql);
        
        while(rs.next()){
            Pais p = new Pais();
            p.setIdpais(rs.getInt("idpais"));
            p.setPais(rs.getString("pais"));
            lst.add(p);
        }
        cn.desconectar(c);
        return lst;
    }
    
    public Pais damePaises(int idpais) throws SQLException{
        Pais pais2 = new Pais();
        String sql = "Select * from scpais.pais where idpais = ?";
        conexiondbms cn = new conexiondbms();
        Connection c = cn.conectar();
        PreparedStatement ps;
        ps = c.prepareStatement(sql);
        ps.setInt(1, idpais);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            pais2.setIdpais(rs.getInt("idpais"));
            pais2.setPais(rs.getString("pais"));
        }
        cn.desconectar(c);
        return pais2;
    }
    
        public void guardarPais(Pais pais) throws SQLException {
        conexiondbms c = new conexiondbms();
        Connection cn = c.conectar();
        PreparedStatement ps;
        String sql = "insert into scpais.pais (idpais, pais) values (?,?)";
        ps = cn.prepareStatement(sql);
        ps.setInt(1, pais.getIdpais());
        ps.setString(2, pais.getPais());
        ps.executeUpdate();
        c.desconectar(cn);
    }

    public void actualizarPais(Pais pais) throws SQLException {
        conexiondbms c = new conexiondbms();
        Connection cn = c.conectar();
        PreparedStatement ps;
        String sql = "update scpais.pais set pais=? where idpais=?";

        ps = cn.prepareStatement(sql);
        ps.setInt(1, pais.getIdpais());
        ps.setString(2, pais.getPais());

        ps.executeUpdate();
        c.desconectar(cn);

    }
    
}

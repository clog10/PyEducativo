/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mbPais;

import java.io.Serializable;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import mbPais.DAOPais.DAOPais;
import mbPais.dominio.Pais;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import mbEstado.DAOEstado.DAOEstado;
import mbEstado.MbEstado;
import mbEstado.dominio.Estado;


/**
 * @author clog10
 */

@Named(value = "mbPais")
@Stateless
public class MbPais implements Serializable {
    @ManagedProperty(value = "#{mbEstado}")
    private MbEstado mbEstado = new MbEstado();
    private ArrayList<SelectItem> cmb1 = null;
    private ArrayList<Pais> lstPais;
    
    private Pais paisNuevo;
    private Pais seleccion;
    
    public MbPais() {
        paisNuevo = new Pais();
        seleccion = new Pais();
    }    
    
    private int idpais = 0;
    private Pais p = new Pais();

    public MbEstado getMbEstado() {
        return mbEstado;
    }

    public void setMbEstado(MbEstado mbEstado) {
        this.mbEstado = mbEstado;
    }

    public ArrayList<SelectItem> getCmb1() {
        if(cmb1 == null){
            cmb1 = new ArrayList<>();
            DAOPais dao = new DAOPais();
            try {
                 for(Pais p : dao.damePaises()){
                SelectItem s = new SelectItem(p.getIdpais(), p.getPais());
                cmb1.add(s);
            }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(MbPais.class.getName()).log(Level.SEVERE,null,ex);
            }  
        }
        return cmb1;
    }

    public void setLstPais(ArrayList<Pais> lstPais) {
        this.lstPais = lstPais;
    }
    
    public ArrayList<Pais> getlstPais(){
        return lstPais;
    }

    public void setCmb1(ArrayList<SelectItem> cmb1) {
        this.cmb1 = cmb1;
    }

    public Pais getP() {
        return p;
    }

    public void setP(Pais p) {
        this.p = p;
    }
    
    public int getIdpais() {
        return idpais;
    }

    public void setIdpais(int idpais) {
        this.idpais = idpais;
    }
    
    public void buscarPais(){
        System.out.println("Hizo clic en pais");
        DAOPais dao = new DAOPais();
        try {
            System.out.println(p.getIdpais());
            Pais pais2 = dao.damePaises(p.getIdpais());
            p = pais2;
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(MbPais.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    public void obtenerInformacion(){
        System.out.println(idpais);
        mbEstado.cargarEstado(idpais);
        
    }

    public Pais getPaisNuevo() {
        return paisNuevo;
    }

    public void setPaisNuevo(Pais paisNuevo) {
        this.paisNuevo = paisNuevo;
    }

    public Pais getSeleccion() {
        return seleccion;
    }

    public void setSeleccion(Pais seleccion) {
        this.seleccion = seleccion;
    }
    
    
    
    public ArrayList<Pais> getLstPais(){
        DAOPais dao = new DAOPais();
        if(lstPais ==null){
            try{
                lstPais = new ArrayList<>();
                for(Pais pais :dao.damePaises()){
                    lstPais.add(pais);
                }
            }catch (SQLException ex) {
                Logger.getLogger(MbPais.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lstPais;
    }
    
    public void ImprimePaises(){
        paisNuevo = seleccion;
        System.out.println(seleccion.getIdpais());
        System.out.println(seleccion.getPais());
    }
    
    public void guardarPais() {
        DAOPais dao = new DAOPais();
        if (seleccion == null) {
            try {
                dao.guardarPais(paisNuevo);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Nuevo pais agregado exitosamente"));
            } catch (SQLException ex) {
                System.out.println("Error al insertar pais");
                Logger.getLogger(MbEstado.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                dao.actualizarPais(paisNuevo);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Pais actualizado exitosamente"));
            } catch (SQLException ex) {
                System.out.println("Error al actualizar pais");
                Logger.getLogger(MbEstado.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        lstPais = null;
        paisNuevo = new Pais();
        seleccion = null;
    }
    
        public void limpiarPais() {
        lstPais = null;
        paisNuevo = new Pais();
        seleccion = null;
    }
    
}

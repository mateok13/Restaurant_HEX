/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.microkernel.client.domain;

import co.unicauca.microkernel.client.access.Factory;
import co.unicauca.microkernel.client.access.IClienteAccess;
import co.unicauca.microkernel.common.entities.CategoriaEnum;
import co.unicauca.microkernel.common.entities.PlatoEspecial;
import co.unicauca.microkernel.common.entities.RacionDia;
import co.unicauca.microkernel.common.entities.Restaurante;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author EdynsonMJ,JhonnyRosero,JhonferRuiz,JuanGonzales,JamesSilva
 */
public class clienteServiceIT {
    private final IClienteAccess service;
    private final ClienteService  instance;
    private List<RacionDia> platosDias;
    private List<PlatoEspecial> platosEspeciales;
    
    public clienteServiceIT() {
        service=Factory.getInstance().getClienteService();
        instance=new  ClienteService(service);
    }

    /**
     * Test of saveRestaurant method, of class clienteService.
     * @throws java.lang.Exception
     */
    @Test
    public void testSaveRestaurant() throws Exception {
        System.out.println("saveRestaurant");
        //se crea un restaurante local
        Restaurante restaurant = new Restaurante(0,1, "mx", "Burrito Sabanero", null, 30,30);
        String expResult = "Burrito Sabanero";
        //se guarda en la base de datos
        String result = instance.saveRestaurant(restaurant);
        assertEquals(expResult, result);
    }
    /**
     * Test of saveRacionDia method, of class clienteService.
     * @throws java.lang.Exception
     */
    @Test
    public void testSaveRacionDia() throws Exception {
        System.out.println("saveRacionDia");
        //se crea una racion local
        RacionDia racion = new RacionDia(0, CategoriaEnum.CARNE, 13000, "carne de borrego", 1, null);
        String expResult = "carne de borrego";
        //se guarda en la base de datos
        String result = instance.saveRacionDia(racion,1);
        int idrac=0;
        platosDias=instance.listMenuDayAll(1, "administrador");
        for (RacionDia pdia : platosDias) {
            if (pdia.getNombre().equals("carne de borrego")) {
                idrac=pdia.getRacId();
                break;
            }
        }
        instance.deleteRacionDia(idrac);
        assertEquals(expResult, result);
    }
    /**
     * Test of savePlatoEspecial method, of class clienteService.
     * @throws java.lang.Exception
     */
    @Test
    public void testSavePlatoEspecial() throws Exception {
        System.out.println("savePlatoEspecial");
        PlatoEspecial plato = new PlatoEspecial(0, 1, "Arroz chino", "rendidor", 13000, null);
        String expResult = "Arroz chino";
        String result = instance.savePlatoEspecial(plato);
        int idpes=0;
        platosEspeciales=instance.listMenuSpecial(1, "administrador");
        for (PlatoEspecial pesp : platosEspeciales) {
            if (pesp.getNombre().equals("Arroz chino")) {
                idpes=pesp.getId_pe();
                break;
            }
        };
        instance.deletePlatoEspecial(idpes);
        assertEquals(expResult, result);
    }

    /**
     * Test of updatePlatoEspecial method, of class clienteService.
     * @throws java.lang.Exception
     */
    @Test
    public void testSupdatePlatoEspecial() throws Exception {
        int idPlatoespecial=0;
        PlatoEspecial plaesp=new PlatoEspecial(0, 1, "Bandeja paisa", "rica y nutritiva", 124000, null);
        instance.savePlatoEspecial(plaesp);
        
        platosEspeciales=instance.listMenuSpecial(1, "administrador");
        System.out.println("updatePlatoEspecial");
        PlatoEspecial plato = new PlatoEspecial(0, 1, "Hoja de achotes", "natural", 12000, null);
        for (PlatoEspecial pesp : platosEspeciales) {
            if (pesp.getNombre().equals("Bandeja paisa") && pesp.getMenuEsp()==1) {
                idPlatoespecial=pesp.getId_pe();
                break;
            }
        }
        plato.setId_pe(idPlatoespecial);
        
        boolean expResult = true;
        boolean result = instance.updatePlatoEspecial(plato);
        instance.deletePlatoEspecial(idPlatoespecial);
        assertEquals(expResult, result);
        
    }
    /**
     * Test of updateRacion method, of class clienteService.
     * @throws java.lang.Exception
     */
    @Test
    public void testSupdateRacion() throws Exception {
        int idracion=0;
        RacionDia racion=new RacionDia(0, CategoriaEnum.BASE, 12000, "Arroz con pollo", 1, null);
        instance.saveRacionDia(racion,1);
        
        platosDias=instance.listMenuDayAll(1,"administrador");
        System.out.println("updateRacion");
        RacionDia racionActualizar = new RacionDia(0, CategoriaEnum.BASE, 13000, "Arroz con pimenton", 1, null);
        for (RacionDia rac : platosDias) {
            if (rac.getNombre().equals("Arroz con pollo") && rac.getMenuId()==1) {
                idracion=rac.getRacId();
                break;
            }
        }
        racionActualizar.setRacId(idracion);
        boolean expResult = true;
        boolean result = instance.updateRacion(racionActualizar);
        instance.deleteRacionDia(idracion);
        assertEquals(expResult, result);
    }
    
     /**
     * Test of deletePlatoEspecial method, of class clienteService.
     * @throws java.lang.Exception
     */
    @Test
    public void testTdeletePlatoEspecial() throws Exception {
        System.out.println("deletePlatoEspecial");
        int plae_id = 0;
        PlatoEspecial plaespe=new PlatoEspecial(0, 1, "arroz con yuca", "delicioso!", 23000, null);
        instance.savePlatoEspecial(plaespe);
        
        platosEspeciales=instance.listMenuSpecial(1, "administrador");
        //esta forma de obtener el codigo no es la mas optima
        //debido a que el codigo del plato es incremental
        //toca buscar la forma de obtener ese codigo
        //implementar
        for (PlatoEspecial pesp : platosEspeciales) {
            if (pesp.getNombre().equals("arroz con yuca") && pesp.getMenuEsp()==1) {
                plae_id=pesp.getId_pe();
                break;
            }
        }
        String expResult = ""+plae_id;
        String result = instance.deletePlatoEspecial(plae_id);
        assertEquals(expResult, result);
    }

    /**
     * Test of deleteRacionDia method, of class clienteService.
     * @throws java.lang.Exception
     */
   @Test
    public void testTdeleteRacionDia() throws Exception {
        System.out.println("deleteRacionDia");
        int rac_id=0;
        RacionDia racion=new RacionDia(0, CategoriaEnum.PRINCIPIO, 12000, "lentejas", 1, null);
        instance.saveRacionDia(racion,1);
        
        platosDias=instance.listMenuDayAll(1, "administrador");
         //esta forma de obtener el codigo no es la mas optima
        //debido a que el codigo del plato es incremental
        //toca buscar la forma de obtener ese codigo
        //implementar
        for (RacionDia rac : platosDias) {
            if (rac.getNombre().equals("lentejas") && rac.getMenuId()==1) {
                rac_id=rac.getRacId();
                break;
            }
        }
        String expResult = ""+rac_id;
        String result = instance.deleteRacionDia(rac_id);
        assertEquals(expResult, result);
    }
}

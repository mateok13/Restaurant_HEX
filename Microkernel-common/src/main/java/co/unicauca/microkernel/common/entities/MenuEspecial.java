package co.unicauca.microkernel.common.entities;

/**
 *representa un menu de platos especiales
 * @author edynson, mateo, james, camilo, jhonfer
 */
public class MenuEspecial {
    /**
     * identificador del menu
     */
    private int id;
    /**
     * identificador del restaurante al que esta asociado
     */
    private int res_id;

    /**
     * constructor parametrizado
     * @param id identificador del menu
     * @param res_id identificador del restaurante al que esta asociado
     */
    public MenuEspecial(int id, int res_id) {
        this.id = id;
        this.res_id = res_id;
    }

    //GET AND SET
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRes_id() {
        return res_id;
    }

    public void setRes_id(int res_id) {
        this.res_id = res_id;
    }
    
   
}

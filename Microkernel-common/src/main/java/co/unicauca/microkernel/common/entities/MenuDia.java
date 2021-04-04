package co.unicauca.microkernel.common.entities;

/**
 * Representa un menu que corresponde a un dia y esta asociado a un los resturantes
 * @author Edynson, Jhonfer, Mateo, Camilo, James
 */
public class MenuDia {
    /**
     * identificador del menu.
     */
    private int idMenu;
    /**
     * dia de la semana al que se encuentra asociado
     */
    private DiaEnum diaSem;
    /**
     * identificador del restaurante al que se asocia.
     */
    private int resId;

    /**
     * constructor parametrizado
     * @param idMenu idntificador del menu
     * @param diaSem dia de la semana que representa
     * @param resId  identificador del restaurante al que esta asociado
     */
    public MenuDia(int idMenu, DiaEnum diaSem, int resId) {
        this.idMenu = idMenu;
        this.diaSem = diaSem;
        this.resId = resId;
    }

    /**
     * Getter y setter
     * @return 
     */
    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public DiaEnum getDiaSem() {
        return diaSem;
    }

    public void setDiaSem(DiaEnum diaSem) {
        this.diaSem = diaSem;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}

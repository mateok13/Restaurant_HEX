package co.unicauca.microkernel.core.infra;

import co.unicacuca.microkernel.core.dominio.IPlatoRepositorio;

/**
 * Fabrica que instacia un repositorio concreto, este repositorio accede a la base de datos
 * @author Edynson, Jhonfer, Mateo, Camilo, James
 */
public class FabricaRepositorio {
    
    private static FabricaRepositorio instance;
    
    private FabricaRepositorio(){
    }
    /**
     * retorno solo una instancia de fabrica
     * singleton
     * @return instancia de FabricaRepositorio
     */
    public static FabricaRepositorio getInstance(){
        if(instance == null){
            instance = new FabricaRepositorio();
        }
        return instance;
    }
    /**
     * retorna un repositorio
     * @return instancia del repositorio
     */
    public IPlatoRepositorio getRepository(){
        IPlatoRepositorio repositorio = new RestauranteRepositorioMysql();
        //IPlatoRepositorio repositorio = new RestauranteRepositorioDeveloper();
        return repositorio;
    }
}
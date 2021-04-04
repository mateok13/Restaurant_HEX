package co.unicauca.microkernel.client.access;

import static co.unicauca.microkernel.common.infra.Utilities.loadProperty;

/**
 * Fabrica que retorna una instancia para el acceso al servidor
 * @author Edynson, Jhonfer, Mateo, Camilo, James
 */
public class Factory {
    private static Factory instance;
    
    private Factory(){
        
    }
    public static Factory getInstance(){
        if(instance == null){
            instance = new Factory();
        }
        return instance;
    }
    /**
     * crea una clase concreta de ClienteAccessSocket que realiza la comunicacion
     * @return 
     */
    public IClienteAccess getClienteService(){
        IClienteAccess result = null;
        var type = loadProperty("customer.service");
        switch (type){
            case "default":
                result =new ClienteAccessSocket();
                break;
        }
        return result;
    }
}

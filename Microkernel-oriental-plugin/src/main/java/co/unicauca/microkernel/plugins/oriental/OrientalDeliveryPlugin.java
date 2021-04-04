package co.unicauca.microkernel.plugins.oriental;

import co.unicauca.microkernel.common.entities.Delivery;
import co.unicauca.microkernel.common.interfaces.IDeliveryPlugin;

/**
 * Plugin para restaurantes orientales
 * @author Edynson, Jhonfer, Mateo, Camilo, James
 */
public class OrientalDeliveryPlugin implements IDeliveryPlugin {
    /**
     * se calcula el costo del domicilio dependiendo de la direccion a la que e enviara el pedido
     * @param delivery
     * @return 
     */
    @Override
    public int calculateCostDomicile(Delivery delivery) {       
        double distancia = delivery.getDistance();

        int cost;
        
        cost = (int)(distancia*200);  

        return cost;
    }
    /**
     * el impuesto varia del tipo del restaurante este impuesto es sobre el
     * valor total de los platos
     *
     * @param delivery
     * @return
     */
    @Override
    public int impuestoRestaurante(Delivery delivery){
        double sumaOrder = delivery.getPrecio();
        
        int cost;
        
        cost = (int) (sumaOrder+(sumaOrder*0.2));
        
        return cost;
    }
}

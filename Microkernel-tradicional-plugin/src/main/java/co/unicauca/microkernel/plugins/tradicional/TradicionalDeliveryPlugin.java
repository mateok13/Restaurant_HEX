package co.unicauca.microkernel.plugins.tradicional;

import co.unicauca.microkernel.common.entities.Delivery;
import co.unicauca.microkernel.common.interfaces.IDeliveryPlugin;
/**
 * Plugin para restaurantes tipicos
 * @author EdynsonMJ,JhonnyRosero,JhonferRuiz,JuanGonzales,JamesSilva
 */
public class TradicionalDeliveryPlugin implements IDeliveryPlugin {
    /**
     * se calcula el costo del domicilio dependiendo de la direccion a la que e enviara el pedido
     * @param delivery
     * @return 
     */
    @Override
    public int calculateCostDomicile(Delivery delivery) {
        
        int distancia = (int)(delivery.getDistance());

        int cost;
        
        cost = (distancia*50);
        

        return cost;
    }
    /**
     * el impuesto varia del tipo del restaurante este impuesto es sobre el valor total de los platos
     * @param delivery
     * @return 
     */
    @Override
    public int impuestoRestaurante(Delivery delivery){
        int sumaOrder = delivery.getPrecio();
        
        int cost;
        
        cost = sumaOrder+(int)(sumaOrder*0.11);
        

        return cost;
    }
}

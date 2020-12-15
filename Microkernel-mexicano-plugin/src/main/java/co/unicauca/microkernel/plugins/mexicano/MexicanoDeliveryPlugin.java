package co.unicauca.microkernel.plugins.mexicano;

import co.unicauca.microkernel.common.entities.Delivery;
import co.unicauca.microkernel.common.interfaces.IDeliveryPlugin;
/**
 * Plugin para restaurantes mexicanos
 * @author EdynsonMJ,JhonnyRosero,JhonferRuiz,JuanGonzales,JamesSilva
 */
public class MexicanoDeliveryPlugin implements IDeliveryPlugin {

    /**
     * se calcula el costo del domicilio dependiendo de la direccion a la que e enviara el pedido
     * @param delivery
     * @return 
     */
    @Override
    public int calculateCostDomicile(Delivery delivery) {
        
        double distancia = (delivery.getDistance());

        int cost;
        
        cost = (int)(distancia*100);
        

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
        
        cost = sumaOrder+(int)(sumaOrder*0.19);
        

        return cost;
    }
}

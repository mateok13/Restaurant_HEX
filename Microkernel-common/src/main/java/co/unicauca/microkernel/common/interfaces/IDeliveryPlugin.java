package co.unicauca.microkernel.common.interfaces;

import co.unicauca.microkernel.common.entities.Delivery;

/**
 * Interface de los pagos donde cada restaurante implementa segun su pais de origen
 * @author Edynson, Jhonfer, Mateo, Camilo, James
 */
public interface IDeliveryPlugin {

    int calculateCostDomicile(Delivery delivery);
    
    int impuestoRestaurante(Delivery delivery);
}

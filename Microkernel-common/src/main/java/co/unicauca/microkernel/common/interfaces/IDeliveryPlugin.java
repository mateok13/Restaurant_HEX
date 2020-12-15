package co.unicauca.microkernel.common.interfaces;

import co.unicauca.microkernel.common.entities.Delivery;

public interface IDeliveryPlugin {

    int calculateCostDomicile(Delivery delivery);
    
    int impuestoRestaurante(Delivery delivery);
}

package co.unicauca.microkernel.core.infra;

import co.unicauca.microkernel.common.entities.Delivery;
import co.unicauca.microkernel.common.interfaces.IDeliveryPlugin;

public class DeliveryService {

    public int calculateDeliveryCostDomicile(Delivery deliveryData) throws Exception {

        String resCode = deliveryData.getResCode();
        DeliveryPluginManager manager = DeliveryPluginManager.getInstance();
        IDeliveryPlugin plugin = manager.getDeliveryPlugin(resCode);

        if (plugin == null) {
            throw new Exception("No hay un plugin disponible para el Restaurante indicado: " + resCode);
        }
        return plugin.calculateCostDomicile(deliveryData);
    }
    public int calculateImpuestoRestaurante(Delivery deliveryData) throws Exception {

        String resCode = deliveryData.getResCode();
        DeliveryPluginManager manager = DeliveryPluginManager.getInstance();
        IDeliveryPlugin plugin = manager.getDeliveryPlugin(resCode);

        if (plugin == null) {
            throw new Exception("No hay un plugin disponible para el Restaurante indicado: " + resCode);
        }
        return plugin.impuestoRestaurante(deliveryData);
    }
}
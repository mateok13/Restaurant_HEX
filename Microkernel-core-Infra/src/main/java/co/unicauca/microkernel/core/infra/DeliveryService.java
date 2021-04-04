package co.unicauca.microkernel.core.infra;

import co.unicauca.microkernel.common.entities.Delivery;
import co.unicauca.microkernel.common.interfaces.IDeliveryPlugin;

/**
 * Se encarga de calcular el valor de los pedidos de cada plugin
 * @author Edynson, Jhonfer, Mateo, Camilo, James
 */
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
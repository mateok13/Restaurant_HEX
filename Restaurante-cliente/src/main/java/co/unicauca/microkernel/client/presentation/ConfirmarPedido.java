package co.unicauca.microkernel.client.presentation;

import static co.unicauca.microkernel.client.access.Factory.getInstance;
import co.unicauca.microkernel.client.access.IClienteAccess;
import co.unicauca.microkernel.client.domain.ClienteService;
import co.unicauca.microkernel.common.entities.Pedido;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Interfaz de confirmación de pedido
 * @author Edynson, Jhonfer, Mateo, Camilo, James
 */
public class ConfirmarPedido extends javax.swing.JFrame {
    IClienteAccess service;
    ClienteService servicioRestaurante;  
    public static int domi;
    public static int sum;
    public static int imp;
    public static int tot;
    private Pedido pedido;
    
    public ConfirmarPedido(Pedido pedido, ClienteService cliente) throws Exception {
        //intancia los servicios
        service = getInstance().getClienteService();
        servicioRestaurante = new ClienteService(service);
        //instancia el pedido
        this.pedido = pedido;
        //captura los datos necesarios para crear la factura
        String suma = servicioRestaurante.sumOrder(pedido.getCliente(), pedido.getIdPedido()); 
        String res = servicioRestaurante.total(pedido.getCliente(), pedido.getIdPedido());
        //parsea los datos para poder hacer las operaciones necesariass para la realizacion de la factura
        String [] TOTAL = res.split("-");
        if (TOTAL.length>=3) {
            System.out.println("SUMA NETO: "+suma);
            System.out.println("IMPUESTO: " + TOTAL[2]);
            System.out.println("DOMICILIO: " + TOTAL[1]);
            System.out.println("TOTAL: " + TOTAL[0]);
            imp = Integer.parseInt(TOTAL[2]);
            domi = Integer.parseInt(TOTAL[1]);
            sum = Integer.parseInt(suma);
            tot = Integer.parseInt(TOTAL[0]);  
        }  
        initComponents();
        lblSumaOrdenesR.setText(String.valueOf(sum));
        lblImpuestoPedido.setText(String.valueOf(imp));
        lblPrecioDomicilio.setText(String.valueOf(domi));
        lblTotalR.setText(String.valueOf(tot));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblSumaOrdenesR = new javax.swing.JLabel();
        lblImpuestoPedido = new javax.swing.JLabel();
        lblPrecioDomicilio = new javax.swing.JLabel();
        lblTotalR = new javax.swing.JLabel();
        btnPagarPedido = new javax.swing.JButton();
        btnCancelarPedido = new javax.swing.JButton();
        lblTOTAL = new javax.swing.JLabel();
        lblDomicilio = new javax.swing.JLabel();
        lblImpuesto = new javax.swing.JLabel();
        lblSumaOrdenes = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(30, 100, 85));

        jPanel1.setBackground(new java.awt.Color(30, 100, 85));

        lblSumaOrdenesR.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblSumaOrdenesR.setText(" ");

        lblImpuestoPedido.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblImpuestoPedido.setText(" ");

        lblPrecioDomicilio.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblPrecioDomicilio.setText(" ");

        lblTotalR.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTotalR.setText(" ");

        btnPagarPedido.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnPagarPedido.setText("PAGAR PEDIDO");
        btnPagarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagarPedidoActionPerformed(evt);
            }
        });

        btnCancelarPedido.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnCancelarPedido.setText("CANCELAR PEDIDO");
        btnCancelarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarPedidoActionPerformed(evt);
            }
        });

        lblTOTAL.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTOTAL.setForeground(new java.awt.Color(255, 0, 0));
        lblTOTAL.setText("TOTAL: ______________");

        lblDomicilio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblDomicilio.setText("COSTO DOMICILIO:_____");

        lblImpuesto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblImpuesto.setText("PRECIO NETO:_________");

        lblSumaOrdenes.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblSumaOrdenes.setText("PRECIO BRUTO:________");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("FACTURA");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelarPedido))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblImpuesto)
                            .addComponent(lblDomicilio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblSumaOrdenes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblImpuestoPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSumaOrdenesR, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPagarPedido)
                    .addComponent(lblPrecioDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblTOTAL, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTotalR, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(148, 148, 148))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSumaOrdenes)
                    .addComponent(lblSumaOrdenesR))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblImpuesto)
                    .addComponent(lblImpuestoPedido))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDomicilio)
                    .addComponent(lblPrecioDomicilio))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTOTAL)
                    .addComponent(lblTotalR))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelarPedido)
                    .addComponent(btnPagarPedido))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarPedidoActionPerformed
        try {
            this.servicioRestaurante.cancelPedido(pedido);
            this.setVisible(false);
            FramePrincipalCliente instancia = new FramePrincipalCliente(pedido.getCliente());
            instancia.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(ConfirmarPedido.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCancelarPedidoActionPerformed

    private void btnPagarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagarPedidoActionPerformed
        try {
            this.servicioRestaurante.payedPedido(pedido);
            this.setVisible(false);
            FramePrincipalCliente instancia = new FramePrincipalCliente(pedido.getCliente());
            instancia.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(ConfirmarPedido.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnPagarPedidoActionPerformed
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelarPedido;
    private javax.swing.JButton btnPagarPedido;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblDomicilio;
    private javax.swing.JLabel lblImpuesto;
    private javax.swing.JLabel lblImpuestoPedido;
    private javax.swing.JLabel lblPrecioDomicilio;
    private javax.swing.JLabel lblSumaOrdenes;
    private javax.swing.JLabel lblSumaOrdenesR;
    private javax.swing.JLabel lblTOTAL;
    private javax.swing.JLabel lblTotalR;
    // End of variables declaration//GEN-END:variables
}

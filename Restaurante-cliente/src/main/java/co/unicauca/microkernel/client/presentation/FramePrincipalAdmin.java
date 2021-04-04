package co.unicauca.microkernel.client.presentation;

import static co.unicauca.microkernel.client.access.Factory.getInstance;
import co.unicauca.microkernel.client.access.IClienteAccess;
import co.unicauca.microkernel.client.domain.ClienteService;
import co.unicauca.microkernel.common.entities.PlatoEspecial;
import co.unicauca.microkernel.common.entities.RacionDia;
import co.unicauca.microkernel.client.gestionTabla.TablaEspeciales;
import co.unicauca.microkernel.client.gestionTabla.TablaPedidos;
import co.unicauca.microkernel.client.gestionTabla.TablaRaciones;
import co.unicauca.microkernel.common.entities.Cliente;
import co.unicauca.microkernel.common.entities.Pedido;
import co.unicauca.microkernel.common.entities.Restaurante;
import co.unicauca.microkernel.common.infra.Utilities;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import static java.util.logging.Level.SEVERE;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.showConfirmDialog;

/**
 * Interfaz para usuario Administrador
 * @author Edynson, Jhonfer, Mateo, Camilo, James
 */
public class FramePrincipalAdmin extends JFrame {
    //listas
    List<RacionDia> raciones;
    List<PlatoEspecial> especiales;
    //tablas
    TablaRaciones tabRaciones;
    TablaEspeciales tabEspeciales;
    TablaPedidos tabPedidos;
    //servicio
    IClienteAccess service;
    ClienteService servicioRestaurante;
    //variables
    private static List<String> idRestaurante;
    private static String varDia;
    private int idSeleccionado;
    private String photoNull;
    private List<Pedido> pedidos;
    private int IdUsuario;
    
    /**
     * Creates new form FramePrincipalAdmin
     * @param idRestaurantes
     */
    public FramePrincipalAdmin(List<String> idRestaurantes, int idUsuario) {
        this.IdUsuario = idUsuario;
        try {
            //INSTANCIANDO EL SERVICIO, POR FABRICA
            this.estilo();
            for(int i = 0 ; i< idRestaurantes.size(); i++){
                System.out.println("["+i+"]="+idRestaurantes.get(i));
            }
            idRestaurante = idRestaurantes;
            service = getInstance().getClienteService();
            servicioRestaurante = new ClienteService(service);
            //INICIALIZANDO TABLAS
            tabRaciones = new TablaRaciones();
            tabEspeciales = new TablaEspeciales();
            tabPedidos=new TablaPedidos();
            //INICIANDO COMPONENTES
            initComponents();
            //INICIALIZANDO TABLAS Y LLENANDO LISTAS
            raciones = new ArrayList<>();
            especiales = new ArrayList<>();
            pedidos=new ArrayList<>();
            varDia = "TODOS";

            this.crearIndexRestaurante();
            this.crearTablaPedidos();

            this.showInfoUsuario();
        } catch (Exception ex) {
            getLogger(FramePrincipalAdmin.class.getName()).log(SEVERE, null, ex);
        }
    }

    private void estilo() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FramePrincipalAdmin.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FramePrincipalAdmin.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FramePrincipalAdmin.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FramePrincipalAdmin.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
    }
    
    
    private void showInfoUsuario(){
        try {
            Cliente aux = this.servicioRestaurante.getClient(this.IdUsuario);
            if (aux != null){
                this.lblNombreUsuario.setText("NOMBRE: "+Utilities.desencriptar(aux.getNombre()));
                this.lblDireccionUsuario.setText("DIRECCION: carrera "+ aux.getCarrera()+" calle "+aux.getCalle());
                this.lblTipoUsuario1.setText("TIPO: "+aux.getTipo()+" de restaurante");
                this.servicioRestaurante.fijarImagen(this.lblImagenUsuario, aux.getImagen(), "FOTOPERFIL");
            }else{
                System.out.println("id: "+this.IdUsuario);
            }
        } catch (Exception ex) {
            Logger.getLogger(FramePrincipalAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtpPestanias = new javax.swing.JTabbedPane();
        pnlInicio = new javax.swing.JPanel();
        pnlInfoUsuario = new javax.swing.JPanel();
        lblImagenUsuario = new javax.swing.JLabel();
        lblNombreUsuario = new javax.swing.JLabel();
        lblNombreRestaurante = new javax.swing.JLabel();
        cbxRestaurante = new javax.swing.JComboBox<>();
        lblImagenInicio = new javax.swing.JLabel();
        lblInformacionUsuario = new javax.swing.JLabel();
        lblDireccionUsuario = new javax.swing.JLabel();
        lblTipoUsuario1 = new javax.swing.JLabel();
        lblTituloRestaurante = new javax.swing.JLabel();
        btnCerrarSesion = new javax.swing.JButton();
        pnlRaciones = new javax.swing.JPanel();
        pnlFondoRaciones = new javax.swing.JPanel();
        lblFiltro = new javax.swing.JLabel();
        cbxDia = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRaciones = new javax.swing.JTable();
        lblImagenRacion = new javax.swing.JLabel();
        btnAgregarRacion = new javax.swing.JButton();
        btnCerrarSesion2 = new javax.swing.JButton();
        pnlEspeciales = new javax.swing.JPanel();
        pnlFondoEspeciales = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblEspeciales = new javax.swing.JTable();
        btnAddEspecial = new javax.swing.JButton();
        lblImagenEspecial = new javax.swing.JLabel();
        pnlPedidos = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblPedidos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jtpPestanias.setBackground(new java.awt.Color(30, 100, 85));
        jtpPestanias.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        pnlInfoUsuario.setBackground(new java.awt.Color(30, 100, 85));

        lblNombreUsuario.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNombreUsuario.setText("Nombre: ");

        lblNombreRestaurante.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNombreRestaurante.setText("Restaurantes:");

        cbxRestaurante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxRestauranteActionPerformed(evt);
            }
        });

        lblInformacionUsuario.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblInformacionUsuario.setText("INFORMACION DEL USUARIO");

        lblDireccionUsuario.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDireccionUsuario.setText("direccion:");

        lblTipoUsuario1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTipoUsuario1.setText("Tipo: ");

        lblTituloRestaurante.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTituloRestaurante.setText("nombre de restaurante");

        btnCerrarSesion.setText("CERRAR SESION");
        btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlInfoUsuarioLayout = new javax.swing.GroupLayout(pnlInfoUsuario);
        pnlInfoUsuario.setLayout(pnlInfoUsuarioLayout);
        pnlInfoUsuarioLayout.setHorizontalGroup(
            pnlInfoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoUsuarioLayout.createSequentialGroup()
                .addGroup(pnlInfoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInfoUsuarioLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlInfoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombreUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDireccionUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTipoUsuario1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblInformacionUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                            .addComponent(lblImagenUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoUsuarioLayout.createSequentialGroup()
                                .addComponent(lblNombreRestaurante, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxRestaurante, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(pnlInfoUsuarioLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(btnCerrarSesion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(pnlInfoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTituloRestaurante, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblImagenInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 549, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );
        pnlInfoUsuarioLayout.setVerticalGroup(
            pnlInfoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoUsuarioLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(lblTituloRestaurante)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblImagenInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
            .addGroup(pnlInfoUsuarioLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(lblImagenUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlInfoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombreRestaurante)
                    .addComponent(cbxRestaurante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(lblInformacionUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblNombreUsuario)
                .addGap(2, 2, 2)
                .addComponent(lblTipoUsuario1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDireccionUsuario)
                .addGap(18, 18, 18)
                .addComponent(btnCerrarSesion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout pnlInicioLayout = new javax.swing.GroupLayout(pnlInicio);
        pnlInicio.setLayout(pnlInicioLayout);
        pnlInicioLayout.setHorizontalGroup(
            pnlInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlInfoUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlInicioLayout.setVerticalGroup(
            pnlInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlInfoUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jtpPestanias.addTab("INICIO", pnlInicio);

        pnlFondoRaciones.setBackground(new java.awt.Color(30, 100, 85));

        lblFiltro.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblFiltro.setText("Filtar por dia:");

        cbxDia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TODOS", "LUNES", "MARTES", "MIERCOLES", "JUEVES", "VIERNES", "SABADO", "DOMINGO" }));
        cbxDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxDiaActionPerformed(evt);
            }
        });

        tblRaciones.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tblRaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "NOMBRE", "DIA", "TIPO", "PRECIO", "ACCION 1", "ACCION 2"
            }
        ));
        tblRaciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRacionesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblRaciones);
        if (tblRaciones.getColumnModel().getColumnCount() > 0) {
            tblRaciones.getColumnModel().getColumn(0).setPreferredWidth(50);
        }

        btnAgregarRacion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAgregarRacion.setText("AGREGAR");
        btnAgregarRacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarRacionActionPerformed(evt);
            }
        });

        btnCerrarSesion2.setText("CERRAR SESION");
        btnCerrarSesion2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesion2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlFondoRacionesLayout = new javax.swing.GroupLayout(pnlFondoRaciones);
        pnlFondoRaciones.setLayout(pnlFondoRacionesLayout);
        pnlFondoRacionesLayout.setHorizontalGroup(
            pnlFondoRacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFondoRacionesLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblFiltro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxDia, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFondoRacionesLayout.createSequentialGroup()
                .addGroup(pnlFondoRacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFondoRacionesLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlFondoRacionesLayout.createSequentialGroup()
                        .addGap(172, 172, 172)
                        .addComponent(btnCerrarSesion2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAgregarRacion, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)))
                .addComponent(lblImagenRacion, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlFondoRacionesLayout.setVerticalGroup(
            pnlFondoRacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFondoRacionesLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(pnlFondoRacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFiltro)
                    .addComponent(cbxDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(pnlFondoRacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblImagenRacion, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlFondoRacionesLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlFondoRacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnAgregarRacion, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlFondoRacionesLayout.createSequentialGroup()
                                .addComponent(btnCerrarSesion2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(9, 9, 9)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlRacionesLayout = new javax.swing.GroupLayout(pnlRaciones);
        pnlRaciones.setLayout(pnlRacionesLayout);
        pnlRacionesLayout.setHorizontalGroup(
            pnlRacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRacionesLayout.createSequentialGroup()
                .addComponent(pnlFondoRaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlRacionesLayout.setVerticalGroup(
            pnlRacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRacionesLayout.createSequentialGroup()
                .addComponent(pnlFondoRaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 53, Short.MAX_VALUE))
        );

        jtpPestanias.addTab("RACIONES", pnlRaciones);

        pnlFondoEspeciales.setBackground(new java.awt.Color(30, 100, 85));

        tblEspeciales.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tblEspeciales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "NOMBRE", "DESCRIPCION", "PRECIO", "ACCION 1", "ACCION 1"
            }
        ));
        tblEspeciales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEspecialesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblEspeciales);
        if (tblEspeciales.getColumnModel().getColumnCount() > 0) {
            tblEspeciales.getColumnModel().getColumn(0).setPreferredWidth(50);
        }

        btnAddEspecial.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAddEspecial.setText("AGREGAR");
        btnAddEspecial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddEspecialActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlFondoEspecialesLayout = new javax.swing.GroupLayout(pnlFondoEspeciales);
        pnlFondoEspeciales.setLayout(pnlFondoEspecialesLayout);
        pnlFondoEspecialesLayout.setHorizontalGroup(
            pnlFondoEspecialesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFondoEspecialesLayout.createSequentialGroup()
                .addContainerGap(516, Short.MAX_VALUE)
                .addComponent(btnAddEspecial, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(218, 218, 218))
            .addGroup(pnlFondoEspecialesLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblImagenEspecial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlFondoEspecialesLayout.setVerticalGroup(
            pnlFondoEspecialesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFondoEspecialesLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pnlFondoEspecialesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblImagenEspecial, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnAddEspecial, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlEspecialesLayout = new javax.swing.GroupLayout(pnlEspeciales);
        pnlEspeciales.setLayout(pnlEspecialesLayout);
        pnlEspecialesLayout.setHorizontalGroup(
            pnlEspecialesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlFondoEspeciales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlEspecialesLayout.setVerticalGroup(
            pnlEspecialesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEspecialesLayout.createSequentialGroup()
                .addComponent(pnlFondoEspeciales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 66, Short.MAX_VALUE))
        );

        jtpPestanias.addTab("PLATOS ESPECIALES", pnlEspeciales);

        pnlPedidos.setBackground(new java.awt.Color(30, 100, 85));

        tblPedidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tblPedidos);

        javax.swing.GroupLayout pnlPedidosLayout = new javax.swing.GroupLayout(pnlPedidos);
        pnlPedidos.setLayout(pnlPedidosLayout);
        pnlPedidosLayout.setHorizontalGroup(
            pnlPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPedidosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(376, Short.MAX_VALUE))
        );
        pnlPedidosLayout.setVerticalGroup(
            pnlPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPedidosLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jtpPestanias.addTab("PEDIDOS", pnlPedidos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jtpPestanias)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jtpPestanias, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tblRacionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRacionesMouseClicked
        int column = tblRaciones.getColumnModel().getColumnIndexAtX(evt.getX());
        int row = evt.getY() / tblRaciones.getRowHeight();
        byte[] imagen = this.raciones.get(row).getImagen();
        //INSTANCIAR IMAGEN
        this.servicioRestaurante.fijarImagen(this.lblImagenRacion, imagen, "FOTONULA");
        if (row < tblRaciones.getRowCount() && row >= 0 && column < tblRaciones.getColumnCount() && column >= 0) {
            Object value = tblRaciones.getValueAt(row, column);
            if (value instanceof JButton) {
                ((JButton) value).doClick();
                var boton = (JButton) value;
                if (boton.getName().equals("modificar")) {
                    RacionDia aux = new RacionDia();
                    aux.setRacId(this.raciones.get(row).getRacId());
                    aux.setNombre(this.raciones.get(row).getNombre());
                    aux.setPrecio(this.raciones.get(row).getPrecio());
                    aux.setTipo(this.raciones.get(row).getTipo());
                    aux.setMenuId(this.raciones.get(row).getMenuId());
                    aux.setImagen(this.raciones.get(row).getImagen());
                    try {
                        ModificarRacion frameRacion = new ModificarRacion(aux, this.servicioRestaurante, this);
                    } catch (Exception ex) {
                        Logger.getLogger(FramePrincipalAdmin.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (boton.getName().equals("eliminar")) {
                    int fila = tblRaciones.getSelectedRow();
                    int clave = this.raciones.get(fila).getRacId();
                    try {
                        if ((JOptionPane.showConfirmDialog(rootPane, "¿Esta seguro que quiere eliminar la racion \""
                                + this.raciones.get(fila).getNombre()
                                + "\"?", "Eliminar Registro", JOptionPane.YES_NO_OPTION
                                , JOptionPane.QUESTION_MESSAGE)) == JOptionPane.YES_OPTION) {
                            if (this.servicioRestaurante.deleteRacionDia(clave) == "FALLO") {
                                JOptionPane.showMessageDialog(rootPane, "El registro no existe");
                            } else {
                                this.servicioRestaurante.fijarImagen(this.lblImagenRacion, null, "FOTONULA");
                                this.crearTablaRaciones();
                                JOptionPane.showMessageDialog(rootPane, "operacion exitosa");
                            }

                        }
                    } catch (Exception ex) {
                        showConfirmDialog(null, "OPERACION FALLIDA", "Confirmar", OK_CANCEL_OPTION);
                    }
                }
            }
            if (value instanceof JCheckBox) {
                //((JCheckBox)value).doClick();
                var ch = (JCheckBox) value;
                if (ch.isSelected() == true) {
                    ch.setSelected(false);
                }
                if (ch.isSelected() == false) {
                    ch.setSelected(true);
                }

            }
        }
    }//GEN-LAST:event_tblRacionesMouseClicked

    private void tblEspecialesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEspecialesMouseClicked
        int column = tblEspeciales.getColumnModel().getColumnIndexAtX(evt.getX());
        int row = evt.getY() / tblEspeciales.getRowHeight();
        byte[] imagen = this.especiales.get(row).getImagen();
        this.servicioRestaurante.fijarImagen(this.lblImagenEspecial, imagen, "FOTONULA");
        if (row < tblEspeciales.getRowCount() && row >= 0 && column < tblEspeciales.getColumnCount() && column >= 0) {
            Object value = tblEspeciales.getValueAt(row, column);
            if (value instanceof JButton) {
                ((JButton) value).doClick();
                var boton = (JButton) value;
                if (boton.getName().equals("modificar")) {
                    PlatoEspecial aux = new PlatoEspecial();
                    aux.setId_pe(this.especiales.get(row).getId_pe());
                    aux.setNombre(this.especiales.get(row).getNombre());
                    aux.setPrecio(this.especiales.get(row).getPrecio());
                    aux.setDescripcion(this.especiales.get(row).getDescripcion());
                    aux.setImagen(this.especiales.get(row).getImagen());
                    aux.setMenuEsp(this.especiales.get(row).getMenuEsp());
                    try {
                        ModificarEspecial frameEspecial = new ModificarEspecial(aux, this.servicioRestaurante, this);
                    } catch (Exception ex) {
                        Logger.getLogger(FramePrincipalAdmin.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (boton.getName().equals("eliminar")) {
                    int fila = tblEspeciales.getSelectedRow();
                    int clave = this.especiales.get(fila).getId_pe();
                    try {
                        if ((JOptionPane.showConfirmDialog(rootPane, "¿Esta seguro que quiere eliminar el plato \""
                                + this.especiales.get(fila).getNombre()
                                + "\"?", "Eliminar Registro", JOptionPane.YES_NO_OPTION
                                , JOptionPane.QUESTION_MESSAGE)) == JOptionPane.YES_OPTION) {

                            if (this.servicioRestaurante.deletePlatoEspecial(clave).equals("FALLO")) {
                                JOptionPane.showMessageDialog(rootPane, "El registro no existe");
                            } else {
                                this.servicioRestaurante.fijarImagen(this.lblImagenEspecial, null, "FOTONULA");
                                this.crearTablaEspeciales();
                                JOptionPane.showMessageDialog(rootPane, "operacion exitosa");
                            } 
                        }
                    } catch (Exception ex) {
                        showConfirmDialog(null, "OPERACION FALLIDA", "Confirmar", OK_CANCEL_OPTION);
                    }
                    //Eventos de eliminar
                }
                if (value instanceof JCheckBox) {
                    //((JCheckBox)value).doClick();
                    var ch = (JCheckBox) value;
                    if (ch.isSelected() == true) {
                        ch.setSelected(false);
                    }
                    if (ch.isSelected() == false) {
                        ch.setSelected(true);
                    }
                }
            }
    }//GEN-LAST:event_tblEspecialesMouseClicked
    }


    private void btnAgregarRacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarRacionActionPerformed
        // TODO add your handling code here:
        AgregarRacion aux = new AgregarRacion(this.servicioRestaurante, this,idSeleccionado);
        aux.setVisible(true);
    }//GEN-LAST:event_btnAgregarRacionActionPerformed

    private void btnAddEspecialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddEspecialActionPerformed
        int menu_esp_id=indexMenuEspecial();
        AgregarEspecial add = new AgregarEspecial(this.servicioRestaurante, this,menu_esp_id);
        add.setVisible(true);
    }//GEN-LAST:event_btnAddEspecialActionPerformed

    private void cbxDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxDiaActionPerformed
        varDia = cbxDia.getSelectedItem().toString();
        try {
            crearTablaRaciones();
        } catch (Exception ex) {
            Logger.getLogger(FramePrincipalAdmin.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cbxDiaActionPerformed

    private void cbxRestauranteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxRestauranteActionPerformed
        String sel = cbxRestaurante.getSelectedItem().toString();
        for (int i = 0; i < idRestaurante.size() - 1; i++) {
            String[] dat = idRestaurante.get(i).split("-");
            if (dat[1].equals(sel)) {
                this.idSeleccionado = Integer.parseInt(dat[0]);
                this.lblTituloRestaurante.setText(sel);
                break;
            }
        }

        try {
            this.crearTablaRaciones();
            this.crearTablaEspeciales();
        } catch (Exception ex) {
            Logger.getLogger(FramePrincipalAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Restaurante aux = this.servicioRestaurante.getRestaurante(idSeleccionado);
            this.servicioRestaurante.fijarImagen(this.lblImagenInicio, aux.getImagen(), "FOTONULA");
        } catch (Exception ex) {
            Logger.getLogger(GUILogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cbxRestauranteActionPerformed

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        cerrarSesion();
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    private void btnCerrarSesion2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesion2ActionPerformed
        cerrarSesion();
    }//GEN-LAST:event_btnCerrarSesion2ActionPerformed

    private void cerrarSesion(){
        GUILogin guiLogin = new GUILogin();
        this.setVisible(false);
        JOptionPane.showMessageDialog(null, "Hasta pronto");
        guiLogin.setVisible(true);
        this.dispose();
    }
    private void serviceListarRaciones() throws Exception {
        if (varDia.equals("TODOS")) {
            this.raciones = servicioRestaurante.listMenuDayAll(idSeleccionado, "administrador");
        } else {
            this.raciones = servicioRestaurante.listMenuDay(idSeleccionado, varDia, "administrador");
        }
    }

    private void serviceListarEspeciales() throws Exception {
        //el numero debe reemplazarse por el codigo del restaurante proveniente de la base de datos
        this.especiales = servicioRestaurante.listMenuSpecial(idSeleccionado, "administrador");
    }

    public void crearTablaRaciones() throws Exception {
        this.tblRaciones.removeAll();
        this.serviceListarRaciones();
        tabRaciones.ver_tabla(tblRaciones, raciones);
    }

    public void crearTablaEspeciales() throws Exception {
        this.tblEspeciales.removeAll();
        this.serviceListarEspeciales();
        tabEspeciales.ver_tabla(tblEspeciales, especiales);
    }
    
    private void crearTablaPedidos() throws Exception{
        this.tblPedidos.removeAll();
        pedidos=servicioRestaurante.listPedido(idSeleccionado);
        tabPedidos.ver_tabla(tblPedidos, pedidos);
    }
    private void crearIndexRestaurante() {
        cbxRestaurante.removeAllItems();
        for (int i = 0; i < idRestaurante.size() - 1; i++) {
            String[] datos = idRestaurante.get(i).split("-");
            cbxRestaurante.addItem(datos[1]);
        }
    }
    
    private int indexMenuEspecial(){
        int valor=0;
        for (PlatoEspecial esp : especiales) {
            valor=esp.getMenuEsp();
            break;
        }
        return valor;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddEspecial;
    private javax.swing.JButton btnAgregarRacion;
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnCerrarSesion2;
    private javax.swing.JComboBox<String> cbxDia;
    private javax.swing.JComboBox<String> cbxRestaurante;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jtpPestanias;
    private javax.swing.JLabel lblDireccionUsuario;
    private javax.swing.JLabel lblFiltro;
    private javax.swing.JLabel lblImagenEspecial;
    private javax.swing.JLabel lblImagenInicio;
    private javax.swing.JLabel lblImagenRacion;
    private javax.swing.JLabel lblImagenUsuario;
    private javax.swing.JLabel lblInformacionUsuario;
    private javax.swing.JLabel lblNombreRestaurante;
    private javax.swing.JLabel lblNombreUsuario;
    private javax.swing.JLabel lblTipoUsuario1;
    private javax.swing.JLabel lblTituloRestaurante;
    private javax.swing.JPanel pnlEspeciales;
    private javax.swing.JPanel pnlFondoEspeciales;
    private javax.swing.JPanel pnlFondoRaciones;
    private javax.swing.JPanel pnlInfoUsuario;
    private javax.swing.JPanel pnlInicio;
    private javax.swing.JPanel pnlPedidos;
    private javax.swing.JPanel pnlRaciones;
    private javax.swing.JTable tblEspeciales;
    private javax.swing.JTable tblPedidos;
    private javax.swing.JTable tblRaciones;
    // End of variables declaration//GEN-END:variables
}

package agenda;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.io.*;
import java.util.*;

public class Agenda extends JFrame implements ActionListener{
    // variables de Swing
    private JMenuBar barra;
    private JMenu menu1, menu2;
    private JMenuItem mi1, mi2, mi3;
    private JTextField cpnombre, cptelefono, cpbuscar;
    private JLabel etnombre, ettelefono, imnombre, imtelefono, nombreapp, nombreautor, numversion, dibuscar;
    private JButton boton, nwboton, botonbuscar;

    // Resto de Variables
    File archivo = new File("/Users/alfredobravocuero/Proyectos Java/Agenda/pruebas/archivo.txt");
    Formatter nuevoarchivo;
    Scanner x;

    public Agenda(){
        setLayout(null);

        // Textos de Introduccion
        nombreapp = new JLabel("Agenda Telefonica");
        nombreapp.setBounds(10,10,180,30);
        add(nombreapp);

        nombreautor = new JLabel("Alfredo Bravo Cuero");
        nombreautor.setBounds(10,30,180,30);
        add(nombreautor);

        numversion = new JLabel("version 1.0");
        numversion.setBounds(10,50,180,30);
        add(numversion);

        // Menu superior
        barra = new JMenuBar();
        setJMenuBar(barra);
        menu1 = new JMenu("Archivo");
        barra.add(menu1);

        menu2 = new JMenu("Ayuda");
        barra.add(menu2);

        mi1 = new JMenuItem("Nuevo");
        mi1.addActionListener(this);
        menu1.add(mi1);

        mi2 = new JMenuItem("Buscar");
        mi2.addActionListener(this);
        menu1.add(mi2);

        mi3 = new JMenuItem("Salir");
        mi3.addActionListener(this);
        menu1.add(mi3);

    }

    public void actionPerformed(ActionEvent e) {
        Container f = this. getContentPane();

        if (e.getSource() == mi1){
            // Ocultar elementos del programa
            nombreapp.setVisible(false);
            nombreautor.setVisible(false);
            numversion.setVisible(false);

            // Formulario
            etnombre = new JLabel("Agregar Nombre");
            etnombre.setBounds(20,30,200, 30);
            add(etnombre);
            etnombre.setVisible(true);

            cpnombre = new JTextField();
            cpnombre.setBounds(160,30,200, 30);
            add(cpnombre);
            cpnombre.setVisible(true);

            ettelefono = new JLabel("Agregar Telefono");
            ettelefono.setBounds(20,60,200, 30);
            add(ettelefono);
            ettelefono.setVisible(true);

            cptelefono = new JTextField();
            cptelefono.setBounds(160,60,200, 30);
            add(cptelefono);
            cptelefono.setVisible(true);

            nwboton = new JButton("Crear");
            nwboton.setBounds(100,100,200,30);
            add(nwboton);
            nwboton.addActionListener(this);
            nwboton.setVisible(true);
        }

        if (e.getSource() == mi2){
            // Ocultar elementos previos
            nombreapp.setVisible(false);
            nombreautor.setVisible(false);
            numversion.setVisible(false);
            ettelefono.setVisible(false);
            etnombre.setVisible(false);
            cpnombre.setVisible(false);
            cptelefono.setVisible(false);
            nwboton.setVisible(false);

            // Formulario
            imnombre = new JLabel("Buscar por Nombre");
            imnombre.setBounds(20,30,200, 30);
            add(imnombre);
            imnombre.setVisible(true);

            cpbuscar = new JTextField();
            cpbuscar.setBounds(160,30,200, 30);
            add(cpbuscar);
            cpbuscar.setVisible(true);

            botonbuscar = new JButton("Cuscar");
            botonbuscar.setBounds(100,60,200,30);
            add(botonbuscar);
            botonbuscar.addActionListener(this);
            botonbuscar.setVisible(true);

        }

        if (e.getSource() == mi3){
            System.exit(0);
        }

        if (e.getSource() == nwboton){
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:8889/agenda", "root","root");
                Statement estado = con.createStatement();
                estado.executeUpdate("INSERT INTO datos VALUE ('4', ' "+cpnombre.getText()+"', '"+cptelefono.getText()+"')");
            }catch (SQLException ex){
                System.out.println("Error de MySQL");
            }catch (ClassNotFoundException err){
                err.printStackTrace();
            }catch (Exception err){
                System.out.println("Se ha encontrado un error inesperado que es: "+err.getMessage());
            }
        }

        if (e.getSource() == botonbuscar){
            // Conectar BD
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:8889/agenda", "root","root");
                Statement estado = con.createStatement();
                ResultSet resultado = estado.executeQuery("SELECT * FROM datos WHERE nombre = '"+ cpbuscar.getText() +"'");

                // Exportar el Resultado
                while (resultado.next()){
                    if (archivo.exists()){
                        if (archivo.canWrite()){
                            nuevoarchivo = new Formatter("/Users/alfredobravocuero/Proyectos Java/Agenda/pruebas/archivo.txt");
                            nuevoarchivo.format("%s %s %s", resultado.getString("nombre"),resultado.getString("telefono"), "telefono");
                            nuevoarchivo.close();
                        }else {
                            System.out.println("El archivo existe pero no puede ser escrito");
                        }
                    }else {
                        try{
                            nuevoarchivo = new Formatter("/Users/alfredobravocuero/Proyectos Java/Agenda/pruebas/archivo.txt");
                            nuevoarchivo.format("%s %s %s", resultado.getString("nombre"),resultado.getString("telefono"), "telefono");
                            nuevoarchivo.close();
                        }catch (Exception errr){
                            System.out.println("Error "+errr);
                        }

                    }
                }

            }catch (SQLException ex){
                System.out.println("Error de MySQL");
            }catch (ClassNotFoundException err){
                err.printStackTrace();
            }catch (Exception err){
                System.out.println("Se ha encontrado un error inesperado que es: "+err.getMessage());
            }

        }
    }

    public static void main(String[] args) {

        Agenda ventana = new Agenda();
        ventana.setBounds(10,20,400,250);
        ventana.setVisible(true);


    }


}

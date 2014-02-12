package agenda;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.io.*;
import java.util.*;

public class Agenda extends JFrame implements ActionListener{
    //Swing Vars
    private JMenuBar menu_bar;
    private JMenu menu;
    private JMenuItem m_item1,m_item2,m_item3;
    
    private JTextField new_name_field, new_tel_field, search_field;
    
    private JLabel new_name_label, new_tel_label, print_name, print_tel, program_name_label, author_label, version_label
            , dibusca;
    
    private JButton button, new_button, search_button;
    
    //Other variables
    File file = new File("~/test/file.txt");
    Formatter new_file;
    Scanner x;
    
    //Constructor
    public Agenda(){
        //Introduction
        setLayout(null);
        program_name_label = new JLabel("Agenda Telefónica");
            program_name_label.setBounds(0,0,180,30);
            add(program_name_label);        
        author_label = new JLabel("Jorge Paternina");
            author_label.setBounds(0,30,180,30);
            add(author_label);
        version_label = new JLabel("Version 0.1");
            version_label.setBounds(0,50,180,30);
            add(version_label);
        
        //Top menu
        menu_bar = new JMenuBar();
            setJMenuBar(menu_bar);
        menu = new JMenu("Archivo");
            menu_bar.add(menu);
            m_item1 = new JMenuItem("Nuevo");
            m_item1.addActionListener(this);
                menu.add(m_item1);
            m_item2 = new JMenuItem("Buscar");
            m_item2.addActionListener(this);
                menu.add(m_item2);
            m_item3 = new JMenuItem("Salir");
            m_item3.addActionListener(this);
                menu.add(m_item3);
    }
    
    //interface implementation
    public void actionPerformed(ActionEvent e){
        Container cont = this.getContentPane();
        if(e.getSource() == m_item1){
            //new element create form
            program_name_label.setVisible(false);
            author_label.setVisible(false);
            version_label.setVisible(false);
            
            //new user
            new_name_label = new JLabel("Nuevo Nombre:");
                new_name_label.setBounds(0,0,180,30);
                add(new_name_label);
                new_name_label.setVisible(true);
            new_name_field = new JTextField();
                new_name_field.setBounds(120,0,180,30);
                add(new_name_field);
                new_name_field.setVisible(true);
            new_tel_label = new JLabel("Nuevo Teléfono:");
                new_tel_label.setBounds(0,50,180,30);
                add(new_tel_label);
                new_tel_label.setVisible(true);
            new_tel_field = new JTextField();
                new_tel_field.setBounds(120,50,180,30);
                add(new_tel_field);
                new_tel_field.setVisible(true);
                
                //New user button
                new_button =  new JButton("Crear");
                    new_button.setBounds(100,120,200,30);
                    add(new_button);
                    new_button.addActionListener(this);
                    new_button.setVisible(true);
                
        }
        if(e.getSource() == m_item2){
            //Search form
            program_name_label.setVisible(false);
            author_label.setVisible(false);
            version_label.setVisible(false);
            new_name_label.setVisible(false);
            new_name_field.setVisible(false);
            new_tel_label.setVisible(false);
            new_tel_field.setVisible(false);
            new_button.setVisible(false);
            //Form
            print_name = new JLabel("Nuevo Nombre:");
                print_name.setBounds(0,0,180,30);
                add(print_name);
                print_name.setVisible(true);
            search_field = new JTextField();
                search_field.setBounds(120,0,180,30);
                add(search_field);
                search_field.setVisible(true);
            //search user button
            search_button =  new JButton("Buscar");
                search_button.setBounds(100,50,200,30);
                add(search_button);
                search_button.addActionListener(this);
                search_button.setVisible(true);
        }
        if(e.getSource() == m_item3){
            System.exit(0);
        }
        if(e.getSource()==new_button){
            //New user button actions
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql:/127.0.0.1/agendatelefonica","root","");
                
                Statement stmt  = con.createStatement();
                stmt.executeUpdate("INSERT INTO agenda VALUES ('2', '"+new_name_field.getText()+"', '"+new_tel_field.getText()+
                                    "')"
                                   );
            }catch(SQLException ex){
                System.out.println("Mysql error");
            }catch(ClassNotFoundException er){
                er.printStackTrace();
            }catch(Exception err){
                System.out.println("Se ha encontrado un error, "+err.getMessage());
            }
        }
        if(e.getSource() == search_button){
            //Search user button actions
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql:/127.0.0.1/agendatelefonica","root","");
                
                Statement stmt  = con.createStatement();
                ResultSet result = stmt.executeQuery("SELECT * FROM agenda WHERE nombew ='"+search_field.getText()+"'"
                );
                //Save result to file
                while(result.next()){
                    if(file.exists()){
                        if(file.canWrite()){
                            new_file = new Formatter("~/test/file.txt");
                            new_file.format("%s %s %s",result.getString("nombre"), result.getString("telefono"), "telefono");
                            new_file.close();
                        }else{
                            System.out.println("El archivo existe pero no puedo escribir en él");
                        }
                    }else{
                        try{
                            new_file = new Formatter("~/test/file.txt");
                            new_file.format("%s %s %s",result.getString("nombre"), result.getString("telefono"), "telefono");
                            new_file.close();
                        }catch(Exception errr){
                            System.out.print("Error: "+errr.getMessage());
                        }
                    }
                }
            }catch(SQLException ex){
                System.out.println("Mysql error");
            }catch(ClassNotFoundException er){
                er.printStackTrace();
            }catch(Exception err){
                System.out.println("Se ha encontrado un error, "+err.getMessage());
            }
        }
        
    }
    
    /*public static void main(String[] args) {
        Agenda instance_agenda = new Agenda();
        
        instance_agenda.setBounds(653,350,640,250);
        instance_agenda.setVisible(true);
        instance_agenda.setResizable(false);
    }*/
    
    public void ventanaNimbus(){
        Agenda instance_agenda = new Agenda();
        
        instance_agenda.setBounds(653,350,640,250);
        instance_agenda.setVisible(true);
        instance_agenda.setResizable(false);
    }
    
}

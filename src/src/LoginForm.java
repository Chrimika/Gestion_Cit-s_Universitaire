import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Scanner;

import Login.Batiment;
import Login.Chambres;
import Login.Lit;
import Login.User;

public class LoginForm extends JDialog{
    private JTextField tfEmail;
    private JPasswordField pfPassword;
    private JButton btnOk;
    private JButton btnCancel;
    private JPanel loginPanel;
    private JButton btnSignUp;

    public LoginForm(JFrame parent){
        super(parent);
        setTitle("Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(550, 550));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = tfEmail.getText();
                String password = String.valueOf(pfPassword.getPassword());

                user = getAuthenticatedUser(email, password);
                if (user != null){
                    dispose();
                    Menu menu = new Menu(null);
                }
                else{
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "Email or Password Invalid",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

            }
        });


        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                    RegistrationForm registrationForm = new RegistrationForm(null);
                    //registrationForm.setVisible(true);
                User user = registrationForm.user;
                    registrationForm.pack();
                    registrationForm.setLocationRelativeTo(null);

            }
        });

        setVisible(true);
    }

    public User user;
    private User getAuthenticatedUser(String email, String password){
        User user = null;

        final String DB_URL = "jdbc:mysql://localhost/gestion_cites";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            //Connected to db successfuly...

            Statement stat  = conn.createStatement();
            String sql = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.name = resultSet.getString("name");
                user.email = resultSet.getString("email");
                user.phone = resultSet.getString("phone");
                user.address = resultSet.getString("address");
                user.password = resultSet.getString("password");
            }

            stat.close();
            conn.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }


    public static void enregistrements(){
        Scanner sc = new Scanner(System.in);
        int choix = 0, rec = 0;

        System.out.println("*enregistrements*\n\n" +
                "Voulez vous enregistrer : \n\n" +
                "1 - Nouveau Batiment \n" +
                "2 - Nouvelle Chambre\n" +
                "3 - Nouveau Lit\n" +
                "4 - Retour");
        choix = sc.nextInt();
        switch (choix){
            case 1:
                do{
                    System.out.println("Enregistrer Batiment");
                    Batiment.EnregistrerBatiment();
                    System.out.println("Re-enregistrer ? :\n" +
                            "1 - oui\n" +
                            "0 - non\n");
                    rec =sc.nextInt();
                }while (rec == 1);
                LoginForm.enregistrements();
                break;
            case 2:
                do{
                    System.out.println("Enregistrer Chambre");
                    Batiment.AjouterChambre();
                    System.out.println("Re-enregistrer ? :\n" +
                            "1 - oui\n" +
                            "0 - non\n");
                    rec = sc.nextInt();
                }while (rec == 1);
                LoginForm.enregistrements();
                break;
            case 3:
                do{
                    System.out.println("Enregistrer Lits");
                    Chambres.AjoutLit();
                    System.out.println("Re-enregistrer ? :\n" +
                            "1 - oui\n" +
                            "0 - non\n");
                    rec = sc.nextInt();
                }while (rec == 1);
                LoginForm.enregistrements();
                break;
            case 4:
                LoginForm.mains();
                break;
            default:
                System.out.println("choix non pris en compte");
                LoginForm.consultations();
        }
    }

    public static void consultations(){
        Scanner sc = new Scanner(System.in);
        int choix = 0, rec = 0;

        System.out.println("*consultations*\n\n" +
                "Voulez vous consulter : \n\n" +
                "1 - Batiments Enregistree\n" +
                "2 - Chambres enregistree\n" +
                "3 - Lits Ajoutee\n" +
                "4 - Retour");
        choix = sc.nextInt();
        switch (choix){
            case 1:
                do{
                    System.out.println("Consultation Batiment");
                    Batiment.ConsulterEtat();
                    System.out.println("Voulez vous recomencer ? :\n" +
                            "1 - oui\n" +
                            "0 - non\n");
                    rec =sc.nextInt();
                }while (rec == 1);
                LoginForm.consultations();
                break;
            case 2:
                do{
                    System.out.println("Affichage Chambre");
                    Chambres.AfficherChambre();
                    System.out.println("Voulez vous recomencer ? :\n" +
                            "1 - oui\n" +
                            "0 - non\n");
                    rec = sc.nextInt();
                }while (rec == 1);
                LoginForm.consultations();
                break;
            case 3:
                do{
                    System.out.println("Affichage Lits");
                    Lit.AfficheLit();
                    System.out.println("Voulez vous recomencer ? :\n" +
                            "1 - oui\n" +
                            "0 - non\n");
                    rec =sc.nextInt();
                }while (rec == 1);
                LoginForm.consultations();
                break;
            case 4:
                LoginForm.mains();
                break;
            default:
                System.out.println("choix non pris en compte");
                LoginForm.consultations();
        }
    }

    public static void suppressions(){
        Scanner sc = new Scanner(System.in);
        int choix = 0, rec = 0;

        System.out.println("*Suppresions*\n\n" +
                "Voulez vous consulter : \n\n" +
                "1 - Batiments\n" +
                "2 - Lits\n" +
                "3 - Retour");
        choix = sc.nextInt();
        switch (choix){
            case 1:
                do{
                    System.out.println("Suppresion Batiment");
                    Batiment.SuppressionBatiment();
                    System.out.println("Voulez vous recomencer ? :\n" +
                            "1 - oui\n" +
                            "0 - non\n");
                    rec =sc.nextInt();
                }while (rec == 1);
                LoginForm.consultations();
                break;
            case 2:
                do{
                    System.out.println("Suppresion Lit");
                    Chambres.SupprimerLit();
                    System.out.println("Voulez vous recomencer ? :\n" +
                            "1 - oui\n" +
                            "0 - non\n");
                    rec = sc.nextInt();
                }while (rec == 1);
                LoginForm.consultations();
                break;
            case 3:
                LoginForm.mains();
                break;
            default:
                System.out.println("choix non pris en compte");
                LoginForm.consultations();
        }
    }

    public User currentUser(){
        return this.user;
    }

    public static void Affectation(){
        System.out.println("En cours d'implementation...");
    }
    public static void mains() {
        LoginForm loginForm = new LoginForm(null);
        User user = loginForm.user;
        if (user != null) {
            System.out.println("Bienvenue Mr " + user.name);
            Scanner sc = new Scanner(System.in);
            int choix = 0, rec = 0;
            System.out.println("__________Bienvenue Mr " + user.name + "__________\n\n" +
                    "*Menue Principale*\n\n" +
                    "Que Voulez vous faire ? :\n" +
                    "1 - Consultations\n" +
                    "2 - Affectations\n" +
                    "3 - Enregistrements\n" +
                    "4 - Suppressions\n" +
                    "5 - Quiter");
            choix = sc.nextInt();
            switch (choix) {
                case 1:
                    consultations();
                case 2:
                    Affectation();
                    break;
                case 3:
                    enregistrements();
                    break;
                case 4:
                    suppressions();
                    break;

                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Choix non pris en compte reccomencez");
                    LoginForm.mains();
            }

        }
        else{
            System.out.println("Authentication canceled");
        }
    }

    public static void main(String[] args) {
        LoginForm loginForm = new LoginForm(null);
    }
}

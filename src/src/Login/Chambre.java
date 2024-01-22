package Login;

import java.sql.*;
import java.util.Scanner;

public class Chambre {
    public String code;
    public int batiment;
    public int niv;
    Chambre(String code, int batiment, int niv){
        this.code = code;
        this.batiment = batiment;
        this.niv = niv;
    }
    public static void AjoutLit() {
        System.out.println("*** ajout lits ***");
        final String DB_URL = "jdbc:mysql://localhost/gestion_cites";
        final String USERNAME = "root";
        final String PASSWORD = "";
        Scanner sc = new Scanner(System.in);

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stat = conn.createStatement();
            String sql = "SELECT * FROM chambres";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Chambres Repertories\n");
            System.out.println("id | code ");
            while (resultSet.next()){
                int id_chambre = resultSet.getInt("id");
                String code= resultSet.getString("code");

                System.out.println(id_chambre + " | " + code);
                System.out.println("--------------------");
            }
            System.out.println("Dans quelle chambre souhaitez vous ajouter ce lit(id)?");
            int id_chambre = sc.nextInt();

            try{
                Connection conn2 = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                Statement stat2 = conn.createStatement();
                String sql2 = "SELECT COUNT(*) FROM lits WHERE id_chambre = ?";

                PreparedStatement preparedStatement1 = conn2.prepareStatement(sql2);
                preparedStatement1.setInt(1,id_chambre);
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                if (resultSet1.next()){
                    int count_chambres = resultSet1.getInt(1);
                    if(count_chambres>=2){
                        System.out.println("Ajout impossible chambre pleine");
                    }else{
                        System.out.println("Numero du lit (1 ou 2)?");
                        int lit = sc.nextInt();
                        if(lit>=1 && lit<3){
                            try{
                                Connection conn3 = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                                Statement stat3 = conn.createStatement();
                                String sql3 = "INSERT INTO lits (Num, id_chambre) VALUES(?,?)";
                                PreparedStatement preparedStatement2 = conn2.prepareStatement(sql3);
                                preparedStatement2.setInt(1,lit);
                                preparedStatement2.setInt(2,id_chambre);
                                int count = preparedStatement2.executeUpdate();
                                if(count>0){
                                    System.out.println("Lit ajoute avec success");
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }else{
                            System.out.println("Choix non pris en compte(1 ou 2)");
                        }
                    }
                }

            }catch (Exception e){
                System.out.println("Choix indisponible");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static void SupprimerLit(){
        System.out.println("*** ajout lits ***");
        final String DB_URL = "jdbc:mysql://localhost/gestion_cites";
        final String USERNAME = "root";
        final String PASSWORD = "";
        Scanner sc = new Scanner(System.in);

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stat = conn.createStatement();
            String sql = "SELECT * FROM lits";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Chambres Repertories\n");
            System.out.println("id | id_chambre ");
            while (resultSet.next()){
                int id_lit = resultSet.getInt("id");
                String id_chambre= resultSet.getString("id_chambre");

                System.out.println(id_lit + " | " + id_chambre);
                System.out.println("--------------------");
            }
            System.out.println("Quelle lit souhaitez vous supprimmer(id)?");
            int id_lit = sc.nextInt();

            try{
                Connection conn2 = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                Statement stat2 = conn.createStatement();
                String sql2 = "DELETE FROM lits WHERE id = ?";

                PreparedStatement preparedStatement1 = conn2.prepareStatement(sql2);
                preparedStatement1.setInt(1,id_lit);
                int count = preparedStatement1.executeUpdate();
                if(count>0){
                    System.out.println("Suppression effectuee avec succes");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
    }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static void AfficherChambre(){
            final String DB_URL = "jdbc:mysql://localhost/gestion_cites";
            final String USERNAME = "root";
            final String PASSWORD = "";
            Scanner sc = new Scanner(System.in);
            System.out.println("**** Ajout chambre ****");

            try {
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                Statement stat = conn.createStatement();
                String sql = "SELECT * FROM batiments";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                System.out.println("Batiments Repertories\n");
                System.out.println("id | nbr_niv | code ");
                while (resultSet.next()) {
                    int id_bat = resultSet.getInt("id");
                    String code = resultSet.getString("code");
                    int niveau = resultSet.getInt("nbr_niv");
                    System.out.println(id_bat + " | " + niveau + " | " + code);
                    System.out.println("--------------------");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
    }

}

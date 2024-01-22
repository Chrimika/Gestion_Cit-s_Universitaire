package Login;
import java.sql.*;
import java.util.Scanner;
public class Batiment {
    public String code;
    public String libelle;
    public int nb_niv;
    public int nb_chambres;
    public int nb_lits_chambres;
    public int prix_chambres;

    //setters

    void SetCode(String code){
        this.code = code;
    }
    void SetLibelle(String libelle){
        this.libelle = libelle;
    }
    void SetNiv(int nb_niv){
        this.nb_niv = nb_niv;
    }
    void SetChambre(int nb_chambres){
        this.nb_chambres = nb_chambres;
    }
    void SetLits(String code){
        this.nb_lits_chambres = nb_lits_chambres;
    }
    void SetPrix(int prix_chambres){
        this.prix_chambres = prix_chambres;
    }
    Batiment(String code, String libelle, int nb_niv, int nb_chambres, int nb_lits_chambres, int prix_chambres){
        this.code = code;
        this.libelle = libelle;
        this.nb_niv = nb_niv;
        this.nb_chambres = nb_chambres;
        this.nb_lits_chambres = nb_lits_chambres;
        this.prix_chambres = prix_chambres;
    }

    public static void EnregistrerBatiment(){
        final String DB_URL = "jdbc:mysql://localhost/gestion_cites";
        final String USERNAME = "root";
        final String PASSWORD = "";
        Scanner sc = new Scanner(System.in);


        System.out.println("***** Enregistrement Batiments *****");
        System.out.println("Entrez le code du batiment svp :");
        String code = sc.nextLine();
        System.out.println("Entrez des details sur ce batiment :");
        String libelle = sc.nextLine();
        System.out.println("Entrez le nombres detages max pour ce batiment :");
        int nb_niv = sc.nextInt();
        System.out.println("Entrez le nombres de chambres dun niveau de ce Batiment :");
        int nb_chambres = sc.nextInt();
        System.out.println("Entrez le nombre de lits par chambres dans ce batiment :");
        int nb_lit_chambres = sc.nextInt();
        System.out.println("Entrez le prix des chambres dans ce batiment:");
        int prix_chambres = sc.nextInt();

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stat = conn.createStatement();
            String sql = "INSERT INTO batiments (code, prix_chambres, localisation, nbr_chambres, nbr_lits_chambres, nbr_niv)" +
                         "VALUES(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,code);
            preparedStatement.setDouble(2,prix_chambres);
            preparedStatement.setString(3,libelle);
            preparedStatement.setInt(4,nb_chambres);
            preparedStatement.setInt(5,nb_lit_chambres);
            preparedStatement.setInt(6,nb_niv);

            int addedRows = preparedStatement.executeUpdate();
            if(addedRows>0){
                System.out.println("["+code+"] a ete enregistrer avec success merci");
            }
            stat.close();
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void ConsulterEtat(){
        final String DB_URL = "jdbc:mysql://localhost/gestion_cites";
        final String USERNAME = "root";
        final String PASSWORD = "";
        Scanner sc = new Scanner(System.in);

        System.out.println("****** Consultation Etat Du Batiment *****");

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stat = conn.createStatement();
            String sql = "SELECT * FROM batiments";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Batiments Repertories\n");
            System.out.println("id | code ");
            while (resultSet.next()){
                int id_bat = resultSet.getInt("id");
                String code = resultSet.getString("code");

                System.out.println(id_bat + " | " + code);
                System.out.println("--------------------");
            }
            System.out.println("Quelle batiment voulez vous consulter(code)?");
            String code = sc.nextLine();

            try{
                Connection conn2 = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                Statement stat2 = conn.createStatement();
                String sql2 = "SELECT * FROM batiments WHERE code = ?";

                PreparedStatement preparedStatement1 = conn2.prepareStatement(sql2);
                preparedStatement1.setString(1,code);
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                while (resultSet1.next()){
                    int id_bat = resultSet1.getInt("id");
                    String codee = resultSet1.getString("code");
                    int prix_chambres = resultSet1.getInt("prix_chambres");
                    String libelee = resultSet1.getString("localisation");
                    int nbr_chambres = resultSet1.getInt("nbr_chambres");
                    int nbr_lits_chambres = resultSet1.getInt("nbr_lits_chambres");
                    int nbr_niv = resultSet1.getInt("nbr_niv");

                    System.out.println( "Nom : "+codee+
                                        "\nLibelee :"+libelee+
                                        "\nNombres De Chambres Par Etages :"+nbr_chambres+
                                        "\nNombres de niveau du batiment :"+nbr_niv+
                                        "\nNombres de Lits par chambre de ce batiment :"+nbr_lits_chambres
                    );
                }

            }catch (Exception e){
                System.out.println("Choix indisponible");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void SuppressionBatiment(){
        final String DB_URL = "jdbc:mysql://localhost/gestion_cites";
        final String USERNAME = "root";
        final String PASSWORD = "";
        Scanner sc = new Scanner(System.in);

        System.out.println("****** Suppression *****");

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stat = conn.createStatement();
            String sql = "SELECT * FROM batiments";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Batiments Repertories\n");
            System.out.println("id | code ");
            while (resultSet.next()){
                int id_bat = resultSet.getInt("id");
                String code = resultSet.getString("code");

                System.out.println(id_bat + " | " + code);
                System.out.println("--------------------");
            }
            System.out.println("Quelle batiment voulez vous supprimer(code)?");
            String code = sc.nextLine();

            try{
                Connection conn2 = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                Statement stat2 = conn.createStatement();
                String sql2 = "DELETE FROM batiments WHERE code = ?";

                PreparedStatement preparedStatement1 = conn2.prepareStatement(sql2);
                preparedStatement1.setString(1,code);
                int count = preparedStatement1.executeUpdate();
                if(count>0){
                    System.out.println("Suppresion effectue avec succes");
                }

            }catch (Exception e){
                System.out.println("Choix indisponible");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void ModifBatiment(){
        final String DB_URL = "jdbc:mysql://localhost/gestion_cites";
        final String USERNAME = "root";
        final String PASSWORD = "";
        Scanner sc = new Scanner(System.in);

        System.out.println("****** Consultation Etat Du Batiment *****");

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stat = conn.createStatement();
            String sql = "SELECT * FROM batiments";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Batiments Repertories\n");
            System.out.println("id | code ");
            while (resultSet.next()){
                int id_bat = resultSet.getInt("id");
                String code = resultSet.getString("code");

                System.out.println(id_bat + " | " + code);
                System.out.println("--------------------");
            }
            System.out.println("Quelle batiment voulez vous Modifier(code)?");
            String code = sc.nextLine();

            try{
                System.out.println("Entrez le nv code du batiment svp :");
                String codee = sc.nextLine();
                System.out.println("Entrez les nv details sur ce batiment :");
                String libelle = sc.nextLine();
                System.out.println("Entrez le nv nombres detages max pour ce batiment :");
                int nb_niv = sc.nextInt();
                System.out.println("Entrez le nv nombres de chambres dun niveau de ce Batiment :");
                int nb_chambres = sc.nextInt();
                System.out.println("Entrez le nv nombre de lits par chambres dans ce batiment :");
                int nb_lit_chambres = sc.nextInt();
                System.out.println("Entrez le nv prix des chambres dans ce batiment:");
                int prix_chambres = sc.nextInt();

                Connection conn2 = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                Statement stat2 = conn.createStatement();
                String sql2 = "UPDATE batiments SET code = ?, prix_chambres = ?, localisation = ?, nbr_chambres = ?, nbr_lits_chambres = ?, nbr_niv = ?";

                PreparedStatement preparedStatement1 = conn2.prepareStatement(sql2);
                preparedStatement1.setString(1,code);
                preparedStatement1.setDouble(2,prix_chambres);
                preparedStatement1.setString(3,libelle);
                preparedStatement1.setInt(4,nb_chambres);
                preparedStatement1.setInt(5,nb_lit_chambres);
                preparedStatement1.setInt(6,nb_niv);
                int count = preparedStatement1.executeUpdate();
                if(count>0){
                    System.out.println("Modifications effectue avec succes");
                }

            }catch (Exception e){
                System.out.println("Choix indisponible");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void AjouterChambre(){
        final String DB_URL = "jdbc:mysql://localhost/gestion_cites";
        final String USERNAME = "root";
        final String PASSWORD = "";
        Scanner sc = new Scanner(System.in);
        System.out.println("**** Ajout chambre ****");

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stat = conn.createStatement();
            String sql = "SELECT * FROM batiments";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Batiments Repertories\n");
            System.out.println("id | nbr_niv | code ");
            while (resultSet.next()){
                int id_bat = resultSet.getInt("id");
                String code = resultSet.getString("code");
                int niveau = resultSet.getInt("nbr_niv");
                System.out.println(id_bat + " | " + niveau + " | " + code);
                System.out.println("--------------------");
            }
            System.out.println("Dans Quelle batiment souhaitez vous l'ajouter(id)?");
            int id_bat = sc.nextInt();

            System.out.println("Dans Quelle niveau du batiment souhaitez vous inserer la chambre?");
            int niv = sc.nextInt();
            try{
                Connection conn2 = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                Statement stat2 = conn.createStatement();
                String sql2 = "SELECT COUNT(*) FROM chambres WHERE id = ? AND niv = ?";

                PreparedStatement preparedStatement1 = conn2.prepareStatement(sql2);
                preparedStatement1.setInt(1,id_bat);
                preparedStatement1.setInt(2,niv);
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                try{
                    Connection conn4 = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                    Statement stat4 = conn.createStatement();
                    String sql4 = "SELECT nbr_niv FROM batiments WHERE id = ?";

                    PreparedStatement preparedStatement3 = conn2.prepareStatement(sql4);
                    preparedStatement3.setInt(1,id_bat);
                    ResultSet resultSet2 = preparedStatement3.executeQuery();
                    while (resultSet2.next()){
                        int count_niv = resultSet2.getInt("nbr_niv");
                        System.out.println(count_niv);
                        if (resultSet1.next()){
                            int count = resultSet1.getInt(1);
                            System.out.println(count);
                                try{
                                    Connection conn3 = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                                    Statement stat3 = conn.createStatement();
                                    System.out.println("Entrez le code de la chambre a ajouter");
                                    String code_chambre = sc.next();
                                    String sql3 = "INSERT INTO chambres (code, id_batiment, niv)" +
                                            "VALUES (?,?,?)";
                                    PreparedStatement preparedStatement2 = conn2.prepareStatement(sql3);
                                    preparedStatement2.setString(1,code_chambre);
                                    preparedStatement2.setInt(2,id_bat);
                                    preparedStatement2.setInt(3,niv);

                                    int rows = preparedStatement2.executeUpdate();
                                    if(rows>0){
                                        System.out.println("Chambre ajouter avec success");
                                    }
                                }catch (Exception e){
                                    System.out.println("Choix indisponible");
                                }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }catch (Exception e){
                System.out.println("Choix indisponible");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AjouterChambre();
    }
}

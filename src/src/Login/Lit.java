package Login;

import java.sql.*;
import java.util.Scanner;

public class Lit {
    public int Num;
    public int chambre;

    public static void AfficheLit() {
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
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

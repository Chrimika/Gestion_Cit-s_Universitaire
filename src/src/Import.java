import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Iterator;
import java.util.Vector;

public class Import extends JDialog{
    private JPanel ImportForm;
    private JButton litsButton;
    private JButton chambresButton;
    private JButton batimentsButton;
    private JButton importerButton;
    private JButton importButton;
    public JTable tableEtudiant;

    public void VotreClasseUI() {
        // Appeler une méthode pour initialiser la table avec des données de la base de données
        initTableData();
    }

    private void initTableData() {
        // Créer un modèle de table personnalisé
        MyTableModel tableModel = new MyTableModel("SELECT id, Mat, Nom, handicap, sexe, age, niveau FROM etudiants");

        // Appliquer le modèle à la table
        tableEtudiant.setModel(tableModel);

    }


    public Import(JFrame parent) {
        super(parent);
        setTitle("Import");
        setContentPane(ImportForm);
        setMinimumSize(new Dimension(700, 550));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    importButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
                choisirFichierEtInserer();
        }
    });


    litsButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            Lits lits = new Lits(null);
        }
    });
    chambresButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            Chambre chambre = new Chambre(null);
        }
    });
    batimentsButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Menu menu = new Menu(null);
        }
    });
    VotreClasseUI();
    setVisible(true);
}
    private void choisirFichierEtInserer() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(this);
        File fichierExcel = fileChooser.getSelectedFile();

        if (fichierExcel != null) {
            insererDonneesDepuisExcel(fichierExcel);
        }
        afficherMessageSucces();
    }
    private void insererDonneesDepuisExcel(File fichierExcel) {
        try (FileInputStream fichierEntree = new FileInputStream(fichierExcel)) {
            try (Workbook classeur = new HSSFWorkbook(fichierEntree)) {

                
                Sheet feuille = classeur.getSheetAt(0); // suppose que les données sont dans la première feuille

                // Parcours des lignes
                Iterator<Row> iterator = feuille.iterator();
                while (iterator.hasNext()) {
                    Row ligne = iterator.next();

                    // Lire les cellules et insérer les données dans votre base de données ou liste d'étudiants
                    Cell matCell = ligne.getCell(0);
                    Cell nomCell = ligne.getCell(1);
                    Cell handicapCell = ligne.getCell(2);
                    Cell sexeCell = ligne.getCell(3);
                    Cell ageCell = ligne.getCell(4);
                    Cell niveauCell = ligne.getCell(5);

                    // Insérez les données dans votre table Etudiant
                    insererDansTableEtudiant(matCell.getStringCellValue(), nomCell.getStringCellValue(),
                            handicapCell.getBooleanCellValue(), sexeCell.getStringCellValue(),
                            (int) ageCell.getNumericCellValue(), niveauCell.getStringCellValue());
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void insererDansTableEtudiant(String mat, String nom, boolean handicap, String sexe, int age, String niveau) {
        // Insérez les données dans votre base de données ou faites ce que vous avez besoin de faire
        // ...
        final String DB_URL = "jdbc:mysql://localhost/gestion_cites";
        final String USERNAME = "root";
        final String PASSWORD = "";
        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            //Connected to db successfuly...

            Statement stat  = conn.createStatement();
            String sql = "INSERT INTO etudiants(Mat, handicap, sexe, age, niveau, Nom) VALUES(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, mat);
            preparedStatement.setBoolean(2, handicap);
            preparedStatement.setString(3, sexe);
            preparedStatement.setInt(4, age);
            preparedStatement.setString(5, niveau);
            preparedStatement.setString(6, nom);

            int count = preparedStatement.executeUpdate();

            /*if (count>0) {
                afficherMessageSucces();
            }*/

            stat.close();
            conn.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        // Exemple d'affichage (à remplacer par votre logique spécifique)
        System.out.println("Insertion dans la table Etudiant : " + mat + ", " + nom + ", " + handicap + ", " + sexe + ", " + age + ", " + niveau);
    }
    private void afficherMessageSucces() {
        JOptionPane.showMessageDialog(this, "Données insérées avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
        setVisible(true);
    }


    /*public static void main(String[] args) {
        Import impor = new Import(null);
    }*/
}
class MyTableModel extends AbstractTableModel {
    private Vector<Vector<Object>> data;
    private Vector<String> columnNames;

    public MyTableModel(String requette) {
        // Initialiser le modèle avec les données de la base de données
        initDataFromDB(requette);
    }

    public void initDataFromDB(String requete) {
        data = new Vector<>();
        columnNames = new Vector<>();

        try {
            // Charger le driver JDBC et établir la connexion à la base de données
            String url = "jdbc:mysql://localhost/gestion_cites";
            String username = "root";
            String password = "";
            Connection connection = DriverManager.getConnection(url, username, password);

            // Exécuter une requête SQL pour récupérer les données de la table
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(requete);

            // Récupérer les noms de colonnes
            int columnCount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(resultSet.getMetaData().getColumnName(i));
            }
            DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
            // Récupérer les données de la table
            while (resultSet.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(resultSet.getObject(i));
                }
                data.add(row);
            }
            // Fermer les ressources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public Object getValueAt(int row, int column) {
        return data.get(row).get(column);
    }

    @Override
    public String getColumnName(int column) {
        return columnNames.get(column);
    }
}

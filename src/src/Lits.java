import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Lits extends JDialog{
    private JPanel LitForm;
    private JButton litsButton;
    private JButton chambresButton;
    private JButton batimentsButton1;
    private JButton importerButton;
    private JTextField NumTF;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JTable batiments;
    private JButton resetButton;
    public JComboBox<String> IdchambreCB;

    private void fillComboBox() {
        // Connexion à la base de données (vous devez remplacer ces informations par les vôtres)
        String url = "jdbc:mysql://localhost/gestion_cites";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            // Requête SQL pour récupérer les valeurs distinctes de la colonne id_chambre
            String sql = "SELECT code FROM chambres";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                // Ajoutez chaque valeur au modèle du JComboBox
                while (resultSet.next()) {
                    String code = resultSet.getString("code");
                    IdchambreCB.addItem(code);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void VotreClasseUI() {
        // Appeler une méthode pour initialiser la table avec des données de la base de données
        initTableData();
    }

    private void initTableData() {
        // Créer un modèle de table personnalisé
        MyTableModel tableModel = new MyTableModel("SELECT * FROM lits");

        // Appliquer le modèle à la table
        batiments.setModel(tableModel);
        batiments.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // S'assurer que l'événement est traité une seule fois lors de la sélection
                int selectedRow = batiments.getSelectedRow();
                if (selectedRow != -1) { // Vérifier si une ligne est réellement sélectionnée
                    updateTextBoxes(selectedRow);
                }
            }
        });
    }

    private void updateTextBoxes(int selectedRow) {
        Object value1 = batiments.getValueAt(selectedRow, 1);

        NumTF.setText(value1.toString());
    }
    public Lits(JFrame parent){
        super(parent);
        setTitle("Lits");
        setContentPane(LitForm);
        setMinimumSize(new Dimension(700, 550));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        fillComboBox();
        chambresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Chambre chambre = new Chambre(null);
                dispose();
            }
        });

        batimentsButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu menu = new Menu(null);
                dispose();
            }
        });
        importerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Import impor = new Import(null);
                dispose();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Lits lits = new Lits(null);
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String num = NumTF.getText();
                String code = IdchambreCB.getSelectedItem().toString();
                if(num.isEmpty()){
                    afficherMessageErreure("Veillez choisir une ligne");
                }else{
                    final String DB_URL = "jdbc:mysql://localhost/gestion_cites";
                    final String USERNAME = "root";
                    final String PASSWORD = "";
                    try{
                        Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                        Statement stat = conn.createStatement();
                        String sql = "SELECT id FROM chambres WHERE code = ?";
                        PreparedStatement preparedStatement = conn.prepareStatement(sql);
                        preparedStatement.setString(1,code);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        if(resultSet.next()){
                            int id_chambre = resultSet.getInt("id");
                            try{
                                Connection conn2 = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                                Statement stat2 = conn2.createStatement();
                                String sql2 = "UPDATE lits SET Num = ?, id_chambre = ?";
                                PreparedStatement preparedStatement2 = conn.prepareStatement(sql2);
                                preparedStatement2.setInt(1,Integer.parseInt(num));
                                preparedStatement2.setInt(2,id_chambre);
                                int count = preparedStatement2.executeUpdate();
                                if(count>0){
                                    afficherMessageErreure("Modifie avec succes");
                                }
                                else {
                                    afficherMessageErreure("Echecc");
                                }
                            }catch (Exception ex){
                                ex.printStackTrace();
                            }
                        }
                        else {
                            afficherMessageErreure("Echec");
                        }
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String num = NumTF.getText();
                String code = IdchambreCB.getSelectedItem().toString();
                if(num.isEmpty()){
                    afficherMessageErreure("Veillez choisir une ligne");
                }else{
                    final String DB_URL = "jdbc:mysql://localhost/gestion_cites";
                    final String USERNAME = "root";
                    final String PASSWORD = "";
                    try{
                        Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                        Statement stat = conn.createStatement();
                        String sql = "SELECT id FROM chambres WHERE code = ?";
                        PreparedStatement preparedStatement = conn.prepareStatement(sql);
                        preparedStatement.setString(1,code);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        if(resultSet.next()){
                            int id_chambre = resultSet.getInt("id");
                            try{
                                Connection conn2 = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                                Statement stat2 = conn2.createStatement();
                                String sql2 = "DELETE FROM lits WHERE Num = ? AND id_chambre = ?";
                                PreparedStatement preparedStatement2 = conn.prepareStatement(sql2);
                                preparedStatement2.setInt(1,Integer.parseInt(num));
                                preparedStatement2.setInt(2,id_chambre);
                                int count = preparedStatement2.executeUpdate();
                                if(count>0){
                                    afficherMessageErreure("Supprime avec succes");
                                }
                                else {
                                    afficherMessageErreure("Echecc");
                                }
                            }catch (Exception ex){
                                ex.printStackTrace();
                            }
                        }
                        else {
                            afficherMessageErreure("Echec");
                        }
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String num = NumTF.getText();
                String code = IdchambreCB.getSelectedItem().toString();
                if(num.isEmpty()){
                    afficherMessageErreure("Veillez choisir une ligne");
                }else{
                    final String DB_URL = "jdbc:mysql://localhost/gestion_cites";
                    final String USERNAME = "root";
                    final String PASSWORD = "";
                    try{
                        Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                        Statement stat = conn.createStatement();
                        String sql = "SELECT id FROM chambres WHERE code = ?";
                        PreparedStatement preparedStatement = conn.prepareStatement(sql);
                        preparedStatement.setString(1,code);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        if(resultSet.next()){
                            int id_chambre = resultSet.getInt("id");
                            try{
                                Connection conn2 = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                                Statement stat2 = conn.createStatement();
                                String sql2 = "INSERT INTO lits(Num, id_chambre) VALUES (?,?)";
                                PreparedStatement preparedStatement2 = conn.prepareStatement(sql2);
                                preparedStatement2.setInt(1,Integer.parseInt(num));
                                preparedStatement2.setInt(2,id_chambre);
                                int count = preparedStatement2.executeUpdate();
                                if(count>0){
                                    afficherMessageErreure("Insere avec succes");
                                }
                                else {
                                    afficherMessageErreure("Echec");
                                }
                            }catch (Exception ex){
                                ex.printStackTrace();
                            }
                        }
                        else {
                            afficherMessageErreure("Echec");
                        }
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        });
        VotreClasseUI();
        setVisible(true);
    }
    public void afficherMessageErreure(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.INFORMATION_MESSAGE);
        setVisible(true);
    }
    /*public static void main(String[] args) {
        Lits lits = new Lits(null);
    }*/
}

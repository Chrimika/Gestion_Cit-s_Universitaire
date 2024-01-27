import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Chambre extends JDialog{
    private JPanel ChambreForm;
    private JButton litsButton;
    private JButton chambresButton;
    private JButton batimentsButton1;
    private JTextField codeTF;
    private JTextField idBatTF;
    private JTextField NivTF;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JTable batiments;
    private JButton importerButton;
    private JButton resetButton;

    public void VotreClasseUI() {
        // Appeler une méthode pour initialiser la table avec des données de la base de données
        initTableData();
    }

    private void initTableData() {
        // Créer un modèle de table personnalisé
        MyTableModel tableModel = new MyTableModel("SELECT * FROM chambres");

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
        // Obtenez les valeurs de la ligne sélectionnée à partir du modèle de table
        Object value1 = batiments.getValueAt(selectedRow, 1); // Colonne 1
        Object value2 = batiments.getValueAt(selectedRow, 2); // Colonne 2
        Object value3 = batiments.getValueAt(selectedRow, 3); // Colonne 3
        // ... Ajoutez d'autres colonnes selon votre modèle

        // Mettez à jour les JTextField avec les valeurs
        codeTF.setText(value1.toString()); // Remplacez jTextField1 par votre premier JTextField
        idBatTF.setText(value2.toString()); // Remplacez jTextField2 par votre deuxième JTextField
        NivTF.setText(value3.toString());
        // ... Ajoutez d'autres mises à jour selon le nombre de colonnes

        // Ajoutez autant de mises à jour que nécessaire pour chaque colonne de votre table
    }


    public Chambre(JFrame parent){
        super(parent);
        setTitle("Chambres");
        setContentPane(ChambreForm);
        setMinimumSize(new Dimension(700, 550));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        batimentsButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu menu = new Menu(null);
                dispose();
            }
        });

        litsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Lits lits = new Lits(null);
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
        VotreClasseUI();

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String code = codeTF.getText();
                String id_Bat = idBatTF.getText();
                String niv = NivTF.getText();
                if(code.isEmpty() || id_Bat.isEmpty()  || niv.isEmpty()){
                    afficherMessageErreure("Veillez choisir une ligne");
                }else{
                    final String DB_URL = "jdbc:mysql://localhost/gestion_cites";
                    final String USERNAME = "root";
                    final String PASSWORD = "";

                    try{
                        Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                        Statement stat = conn.createStatement();
                        String sql = "UPDATE chambres SET code = ?, id_batiment = ?, niv = ?";
                        PreparedStatement preparedStatement = conn.prepareStatement(sql);
                        preparedStatement.setString(1,code);
                        preparedStatement.setInt(2,Integer.parseInt(id_Bat));
                        preparedStatement.setInt(3,Integer.parseInt(niv));
                        int count = preparedStatement.executeUpdate();
                        if(count>0){
                            afficherMessageErreure("Modifier avec succes");
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
                String code = codeTF.getText();
                String id_Bat = idBatTF.getText();
                String niv = NivTF.getText();
                if(code.isEmpty() || id_Bat.isEmpty()  || niv.isEmpty()){
                    afficherMessageErreure("Veillez choisir une ligne");
                }else{
                    final String DB_URL = "jdbc:mysql://localhost/gestion_cites";
                    final String USERNAME = "root";
                    final String PASSWORD = "";

                    try{
                        Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                        Statement stat = conn.createStatement();
                        String sql = "INSERT INTO chambres(code, id_batiment, niv) VALUES (?,?,?)";
                        PreparedStatement preparedStatement = conn.prepareStatement(sql);
                        preparedStatement.setString(1,code);
                        preparedStatement.setInt(2,Integer.parseInt(id_Bat));
                        preparedStatement.setInt(3,Integer.parseInt(niv));
                        int count = preparedStatement.executeUpdate();
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
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String code = codeTF.getText();
                String id_Bat = idBatTF.getText();
                String niv = NivTF.getText();
                if(code.isEmpty() || id_Bat.isEmpty()  || niv.isEmpty()){
                    afficherMessageErreure("Veillez choisir une ligne");
                }else{
                    final String DB_URL = "jdbc:mysql://localhost/gestion_cites";
                    final String USERNAME = "root";
                    final String PASSWORD = "";

                    try{
                        Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                        Statement stat = conn.createStatement();
                        String sql = "DELETE FROM chambres WHERE (code = ? AND id_batiment = ? AND niv = ?)";
                        PreparedStatement preparedStatement = conn.prepareStatement(sql);
                        preparedStatement.setString(1,code);
                        preparedStatement.setInt(2,Integer.parseInt(id_Bat));
                        preparedStatement.setInt(3,Integer.parseInt(niv));
                        int count = preparedStatement.executeUpdate();
                        if(count>0){
                            afficherMessageErreure("Supprimer avec succes");
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

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Chambre chambre = new Chambre(null);
            }
        });
        setVisible(true);
    }
    public void afficherMessageErreure(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.INFORMATION_MESSAGE);
        setVisible(true);
    }

    /*public static void main(String[] args) {
        Chambre chambre = new Chambre(null);
    }*/
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Menu extends JDialog{
    private JPanel MenuForm;
    private JButton litsButton;
    private JButton chambresButton;
    private JButton batimentsButton;
    private JTextField codeTF;
    private JTextField prixTF;
    private JTextField LibeleeTF;
    private JTextField nbr_litTF;
    private JTextField nbr_nivTF;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JTextField nbr_chambreTF;
    private JTable batiments;
    private JButton importerButton;
    private JButton resetButton;

    public void VotreClasseUI() {
        // Appeler une méthode pour initialiser la table avec des données de la base de données
        initTableData();
    }

    private void initTableData() {
        // Créer un modèle de table personnalisé
        MyTableModel tableModel = new MyTableModel("SELECT * FROM batiments");

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
        Object value4 = batiments.getValueAt(selectedRow, 4);
        Object value5 = batiments.getValueAt(selectedRow, 5);
        Object value6 = batiments.getValueAt(selectedRow, 6);
        // ... Ajoutez d'autres colonnes selon votre modèle

        // Mettez à jour les JTextField avec les valeurs
        codeTF.setText(value1.toString()); // Remplacez jTextField1 par votre premier JTextField
        prixTF.setText(value2.toString()); // Remplacez jTextField2 par votre deuxième JTextField
        LibeleeTF.setText(value3.toString());
        nbr_chambreTF.setText(value4.toString());
        nbr_litTF.setText(value5.toString());
        nbr_nivTF.setText(value6.toString());
        // ... Ajoutez d'autres mises à jour selon le nombre de colonnes

        // Ajoutez autant de mises à jour que nécessaire pour chaque colonne de votre table
    }

    public Menu(JFrame parent){
        super(parent);
        setTitle("Login");
        setContentPane(MenuForm);
        setMinimumSize(new Dimension(700, 550));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        chambresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Chambre chambre = new Chambre(null);
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

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String code = codeTF.getText();
                String prix = prixTF.getText();
                String libelle = LibeleeTF.getText();
                String nbr_chambres = nbr_chambreTF.getText();
                String nbr_Lit = nbr_litTF.getText();
                String nbr_niv = nbr_nivTF.getText();

                if(code.isEmpty() || prix.isEmpty()  || libelle.isEmpty() || nbr_chambres.isEmpty() || nbr_Lit.isEmpty() || nbr_niv.isEmpty()){
                    afficherMessageErreure("Veillez choisir une ligne");
                }else{
                    final String DB_URL = "jdbc:mysql://localhost/gestion_cites";
                    final String USERNAME = "root";
                    final String PASSWORD = "";

                    try{
                        Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                        Statement stat = conn.createStatement();
                        String sql = "INSERT INTO batiments(code, prix_chambres, localisation, nbr_chambres, nbr_lits_chambres, nbr_niv) VALUES (?,?,?,?,?,?)";
                        PreparedStatement preparedStatement = conn.prepareStatement(sql);
                        preparedStatement.setString(1,code);
                        preparedStatement.setInt(2,Integer.parseInt(prix));
                        preparedStatement.setString(3,libelle);
                        preparedStatement.setInt(4,Integer.parseInt(nbr_chambres));
                        preparedStatement.setInt(5,Integer.parseInt(nbr_Lit));
                        preparedStatement.setInt(6,Integer.parseInt(nbr_niv));
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


        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu menu = new Menu(null);
                dispose();
            }
        });
        setVisible(true);
    }
    public void afficherMessageErreure(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.INFORMATION_MESSAGE);
        setVisible(true);
    }
    /*public static void main(String[] args) {
        Menu menu = new Menu(null);
    }*/
}

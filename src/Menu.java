import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.JFileChooser;

public class Menu extends JFrame{
    private String projet = System.getProperty("user.dir");
    private String picture = System.getProperty("user.dir") + "/Picture/"; //Chemin vers répertoire Picture
    private Path pathSource;
    private Path pathDestination;

    //MenuBar
    private JMenuBar menuBar = new JMenuBar();
    private JMenu file = new JMenu("File");
    private JMenu edit = new JMenu("Edit");
    private JMenu view = new JMenu("View");
    private JMenu help = new JMenu("Help");

    private JMenuItem add_item = new JMenuItem("Add");
    private JMenuItem rename_item = new JMenuItem("Rename");
    private JMenuItem delete_item = new JMenuItem("Delete");
    private JMenuItem view_item = new JMenuItem("View");
    private JMenuItem help_item = new JMenuItem("Help");

    public Menu(Fenetre fenetre){

        //Mise en place du MenuBar
        this.menuBar.add(file);
        this.menuBar.add(edit);
        this.menuBar.add(view);
        this.menuBar.add(help);

        this.file.add(add_item);
        this.edit.add(rename_item);
        this.edit.add(delete_item);
        this.view.add(view_item);
        this.help.add(help_item);


        //Gere les actions des JMenuItems
        add_item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Add");
                boiteDialog();
                if(pathSource != null){
                    fenetre.getContentPane().setVisible(false);
                    fenetre.getContentPane().removeAll();
                    fenetre.getContentPane().validate();

                    // Fonction Copier_Coller
                    copier_collerFile(pathSource, pathDestination);
                    fenetre.getContentPane().removeAll();  // enlever les images précédentes
                    fenetre.affichage_image();

                    pathSource = null;
                    pathDestination = null;
                }
            }
        });

        rename_item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Rename");
                boiteDialog();
                if(pathSource != null){
                    // Rename
                    int indexRacine = pathSource.toString().lastIndexOf('\\') + 1;
                    String racinePathSource = pathSource.toString().substring(0, indexRacine);

                    int indexExtension = pathSource.toString().lastIndexOf('.');
                    String extention = pathSource.toString().substring(indexExtension);

                    JFrame frame = new JFrame("Inserer le nouveau nom du fichier");
                    String name = JOptionPane.showInputDialog(frame, "Nouveau nom : ");

                    String nomNouveau = racinePathSource + name + extention;

                    if(pathSource.toFile().renameTo(Paths.get(nomNouveau).toFile())) {

                        System.out.println("Success\nLe fichier a été renommé.");
                        fenetre.getContentPane().setVisible(false);
                        fenetre.getContentPane().removeAll();
                        fenetre.getContentPane().validate();
                    }
                    else {
                        System.out.println("Echec !\nLe fichier " + pathSource + " n'a pas été renommé.");
                    }

                    fenetre.affichage_image();

                    pathSource = null;
                    pathDestination = null;
                }
            }
        });

        delete_item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Delete!");
                boiteDialog();
                if(pathSource != null){
                    // Delete
                    fenetre.getContentPane().setVisible(false);
                    fenetre.getContentPane().removeAll();
                    fenetre.getContentPane().validate();

                    new File(pathSource.toString()).delete();
                    fenetre.affichage_image();

                    pathSource = null;
                    pathDestination = null;
                }
            }
        });

        view_item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //System.out.println("View");
                boiteDialog();
                if(pathSource != null){
                    // Fonction view pour ouvrir une image !
                    if(Desktop.getDesktop().isSupported(java.awt.Desktop.Action.OPEN)){
                        try {
                            java.awt.Desktop.getDesktop().open(new File(pathSource.toString()));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                    pathSource = null;
                    pathDestination = null;
                }
            }
        });

        help_item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                System.out.println("Help");
                JOptionPane messageBox = new JOptionPane();
                messageBox.showMessageDialog(null, "File :\n    Add permet d'ajouter une image\nEdit :\n    Rename pour renommer une image\n    Delete pour supprimer une image\nView :\n   View permet d'ouvrir les images\nHelp :\n   HELP !", "Help", JOptionPane.INFORMATION_MESSAGE);

            }
        });
    }

    // Getter menuBar
    public JMenuBar getmenuBar() {
        return menuBar;
    }

    public String getNamePicture(JFileChooser fichierChoisi) {
        if(fichierChoisi.toString().indexOf('/') != -1){
            int index = fichierChoisi.getSelectedFile().toString().lastIndexOf('/') + 1;
            String namePicture = fichierChoisi.getSelectedFile().toString().substring(index);
            return namePicture;
        }
        else {
            int index = fichierChoisi.getSelectedFile().toString().lastIndexOf('\\') + 1;
            String namePicture = fichierChoisi.getSelectedFile().toString().substring(index);
            return namePicture;
        }
    }

    public boolean checkExtensionImage(JFileChooser fichierChoisi) {
        int index = fichierChoisi.getSelectedFile().toString().lastIndexOf('.') + 1;
        String extension = fichierChoisi.getSelectedFile().toString().substring(index);
        if (extension != null) {
            if (extension.equals("tiff") ||
                    extension.equals("tif") ||
                    extension.equals("gif") ||
                    extension.equals("jpeg") ||
                    extension.equals("jpg") ||
                    extension.equals("png")) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    public void boiteDialog() {
        JFileChooser fichierChoisi = new JFileChooser(projet);
        int isSelect = fichierChoisi.showOpenDialog(null);

        if(isSelect == JFileChooser.APPROVE_OPTION && checkExtensionImage(fichierChoisi)){
            fichierChoisi.getSelectedFile();
            //System.out.println(fichierChoisi.getSelectedFile());  // Chemin absolu du fichier selectionné

            pathSource = Paths.get(fichierChoisi.getSelectedFile().toURI());  // Transforme JFileChooser en Path
            pathDestination = Paths.get(picture + getNamePicture(fichierChoisi));

        }
        else {
            System.out.println("non selectionner");

            if(!checkExtensionImage(fichierChoisi)){
                // Message d'erreur
                JOptionPane.showMessageDialog(null, "Le fichier selectionné doit être une image !", "Action impossible", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public boolean copier_collerFile(Path source, Path destination) {
        try {
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
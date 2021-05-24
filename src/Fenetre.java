import javax.swing.*;
import java.awt.*;
import java.io.File;


public class Fenetre extends JFrame{

    private String pathProjet = System.getProperty("user.dir");
    private String pathPicture = System.getProperty("user.dir") + "/Picture/"; //Chemin vers répertoire Picture
    private File directory = new File(pathPicture);
    private File[] filePicture = directory.listFiles();

    public Fenetre(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //END
        this.setTitle("SupPicture"); //Title
        this.setSize(1280, 1024);
        this.setLocationRelativeTo(null); //Position au centre
        this.setResizable(false); //non redimensionnable

        Menu menu = new Menu(this);
        this.setJMenuBar(menu.getmenuBar());

        affichage_image();

        this.setVisible(true);
    }

    public void affichage_image() {
        String pathPicture = System.getProperty("user.dir") + "/Picture/"; //Chemin vers répertoire Picture
        File directory = new File(pathPicture);
        File[] filePicture = directory.listFiles();

        this.setLayout(new GridLayout(3, 0));

        for (int i = 0; i < filePicture.length; i++) {
            ImageIcon icone = new ImageIcon(filePicture[i].toString());
            JLabel textLabel = new JLabel(filePicture[i].getName());
            textLabel.setIcon(icone);
            textLabel.setHorizontalTextPosition(JLabel.CENTER);
            textLabel.setVerticalTextPosition(JLabel.BOTTOM);

            this.getContentPane().add(textLabel);

        }

        this.setVisible(true);
        this.getContentPane().setVisible(true);
    }


}
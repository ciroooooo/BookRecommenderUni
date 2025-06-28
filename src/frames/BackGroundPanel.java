package frames;

import java.awt.*;
import javax.swing.JPanel;
class BackGroundPanel extends JPanel{
    private Image backgroundImage;

    public BackGroundPanel(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Disegna l'immagine di sfondo che riempie tutto il pannello
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
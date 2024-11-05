import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Fly extends JFrame {
    private JLabel lblMOSCA;
    private JLabel lblCoorMos;
    private JLabel lblCoorPunt;
    private boolean isDragging = false;
    private static final int DETECTION_RADIUS = 50; // Distancia la cual la mosca empieza a huir
    private static final int MOVE_SPEED = 10; // Velocidad de la mosca

    public Fly() {
        setTitle("Mosca que se Mueve");
        setSize(500, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Crear etiqueta para la mosca
        ImageIcon moscaIcon = new ImageIcon("C:/Users/CFGS/Desktop/fly.png");
        Image scaled = moscaIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        lblMOSCA = new JLabel(new ImageIcon(scaled));
        lblMOSCA.setBounds(50, 50, 50, 50);

        // Etiquetas para coordenadas
        lblCoorMos = new JLabel("Coordenadas Mosca: X=0 Y=0");
        lblCoorMos.setBounds(10, 400, 200, 20);

        lblCoorPunt = new JLabel("Coordenadas Puntero: X=0 Y=0");
        lblCoorPunt.setBounds(10, 420, 200, 20);

        // Agregar MouseListener para arrastrar
        lblMOSCA.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    isDragging = true;
                }
            }

            public void mouseReleased(MouseEvent e) {
                isDragging = false;
            }
        });

        // Agregar MouseMotionListener para seguimiento
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                actualizarCoordenadas(e);
                if (!isDragging) {
                    moveAwayFromCursor(e.getPoint());
                }
            }

            public void mouseDragged(MouseEvent e) {
                if (isDragging) {
                    lblMOSCA.setLocation(e.getX() - 25, e.getY() - 25);
                }
                actualizarCoordenadas(e);
            }
        });

        add(lblMOSCA);
        add(lblCoorMos);
        add(lblCoorPunt);
    }

    private static void cambiarIcono(JFrame frame, String rutaIcon) {
        ImageIcon icono = new ImageIcon(rutaIcon);
        frame.setIconImage(icono.getImage());

    }

    private void actualizarCoordenadas(MouseEvent e) {
        lblCoorMos.setText("Coordenadas Mosca: X=" + lblMOSCA.getX() + " Y=" + lblMOSCA.getY());
        lblCoorPunt.setText("Coordenadas Puntero: X=" + e.getX() + " Y=" + e.getY());
    }

    private void moveAwayFromCursor(Point cursorPoint) {
        Point flyCenter = new Point(
                lblMOSCA.getX() + lblMOSCA.getWidth() / 2,
                lblMOSCA.getY() + lblMOSCA.getHeight() / 2
        );

        double distance = cursorPoint.distance(flyCenter);

        if (distance < DETECTION_RADIUS) {
            // Calculate direction vector from cursor to fly
            double dx = flyCenter.x - cursorPoint.x;
            double dy = flyCenter.y - cursorPoint.y;

            // Normalize the vector and multiply by speed
            double length = Math.sqrt(dx * dx + dy * dy);
            dx = (dx / length) * MOVE_SPEED;
            dy = (dy / length) * MOVE_SPEED;

            // Calculate new position
            int newX = (int) (lblMOSCA.getX() + dx);
            int newY = (int) (lblMOSCA.getY() + dy);

            // Keep the fly within the frame bounds
            newX = Math.max(0, Math.min(newX, getWidth() - lblMOSCA.getWidth()));
            newY = Math.max(0, Math.min(newY, getHeight() - lblMOSCA.getHeight()));

            lblMOSCA.setLocation(newX, newY);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Fly frame = new Fly();
            frame.setVisible(true);
            cambiarIcono(frame, "C:\\Users\\CFGS\\Desktop\\icon.png");
        });
    }
}

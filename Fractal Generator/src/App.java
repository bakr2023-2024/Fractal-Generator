import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import org.apache.commons.numbers.complex.Complex;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class App {
    static Screen screen;
    static int delay = 2;
    static JComboBox<String> box;
    static JTextField constantFormula;
    static JTextField constantC;
    static JTextField constantPower;
    static JTextField constantZ;
    static JButton submit;
    static JLabel response;
    static JButton button, button2;
    static JLabel formulaLabel;
    static JLabel ZLabel;
    static JLabel CLabel;
    static JLabel PowerLabel;
    static private final double ZOOM_FACTOR = 0.9;
    static private final double PAN_FACTOR = 0.001;

    static class Screen extends JComponent implements MouseWheelListener, MouseListener, MouseMotionListener {
        int w, h;
        Fractals fractal;
        String set = "Multicorn";
        private int prevX;
        private int prevY;
        private int currX;
        private int currY;

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        public Screen() {
            this.addMouseListener(this);
            this.addMouseMotionListener(this);
            this.addMouseWheelListener(this);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            prevX = e.getX();
            prevY = e.getY();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            currX = e.getX();
            currY = e.getY();
            double deltaX = (currX - prevX) * PAN_FACTOR;
            double deltaY = (currY - prevY) * PAN_FACTOR;
            fractal.minRe -= deltaX;
            fractal.maxRe -= deltaX;
            fractal.minIm += deltaY;
            fractal.maxIm += deltaY;
            prevX = currX;
            prevY = currY;
            repaint();
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            int rotation = e.getWheelRotation();
            if (rotation > 0) {
                fractal.minRe *= ZOOM_FACTOR;
                fractal.maxRe *= ZOOM_FACTOR;
                fractal.minIm *= ZOOM_FACTOR;
                fractal.maxIm *= ZOOM_FACTOR;
            } else if (rotation < 0) {
                fractal.minRe /= ZOOM_FACTOR;
                fractal.maxRe /= ZOOM_FACTOR;
                fractal.minIm /= ZOOM_FACTOR;
                fractal.maxIm /= ZOOM_FACTOR;
            }
            repaint();
        }

        @Override
        public Dimension getPreferredSize() {
            this.w = 960;
            this.h = 720;
            fractal = new Fractals(w, h);
            return new Dimension(w, h);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            BufferedImage buffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = buffer.createGraphics();
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    if (this.set.equals("Multicorn")) {
                        g2d.setColor(fractal.getColorMulticorn(i, j));
                    } else if (this.set.equals("Julia")) {
                        g2d.setColor(fractal.getColorJulia(i, j));
                    } else if (this.set.equals("Burning Ship")) {
                        g2d.setColor(fractal.getColorBurningShip(i, j));
                    } else if (this.set.equals("Nova")) {
                        g2d.setColor(fractal.getColorNova(i, j));
                    } else if (this.set.equals("Phoenix")) {
                        g2d.setColor(fractal.getColorPhoenix(i, j));
                    } else if (this.set.equals("Collatz")) {
                        g2d.setColor(fractal.getColorCollatz(i, j));
                    } else if (this.set.equals("Magnet 1")) {
                        g2d.setColor(fractal.getColorMagnet1(i, j));
                    } else if (this.set.equals("Magnet 2")) {
                        g2d.setColor(fractal.getColorMagnet2(i, j));
                    }
                    g2d.fillRect(i, j, 1, 1);
                }
            }
            g.drawImage(buffer, 0, 0, null);
        }

        public static void main(String[] args) {
            JFrame frame = new JFrame("Fractal Generator");
            frame.setSize(1280, 720);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JPanel left = new JPanel();
            JPanel right = new JPanel();
            right.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.gridy = 0;
            frame.add(left, BorderLayout.WEST);
            frame.add(right, BorderLayout.EAST);
            screen = new Screen();
            left.add(screen, BorderLayout.CENTER);
            JLabel label = new JLabel("Fractal Generator");
            right.add(label, c);
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(1, 2));
            String[] sets = { "Multicorn", "Julia", "Burning Ship", "Nova", "Phoenix", "Collatz", "Magnet 1",
                    "Magnet 2" };
            box = new JComboBox<>(sets);
            JButton choice = new JButton("Load");
            panel.add(box);
            panel.add(choice);
            box.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String s = (String) box.getSelectedItem();
                    if (s.equals("Multicorn")) {
                        formulaLabel.setVisible(false);
                        constantFormula.setVisible(false);
                        constantC.setVisible(false);
                        CLabel.setVisible(false);
                        constantPower.setVisible(true);
                        PowerLabel.setVisible(true);
                        constantZ.setVisible(false);
                        ZLabel.setVisible(false);
                    } else if (s.equals("Julia") || s.equals("Magnet 1") || s.equals("Magnet 2")) {
                        formulaLabel.setVisible(false);
                        constantFormula.setVisible(false);
                        constantC.setVisible(true);
                        CLabel.setVisible(true);
                        constantPower.setVisible(false);
                        PowerLabel.setVisible(false);
                        constantZ.setVisible(false);
                        ZLabel.setVisible(false);
                    } else if (s.equals("Burning Ship") || s.equals("Collatz")) {
                        formulaLabel.setVisible(false);
                        constantFormula.setVisible(false);
                        constantC.setVisible(false);
                        CLabel.setVisible(false);
                        PowerLabel.setVisible(false);
                        constantPower.setVisible(false);
                        constantZ.setVisible(false);
                        ZLabel.setVisible(false);
                    } else if (s.equals("Nova")) {
                        formulaLabel.setVisible(true);
                        constantFormula.setVisible(true);
                        constantC.setVisible(true);
                        CLabel.setVisible(true);
                        PowerLabel.setVisible(true);
                        constantPower.setVisible(true);
                        constantZ.setVisible(false);
                        ZLabel.setVisible(false);
                    } else if (s.equals("Phoenix")) {
                        formulaLabel.setVisible(false);
                        constantFormula.setVisible(false);
                        constantC.setVisible(true);
                        CLabel.setVisible(true);
                        PowerLabel.setVisible(false);
                        constantPower.setVisible(false);
                        constantZ.setVisible(true);
                        ZLabel.setVisible(true);
                    }
                }
            });
            c.gridy = 1;
            right.add(panel, c);
            choice.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    submit.setEnabled(true);
                    screen.set = (String) box.getSelectedItem();
                    screen.fractal.resetVariables();
                    screen.repaint();
                }
            });
            c.gridy = 2;
            JPanel constantPanel = new JPanel();
            constantPanel.setLayout(new BoxLayout(constantPanel, BoxLayout.Y_AXIS));
            JLabel constantLabel = new JLabel("Constant value(s): ");
            formulaLabel = new JLabel("Formula: ");
            CLabel = new JLabel("C: ");
            PowerLabel = new JLabel("Power: ");
            ZLabel = new JLabel("Z: ");
            JPanel p1 = new JPanel(new GridLayout(1, 2));
            JPanel p2 = new JPanel(new GridLayout(1, 2));
            JPanel p3 = new JPanel(new GridLayout(1, 2));
            JPanel p4 = new JPanel(new GridLayout(1, 2));
            constantFormula = new JTextField("(1,0)z^3 (-1,0)z^0");
            constantC = new JTextField("(0,0)");
            constantPower = new JTextField("0");
            constantZ = new JTextField("(0,0)");
            p1.add(formulaLabel);
            p1.add(constantFormula);
            p2.add(CLabel);
            p2.add(constantC);
            p3.add(PowerLabel);
            p3.add(constantPower);
            p4.add(ZLabel);
            p4.add(constantZ);
            formulaLabel.setVisible(false);
            constantFormula.setVisible(false);
            constantC.setVisible(false);
            CLabel.setVisible(false);
            constantPower.setVisible(true);
            PowerLabel.setVisible(true);
            constantZ.setVisible(false);
            ZLabel.setVisible(false);
            submit = new JButton("Submit");
            response = new JLabel("");
            response.setFont(new Font("MV Boli", Font.PLAIN, 10));
            constantPanel.add(constantLabel);
            constantPanel.add(p1);
            constantPanel.add(p2);
            constantPanel.add(p3);
            constantPanel.add(p4);
            constantPanel.add(submit);
            constantPanel.add(response);
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (screen.set.equals("Nova")) {
                        try {
                            Fractals.formula = constantFormula.getText();
                            Polynomial pol = new Polynomial();
                            pol.parseFormula(Fractals.formula);
                            Fractals.constant = Complex.parse(constantC.getText());
                            Fractals.power = Double.parseDouble(constantPower.getText());
                            Fractals.constant2 = Complex.parse(constantZ.getText());
                            response.setText("done, please reload");
                        } catch (NumberFormatException ex) {
                            Fractals.formula = "(1,0)z^3 (-1,0)z^0";
                            Fractals.constant = Complex.ofCartesian(1, 0);
                            Fractals.constant2 = Complex.ofCartesian(1, 0);
                            Fractals.power = 0.5;
                            response.setText("invalid formula or A");
                        }
                    } else {
                        try {
                            Fractals.formula = "(1,0)z^3 (-1,0)z^0";
                            Fractals.constant = Complex.parse(constantC.getText());
                            Fractals.power = Double.parseDouble(constantPower.getText());
                            Fractals.constant2 = Complex.parse(constantZ.getText());
                            response.setText("done, please reload");
                        } catch (NumberFormatException ex) {
                            Fractals.constant = Complex.ofCartesian(-0.4, 0.6);
                            Fractals.power = 0.5;
                            Fractals.constant2 = Complex.ofCartesian(1, 0);
                            response.setText("Please enter a valid number");
                        }
                    }
                }
            });
            right.add(constantPanel, c);
            frame.pack();
        }
    }
}
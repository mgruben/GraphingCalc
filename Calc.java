import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/*
 * Copyright (C) 2016 Michael <GrubenM@GMail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 *
 * @author Michael <GrubenM@GMail.com>
 */
public class Calc extends JFrame {
    
    class GraphPanel extends JPanel {
        Tree tree = new Tree();
        public GraphPanel() {
            tree.parse("sin(x)/cos(x)");
        }
        public float f(float x) {
            return tree.calc(x);
        }
        public void setEqn(String eqn) {
            tree.parse(eqn);
        }
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Draw the graphing window
            g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            g.setColor(new Color(0,255,255,128));
            int w = getWidth();
            int h = getHeight();
            g.drawLine(10,h/2,w-10,h/2);
            g.drawLine(w/2, 10, w/2, h-10);
            
            // Draw the function y = x**2;
            g2d.setStroke(new BasicStroke(2.0f));
            g.setColor(Color.red);
            float scale = 40;
            float bounds = w / 2.0f / scale;
            float x = -bounds;
            float y = f(x);
            int grx = Math.round(x*40+w/2); // Affine
            int gry = Math.round(h/2-y*40); // transformation
            
            while (x < bounds) {
                x += 0.01f;
                y = f(x);
                int oldgrx = grx, oldgry = gry;
                grx = Math.round(x*40+w/2);
                gry = Math.round(h/2-y*40);
                g.drawLine(oldgrx, oldgry, grx, gry);
            }
        }
    }
    
    class CalcPanel extends JPanel {
        public CalcPanel() {
            String ops = "789+456-123*0.=/";
            String[] op2 = {"sin", "cos", "tan", "\u00b1"};
            setLayout(new GridLayout(4,5));
            
            JButton jb;
            Font fnt = new Font("Segoe UI", Font.PLAIN, 20);
            for (int i=0, ia=0; i < ops.length(); i++) {
                if (i%4==0) {
                    add(jb = new JButton(op2[ia]));
                    jb.setFont(fnt);
                    ia++;
                }
                add (jb = new JButton(ops.charAt(i)+""));
                jb.setFont(fnt);
            }
        }
    }
    public Calc() {
        super();
        /* Jave uses layout managers for positioning
        and sizing / resizing. JFrame uses BorderLayout
        with cardinal directions (Center is the default).
        Center will take up as much room as possible.
        */
        
        setLayout(null);
        
        GraphPanel gp = new GraphPanel();
        gp.setBounds(10,10,580,360);
        add(gp);
        
        CalcPanel cp = new CalcPanel();
        cp.setBounds(10,490,580,300);
        add(cp);
        
        JTextField tf = new JTextField();
        tf.setBounds(10,450,580,40);
        tf.setForeground(Color.WHITE);
        tf.setBackground(Color.BLACK);
        tf.setBorder(null);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        tf.setHorizontalAlignment(SwingConstants.RIGHT);
        tf.addActionListener(e -> {
            gp.setEqn(tf.getText().trim());
            repaint();
        });
        add(tf);

        
        setUndecorated(true);
        
        setBackground(new Color(0,0,0));
        /* Transparency is broken on X11, see
        http://bugs.java.com/view_bug.do?bug_id=6849774
        "Note that until the bug 6848852 is fixed, that is unfeasible to
        test transparency on X11 because it simply does not work."
        */
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(20,40,600,800);
        setVisible(true);
    }
    public static void main(String[] args) {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        new Calc();
    }
}

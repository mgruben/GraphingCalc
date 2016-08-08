
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
    class CalcPanel extends JPanel {
        public CalcPanel() {
            String ops = "789+456-123*0.=/";
            
            JButton jb;
            Font fnt = new Font("Segoe UI", Font.PLAIN, 20);
            for (int i=0; i < ops.length(); i++) {
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
        add(new CalcPanel());
        setUndecorated(true);
        setBackground(new Color(0,0,0,40));
        /* Transparency is broken on X11, see
        http://bugs.java.com/view_bug.do?bug_id=6849774
        "Note that until the bug 6848852 is fixed, that is unfeasible to
        test transparency on X11 because it simply does not work."
        */
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(20,40,600,1024);
        setVisible(true);
    }
    public static void main(String[] args) {
        new Calc();
    }
}

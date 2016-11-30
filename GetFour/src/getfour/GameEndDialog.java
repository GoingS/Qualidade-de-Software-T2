// file GameEndDialog.java
//
// This file is part of the GetFour Project.
//
// Copyright (c) 2005-2009 Oliver Kranz ( o.kranz@gmx.de )
//
// Any parts of this program derived from the GetFour project,
// or contributed by third-party developers are copyrighted by their
// respective authors.
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301, USA
//

package getfour;

//imports
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class GameEndDialog extends JDialog
{
    /** The constructor of the class GameEndDialog creates a window with a
      * message in it.
      * @param owner An instance of the class GetFourGUI.
      * @param title A String representing the title of the window.
      * @param text The text message inside the window.
      * @param bgcolor The background color of the window.
      */
    GameEndDialog(GetFourGUI owner, String title, String text, Color bgcolor)
    {
        super(owner, true);
        setTitle(title);
        this.getContentPane().setBackground(bgcolor);
        
        this.getContentPane().add(new Label(text), 
            BorderLayout.NORTH);
        JButton b = new JButton("Close");
        b.setBackground(Color.darkGray);
        b.setForeground(GetFourGUI.GREEN);
        this.getContentPane().add(b, BorderLayout.EAST);

        b.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) { dispose(); }
        });
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                dispose();
            }
        });

        validate();
        setSize(300, 100);
        setVisible(true);          
    }
}

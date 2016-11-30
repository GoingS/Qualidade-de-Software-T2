// File GetFourGUI.java
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
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//begin class GetFourGUI
/** The class GetFourGUI provides the GUI for the game.
  */
class GetFourGUI extends JFrame
{
    private GetFour getFour;
    private GridCanvas grid;
    private Choice modeChoice;
    public static final Color GREEN = new Color(0, 223, 0);
    public static final String humanhuman = "Human - Human";
    public static final String humancomputer = "Human - Computer";
    public static final String computerhuman = "Computer - Human";    
    
    /** Constructor of the class GetFourGUI creates a window with buttons etc.
      * and the grid.
      * @param four An object of the class GetFour.
      */
    GetFourGUI(GetFour four)
    {
        super("GetFour Version 2.0.7_svn");
        
        this.getFour = four;
        
        this.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                getFour.quit();
            }
        });
        
        GridBagLayout layout = new GridBagLayout();
        this.getContentPane().setLayout(layout);
        //this.getContentPane().setLayout(new FlowLayout(
        //    FlowLayout.LEADING, 20, 5));
        this.setResizable(true);
        Insets insets = new Insets(5,5,5,5);
        
        // Commented. Not needed.
        // NewGame button provides the same functionality.
        /*
        JButton start = new JButton("Start");
        start.setBackground(Color.darkGray);
        start.setForeground(this.GREEN);
        start.addActionListener(new StartButtonActionListener());
        this.getContentPane().add(start);
        */
        
        JButton newGameButton = new JButton("New Game");
        newGameButton.setBackground(Color.darkGray);
        newGameButton.setForeground(this.GREEN);
        newGameButton.addActionListener(new NewGameButtonActionListener());
        GridBagConstraints newGameButtonConstraints = new GridBagConstraints(
            0, 0, 20, 10, 0, 0, GridBagConstraints.NORTHWEST, 
            GridBagConstraints.NONE, insets, 0, 0);
        layout.setConstraints(newGameButton, newGameButtonConstraints);
        this.getContentPane().add(newGameButton);
        
        JLabel modeLabel = new JLabel("Mode:");
        modeLabel.setForeground(this.GREEN);
        GridBagConstraints modeLabelConstraints = new GridBagConstraints(
            30, 0, 20, 10, 0, 0, GridBagConstraints.NORTHWEST, 
            GridBagConstraints.NONE, insets, 0, 0);
        layout.setConstraints(modeLabel, modeLabelConstraints);
        this.getContentPane().add(modeLabel);
        
        modeChoice = new Choice();
        modeChoice.setBackground(Color.darkGray);
        modeChoice.setForeground(this.GREEN);
        modeChoice.add(this.humanhuman);
        modeChoice.add(this.humancomputer);
        modeChoice.add(this.computerhuman);
        GridBagConstraints modeChoiceConstraints = new GridBagConstraints(
            50, 0, 20, 10, 0, 0, GridBagConstraints.NORTHWEST, 
            GridBagConstraints.NONE, insets, 0, 0);
        layout.setConstraints(modeChoice, modeChoiceConstraints);
        this.getContentPane().add(modeChoice);
        
        JLabel difficultyLabel = new JLabel("Difficulty:");
        difficultyLabel.setForeground(this.GREEN);
        GridBagConstraints difficultyLabelConstraints = new GridBagConstraints(
            30, 20, 20, 10, 0, 0, GridBagConstraints.NORTHWEST, 
            GridBagConstraints.NONE, insets, 0, 0);
        layout.setConstraints(difficultyLabel, difficultyLabelConstraints);
        this.getContentPane().add(difficultyLabel);
        
        Choice difficultyChoice = new Choice();
        difficultyChoice.setBackground(Color.darkGray);
        difficultyChoice.setForeground(this.GREEN);
        difficultyChoice.add("easy");
        difficultyChoice.add("moderate");
        difficultyChoice.add("hard");
        GridBagConstraints difficultyChoiceConstraints = new GridBagConstraints(
            50, 20, 20, 10, 0, 0, GridBagConstraints.NORTHWEST, 
            GridBagConstraints.NONE, insets, 0, 0);
        layout.setConstraints(difficultyChoice, 
            difficultyChoiceConstraints);
        this.getContentPane().add(difficultyChoice);
        difficultyChoice.addItemListener(new DifficultyItemListener(
            this.getFour));
        
        JButton quitButton = new JButton("Quit");
        quitButton.setBackground(Color.darkGray);
        quitButton.setForeground(this.GREEN);
        quitButton.addActionListener(new QuitActionListener());
        GridBagConstraints quitButtonConstraints = new GridBagConstraints(
            0, 20, 20, 10, 0, 0, GridBagConstraints.NORTHWEST, 
            GridBagConstraints.NONE, insets, 0, 0);
        layout.setConstraints(quitButton, quitButtonConstraints);
        this.getContentPane().add(quitButton);
        
        int gridwidth = 7 * GridCanvas.RECTWIDTH;
        int gridheight = 6 * GridCanvas.RECTHEIGHT;
        grid = new GridCanvas(this, this.getFour);
        grid.setSize(gridwidth, gridheight);
        GridBagConstraints gridConstraints = new GridBagConstraints(
            0, 30, gridwidth, gridheight, 0, 0, GridBagConstraints.NORTHWEST, 
            GridBagConstraints.NONE, insets, 0, 0);
        layout.setConstraints(grid, gridConstraints);
        this.getContentPane().add(grid);
        this.getFour.setGridCanvas(this.grid);
        
        modeChoice.addItemListener(new ModeChoiceItemListener(this.getFour, 
            this, this.grid));
        
        this.getContentPane().setBackground(Color.black);
        this.setSize(3 * insets.left + 3 * insets.right + gridwidth, 
            6 * insets.top + 6 * insets.bottom + gridheight + 2 * 20 + 2 * 10);
        this.setBackground(Color.white);
        this.setVisible(true);
    }
    
    /*
    private class StartButtonActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent a)
        {
            getFour.start();
        }
    }
    */
    
    private class NewGameButtonActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent a)
        {
            getFour.newGame();
        }
    }
    
    private class QuitActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent a)
        {
            getFour.quit();
        }
    }
    
    /** This method returns the attribute which contains the grid canvas.
      * @return An object of the class GridCanvas.
      */
    GridCanvas getGridCanvas()
    {
        return this.grid;
    }
    
    /** This method sets the mode choice to the specified mode.
      * @param mode The String specifiing the mode the mode choice is set to.
      */
    void setMode(String mode)
    {
        this.modeChoice.select(mode);
    }
    
    /** This method returns the current mode.
      * @return The current mode.
      */
    public String getCurrentMode()
    {
        return this.modeChoice.getSelectedItem();
    }
}
//--end class GetFourGUI

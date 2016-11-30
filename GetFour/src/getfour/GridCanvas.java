// File GridCanvas.java
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
import java.util.*;

//begin class GridCanvas
class GridCanvas extends Canvas
{
    private GetFourGUI getfourgui;
    private GetFour getfour;
    public static final Color RED = new Color(223, 0, 0);
    public static final Color YELLOW = new Color(223, 223, 0);
    
    //grid parameters
    public static final int RECTWIDTH = 66;
    public static final int RECTHEIGHT = 66;
    public static final int CIRCLEWIDTH = 60;
    
    /** This constructor creates the grid of the game.
      * @param gfg An object of the class GetFourGUI.
      * @param gf An object of the class GetFour.
      */
    GridCanvas(GetFourGUI gfg, GetFour gf)
    {
        super();
        this.getfourgui = gfg;
        this.getfour = gf;
        this.addMouseListener(new GridMouseListener());
    }
    
    /** Overwrites the method of the inherited class Canvas. It paints the grid
      * with all discs on the screen again.
      * @param g An object of the class Graphics.
      */
    public void paint(Graphics g)
    {
        this.drawGrid();
        this.drawAllDiscs();
    }
    
    /** This method draws an empty grid.
      */
    void drawGrid()
    {
        for(int i = 0; i < getfour.numberOfPlaces; i++)
        {
            Graphics g = this.getGraphics();
            this.setBackground(Color.black);
            g.setColor(Color.gray);
            g.fillOval((i % 7) * RECTWIDTH + (RECTWIDTH - CIRCLEWIDTH) / 2, 
                ((int) Math.ceil(i / 7)) * RECTHEIGHT + 
                (RECTHEIGHT - CIRCLEWIDTH) / 2, CIRCLEWIDTH, CIRCLEWIDTH);
        }
    }
    
    /** This method draws a discs in the color of the current player. The disc
      * is dropped into the specified column and the undermost free row. A 
      * window with a message pops up after this turn if the game is over.
      * @param column The column the disc is dropped into.
      */
    public void drawDisc(int column)
    {
        Graphics g = this.getGraphics();
        
        if(this.getfour.getNextPlayer() == 1)
            g.setColor(this.RED);
        else
            g.setColor(this.YELLOW);
            
        g.fillOval((column - 1) * RECTWIDTH + (RECTWIDTH - CIRCLEWIDTH) / 2, 
                (this.getfour.getFreeRow(column) - 1) * 
                        RECTHEIGHT + (RECTHEIGHT - CIRCLEWIDTH) / 2, 
                            CIRCLEWIDTH, CIRCLEWIDTH);
        this.getfour.setDisc(column, this.getfour.getFreeRow(column));
        this.checkAndProcessGameEnd();
    }
    
    /** This method checks if the game is ended. This is true if someone has won
      * the game or the grid is full. If the game has ended the user will be
      * notified.
      */
    private void checkAndProcessGameEnd()
    {
        if(this.getfour.hasWon())
        {
            //this.processWin(this.getfour.getNextPlayer());
            if(this.getfour.getNextPlayer() == 1)
                new GameEndDialog(this.getfourgui, 
                    "Yellow wins", "Yellow wins the game.",
                    Color.yellow);
            else
                new GameEndDialog(this.getfourgui, "Red wins", 
                    "Red wins the game.", Color.red);
        }
        else if(this.getfour.isFull())
            new GameEndDialog(this.getfourgui, "Draw", 
                "The game is a draw.", Color.white);
    }
    
    // todo: Complete this method. And add call to method
    // checkAndProcessGameEnd.
    private void processWin(int player)
    {
        Graphics g = this.getGraphics();
        if(player == 1)
        {
            g.setColor(this.YELLOW);
        }
        else
        {
            g.setColor(this.RED);
        }
        //int[] xPoints = {};
        //int[] yPoints = {};
        //g.fillPolygon(xPoints, yPoints, 4);
    }
    
    /** This method draws all the discs inside the grid on the screen again.
      */
    private void drawAllDiscs()
    {
        Graphics g = this.getGraphics();
        int[] gridArray = getfour.getGridArray();
        
        for(int i = 0; i < getfour.numberOfPlaces; i++)
        {
            if(gridArray[i] == 1)
                g.setColor(this.RED);
            if(gridArray[i] == -1)
                g.setColor(this.YELLOW);
            if(gridArray[i] == 0)
                g.setColor(Color.gray);
            
            g.fillOval((i % 7) * RECTWIDTH + (RECTWIDTH - CIRCLEWIDTH) / 2, 
                ((int) Math.ceil(i / 7)) * RECTHEIGHT + 
                (RECTHEIGHT - CIRCLEWIDTH) / 2, CIRCLEWIDTH, CIRCLEWIDTH);
        }
    }
    
    /** This method returns the number of the column for the specified 
      * x-coordinate in pixels.
      * @param xCoordinate The x-coordinate in pixels on the grid.
      * @return int The number of the column.
      */
    public int getColumn(int xCoordinate)
    {
        return 1 + (int) Math.ceil(xCoordinate / RECTWIDTH);
    }
    
    /** This class implements the MouseListener interface for the class
      * GridCanvas.
      */
    private class GridMouseListener extends MouseAdapter
    {
        public void mouseReleased(MouseEvent e)
        {
            int column = getColumn(e.getX());
            if(getfour.getFreeRow(column) != 0 && 
                (! getfour.hasWon()))
            {
                drawDisc(column);
                if((getfourgui.getCurrentMode() != GetFourGUI.humanhuman) &&
                    (! getfour.hasWon()) && 
                    (! getfour.isFull()))
                {
                    drawDisc(getfour.ai());
                }
            }
            else if(getfour.hasWon())
            {
                getfour.newGame();
            }
        }
    }
}
//--end class GridCanvas

// File GetFour.java
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
import java.awt.event.*;
import java.util.*;

/** File GetFour.java
  * @author <a href="mailto:kranzler@users.sourceforge.net"> 
  * Oliver Kranz </a>
  * <br>Copyright (c) 2005-2009 Oliver Kranz
  * <br>GNU General Public Licence version 2 or later
  * @version 2.0.7, date 18th June 2009
  */

//begin class GetFour
public class GetFour
{
    private GetFourGUI getFourGUI;
    private GridCanvas gridCanvas;
    /* Array contains the discs of the grid. It contains them in descending
     * order beginning with the top row and ending with the bottom row. No disc 
     * is 0, a red disc is 1 and a yellow disc is -1.
     */
    public static int[] gridArray;
    private static int nextPlayer;
    private int treeDepth = 5;
    private Hashtable<String, Integer> difficulty = 
        new Hashtable<String, Integer>(3);
    public static final int numberOfPlaces = 42;
    
    /** Standard constructor of the class GetFour.
      */
    public GetFour()
    {
        this.getFourGUI = new GetFourGUI(this);
        this.gridArray = new int[numberOfPlaces];
        this.initGridArray();
        this.nextPlayer = 1;
        this.difficulty.put("easy", new Integer(5));
        this.difficulty.put("moderate", new Integer(6));
        this.difficulty.put("hard", new Integer(7));
    }
    
    /** This method returns the value of the difficulty hashmap for the
      * specified key.
      * @param key A String representing the key the regarding value is returned
      * for.
      * @return int The value for the specified key in the difficulty hashmap.
      */
    int getDifficultyValue(String key)
    {
        Integer value = (Integer) this.difficulty.get(key);
        return value.intValue();
    }
    
    /** Get the value of the attribute treeDepth.
      * @return int The depth of the tree for the Minimax algorithm.
      */
    int getTreeDepth()
    {
        return this.treeDepth;
    }
    
    /** Set the value of the attribute treeDepth.
      * @param depth The value the attribute is to be set.
      */
    void setTreeDepth(int depth)
    {
        this.treeDepth = depth;
    }
    
    /** quit() quits the application.
      */
    void quit()
    {
        this.getFourGUI.dispose();
        System.exit(0);
    }
    
    /** start() begins a new game if no other game is currently running.
      */
    
    // Commented. Not needed.
    // Same functionality provided by GetFour.newGame().
    /*
    void start()
    {
        if(this.hasWon(this.gridArray) == true || 
            this.isFull(this.gridArray) == true)
        {
            this.initGridArray();
            this.nextPlayer = 1;
            this.getFourGUI.getGridCanvas().repaint();
            if(this.gridCanvas.getModeString().equals("Computer - Human"))
                this.gridCanvas.drawDisc(this.ai());
        }
    }
    */
    
    /** newGame() begins a game even if another game is running and must be
      * quit.
      */
    void newGame()
    {
        this.initGridArray();
        this.nextPlayer = 1;
        this.getFourGUI.getGridCanvas().repaint();
        if(this.getFourGUI.getCurrentMode().equals(GetFourGUI.computerhuman))
                this.gridCanvas.drawDisc(this.ai());
    }
    
    /** initGridArray() initializes the grid with 7 * 6 = 42 empty places.
      */
    void initGridArray()
    {
        for(int i = 0; i < numberOfPlaces; i++)
        {
            this.gridArray[i] = 0;
        }
    }
    
    /** Returns the undermost free row for the given parameter column in the 
      * current grid. Column is the column of which the undermost free row is 
      * returned. If the column is full then 0 is returned.
      * @param column The column the free row is searched for.
      * @return The undermost free row for the specified parameter or 0 if the
      * column is full with discs.
      */
    public int getFreeRow(int column)
    {
        return this.getFreeRow(this.gridArray, column);
    }
    
    /** Returns the undermost free row for the given parameters
      * situation and column. Where situation is the situation on the grid and
      * column is the column of which the undermost free row is returned. If the
      * column is full then 0 is returned.
      * @param situation The grid array.
      * @param column The column the free row is searched for.
      * @return The undermost free row for the specified parameters or 0 if the
      * column is full with discs.
      */
    public static int getFreeRow(int[] situation, int column)
    {
        for(int i = 1; i < 7; i++)
        {
            if(situation[41 - (7 * i - column)] == 0)
            {
                return 7 - i;
            }
        }
        return 0;
    }
    
    /** setDisc sets the disc in the grid at the specified postition column and
      * row.
      * @param column The column in the grid.
      * @param row The row in the grid.
      */
    public void setDisc(int column, int row)
    {
        this.gridArray[((row - 1) * 7) + column - 1] = this.nextPlayer;
        this.flipNextPlayer();
    }
    
    /** It is the next players turn. Set the next player.
      */
    private void flipNextPlayer()
    {
        if(this.nextPlayer == 1)
            this.nextPlayer = -1;
        else
            this.nextPlayer = 1;
    }
    
    /** getOpponent returns the opponent.
      * @param player The current player.
      * @return int The opponent of the current player.
      */
    static int getOpponent(int player)
    {
        if(player == 1)
            return -1;
        else
            return 1;
    }
    
    /** Whose turn is it?
      * @return It is this players turn.
      */
    int getNextPlayer()
    {
        return this.nextPlayer;
    }
    
    /** This method determines for the current grid if some player has won.
      * @return boolean True if a player has won. False if nobody has won.
      */
    public boolean hasWon()
    {
        return this.discsInARow(this.gridArray, GetFour.nextPlayer, 4, true);
    }
    
    /**   true if a player has won and returns false if no player
      * has won.
      * @param situation The grid to be processed.
      * @return True if a player has won and false if no player has won.
      */
    public static boolean hasWon(int[] situation)
    {
        return GetFour.discsInARow(situation, GetFour.nextPlayer, 4, 
            true);
    }
    
    /** discsInARow returns true if the specified player has a connected row of 
      * discs in the specified grid of the specified length. The row can be
      * horizontal, vertical or diagonal.
      * @param situation The grid array.
      * @param player The player the discs in a row are searched for.
      * @param length The explicit length of the row of discs, not minimal 
      * length nor maximal length.
      * @param bothPlayers Search a row of discs for both players or for one 
      * player.
      * @return Returns true if a row of discs exists and returns false if no 
      * row of discs exists.
      */
    public static boolean discsInARow(int[] situation, int player, int length, 
        boolean bothPlayers)
    {
        //check for one or two players
        int[] playerArray;
        if(bothPlayers)
        {
            playerArray = new int[2];
            playerArray[0] = player;
            playerArray[1] = GetFour.getOpponent(player);
        }
        else
        {
            playerArray = new int[1];
            playerArray[0] = player;
        }
        
        for(int playerPosition = 0; playerPosition < playerArray.length; 
            playerPosition++)
        {
            player = playerArray[playerPosition];
            
            //start search for discs in a row
            for(int i = 0; i < numberOfPlaces; i += 7)
            {
               //number of discs in this row
                byte discs = 0;
                    
                for(int j = 0; j < 7; j++)
                {
                    if(situation[i + j] == player)
                        discs += 1;
                    else
                        discs = 0;
                    
                    if(discs == length)
                    {
                        return true;
                    }
                }
            }
            //--end search for discs in a row
               
            //start search for discs in a column
            for(int i = 0; i < 7; i++)
            {
                //number of discs in this column
                byte discs = 0;
                    
                for(int j = 0; j < numberOfPlaces; j +=7)
                {
                    if(situation[i + j] == player)
                        discs += 1;
                    else
                        discs = 0;
                    
                    if(discs == length)
                    {
                        return true;
                    }
                }
            }
            //--end search for discs in a column
                
            //start search for discs in a diagonal
            //start search for a diagonal which conflates discs from lower left 
                //to upper right
            for(int k = 0; k < (8 - length); k++)
            {
                for(int i = 0; i < (7 - length) * 7; i += 7)
                {
                    //number of discs in this diagonal
                    byte discs = 0;
                    
                    for(int j=length - 1; j < (length - 1) * 6 + length; j += 6)
                    {
                        if(situation[k + i + j] == player)
                            discs += 1;
                        else
                            discs = 0;
                            
                        if(discs == length)
                        {
                            return true;
                        }
                    }
                }
            }
            //--end search for a diagonal which conflates discs from lower left 
                //to upper right
                    
            //start search for a diagonal which conflates discs from upper left 
                //to lower right
            for(int k = 0; k < (8 - length); k++)
            {
                for(int i = 0; i < (7 - length) * 7; i += 7)
                {
                    //number of discs in this diagonal
                    byte discs = 0;
                    
                    for(int j = 0; j < (length - 1) * 8 + 1; j += 8)
                    {
                        if(situation[k + i + j] == player)
                            discs += 1;
                        else
                            discs = 0;
                            
                        if(discs == length)
                        {
                            return true;
                        }
                    }
                }
            }
            //--end search for a diagonal which conflates discs from upper left 
                //to lower right
            //--end search for discs in a diagonal
        }
        return false;
    }
    
    /** This method returns true if there is no disc on the grid. And it returns
      * false if there is at least one disc on the grid.
      * @return True if there is no disc on the grid. False if there is at least
      * one disc on the grid.
      */
    public boolean isEmpty()
    {
        for(int i = 0; i < numberOfPlaces; i++)
        {
            if(this.gridArray[i] == 1 || this.gridArray[i] == -1)
                return false;
        }
        return true;
    }
    
    /** This method returns true if the current grid is filled
      * completely with discs. If there is at least one free position on the
      * grid false is returned.
      * @return True if the grid is completely filled with discs. False if there
      * is at least one free position on the grid.
      */
    public boolean isFull()
    {
        
        return this.isFull(this.gridArray);
    }
    
    /** This method returns true if the passed grid situation is filled
      * completely with discs. If there is at least one free position on the
      * grid false is returned.
      * @param situation The grid array.
      * @return True if the grid is completely filled with discs. False if there
      * is at least one free position on the grid.
      */
    static boolean isFull(int[] situation)
    {
        for(int i = 0; i < numberOfPlaces; i++)
        {
            if(situation[i] == 0)
                return false;
        }
        return true;
    }
    
    /** This method returns the number of discs on the grid.
      * @return The number of discs on the current grid.
      */
    int numberOfDiscs()
    {
        int counter = 0;
        for(int i = 0; i < numberOfPlaces; i++)
        {
            if(this.gridArray[i] == 1 ||
                this.gridArray[i] == -1)
            {
                counter++;
            }
        }
        return counter;
    }

    /** This method returns the current grid array.
      * @return The current grid array.
      */
    int[] getGridArray()
    {
        return this.gridArray;
    }
    
    /** This method returns a random column which has at least one free position
      * for a disc.
      * @return The column.
      */
    public int getFreeRandomColumn()
    {
        double random = Math.random();
        for(double i = 1; i < 8; i++)
        {
            if((((i - 1) / 7) <= random) && (random < (i / 7)))
            {
                int freeRow = GetFour.getFreeRow(
                    this.getGridArray(), (int) i);
                if(freeRow != 0)
                {
                    return (int) i;
                }
                else
                    return this.getFreeRandomColumn();
            }
        }
        return 0;
    }
    
    /** This method returns the column the computer player wants to place its
      * disc.
      * @return The column of the computer player's turn.
      */
    public int ai()
    {
        try
        {
            GetFourTree getFourTree = new GetFourTree(
                this.treeDepth, this);
            return getFourTree.getBestColumn();
        }
        catch(AIException aiException)
        {
            System.err.println(aiException.getMessage());
            return this.getFreeRandomColumn();
        }
    }
    
    /** This method sets the attribute gridCanvas of an object of type GetFour 
      * to the passed reference "gc" of a GridCanvas object.
      * @param gc An object of type GridCanvas.
      */
    public void setGridCanvas(GridCanvas gc)
    {
        this.gridCanvas = gc;
    }
    
    /** The main method of the class GetFour which creates an instance of the
      * class.
      */
    public static void main(String[] args)
    {
        GetFour v = new GetFour();
    }
}
//--end class GetFour

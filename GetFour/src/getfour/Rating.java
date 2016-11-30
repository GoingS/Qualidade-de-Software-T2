// File Rating.java
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

//This class is used to rate the tree's leafs.
class Rating
{   
    /** This method rates the specified situation on the grid for the specified
      * player.
      * @param node This node is a leaf of the tree which is to be rated.
      * @param situation The situation on the grid.
      * @param nextPlayer The player whose turn is next.
      * @return int The rating. That is, -10 <= rating <= 10.
      */
    static int rate(Node node, int[] situation, int nextPlayer)
    {
        int player = nextPlayer; //positive rates for this player
        
        /*If the node is a minimum node then the other player gets a
            positive rating.*/
        if(! node.isMaxNode())
            player = GetFour.getOpponent(player);
        
        if(GetFour.discsInARow(situation, player, 4, false))
            return 9;
        if(GetFour.discsInARow(situation, GetFour.getOpponent(player), 
            4, false))
                return -10;
        
        byte score = 0;
        if(GetFour.discsInARow(situation, player, 3, false))
            score += 4;
        else if(GetFour.discsInARow(situation, 
            GetFour.getOpponent(player), 3, false))
                score += -5;
        else if(GetFour.discsInARow(situation, player, 2, false))
            score += 1;
        else if(GetFour.discsInARow(situation, 
            GetFour.getOpponent(player), 2, false))
                score += -2;
        
        return (int) score;
    }
}

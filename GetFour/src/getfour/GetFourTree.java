// file GetFourTree.java
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

public class GetFourTree
{
    private Node root;
    private int treeDepth;
    
    /** The constructor of the class GetFourTree creates a tree to determine the
      * best move using the Minimax algorithm.
      * @param treeDepth The tree's depth.
      * @param getFour An instance of the class GetFour.
      */
    public GetFourTree(int treeDepth, GetFour getFour)
    {
        this.treeDepth = treeDepth;
        this.root = new Node(null, 
            (int[]) getFour.getGridArray().clone(), 
                getFour.getNextPlayer());
        this.root.setRootMaxNode();
        this.expandTree();
    }
    
    /** This method determines the column of the best move computed by the
      * Minimax algorithm.
      * @return int The column in the grid.
      * @exception Throws an AIException if an error occurs during calculation.
      */
    public int getBestColumn() throws AIException
    {
        this.root.minimax();
        int nodeNumber = 3;
        boolean plus = true;
        
        for(int i = 1; i < 8; i++)
        {
            if(this.root.getRating() == this.root.getNextNode(nodeNumber).
                getRating())
            {
                return nodeNumber + 1;
            }
            else
            {}
            
            if(plus)
            {
                plus = false;
                nodeNumber += i;
            }
            else
            {
                plus = true;
                nodeNumber -= i;
            }
        }
        int[] ratings = new int[7];
        for(int k = 0; k < 7; k++)
        {
            ratings[k] = this.root.getNextNode(k).getRating();
        }
        throw new AIException("Error in root-node. Wrong rating for situation "
            + "in grid. \n" + "Rating in root-node: " + 
            this.root.getRating() + "\n Rating in node0: " + ratings[0] + 
            "\n Rating in node1: " + ratings[1] + "\n Rating in node2: " + 
            ratings[2] + "\n Rating in node3: " + ratings[3] + 
            "\n Rating in node4: " + ratings[4] + "\n Rating in node5: " + 
            ratings[5] + "\n Rating in node6: " + ratings[6]);
    }
    
    /** This method creates the tree.
      */
    private void expandTree()
    {
        this.root.expand(treeDepth);
    }
}

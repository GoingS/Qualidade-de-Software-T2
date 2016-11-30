// File Node.java
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

//begin class Node
class Node
{
    private int[] situation; //the grid with all turns up to this node
    private Node prevNode = null; //parent node
    //child nodes
    private Node[] nextNode = {null, null, null, null, null, null, null};
    private boolean maxNode = false; //Is this node a max node?
    private int rating; /*A rating calculated by the Minimax algorithm 
        concerning the situation on the grid.*/
    private int nextPlayer;
    
    /** The constructor of the class Node creates an instance.
      * @param prevNode The parent node object.
      * @param situation The grid array with discs.
      * @param nextPlayer The player whose turn is next.
      */
    Node(Node prevNode, int[] situation, int nextPlayer)
    {
        this.prevNode = prevNode;
        this.situation = situation;
        this.nextPlayer = nextPlayer;
        
        if((! this.isRoot()) && (! this.prevNode.isMaxNode()))
            this.maxNode = true;
    }
    
    /** This method at first rates all the leafs of the tree and then 
      * recursively rates all the nodes.
      * @return int The node's rating.
      */
    int minimax()
    {
        if(this.isLeaf())
        {
            this.rating = Rating.rate(this, this.situation, this.nextPlayer);
            return this.rating;
        }
        else
        {
            int[] ratings = {-11, -11, -11, -11, -11, -11, -11};
            
            for(int nodeNumber = 0; nodeNumber < 7; nodeNumber++)
            {
                if(this.nextNode[nodeNumber] != null)
                {
                    ratings[nodeNumber] = this.nextNode[nodeNumber].minimax();
                }
            }
            
            if(this.isMaxNode())
            {
                int max = -10;
                
                for(int i = 0; i < 7; i++)
                {
                    max = Math.max(max, ratings[i]);
                }
                this.rating = max;
                return max;
            }
            
            else
            {
                int min = 10;
                
                for(int i = 0; i < 7; i++)
                {
                    if(ratings[i] != -11)
                        min = Math.min(min, ratings[i]);
                }
                this.rating = min;
                return min;
            }
        }
    }
    
    /** This method expands the further tree up to the specified depth.
      * @param depth The tree's overall depth.
      */
    void expand(int depth)
    {
        if(this.isLeaf() && this.getDepth() < depth)
        {
            this.setNextNodes();
            for(int nodeNumber = 0; nodeNumber < 7; nodeNumber++)
            {
                if(this.nextNode[nodeNumber] != null)
                    this.nextNode[nodeNumber].expand(depth);
            }
        }
    }
    
    /** Return the parent node of the current node.
      * @return Node The parent node.
      */
    Node getPrevNode()
    {
        return this.prevNode;
    }
    
    /** Determine if this node is a root node.
      * @return boolean True if this node is a root node. False if this node is
      * no root node.
      */
    boolean isRoot()
    {
        if(this.prevNode == null)
            return true;
        else
            return false;
    }
    
    /** Determine the depth of the current node inside of the tree.
      * @return int The depth of the current node.
      */
    int getDepth()
    {
        if(this.isRoot())
            return 1;
        else
            return this.prevNode.getDepth() + 1;
    }
    
    /** Rate the current node manually without use of the Minimax algorithm.
      * @param rating The rating of the current node.
      */
    void setRating(int rating)
    {
        this.rating = rating;
    }
    
    /** Returns the current node's rating.
      * @return int The rating.
      */
    int getRating()
    {
        return this.rating;
    }
    
    /** Returns the current node's child node of the specified position. If a
      * child node does not exist then a pseudo node with rating -11 is
      * returned.
      * @param position The child node's position. The position has to be 
      * between 0 and 6. That is, 0 <= position <= 6.
      * @return Node The child node.
      */
    Node getNextNode(int position) throws AIException
    {
        if(position < 7 && position >= 0)
        {
            if(this.nextNode[position] != null)
                return this.nextNode[position];
            else
            {
                Node node = new Node(this, null, 1);
                node.setRating(-11);
                return node;
            }
        }
        else
        {
            throw new AIException("Exception in getNextNode(position: " + 
                position +").");
        }
    }
    
    /** Creates the child nodes.
      */
    void setNextNodes()
    {
        if(! this.mustBeLeaf())
        {
            for(int nodeNumber = 0; nodeNumber < 7; nodeNumber++)
            {
                int[] newSituation = (int[]) situation.clone();
                
                if(GetFour.getFreeRow(this.situation, nodeNumber + 1) != 0)
                {
                    newSituation[((GetFour.getFreeRow(this.situation, 
                        nodeNumber + 1) - 1) * 7) + nodeNumber] = 
                            this.nextPlayer;
                    this.nextNode[nodeNumber] = new Node(this, newSituation, 
                        GetFour.getOpponent(this.nextPlayer));
                }
            }
        }
    }
    
    /** Determines if the current node has to be a leaf or not. It has to be a
      * leaf if the grid is full with discs or if some player has won the game.
      * @return boolean True if the node has to be a leaf. False if it does not
      * have to be a leaf.
      */
    private boolean mustBeLeaf()
    {
        if(GetFour.isFull(this.situation) || 
            GetFour.hasWon(this.situation))
                return true;
        else
            return false;
    }
    
    /** Determines if the current node is leaf or not.
      * @return boolean True if the node is a leaf. False if it is no leaf.
      */
    boolean isLeaf()
    {
        for(int nodeNumber = 0; nodeNumber < 7; nodeNumber++)
        {
            if(nextNode[nodeNumber] != null)
                return false;
        }
        return true;
    }
    
    /** Determines if the current node is a maximum node or not.
      * @return boolean True if the node is a maximum node. False if it is no
      * maximum node.
      */
    boolean isMaxNode()
    {
        return this.maxNode;
    }
    
    /** Sets the root node to maximum node. All root nodes are maximum nodes.
      */
    void setRootMaxNode()
    {
        this.maxNode = true;
    }           
}
//--end class Node

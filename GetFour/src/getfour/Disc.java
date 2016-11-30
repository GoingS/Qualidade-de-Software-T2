// File Disc.java
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

// begin class Disc
class Disc
{
    private int player = 0;
    
    /** Return the value of the attribute player.
      * @return The int value representing the player.
      */
    int getPlayer()
    {
        return this.player;
    }
    
    /** Assign the specified parameter player to the attribute player.
      * @param player The int value representing the player. Player is valid if
      * -1 <= player <= 1.
      * @exception Throws an InvalidPlayerException if the passed parameter is
      * invalid.
      */
    void setPlayer(int player) throws InvalidPlayerException
    {
        if(-1 <= player && player <= 1)
        {
            this.player = player;
        }
        else
            throw new InvalidPlayerException("Parameter player has to be " +
                "between -1 and 1. That is, -1 <= player <= 1.");
    }
}
// --end class Disc

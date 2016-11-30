// File DifficultyItemListener.java
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

//begin class DifficultyItemListener
public class DifficultyItemListener implements ItemListener
{
    private GetFour getfour;
    
    /** The constructor creates an instance of the class.
      * @param gf An object of the class GetFour.
      */
    DifficultyItemListener(GetFour gf)
    {
        this.getfour = gf;
    }

    /** Implements the ItemListener interface for the difficulty choice.
      * @param e The event.
      */
    public void itemStateChanged(ItemEvent e)
    {
        Object item = e.getItem();
        if(item instanceof String)
        {
            this.getfour.setTreeDepth(this.getfour.getDifficultyValue(
                (String) item));
        }
    }
}
//--end class DifficultyItemListener

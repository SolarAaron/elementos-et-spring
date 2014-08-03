/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package slr.epoo.web.unused.mdl;

import slr.unused.lib.Author;

@Author(name = "Aaron", version="0.0.1")
public class Cantante implements IPersona {

    public Cantante(){
    }

    @Override
    public String ejecutarGracia() {
        return "Ay larari larara";
    }

}

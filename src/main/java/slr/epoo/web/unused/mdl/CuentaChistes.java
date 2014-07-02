/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package slr.epoo.web.unused.mdl;

import slr.lib.Author;

@Author(name = "Aaron", version="0.0.1")
public class CuentaChistes implements IPersona{

    public CuentaChistes() {
    }

    @Override
    public String ejecutarGracia() {
        return "Un perro se llamaba pegamento se cayo y se pego";
    }

}

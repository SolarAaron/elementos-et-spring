/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package slr.epoo.web.cnt;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Aaron
 */
@Controller
@RequestMapping("/hola")
public class PersonaCntr {
    @RequestMapping(method=RequestMethod.GET, value="/message", headers = {"Accept=text/html"})
    public @ResponseBody String resp(){
        return "Servicio REST/Spring";
    }
    
}

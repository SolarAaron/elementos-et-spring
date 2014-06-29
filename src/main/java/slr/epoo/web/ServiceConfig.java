/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package slr.epoo.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import slr.unused.epoo.web.mdl.CuentaChistes;
import slr.unused.epoo.web.mdl.IPersona;

/**
 *
 * @author T107
 */
@Configuration
public class ServiceConfig {
    @Bean
    IPersona ejecutarGracia(){
        return new CuentaChistes();
    }
}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tongji.collaborationteam.pagecontrollers;

import com.tongji.collaborationteam.entities.MyInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author coodoo
 */
@Controller
public class RegisterController {
    
   @RequestMapping(value="/register")
   public ModelAndView register(){
    
       ModelAndView mv= new ModelAndView("register","myinfo",new MyInfo());

       return mv;
   }
   
   
    
}

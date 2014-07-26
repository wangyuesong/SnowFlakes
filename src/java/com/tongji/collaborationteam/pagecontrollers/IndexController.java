
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tongji.collaborationteam.pagecontrollers;

import com.tongji.collaborationteam.entities.MyInfo;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author coodoo
 */
@Controller
@SessionAttributes("login-info")
public class IndexController {
   
    @RequestMapping("/index")
    public ModelAndView index(){
       
       ModelAndView  mv= new ModelAndView("index","myinfo",new MyInfo());
       mv.addObject("login_info",new MyInfo());
       return mv;
        
    }
    @RequestMapping(value="/index",params="log_out")
    public ModelAndView log_out(HttpSession session){
     //  session.removeAttribute("session_user");
       session.invalidate();
       ModelAndView  mv= new ModelAndView("redirect:index","myinfo",new MyInfo());
       mv.addObject("login_info",new MyInfo());
       return mv;
        
    }

    
   
}
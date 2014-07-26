/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tongji.collaborationteam.pagecontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author WYS
 */

@Controller
 @RequestMapping("/error")
public class ErrorPageController {
    @RequestMapping("/{details}")
    public ModelAndView error(@PathVariable String details) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("errorPage");
        mv.addObject("details",details);
        return mv;
    }
}

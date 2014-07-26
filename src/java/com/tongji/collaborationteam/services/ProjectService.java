/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tongji.collaborationteam.services;

import com.tongji.collaborationteam.dbcontrollers.ProjectJpaController;
import com.tongji.collaborationteam.dbcontrollers.UserJpaController;
import com.tongji.collaborationteam.dbentities.Project;
import com.tongji.collaborationteam.dbentities.User;
import com.tongji.collaborationteam.entities.SimpleProject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * @author coodoo
 */
@SessionAttributes("session_user")
@Controller
public class ProjectService {
      EntityManagerFactory fm = Persistence.createEntityManagerFactory("SpringMVCPU");
        EntityManager em = fm.createEntityManager();
        ProjectJpaController handler = new ProjectJpaController(fm);
        UserJpaController ujc = new UserJpaController(fm);
 
    @RequestMapping(value="/projectManagement",params="create")
    public String create_project(@ModelAttribute("create_project")SimpleProject pro,@ModelAttribute("session_user")User user, HttpSession session){
        String duetime=pro.getDueTime();
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("MM/dd/yyyy"); 
        Date due = null;
        try {
            due = bartDateFormat.parse(duetime);
        } catch (ParseException ex) {
            Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        Calendar calendar = Calendar.getInstance();                        
        Date now =calendar.getTime();
         
        Project project = new Project();
        project.setCategory(pro.getCategory());
        project.setCreateTime(now);
        project.setDescription(pro.getDescription());
        project.setDueTime(due);
        project.setGoal(pro.getGoal());
        project.setId(0);
        project.setModifiedTime(now);
        project.setName(pro.getName()); 
        project.setOwner(user); 
        handler.create(project);    
        user = ujc.findUser(user.getId());
        session.setAttribute("session_user",user);
        return "redirect:projectManagement";
        //这块有问题
    }
    
}

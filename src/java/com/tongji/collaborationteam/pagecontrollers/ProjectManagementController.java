/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tongji.collaborationteam.pagecontrollers;

import com.tongji.collaborationteam.dbcontrollers.UserJpaController;
import com.tongji.collaborationteam.dbentities.Project;
import com.tongji.collaborationteam.dbentities.User;
import com.tongji.collaborationteam.entities.SimpleProject;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author coodoo
 */
@Controller
@SessionAttributes("session_user")
@RequestMapping("/projectManagement")
public class ProjectManagementController {
   
        EntityManagerFactory fm = Persistence.createEntityManagerFactory("SpringMVCPU");
        EntityManager em = fm.createEntityManager();
        
        UserJpaController handler = new UserJpaController(fm);

    @RequestMapping()
    public ModelAndView render(@ModelAttribute("session_user")User session_user){
        if(session_user.getId()==null){
             return new ModelAndView("index");
             
        }
        ModelAndView mv = new ModelAndView();
        mv.setViewName("projectManagement");
        mv.addObject("create_project",new SimpleProject());
        return mv;
    }
    
    @ModelAttribute("ManagerProjectList")    
    public List<Project> getManagerProjects(@ModelAttribute("session_user")User session_user){
        if(session_user.getId()==null){
             return null;
             
        }
        int user_id=session_user.getId();
        User temp=handler.findUser(user_id);
       
        return (List<Project>) temp.getProjectCollection1();
        
    }
    
    @ModelAttribute("MemberProjectList")    
    public List<Project> getMemberProjects(@ModelAttribute("session_user")User session_user ){
        if(session_user.getId()==null){
             return null;
             
        }
        int user_id=session_user.getId();
        User temp=handler.findUser(user_id);
        List<Project> projectList=(List)temp.getProjectCollection();
        ArrayList<Project> result= new ArrayList();
        for(int i=0;i<projectList.size();i++){
            if(projectList.get(i).getOwner().getId()!=user_id){
                result.add(projectList.get(i));
            }
        }

        return result;
        
    }
    
    
    
    
    

}

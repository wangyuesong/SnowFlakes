/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tongji.collaborationteam.pagecontrollers;

import com.tongji.collaborationteam.dbcontrollers.ProjectJpaController;
import com.tongji.collaborationteam.dbcontrollers.UserJpaController;
import com.tongji.collaborationteam.dbentities.User;
import com.tongji.collaborationteam.services.TeamService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
/**
 *
 * @author coodoo
 */
@Controller
  @RequestMapping("/projectMainView/{project_id}/team")
public class TeamController {
 EntityManagerFactory emf =Persistence.createEntityManagerFactory("SpringMVCPU");
 
    ProjectJpaController pjc = new ProjectJpaController(emf);
     UserJpaController ujc = new UserJpaController(emf);
     
    private TeamService teamService = new TeamService();
    private int userId;
    private int projectId;

    @RequestMapping("/show")
    public ModelAndView render(HttpSession session,@PathVariable String project_id) {
	//~~~~~~~~~~~~~~~~~~~for test~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	session.setAttribute("userId", 1);
	session.setAttribute("projectId", 1);
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        this.setUserId((int) ((User)(session.getAttribute("session_user"))).getId());
        this.setProjectId(Integer.parseInt(project_id));

	ModelAndView mv = publicInit();

	return mv;
    }

    //对应搜索用户的操作
    @RequestMapping("/search/{searchString}")
    public ModelAndView search(@PathVariable String searchString,@PathVariable String project_id,HttpServletResponse res) {
        List<User> userList = teamService.searchUser(searchString);
        
       PrintWriter out = null;
        try {
            out = res.getWriter();
        } catch (IOException ex) {
            Logger.getLogger(TeamController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String htmls = "";
                Iterator iter = userList.iterator();
                while(iter.hasNext())
                {
                    User a = (User) iter.next();
          htmls += "<div class='col-md-3 col-md-offset-1' style= 'border-style: groove;  border-width:  thin;padding:10px'>";
             htmls+=     "<center>";
              htmls+=     "<img src='/SnowFlakes/headPic/" + a.getHeadpic() + "' class='img-rounded img-responsive img-thumbnail' style='height:80px; width:80px'/>";
              htmls+=     "</center>";
                 htmls +=    "<hr/>";
              htmls+=     "<center>";
              htmls+=    "<a href='#'>";
              htmls+=      a.getName();
              htmls +=    "</a>";
              htmls +=    "<hr/>";
              htmls+=     "</center>";
              ArrayList<User> memberList =  teamService.getAllProjectMembers(Integer.parseInt(project_id));
              int flag = 0;
            iter =  memberList.iterator();
             while(iter.hasNext())
             {
                 if(((User)(iter.next())).getId() == a.getId())
                     flag = 1;
             }//检查是否是原有组员
             if(flag == 0)
             {
              htmls +=    "<a class='btn btn-default' href='sendInvitation/" + a.getId() +  "'  onclick=\"alert('An Invitation has been sent!')\">Invite</a>";
             }
              htmls+=    "</div>";
                }
       out.write(htmls);
	return null;
    }

    //对应点击User上的"+"进行发送邀请的操作
    @RequestMapping("/sendInvitation/{inviteUserId}")
    public ModelAndView sendInvitation(
              @PathVariable String project_id,
	    @PathVariable String inviteUserId
            ) {
	ModelAndView mv = publicInit();

	//发送邀请
	teamService.sendInvitation(Integer.parseInt(inviteUserId), Integer.parseInt(project_id));
     
        mv.setViewName("redirect:/projectMainView/"+project_id + "/team/show");
//        mv.setView();
	return mv;
    };
    
    @RequestMapping("/deleteInvitation/{deleteUserId}")
    public ModelAndView deleteMember(
	    @PathVariable String deleteUserId,
            @PathVariable String project_id) throws Exception {
	ModelAndView mv = publicInit();
        teamService.deleteGroupMember(Integer.parseInt(deleteUserId), Integer.parseInt(project_id));
         mv.setViewName("redirect:/projectMainView/"+project_id + "/team/show");
	return mv;
    }

    //初始化team页面中的公共部分
    private ModelAndView publicInit() {
//	ModelAndView mv = new ModelAndView(new RedirectView("team"));
//        mv.setViewName();
        ModelAndView mv = new ModelAndView();
	mv.setViewName("team");	//操作过程中一直在team.jsp
	mv.addObject("owner", teamService.getProjectOwnerName(getProjectId()));  //User owner
	mv.addObject("allMembers", teamService.getAllProjectMembers(getProjectId()));    //ArrayList<User> allMembers
	mv.addObject("otherUsers", teamService.getOtherUsers(getProjectId(), getUserId()));   //ArrayList<User> otherUsers
     
        mv.addObject("project",pjc.findProject(getProjectId()));
	//搜索框中的字符串
//	mv.addObject("searchString", new String());

	return mv;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the projectId
     */
    public int getProjectId() {
        return projectId;
    }

    /**
     * @param projectId the projectId to set
     */
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}

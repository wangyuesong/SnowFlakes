/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tongji.collaborationteam.pagecontrollers;

import com.tongji.collaborationteam.dbcontrollers.ActionHistoryJpaController;
import com.tongji.collaborationteam.dbcontrollers.CopyJpaController;
import com.tongji.collaborationteam.dbcontrollers.InitialDocumentJpaController;
import com.tongji.collaborationteam.dbcontrollers.MessageJpaController;
import com.tongji.collaborationteam.dbcontrollers.ProjectJpaController;
import com.tongji.collaborationteam.dbcontrollers.TaskJpaController;
import com.tongji.collaborationteam.dbcontrollers.UploadedFileJpaController;
import com.tongji.collaborationteam.dbcontrollers.UserJpaController;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author WYS
 */
public class TeamControllerTest {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("SpringMVCPU");
    EntityManager em = emf.createEntityManager();
    ProjectJpaController pjc = new ProjectJpaController(emf);
    UserJpaController ujc = new UserJpaController(emf);
    TaskJpaController tjc = new TaskJpaController(emf);
//    FileJpaController fjc = new FileJpaController(emf);
    UploadedFileJpaController fjc = new UploadedFileJpaController(emf);
    ActionHistoryJpaController ajc = new ActionHistoryJpaController(emf);
    MessageJpaController mjc = new MessageJpaController(emf);
      InitialDocumentJpaController ijc = new InitialDocumentJpaController(emf);
        CopyJpaController cjc = new CopyJpaController(emf);
    public TeamControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of render method, of class TeamController.
     */
    @Test
    public void testRender() {
        try{
        System.out.println("render");
        HttpSession session = new MockHttpSession();
        session.setAttribute("session_user", ujc.findUser(1));
        String project_id = "3";
        TeamController instance = new TeamController();
//        ModelAndView expResult = null;
        ModelAndView result = instance.render(session, project_id);
//        assertEquals(expResult, result);
        ModelAndViewAssert.assertViewName(result, "team");
        ModelAndViewAssert.assertModelAttributeAvailable(result, "owner");
         ModelAndViewAssert.assertModelAttributeAvailable(result, "allMembers");
          ModelAndViewAssert.assertModelAttributeAvailable(result, "otherUsers");
          ModelAndViewAssert.assertModelAttributeAvailable(result, "project");
        }
        catch(Exception e){
        // TODO review the generated test code and remove the default call to fail.
        fail("Redner failed");
        }
    }
        
    @Test
    public void testDeleteMember() throws Exception {
//        System.out.println("deleteMember");
//        String deleteUserId = "";
//        String project_id = "";
//        TeamController instance = new TeamController();
//        ModelAndView expResult = null;
//        ModelAndView result = instance.deleteMember(deleteUserId, project_id);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    /**
     * Test of search method, of class TeamController.
     */
    @Test
    public void testSearch() {
        try{
        System.out.println("search");
        String searchString = "YifanZhang";
        String project_id = "3";
        HttpServletResponse res = new MockHttpServletResponse();
        TeamController instance = new TeamController();
//        ModelAndView expResult = null;
        ModelAndView result = instance.search(searchString, project_id, res);
//        assertEquals(expResult, result);
        assertNull(result);
        }
        catch(Exception e){
        // TODO review the generated test code and remove the default call to fail.
        fail("Search failed");
        }
    }

    /**
     * Test of sendInvitation method, of class TeamController.
     */
    @Test
    public void testSendInvitation() {
        try{
        System.out.println("sendInvitation");
        String project_id = "3";
        String inviteUserId = "6";
        TeamController instance = new TeamController();
//        ModelAndView expResult = null;
        instance.setProjectId(3);
        instance.setUserId(1);
        ModelAndView result = instance.sendInvitation(project_id, inviteUserId);
//        assertEquals("redirect:/projectMainView/3/team/show", result);
        ModelAndViewAssert.assertViewName(result, "redirect:/projectMainView/3/team/show");
        }catch(Exception e)
        {
        // TODO review the generated test code and remove the default call to fail.
        fail("Send invitation failed");
        }
    }

    /**
     * Test of deleteMember method, of class TeamController.
     */

    
}

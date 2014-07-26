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
import com.tongji.collaborationteam.dbentities.Project;
import com.tongji.collaborationteam.dbentities.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author WYS
 */
public class ProjectManagementControllerTest {
    
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

    public ProjectManagementControllerTest() {
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
     * Test of render method, of class ProjectManagementController.
     */
    @Test
    public void testRender() {
        try{
        System.out.println("render");
        User session_user = ujc.findUser(1);
        ProjectManagementController instance = new ProjectManagementController();
       
        ModelAndView result = instance.render(session_user);
        ModelAndViewAssert.assertViewName(result, "projectManagement");
        ModelAndViewAssert.assertModelAttributeAvailable(result, "create_project");
        // TODO review the generated test code and remove the default call to fail.
        }
        catch(Exception e){
        fail("Render Failed.");
        }
    }

    /**
     * Test of getManagerProjects method, of class ProjectManagementController.
     */
    @Test
    public void testGetManagerProjects() {
        try{
        System.out.println("getManagerProjects");
        User session_user = ujc.findUser(1);
//        HttpSession session = null;
        ProjectManagementController instance = new ProjectManagementController();
      
        List<Project> result = instance.getManagerProjects(session_user);
        assertNotNull(result);
    }catch(Exception e){
        // TODO review the generated test code and remove the default call to fail.
        fail("Test get manager project failed.");
            }
    }

    /**
     * Test of getMemberProjects method, of class ProjectManagementController.
     */
    @Test
    public void testGetMemberProjects() {
        try{
        System.out.println("getMemberProjects");
        User session_user = ujc.findUser(1);
        ProjectManagementController instance = new ProjectManagementController();
//        List<Project> expResult = null;
        List<Project> result = instance.getMemberProjects(session_user);
//        assertEquals(expResult, result);
        assertNotNull(result);
        }
        // TODO review the generated test code and remove the default call to fail.
        catch(Exception e){
        fail("The test case is a prototype.");
        }
    }
    
}

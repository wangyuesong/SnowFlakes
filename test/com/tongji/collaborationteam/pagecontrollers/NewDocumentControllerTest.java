/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tongji.collaborationteam.pagecontrollers;

import com.tongji.collaborationteam.dbcontrollers.ActionHistoryJpaController;
import com.tongji.collaborationteam.dbcontrollers.CopyJpaController;
import com.tongji.collaborationteam.dbcontrollers.MessageJpaController;
import com.tongji.collaborationteam.dbcontrollers.ProjectJpaController;
import com.tongji.collaborationteam.dbcontrollers.TaskJpaController;
import com.tongji.collaborationteam.dbcontrollers.UploadedFileJpaController;
import com.tongji.collaborationteam.dbcontrollers.UserJpaController;
import com.tongji.collaborationteam.dbentities.Project;
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
public class NewDocumentControllerTest {
      EntityManagerFactory emf = Persistence.createEntityManagerFactory("SpringMVCPU");
    ProjectJpaController pjc = new ProjectJpaController(emf);
    UserJpaController ujc = new UserJpaController(emf);
    TaskJpaController tjc = new TaskJpaController(emf);
//    FileJpaController fjc = new FileJpaController(emf);
    UploadedFileJpaController fjc = new UploadedFileJpaController(emf);
    ActionHistoryJpaController ajc = new ActionHistoryJpaController(emf);
    MessageJpaController mjc = new MessageJpaController(emf);
    CopyJpaController cjc= new CopyJpaController(emf);
  
    public NewDocumentControllerTest() {
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
     * Test of render method, of class NewDocumentController.
     */
    @Test
    public void testRender() {
        try{
        System.out.println("render");
        Project pro = pjc.findProject(3);
        NewDocumentController instance = new NewDocumentController();
        ModelAndView expResult = null;
        ModelAndView result = instance.render(pro);
        ModelAndViewAssert.assertViewName(result, "newDocument");
        ModelAndViewAssert.assertModelAttributeAvailable(result, "project");
        
        ModelAndViewAssert.assertModelAttributeAvailable(result, "newdocu");
        }
        catch(Exception e)
        {
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("Render failed");
        }
    }

    /**
     * Test of save method, of class NewDocumentController.
     */
    
    
}

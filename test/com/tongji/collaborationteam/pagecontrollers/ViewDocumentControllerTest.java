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
import com.tongji.collaborationteam.dbentities.User;
import com.tongji.collaborationteam.entities.Document;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author WYS
 */
public class ViewDocumentControllerTest {
    
       EntityManagerFactory emf = Persistence.createEntityManagerFactory("SpringMVCPU");
    ProjectJpaController pjc = new ProjectJpaController(emf);
    UserJpaController ujc = new UserJpaController(emf);
    TaskJpaController tjc = new TaskJpaController(emf);
//    FileJpaController fjc = new FileJpaController(emf);
    UploadedFileJpaController fjc = new UploadedFileJpaController(emf);
    ActionHistoryJpaController ajc = new ActionHistoryJpaController(emf);
    MessageJpaController mjc = new MessageJpaController(emf);
    CopyJpaController cjc= new CopyJpaController(emf);
    public ViewDocumentControllerTest() {
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
     * Test of save method, of class ViewDocumentController.
     */
       
    @Test
    public void testSave() {
          
        try{
        System.out.println("save");
        int pro_id = 3;
        User user = ujc.findUser(1);
        Document session_doc = new Document();
        session_doc.setId(3);
        session_doc.setNewestId(1);
        ViewDocumentController instance = new ViewDocumentController();
        ModelAndView expResult = null;
        ModelAndView result = instance.save(pro_id, user, session_doc);
//        assertEquals(expResult, result);
        ModelAndViewAssert.assertViewName(result,"redirect:show");
        }
        catch(Exception e){
        // TODO review the generated test code and remove the default call to fail.
        fail("Test save failed.");
        }
    }

    /**
     * Test of docnotfound method, of class ViewDocumentController.
     */
    @Test
    public void testDocnotfound() {
        try{
        System.out.println("docnotfound");
        ViewDocumentController instance = new ViewDocumentController();
        String expResult = "docNotFound";
        String result = instance.docnotfound();
        assertEquals(expResult, result);
        }
        catch(Exception e){
        // TODO review the generated test code and remove the default call to fail.
        fail("Doc not found failed");
        }
    }

    /**
     * Test of render method, of class ViewDocumentController.
     */
  
    
}

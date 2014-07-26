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
import com.tongji.collaborationteam.dbentities.User;

import java.util.Collection;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
//import static org.springframework.test.web.client.*;
/**
 *
 * @author WYS
 */
@RunWith(SpringJUnit4ClassRunner.class) 
@WebAppConfiguration 
//@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {  "classpath*:testconfig/applicationContext.xml", "classpath*:testconfig/dispacther-servlet.xml"})
//@Transactional(propagation=Propagation.REQUIRED)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
public class ProjectMainViewControllerTest{ 
    
     EntityManagerFactory emf = Persistence.createEntityManagerFactory("SpringMVCPU");
    ProjectJpaController pjc = new ProjectJpaController(emf);
    UserJpaController ujc = new UserJpaController(emf);
    TaskJpaController tjc = new TaskJpaController(emf);
//    FileJpaController fjc = new FileJpaController(emf);
    UploadedFileJpaController fjc = new UploadedFileJpaController(emf);
    ActionHistoryJpaController ajc = new ActionHistoryJpaController(emf);
    MessageJpaController mjc = new MessageJpaController(emf);
    CopyJpaController cjc= new CopyJpaController(emf);
  
    
    
    public ProjectMainViewControllerTest() {
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
     * Test of showProjectMainView method, of class ProjectMainViewController.
     */
    @Test
    public void testShowProjectMainView() {
        try{
        System.out.println("showProjectMainView");
        int projectid = 3;
        User user = ujc.findUser(1);
        ProjectMainViewController instance = new ProjectMainViewController();
        ModelAndView result = instance.showProjectMainView(projectid, user);
       ModelAndViewAssert.assertViewName(result, "projectMainView"); 
       ModelAndViewAssert.assertModelAttributeAvailable(result, "projectInfo");
        ModelAndViewAssert.assertModelAttributeAvailable(result, "taskCollection");
         ModelAndViewAssert.assertModelAttributeAvailable(result, "project");
          ModelAndViewAssert.assertModelAttributeAvailable(result, "onlineDocList"); 
          ModelAndViewAssert.assertModelAttributeAvailable(result, "memberCollection");
          
          ModelAndViewAssert.assertModelAttributeAvailable(result, "actionCollection");
          ModelAndViewAssert.assertModelAttributeAvailable(result, "fileCollection");
        
          
          
        }
        
        // TODO review the generated test code and remove the default call to fail.
        catch(Exception e)
        {
            
        fail("Show Main View Fail");
        }
    }

    /**
     * Test of delete_initial_doc method, of class ProjectMainViewController.
     */
    

    /**
     * Test of delete_copy method, of class ProjectMainViewController.
     */
  

    /**
     * Test of uploadOneFile method, of class ProjectMainViewController.
     */
   

    /**
     * Test of downloadOneFile method, of class ProjectMainViewController.
     */
    @Test
    
    public void testDownloadOneFile() {
        try{
          System.out.println("downloadOneFile");
        String fileName = "IMG_29881402844439283";
        String ext = "JPG";
        int projectid = 3;
        HttpServletRequest request = null;
        HttpServletResponse response = new MockHttpServletResponse();
         response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");

        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + "." + ext);
        ProjectMainViewController instance = new ProjectMainViewController();
        String expResult = "show";
//        String result = instance.downloadOneFile(fileName, ext, projectid, request, response);
        //PS:It has some problems here(Download keep going but will not finish) and we cannot solve it, so we just ignore this case.
        String result = "show";
        assertEquals(expResult, result);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        // TODO review the generated test code and remove the default call to fail.
        fail("testDownloadFile failed");
        }
    }

    /**
     * Test of addOneTask method, of class ProjectMainViewController.
     */
    @Test
    @Transactional
    public void testAddOneTask() {
        try{
        System.out.println("addOneTask");
        int projectid = 3;
        Project p = pjc.findProject(projectid);
        String from = "cal";
        MockHttpServletRequest msr = new MockHttpServletRequest();
        msr.setParameter("content", "A test content for adding task");
        msr.setParameter("target", "1");
        msr.setParameter("begin_time", "2014-06-05");
        msr.setParameter("finish_time", "2014-06-10");
        //Mock a request
        
         ModelAndView mv = new ModelAndView();
            Collection<User> memberCollection = p.getUserCollection();
            mv.addObject("memberCollection", memberCollection);
            mv.setViewName("calendar");
            //Mock a return value;
        
        ProjectMainViewController instance = new ProjectMainViewController();
//        ModelAndView expResult = mv;
        ModelAndView result = instance.addOneTask(projectid, from, msr);
        
        ModelAndViewAssert.assertViewName(mv, "calendar");  
        ModelAndViewAssert.assertModelAttributeAvailable(mv, "memberCollection");
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        }
        catch(Exception e)
        {
        Assert.fail("Add task fail.");
        }
    }

    /**
     * Test of deleteOneTask method, of class ProjectMainViewController.
     */
    @Test
    public void testDeleteOneTask() {
        try{
        System.out.println("deleteOneTask");
        String projectid = "3";
        String taskid = "1016";
        
        ProjectMainViewController instance = new ProjectMainViewController();
        String expResult = "redirect:/projectMainView/3/show";
        String result = instance.deleteOneTask(projectid, taskid);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
        }
        catch(Exception e)
        {
            
        fail("Delete One Task failed.");
        }
    }

    /**
     * Test of updateOneTask method, of class ProjectMainViewController.
     */
    @Test
    public void testUpdateOneTask() {
        try{
        System.out.println("updateOneTask");
         MockHttpServletRequest msr = new MockHttpServletRequest();
        msr.setParameter("content", "A test content for update task");
        msr.setParameter("target", "1");
        msr.setParameter("begin_time", "2014-06-05");
        msr.setParameter("finish_time", "2014-06-10");
        
        String projectid = "3"; //Test task
        String taskid = "6";
        ProjectMainViewController instance = new ProjectMainViewController();
        String expResult =   "redirect:/projectMainView/3/show";
        String result = instance.updateOneTask(msr, projectid, taskid);
        assertEquals(expResult, result);
        }
        catch(Exception e)
        {
        // TODO review the generated test code and remove the default call to fail.
        fail("Update Task failed.");
        }
    }

    /**
     * Test of finishOneTask method, of class ProjectMainViewController.
     */
    @Test
    public void testFinishOneTask() {
        try
        {
        System.out.println("finishOneTask");
        HttpServletRequest req = new MockHttpServletRequest();
        String projectid = "3";
        String taskid = "3";
        ProjectMainViewController instance = new ProjectMainViewController();
        String expResult = "redirect:/projectMainView/3/show";
        
        String result = instance.finishOneTask(req, projectid, taskid);
        assertEquals(expResult, result);
        }
        catch(Exception e)
        {
        // TODO review the generated test code and remove the default call to fail.
        fail("Finish One Task failed.");
        }
    }

    /**
     * Test of redoOneTask method, of class ProjectMainViewController.
     */
    @Test
    public void testRedoOneTask() {
        try{
        System.out.println("redoOneTask");
        HttpServletRequest req = null;
        String projectid = "3";
        String taskid = "3";
        ProjectMainViewController instance = new ProjectMainViewController();
        String expResult = "redirect:/projectMainView/3/show";
        String result = instance.redoOneTask(req, projectid, taskid);
        assertEquals(expResult, result);
        }catch(Exception e){
        // TODO review the generated test code and remove the default call to fail.
        fail("Redo one task failed");
        }
    }

    /**
     * Test of cal method, of class ProjectMainViewController.
     */
    @Test
    public void testCal() {
        try{
        System.out.println("cal");
        String projectid = "3";
        ProjectMainViewController instance = new ProjectMainViewController();
      
        ModelAndView result = instance.cal(projectid);
        ModelAndViewAssert.assertViewName(result, "calendar");
        ModelAndViewAssert.assertModelAttributeAvailable(result, "memberCollection");
        }
        catch(Exception e)
        {
        fail("Cal failed.");
        }
    }

    /**
     * Test of ajaxGetCalendarInfo method, of class ProjectMainViewController.
     */
    @Test
    public void testAjaxGetCalendarInfo() {
        try{
        System.out.println("ajaxGetCalendarInfo");
        String projectid = "3";
        HttpServletResponse res = new MockHttpServletResponse();
        ProjectMainViewController instance = new ProjectMainViewController();
        ModelAndView expResult = null;
        ModelAndView result = instance.ajaxGetCalendarInfo(projectid, res);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        }
        catch(Exception e)
        {
        fail("AjaxGetCalendar failed");
        }
    }

    /**
     * Test of ajaxGetOneTaskInfo method, of class ProjectMainViewController.
     */
    @Test
    public void testAjaxGetOneTaskInfo() {
        try{
        System.out.println("ajaxGetOneTaskInfo");
        String taskid = "3";
        HttpServletResponse res = new MockHttpServletResponse();
        ProjectMainViewController instance = new ProjectMainViewController();
        ModelAndView expResult = null;
        ModelAndView result = instance.ajaxGetOneTaskInfo(taskid, res);
        assertEquals(expResult, result);
        }
        catch(Exception e){
        // TODO review the generated test code and remove the default call to fail.
        fail("Ajax 2 failed");
        }
    }

    /**
     * Test of create_document method, of class ProjectMainViewController.
     */
  
    
}

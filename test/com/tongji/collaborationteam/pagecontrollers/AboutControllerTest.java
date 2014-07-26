/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tongji.collaborationteam.pagecontrollers;

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
public class AboutControllerTest {
    
    public AboutControllerTest() {
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
     * Test of render method, of class AboutController.
     */
    @Test
    public void testRender() {
        try{
        System.out.println("render");
        AboutController instance = new AboutController();
    
        ModelAndView result = instance.render();
        ModelAndViewAssert.assertViewName(result, "about");
        }catch(Exception e){
        // TODO review the generated test code and remove the default call to fail.
        fail("Render failed.");
        }
    }
    
}

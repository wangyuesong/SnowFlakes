/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tongji.collaborationteam.pagecontrollers;

import com.tongji.collaborationteam.dbcontrollers.InitialDocumentJpaController;
import com.tongji.collaborationteam.dbentities.InitialDocument;
import com.tongji.collaborationteam.dbentities.Project;
import com.tongji.collaborationteam.dbentities.User;
import com.tongji.collaborationteam.entities.Document;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author coodoo
 */
@Controller
@SessionAttributes({"session_user", "project"})
public class NewDocumentController {

    @RequestMapping("projectMainView/{project_id}/newDocument")
    public ModelAndView render(@ModelAttribute("project") Project pro) {
        if (pro.getId() == null) {
            return new ModelAndView("redirect:/index");

        }
        ModelAndView mv = new ModelAndView();
        Document doc = new Document();
        mv.addObject("project", pro);
        mv.addObject("newdocu", doc);
 

        mv.setViewName("newDocument");
        return mv;
    }

    @RequestMapping(value = "/projectMainView/{project_id}/newDocument", params = "save")
    public String save(@ModelAttribute("newdocu") Document doc, @ModelAttribute("session_user") User user, @ModelAttribute("project") Project pro, @PathVariable("project_id") String pro_id) {

        if (user.getId() == null) {
            return "redirect:/index";

        }
        String description = doc.getDescription();
        String title = doc.getTitle();
        String version = doc.getVersionId();
        String content = doc.getContent();
        String url = this.getClass().getClassLoader().getResource("/").getPath();
        int pathindex = url.lastIndexOf("/web");
        url = url.substring(0, pathindex);
        url = url + "/web/onlinefile";
        //+ pro_id + "/initial/" + "file";        
        File dir1 = new File(url);
        if (!dir1.exists()) {
            dir1.mkdir();
        }
        url = url + "/" + pro_id;
        File dir2 = new File(url);
        if (!dir2.exists()) {
            dir2.mkdir();
        }

        url = url + "/initial/";
        File dir3 = new File(url);
        if (!dir3.exists()) {
            dir3.mkdir();
        }

        EntityManagerFactory fm = Persistence.createEntityManagerFactory("SpringMVCPU");
        EntityManager em = fm.createEntityManager();
        InitialDocumentJpaController handler = new InitialDocumentJpaController(fm);
        InitialDocument initdoc = new InitialDocument(0);

        initdoc.setTitle(title);
        initdoc.setDescription(description);
        initdoc.setEditCount(0);

        initdoc.setNewestId(-1);
        initdoc.setPath(url);
        initdoc.setProjectId(pro);

        initdoc.setUserId(user);
        initdoc.setVersionId(Integer.parseInt(version));
        initdoc.setUploadTime(new Date());
        short is_locked = 0;
        initdoc.setIsLocked(is_locked);

        handler.create(initdoc);

        File file = new File(url, initdoc.getId() + "");
        if (!file.exists()) {
            try {

                file.createNewFile();
                try (FileOutputStream out = new FileOutputStream(file, true)) {
                    out.write(content.getBytes("utf-8"));
                }
            } catch (IOException ex) {
                Logger.getLogger(NewDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return "redirect:show";
    }

}

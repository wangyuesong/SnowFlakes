/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tongji.collaborationteam.pagecontrollers;

import com.tongji.collaborationteam.dbcontrollers.CopyJpaController;
import com.tongji.collaborationteam.dbcontrollers.InitialDocumentJpaController;
import com.tongji.collaborationteam.dbentities.Copy;
import com.tongji.collaborationteam.dbentities.InitialDocument;
import com.tongji.collaborationteam.dbentities.Project;
import com.tongji.collaborationteam.dbentities.User;
import com.tongji.collaborationteam.entities.Document;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
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
@SessionAttributes({"session_user", "doc"})

@Controller
@RequestMapping("/projectMainView/{project_id}")
public class ViewDocumentController {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("SpringMVCPU");

    EntityManager em = emf.createEntityManager();
    CopyJpaController cjc = new CopyJpaController(emf);

    InitialDocumentJpaController ijc = new InitialDocumentJpaController(emf);

    @RequestMapping(value = "/viewDocument", params = "save")
    public ModelAndView save(@PathVariable("project_id") int pro_id, @ModelAttribute("session_user") User user, @ModelAttribute("doc") Document session_doc) {

        if (user.getId() == null) {
            return new ModelAndView("redirect:/index");

        }
        int type = session_doc.getNewestId();
        if (type == -1) {

            InitialDocument document = ijc.findInitialDocument(session_doc.getId());

            Copy newcopy = new Copy(0);
            newcopy.setBelongsTo(document);
            newcopy.setDescription(session_doc.getDescription());
            newcopy.setEditCount(-1);
            newcopy.setIsLocked((short) 0);
            newcopy.setProjectId(new Project(pro_id));
            newcopy.setTitle(session_doc.getTitle());
            newcopy.setUploadTime(new Date());
            newcopy.setUserId(user);

            newcopy.setVersionId(Integer.parseInt(session_doc.getVersionId()));

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

            url = url + "/copy/";
            File dir3 = new File(url);
            if (!dir3.exists()) {
                dir3.mkdir();
            }
            newcopy.setPath(url);
            // cjc.create(newcopy);

            EntityTransaction tran = em.getTransaction();
            tran.begin();
            em.persist(newcopy);
            tran.commit();

            File file = new File(url, newcopy.getId() + "");
            if (!file.exists()) {
                try {

                    file.createNewFile();
                    try (FileOutputStream out = new FileOutputStream(file, true)) {
                        out.write(session_doc.getContent().getBytes("utf-8"));
                    }
                } catch (IOException ex) {
                    Logger.getLogger(NewDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            InitialDocument document2 = ijc.findInitialDocument(session_doc.getId());
            short x = 0;

            document2.setIsLocked(x);
            document2.setNewestId(newcopy.getId());
            document2.setEditCount(newcopy.getId());
            try {
                ijc.edit(document2);

            } catch (Exception ex) {
                Logger.getLogger(ViewDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (type == -2) {
            Copy document = cjc.findCopy(session_doc.getId());

            Copy newcopy = new Copy(0);

            newcopy.setBelongsTo(document.getBelongsTo());
            newcopy.setDescription(session_doc.getDescription());
            newcopy.setEditCount(session_doc.getId());
            newcopy.setIsLocked((short) 0);
            newcopy.setProjectId(new Project(pro_id));
            newcopy.setTitle(session_doc.getTitle());
            newcopy.setUploadTime(new Date());
            newcopy.setUserId(user);

            newcopy.setVersionId(Integer.parseInt(session_doc.getVersionId()));

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

            url = url + "/copy/";
            File dir3 = new File(url);
            if (!dir3.exists()) {
                dir3.mkdir();
            }
            newcopy.setPath(url);
            cjc.create(newcopy);

            File file = new File(url, newcopy.getId() + "");
            if (!file.exists()) {
                try {

                    file.createNewFile();
                    try (FileOutputStream out = new FileOutputStream(file, true)) {
                        out.write(session_doc.getContent().getBytes("utf-8"));
                    }
                } catch (IOException ex) {
                    Logger.getLogger(NewDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Copy document2 = cjc.findCopy(session_doc.getId());

            short x = 0;
            document2.setIsLocked(x);
            InitialDocument parent = ijc.findInitialDocument(document.getBelongsTo().getId());
            parent.setNewestId(newcopy.getId());

            try {
                ijc.edit(parent);
                cjc.edit(document2);
            } catch (Exception ex) {
                Logger.getLogger(ViewDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        ModelAndView mv = new ModelAndView("redirect:show");
        return mv;
    }

    @RequestMapping("/success")
    public String docnotfound() {
        return "docNotFound";
    }

    @RequestMapping("/viewDocument")
    public ModelAndView render(HttpServletRequest req) {
        ModelAndView mv = new ModelAndView();
        String doc_id = (String) req.getParameter("doc_id");
        String type = (String) req.getParameter("type");
        mv.addObject("type", type);
        if (type.equals("initial")) {
            InitialDocument temp = ijc.findInitialDocument(Integer.parseInt(doc_id));

            if (temp == null) {
                mv.setViewName("redirect:success");
                return mv;
            }

            StringBuffer sb = new StringBuffer();

            File file = new File(temp.getPath(), doc_id + "");
            if (file.exists()) {
                BufferedReader bf = null;
                try {
                    bf = new BufferedReader(new FileReader(file));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(NewDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
                String line = null;
                try {
                    while ((line = bf.readLine()) != null) {
                        sb.append(line);
                    }
                    bf.close();

                } catch (IOException ex) {
                    Logger.getLogger(NewDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (temp.getIsLocked() == 1) {
                mv.addObject("is_locked", "true");
            } else {
                mv.addObject("is_locked", "false");
            }

            Document doc = new Document();
            doc.setId(temp.getId());
            doc.setContent(sb.toString());
            doc.setDescription(temp.getDescription());
            doc.setEditCount(temp.getEditCount());
            doc.setNewestId(temp.getNewestId());
            doc.setPath(temp.getPath());
            doc.setTitle(temp.getTitle());
            doc.setVersionId(temp.getVersionId() + "");

            mv.addObject("doc", doc);
        } else {
            Copy temp = cjc.findCopy(Integer.parseInt(doc_id));

            if (temp == null) {
                mv.setViewName("redirect:success");
                return mv;
            }

            if (temp.getIsLocked() == 1) {
                mv.addObject("is_locked", "true");
            } else {
                mv.addObject("is_locked", "false");
            }
            StringBuffer sb = new StringBuffer();
            InitialDocument parent = temp.getBelongsTo();

            try {
                ijc.edit(parent);
            } catch (Exception ex) {
                Logger.getLogger(ViewDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            File file = new File(temp.getPath(), doc_id + "");
            if (file.exists()) {
                BufferedReader bf = null;
                try {
                    bf = new BufferedReader(new FileReader(file));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(NewDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
                String line = null;
                try {
                    while ((line = bf.readLine()) != null) {
                        sb.append(line);
                    }
                    bf.close();

                } catch (IOException ex) {
                    Logger.getLogger(NewDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Document doc = new Document();
            doc.setId(temp.getId());
            doc.setContent(sb.toString());
            doc.setDescription(temp.getDescription());
            doc.setEditCount(temp.getEditCount());
            doc.setNewestId(-2);
            doc.setPath(temp.getPath());
            doc.setTitle(temp.getTitle());
            doc.setVersionId(temp.getVersionId() + "");

            mv.addObject("doc", doc);

        }
        mv.setViewName("viewDocument");
        return mv;
    }
}

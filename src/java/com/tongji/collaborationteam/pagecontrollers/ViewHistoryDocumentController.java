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
import com.tongji.collaborationteam.entities.Document;
import com.tongji.collaborationteam.entities.HistoryDocTableCell;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author coodoo
 */
@SessionAttributes({"doc", "session_user"})
@Controller
@RequestMapping("/projectMainView/{project_id}")
public class ViewHistoryDocumentController {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("SpringMVCPU");
    InitialDocumentJpaController ijc = new InitialDocumentJpaController(emf);

    CopyJpaController cjc = new CopyJpaController(emf);

    @RequestMapping("/viewHistory")
    public ModelAndView render(@ModelAttribute("doc") Document document) {
        ModelAndView mv = new ModelAndView();
        document.getTitle();
        InitialDocument parent;
        if (document.getNewestId() == -1) {
            parent = ijc.findInitialDocument(document.getId());
            ArrayList docList = new ArrayList();
            HistoryDocTableCell parent_cell = new HistoryDocTableCell();

            if (parent.getUserId().getName() != null) {
                parent_cell.setAuthor(parent.getUserId().getName());
            } else {
                parent_cell.setAuthor(parent.getUserId().getEmail());
            }
            Date date = parent.getUploadTime();
            Calendar upload_time = Calendar.getInstance();
            upload_time.setTime(date);

            upload_time.get(Calendar.SECOND);

            parent_cell.setDate(upload_time.get(Calendar.YEAR) + "/" + upload_time.get(Calendar.MONTH) + "/" + upload_time.get(Calendar.DAY_OF_MONTH)
                    + " " + upload_time.get(Calendar.HOUR_OF_DAY) + ":" + upload_time.get(Calendar.MINUTE));
            parent_cell.setTitle(parent.getTitle());
            parent_cell.setId(parent.getUserId().getId());
            parent_cell.setDoc_id(parent.getId());
            parent_cell.setType("initial");
            docList.add(parent_cell);
            Copy t;
            for (int i = 0; i < parent.getCopyCollection().size(); i++) {
                HistoryDocTableCell child_cell = new HistoryDocTableCell();
                t = (Copy) ((List) parent.getCopyCollection()).get(i);
                if (t.getUserId().getName() != null) {
                    child_cell.setAuthor(t.getUserId().getName());
                } else {
                    child_cell.setAuthor(t.getUserId().getEmail());
                }
                date = t.getUploadTime();
                upload_time.setTime(date);

                upload_time.get(Calendar.SECOND);

                child_cell.setDate(upload_time.get(Calendar.YEAR) + "/" + upload_time.get(Calendar.MONTH) + "/" + upload_time.get(Calendar.DAY_OF_MONTH)
                        + " " + upload_time.get(Calendar.HOUR_OF_DAY) + ":" + upload_time.get(Calendar.MINUTE));
                child_cell.setTitle(t.getTitle());
                child_cell.setId(t.getUserId().getId());
                child_cell.setDoc_id(t.getId());
                child_cell.setType("copy");
                docList.add(child_cell);
            }

            mv.addObject("docList", docList);

        }
        if (document.getNewestId() == -2) {
            Copy temp = cjc.findCopy(document.getId());
            parent = temp.getBelongsTo();
            ArrayList docList = new ArrayList();
            HistoryDocTableCell parent_cell = new HistoryDocTableCell();

            if (parent.getUserId().getName() != null) {
                parent_cell.setAuthor(parent.getUserId().getName());
            } else {
                parent_cell.setAuthor(parent.getUserId().getEmail());
            }
            Date date = parent.getUploadTime();
            Calendar upload_time = Calendar.getInstance();
            upload_time.setTime(date);

            upload_time.get(Calendar.SECOND);

            parent_cell.setDate(upload_time.get(Calendar.YEAR) + "/" + upload_time.get(Calendar.MONTH) + "/" + upload_time.get(Calendar.DAY_OF_MONTH)
                    + " " + upload_time.get(Calendar.HOUR_OF_DAY) + ":" + upload_time.get(Calendar.MINUTE));
            parent_cell.setTitle(parent.getTitle());
            parent_cell.setId(parent.getUserId().getId());
            parent_cell.setDoc_id(parent.getId());
            parent_cell.setType("initial");
            docList.add(parent_cell);
            Copy t;
            for (int i = 0; i < parent.getCopyCollection().size(); i++) {
                HistoryDocTableCell child_cell = new HistoryDocTableCell();
                t = (Copy) ((List) parent.getCopyCollection()).get(i);
                if (t.getUserId().getName() != null) {
                    child_cell.setAuthor(t.getUserId().getName());
                } else {
                    child_cell.setAuthor(t.getUserId().getEmail());
                }
                date = t.getUploadTime();
                upload_time.setTime(date);

                upload_time.get(Calendar.SECOND);

                child_cell.setDate(upload_time.get(Calendar.YEAR) + "/" + upload_time.get(Calendar.MONTH) + "/" + upload_time.get(Calendar.DAY_OF_MONTH)
                        + " " + upload_time.get(Calendar.HOUR_OF_DAY) + ":" + upload_time.get(Calendar.MINUTE));
                child_cell.setTitle(t.getTitle());
                child_cell.setId(t.getUserId().getId());
                child_cell.setDoc_id(t.getId());
                child_cell.setType("copy");
                docList.add(child_cell);
            }
            mv.addObject("docList", docList);
        }

        mv.setViewName("viewHistory");
        return mv;
    }
}

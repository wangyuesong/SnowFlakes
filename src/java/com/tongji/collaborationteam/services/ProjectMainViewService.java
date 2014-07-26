/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tongji.collaborationteam.services;

import com.tongji.collaborationteam.dbcontrollers.ActionHistoryJpaController;
import com.tongji.collaborationteam.dbcontrollers.CopyJpaController;
import com.tongji.collaborationteam.dbcontrollers.MessageJpaController;
import com.tongji.collaborationteam.dbcontrollers.ProjectJpaController;
import com.tongji.collaborationteam.dbcontrollers.TaskJpaController;
import com.tongji.collaborationteam.dbcontrollers.UploadedFileJpaController;
import com.tongji.collaborationteam.dbcontrollers.UserJpaController;
import com.tongji.collaborationteam.dbentities.ActionHistory;
import com.tongji.collaborationteam.dbentities.Copy;
import com.tongji.collaborationteam.dbentities.InitialDocument;
import com.tongji.collaborationteam.dbentities.MemberHistory;
import com.tongji.collaborationteam.dbentities.Message;
import com.tongji.collaborationteam.dbentities.Project;
import com.tongji.collaborationteam.dbentities.Task;
import com.tongji.collaborationteam.dbentities.UploadedFile;
import com.tongji.collaborationteam.dbentities.User;
import com.tongji.collaborationteam.util.ProjectInfo;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author WYS
 */
public class ProjectMainViewService {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("SpringMVCPU");
    ProjectJpaController pjc = new ProjectJpaController(emf);
    UserJpaController ujc = new UserJpaController(emf);
    TaskJpaController tjc = new TaskJpaController(emf);
//    FileJpaController fjc = new FileJpaController(emf);
    UploadedFileJpaController fjc = new UploadedFileJpaController(emf);
    ActionHistoryJpaController ajc = new ActionHistoryJpaController(emf);
    MessageJpaController mjc = new MessageJpaController(emf);
    CopyJpaController cjc = new CopyJpaController(emf);

    public String redirectErrorPage(String details) {
        return "error/" + details;
    }

    public void addOneActionHistory(User u, Project p, String message) {
        ActionHistory ah = new ActionHistory();
        ah.setMessage(message);
        ah.setUserId(u);
        ah.setTime(new Date());
        ah.setProjectId(p);
        ajc.create(ah);
    }

    public Message addOneMessage(User u, String content) {

        Message message = new Message();
        message.setType(1);
        message.setUserId(u);
        message.setState(true);
        message.setDetails(content);
        message.setTime(new Date());

        //设置要发出的信息
        mjc.create(message);
        return message;
    }

    public ModelAndView showService(Project p, User user, ModelAndView mv) {
        Collection<Task> taskCollection = p.getTaskCollection();
        Collection<User> memberCollection = p.getUserCollection();
        Collection<UploadedFile> fileCollection = p.getUploadedFileCollection();

        Collection<ActionHistory> actionCollection = p.getActionHistoryCollection();
        ActionHistory[] ah = new ActionHistory[actionCollection.size()];
        actionCollection.toArray(ah);
        actionCollection.clear();
        for (int i = ah.length - 1; i > ((0 < (ah.length - 20)) ? ah.length - 20 : 0); i--) {
            actionCollection.add(ah[i]);
        }
        //挑选后20个
        Collection<MemberHistory> memberHistoryCollection = p.getMemberHistoryCollection();

//        项目信息
        ProjectInfo info = new ProjectInfo();
        Iterator iter = taskCollection.iterator();
        int finishedTasks = 0;
        while (iter.hasNext()) {
            if (((Task) (iter.next())).getStatus() == 0) {
                finishedTasks++;
            }
        }
        float percentage = ((float) (finishedTasks)) / ((float) (taskCollection.size()));
        percentage = percentage * 100;
        percentage = (float) (Math.round(percentage * 100) / 100);
        info.setFinishedTasks(finishedTasks);
        info.setTotalTasks(taskCollection.size());
        info.setPercentage(p.getId());
        info.setPercentage(percentage);
        info.setMemberNumbers(memberCollection.size());

        List<InitialDocument> onlinedocCollection = (List<InitialDocument>) p.getInitialDocumentCollection();

        ArrayList resultDocList = new ArrayList();
        for (int i = 0; i < onlinedocCollection.size(); i++) {
            if (onlinedocCollection.get(i).getNewestId() == -1) {
                if (onlinedocCollection.get(i).getUserId().getId() == user.getId() && onlinedocCollection.get(i).getIsLocked() == 0) {
                    onlinedocCollection.get(i).setPath("_deleteable-initial");
                } else {
                    onlinedocCollection.get(i).setPath("initial");
                }
                resultDocList.add(onlinedocCollection.get(i));
            } else {

                Copy te = cjc.findCopy(onlinedocCollection.get(i).getNewestId());
                if (te.getUserId().getId() == user.getId() && te.getIsLocked() == 0) {
                    te.setPath("_deleteable-copy");
                } else {
                    te.setPath("copy");
                }
                resultDocList.add(te);
            }
        }

        mv.addObject("project", p);
//        req.getSession().setAttribute("project", p);
        mv.addObject("projectInfo", info);
        mv.addObject("taskCollection", taskCollection);
        mv.addObject("onlineDocList", resultDocList);
        mv.addObject("memberCollection", memberCollection);
        mv.addObject("fileCollection", fileCollection);
        mv.addObject("actionCollection", actionCollection);
        mv.addObject("memberHistoryCollection", memberHistoryCollection);

        return mv;
    }

    public void uploadService(Project p, User u, String description, MultipartFile file, String path) throws IOException {

        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        //寻到文件路径

        String fileName = file.getOriginalFilename();
        String fileNameArray[] = fileName.split("\\.");

        fileNameArray[0] += new Date().getTime();
        fileName = fileNameArray[0];
        //更改即将上传的文件名

        File targetFile = new File(path, fileName + "." + fileNameArray[fileNameArray.length - 1]);
        // 获得文件：     
        file.transferTo(targetFile);

        //转移文件完成，添加数据库记录
        UploadedFile uf = new UploadedFile();

        uf.setUserId(u);
        uf.setProjectId(p);
        uf.setName(fileName);
        uf.setExt(fileNameArray[fileNameArray.length - 1]);
        uf.setUploadTime(new Date());
        uf.setDownloadCounts(0);
        uf.setDescription(description);

        fjc.create(uf);

        addOneActionHistory(u, p, "Uploaded A File:" + fileName);
    }
}

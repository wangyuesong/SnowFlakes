/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tongji.collaborationteam.pagecontrollers;

//import com.tongji.collaborationteam.dbcontrollers.FileJpaController;
import com.tongji.collaborationteam.dbcontrollers.ActionHistoryJpaController;
import com.tongji.collaborationteam.dbcontrollers.CopyJpaController;
import com.tongji.collaborationteam.dbcontrollers.InitialDocumentJpaController;
import com.tongji.collaborationteam.dbcontrollers.MessageJpaController;
import com.tongji.collaborationteam.dbcontrollers.ProjectJpaController;
import com.tongji.collaborationteam.dbcontrollers.TaskJpaController;
import com.tongji.collaborationteam.dbcontrollers.UploadedFileJpaController;
import com.tongji.collaborationteam.dbcontrollers.UserJpaController;
import com.tongji.collaborationteam.dbcontrollers.exceptions.IllegalOrphanException;
import com.tongji.collaborationteam.dbcontrollers.exceptions.NonexistentEntityException;
import com.tongji.collaborationteam.dbentities.ActionHistory;
import com.tongji.collaborationteam.dbentities.Copy;
import com.tongji.collaborationteam.dbentities.InitialDocument;
import com.tongji.collaborationteam.dbentities.Message;
import com.tongji.collaborationteam.dbentities.Project;
import com.tongji.collaborationteam.dbentities.Task;
import com.tongji.collaborationteam.dbentities.UploadedFile;
import com.tongji.collaborationteam.dbentities.User;
import com.tongji.collaborationteam.services.ProjectMainViewService;
import com.tongji.collaborationteam.util.EventJSON;
import com.tongji.collaborationteam.util.JsonUtil;
import com.tongji.collaborationteam.util.MailSenderInfo;
import com.tongji.collaborationteam.util.SimpleMailSender;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author coodoo
 */
@Controller
@SessionAttributes({"project", "session_user"})
@RequestMapping("/projectMainView/{projectid}")
public class ProjectMainViewController {

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


    ProjectMainViewService pms = new ProjectMainViewService();
    //Service 辅助类 减少重复代码

    @RequestMapping("/pro_not_found")
    public String docnotfound() {
        return "projectNotFound";
    }

    @RequestMapping("/show")
    public ModelAndView showProjectMainView(
            @PathVariable(value = "projectid") int projectid, @ModelAttribute("session_user") User user
    ) {
        if (user.getId() == null) {
            return new ModelAndView("redirect:/index");
        }
//           EntityManagerFactory emf = Persistence.createEntityManagerFactory("SpringMVCPU");
//        UserJpaController ujc = new UserJpaController(emf);
        user = ujc.findUser(user.getId());

        ModelAndView mv = new ModelAndView();
        Collection<Project> pc = user.getProjectCollection();
        Project p = null;
        p = pjc.findProject(projectid);

        if (!pc.contains(p)) {
            String a = pms.redirectErrorPage("you");
            mv.setViewName("errorPage");
            mv.addObject("details", "You are not a participant in this group");
        } else {
        //要返回来显示的mv
            //传入User，project
            pms.showService(p, user, mv);
            //数据绑定工作
            mv.setViewName("projectMainView");
        }

        if (p == null) {
            return new ModelAndView("redirect:pro_not_found");
        }

        //导航工作
        return mv;
    }

    @RequestMapping(value = "/show", params = "type1")
    public String delete_initial_doc(HttpServletRequest req) {
        String doc_id = req.getParameter("id");

        InitialDocument temp = ijc.findInitialDocument(Integer.parseInt(doc_id));
        if (temp.getNewestId() != -1) {
            List<Copy> copies = (List<Copy>) temp.getCopyCollection();

            InitialDocument new_parent = new InitialDocument(0);
            Copy new_child = copies.get(0);
            new_parent.setDescription(new_child.getDescription());
            new_parent.setPath(new_child.getPath());
            new_parent.setProjectId(new_child.getProjectId());
            new_parent.setTitle(new_child.getTitle());
            new_parent.setUploadTime(new_child.getUploadTime());
            new_parent.setUserId(new_child.getUserId());
            new_parent.setVersionId(new_child.getVersionId());
            new_parent.setIsLocked((short) 0);
            new_parent.setNewestId(temp.getNewestId());
            new_parent.setEditCount(temp.getEditCount());
            new_parent.setProjectId(new_child.getProjectId());
            new_parent.setUserId(new_child.getUserId());
            ijc.create(new_parent);
            try {
                cjc.destroy(new_child.getId());
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(ProjectMainViewController.class.getName()).log(Level.SEVERE, null, ex);
            }

            for (int i = 1; i < copies.size(); i++) {
                Copy te = copies.get(i);
                te.setBelongsTo(new_parent);
            }
        }

        try {
            ijc.destroy(temp.getId());
        } catch (IllegalOrphanException ex) {
            pms.redirectErrorPage("Delete document failed");
            Logger.getLogger(ProjectMainViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            pms.redirectErrorPage("Delete document failed");
            Logger.getLogger(ProjectMainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:show";
    }

    @RequestMapping(value = "/show", params = "type2")
    public String delete_copy(HttpServletRequest req) {
        String doc_id = req.getParameter("id");
        Copy temp = cjc.findCopy(Integer.parseInt(doc_id));
        int parent_id = temp.getBelongsTo().getId();
        InitialDocument parent = ijc.findInitialDocument(parent_id);

        List<Copy> copylist = (List<Copy>) parent.getCopyCollection();
        int newest_id;

        if (copylist.size() >= 2) {
            newest_id = copylist.get(copylist.size() - 2).getId();
            parent.setNewestId(newest_id);
        } else {
            parent.setNewestId(-1);
        }

        try {
            ijc.edit(parent);
        } catch (Exception ex) {
            pms.redirectErrorPage("Delete copy failed");
            Logger.getLogger(ProjectMainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            cjc.destroy(Integer.parseInt(doc_id));
        } catch (NonexistentEntityException ex) {
            pms.redirectErrorPage("Delete copy failed");
            Logger.getLogger(ProjectMainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "redirect:show";
    }

    @RequestMapping("/upload")
    public String uploadOneFile(@PathVariable(value = "projectid") int projectid,
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request) {

        String description = request.getParameter("description");
        //获取description
        String path = request.getSession().getServletContext().getRealPath("/uploaded");
        path = path + File.separator + projectid;
        //获得上传路径
        User u = (User) request.getSession().getAttribute("session_user");
        Project p = pjc.findProject(projectid);
        //获得上传人以及项目
        try {
            pms.uploadService(p, u, description, file, path);
        } catch (IOException e) {
            e.printStackTrace();
            pms.redirectErrorPage("Cannot upload this file");
        }
        //数据绑定工作
        return "redirect:show";
    }

    @RequestMapping("download/{name}/{ext}")
    public String downloadOneFile(@PathVariable(value = "name") String fileName,
            @PathVariable(value = "ext") String ext,
            @PathVariable(value = "projectid") int projectid,
            HttpServletRequest request,
            HttpServletResponse response) {

        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");

        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + "." + ext);
        try {

            File file = new File(fileName + "." + ext);
//            System.out.println(file.getAbsolutePath());  
            HttpClient client = new HttpClient();
            GetMethod get = new GetMethod("http://localhost:8081/SnowFlakes/uploaded/" + projectid + "/" + file);
            client.executeMethod(get);

            OutputStream os = response.getOutputStream();
            os.write(get.getResponseBody());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            pms.redirectErrorPage("Cannot find this file on the server");
        } catch (IOException e) {
            e.printStackTrace();
            pms.redirectErrorPage("Download failed");
        }
        Query q = em.createNamedQuery("UploadedFile.findByName").setParameter("name", fileName);
        List<UploadedFile> l = q.getResultList();
        UploadedFile f = (UploadedFile) l.get(0);
        f.setDownloadCounts(f.getDownloadCounts() + 1);
        try {
            fjc.edit(f);
        } catch (Exception ex) {
            Logger.getLogger(ProjectMainViewController.class.getName()).log(Level.SEVERE, null, ex);
            pms.redirectErrorPage("Unknown database problems");
        }
        //增加一次下载计数
        return "show";

    }

    @RequestMapping("/addOneTask")
    public ModelAndView addOneTask(@PathVariable(value = "projectid") int projectid, @RequestParam("from") String from,
            HttpServletRequest req) {

        String description = (String) req.getParameter("content");
        int target = Integer.parseInt(req.getParameter("target"));
        String begin_time = (String) req.getParameter("begin_time");
        String finish_time = (String) req.getParameter("finish_time");
        //取数据
        Project p = pjc.findProject(projectid);
        //找到这个Project的信息，为message铺垫
        User u = ujc.findUser(target);
          Message m ;
        if(u.getNotificationSettings().substring(0, 1).equals("1")){
           m = pms.addOneMessage(u, "You were assigned a task in  " + p.getName() + ": " + description);
        }//Send a message
        else 
        {
            m = null;
        }
        //添加一条Message
         if(u.getNotificationSettings().substring(1, 2).equals("1")){
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost("smtp.gmail.com");
        mailInfo.setMailServerPort("587");
        mailInfo.setValidate(true);
        mailInfo.setUserName("kjamvictory@gmail.com");
        mailInfo.setPassword("jk2010106164");//您的邮箱密码    
        mailInfo.setFromAddress("kjamvictory@gmail.com");
//        mailInfo.setToAddress("973104238@qq.com");
        mailInfo.setToAddress(u.getEmail());
        mailInfo.setSubject("SnowFlakes:Task Assigned:" + description + "in " + p.getName());
        String content = "\nHi: " + u.getName();
        content += "You were assgined a Task: " + description + " in " + p.getName();
        content += "\n Please visit our website and check it out";
        mailInfo.setContent(content);
        //这个类主要来发送邮件   
        SimpleMailSender sms = new SimpleMailSender();
        boolean flag = false;
      //  flag = sms.authenticate(mailInfo);
        flag=sms.sendTextMail(mailInfo);//发送文体格式    
         }
        
        Task t = new Task();
        t.setProjectId(p);
        t.setMessageId(m);
        t.setUserId(u);
        t.setDescription(description);
        t.setStatus(1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date begintime;
        Date finishtime;
        try {
            begintime = sdf.parse(begin_time);
            finishtime = sdf.parse(finish_time);
            t.setBeginTime(begintime);
            t.setFinishTime(finishtime);

        } catch (ParseException ex) {
            Logger.getLogger(ProjectMainViewController.class.getName()).log(Level.SEVERE, null, ex);
            pms.redirectErrorPage("Your date format is incorret");
        }
        tjc.create(t);
        //添加一个Task
        pms.addOneActionHistory(t.getUserId(), p, "Was assgined a task: " + t.getDescription());

        //添加一个ActionHIstory 
        if (from.equals("cal")) {
            ModelAndView mv = new ModelAndView();
//          Project p  = pjc.findProject(Integer.parseInt(projectid));
            Collection<User> memberCollection = p.getUserCollection();
            mv.addObject("memberCollection", memberCollection);
            mv.setViewName("calendar");
            return mv;
        } else {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("redirect:show");
            return mv;
        }
        //导航，从calendar来的回到calender否则回到mainview
    }

    @RequestMapping("/deleteOneTask/{taskid}")
    public String deleteOneTask(
            @PathVariable String projectid, @PathVariable String taskid) {

        Task t = tjc.findTask(Integer.parseInt(taskid));
        Project p = pjc.findProject(Integer.parseInt(projectid));

        pms.addOneActionHistory(t.getUserId(), p, "Task:(" + t.getDescription() + ")had been cancelled");
        try {
            tjc.destroy(Integer.parseInt(taskid));
        } catch (Exception e) {
            e.printStackTrace();
            pms.redirectErrorPage("Unknown database problems");
        }

        //增加一条活动记录
        return "redirect:/projectMainView/" + projectid + "/show";
    }

    @RequestMapping("/updateOneTask/{taskid}")
    public String updateOneTask(HttpServletRequest req,
            @PathVariable String projectid, @PathVariable String taskid) {
        String content = req.getParameter("content");
        String target = req.getParameter("target");

        String begin_time = req.getParameter("begin_time");
        String finish_time = req.getParameter("finish_time");

        Task t = tjc.findTask(Integer.parseInt(taskid));

        User u = ujc.findUser(Integer.parseInt(target));

        t.setDescription(content);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            t.setBeginTime(sdf.parse(begin_time));
        } catch (ParseException ex) {
            pms.redirectErrorPage("Your date format is incorrect");
            Logger.getLogger(ProjectMainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {

            t.setFinishTime(sdf.parse(finish_time));
        } catch (ParseException ex) {
            pms.redirectErrorPage("Your date format is incorrect");
            Logger.getLogger(ProjectMainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        t.setUserId(u);
        try {
            tjc.edit(t);
        } catch (Exception ex) {
            pms.redirectErrorPage("Unknown database problems");
            Logger.getLogger(ProjectMainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Project p = pjc.findProject(Integer.parseInt(projectid));

        pms.addOneActionHistory(t.getUserId(), p, "Task:(" + t.getDescription() + ")had been updated");

        //增加一条活动记录
        return "redirect:/projectMainView/" + projectid + "/show";
    }

    @RequestMapping("/finishOneTask/{taskid}")
    public String finishOneTask(HttpServletRequest req,
            @PathVariable String projectid, @PathVariable String taskid) {

        Task t = tjc.findTask(Integer.parseInt(taskid));
        t.setStatus(0);
        try {
            tjc.edit(t);
        } catch (Exception ex) {

            Logger.getLogger(ProjectMainViewController.class.getName()).log(Level.SEVERE, null, ex);
            pms.redirectErrorPage("Unknown database problems");
        }

        Project p = pjc.findProject(Integer.parseInt(projectid));
        ActionHistory ah = new ActionHistory();

        pms.addOneActionHistory(t.getUserId(), p, "Task:(" + t.getDescription() + ")had been finished");

        return "redirect:/projectMainView/" + projectid + "/show";
    }

    @RequestMapping("/redoOneTask/{taskid}")
    public String redoOneTask(HttpServletRequest req,
            @PathVariable String projectid, @PathVariable String taskid) {

        Task t = tjc.findTask(Integer.parseInt(taskid));
        t.setStatus(1);
        try {
            tjc.edit(t);
        } catch (Exception ex) {
            Logger.getLogger(ProjectMainViewController.class.getName()).log(Level.SEVERE, null, ex);
            pms.redirectErrorPage("Unknown database problems");

        }
        Project p = pjc.findProject(Integer.parseInt(projectid));
        pms.addOneActionHistory(t.getUserId(), p, "Task:(" + t.getDescription() + ")had been redone");
        return "redirect:/projectMainView/" + projectid + "/show";
    }

    //切换到日历试图
    @RequestMapping("/cal")
    public ModelAndView cal(@PathVariable String projectid) {

        ModelAndView mv = new ModelAndView();
        Project p = pjc.findProject(Integer.parseInt(projectid));
        Collection<User> memberCollection = p.getUserCollection();
        mv.addObject("memberCollection", memberCollection);
        mv.setViewName("calendar");
        return mv;

    }

    @RequestMapping("/ajaxGetProjectInfo")
    public ModelAndView ajaxGetCalendarInfo(
            @PathVariable String projectid, HttpServletResponse res) {
        PrintWriter out = null;
        try {
            out = res.getWriter();
        } catch (IOException ex) {
            Logger.getLogger(ProjectMainViewController.class.getName()).log(Level.SEVERE, null, ex);
            pms.redirectErrorPage("Unknown problems, try to use normal task assign function");
        }
        Project p = pjc.findProject(Integer.parseInt(projectid));
        Collection<Task> taskCollection = p.getTaskCollection();
        Iterator iter = taskCollection.iterator();
        List<EventJSON> list = new ArrayList<EventJSON>();

        while (iter.hasNext()) {
            Task t = (Task) (iter.next());
            EventJSON e = new EventJSON();
            e.setDescription(t.getDescription());
            e.setStarttime(t.getBeginTime());
            e.setFinishtime(t.getFinishTime());
            e.setAssignee(t.getUserId().getName());
            list.add(e);
        }
        JsonUtil ju = new JsonUtil();
        ju.put("events", list);
        String a = ju.toString();
        a = a.replace("\'", "\"");
        out.print(a);
        return null;
    }

    @RequestMapping("/ajaxGetOneTaskInfo/{taskid}")
    public ModelAndView ajaxGetOneTaskInfo(
            @PathVariable String taskid, HttpServletResponse res) {
        PrintWriter out = null;
        try {
            out = res.getWriter();
        } catch (IOException ex) {
            Logger.getLogger(ProjectMainViewController.class.getName()).log(Level.SEVERE, null, ex);
            pms.redirectErrorPage("Unknown problems, try to use normal task assign function");
        }

        Task t = tjc.findTask(Integer.parseInt(taskid));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String description = t.getDescription();
        String begintime = sdf.format(t.getBeginTime());
        String finishtime = sdf.format(t.getFinishTime());
        String target = t.getUserId().getId() + "";
        JsonUtil ju = new JsonUtil();
        ju.put("target", target);
        ju.put("description", description);
        ju.put("begindate", begintime);
        ju.put("finishdate", finishtime);
        String a = ju.toString();
        out.print(ju);
        return null;
    }

    @RequestMapping(value = "/newDocument", params = "project_id")
    public ModelAndView create_document(HttpServletRequest req) {
        Project temp = new Project();
        temp.setId(Integer.parseInt(req.getParameter("project_id")));
        ModelAndView mv = new ModelAndView("redirect:newDocument", "project", temp);

        return mv;
    }

}

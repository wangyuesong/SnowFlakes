/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tongji.collaborationteam.pagecontrollers;

import com.tongji.collaborationteam.dbcontrollers.InvitationJpaController;
import com.tongji.collaborationteam.dbcontrollers.MessageJpaController;
import com.tongji.collaborationteam.dbcontrollers.ProjectJpaController;
import com.tongji.collaborationteam.dbcontrollers.TaskJpaController;
import com.tongji.collaborationteam.dbcontrollers.UserJpaController;
import com.tongji.collaborationteam.dbcontrollers.exceptions.NonexistentEntityException;
import com.tongji.collaborationteam.dbentities.Invitation;
import com.tongji.collaborationteam.dbentities.Message;
import com.tongji.collaborationteam.dbentities.Project;
import com.tongji.collaborationteam.dbentities.Task;
import com.tongji.collaborationteam.dbentities.User;
import com.tongji.collaborationteam.entities.ModifyPW;
import com.tongji.collaborationteam.entities.MyInfo;
import com.tongji.collaborationteam.entities.MyTask;
import com.tongji.collaborationteam.entities.myInvitation;
import com.tongji.collaborationteam.services.MyInfoService;
import com.tongji.collaborationteam.util.JsonUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
@RequestMapping("/personalCenter")
@SessionAttributes("session_user")
public class PersonalCenterController {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("SpringMVCPU");
    EntityManager em = emf.createEntityManager();
    UserJpaController ujc = new UserJpaController(emf);
    InvitationJpaController ijc = new InvitationJpaController(emf);
    ProjectJpaController pjc = new ProjectJpaController(emf);
        MessageJpaController mjc = new MessageJpaController(emf);
        TaskJpaController tjc = new TaskJpaController(emf);
    @RequestMapping()
    public ModelAndView render(HttpServletRequest request, @ModelAttribute("session_user") User user) {

        ModelAndView mv = new ModelAndView();
        if (user.getId() == null) {
            mv.setViewName("redirect:register");
            return mv;
        }

        user = ujc.findUser(user.getId());
        //MyInfo myInfo = new MyInfo(user.getName(),user.getEmail(),user.getPassword());
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String bir = "";
        String headPic;
        if (user.getHeadpic() != null) {
            //headPic = request.getContextPath() + "\\headPic\\" + user.getHeadpic();
            headPic = request.getSession().getServletContext().getRealPath("headPic") + "\\" + user.getHeadpic();
            headPic = "/headPic/" + user.getHeadpic();
        } else {
            headPic = "/img/miao2.png";
        }
        if (user.getBirthday() != null) {
            bir = df.format(user.getBirthday());
        }
        String add1 = user.getAddress1() == null ? "" : user.getAddress1();
        String add2 = user.getAddress2() == null ? "" : user.getAddress2();
        String pho = user.getPhone() == null ? "" : user.getPhone();
        MyInfo myInfo = new MyInfo(user.getName(), user.getEmail(), "", bir, add1, add2, pho);

        String setting = user.getNotificationSettings();
        if (setting == null) {
            setting = "11";
        }
        int set[] = {1, 1};
        for (int i = 0; i < 2 && i < setting.length(); ++i) {
            if (setting.charAt(i) == '0') {
                set[i] = 0;
            }
        }

        myInfo.setNotification_settings(set);
        myInfo.setId(user.getId());
        mv.addObject("myInfo", myInfo);
        mv.addObject("headPic", headPic);
        mv.addObject("task_detail", new MyTask());

        mv.addObject("modipw", new ModifyPW());

        mv.setViewName("personalCenter");
        //mv = new ModelAndView("personalCenter","myInfo",myInfo);
        return mv;
    }

    @RequestMapping(params = "modifyPic")
    public ModelAndView modifyPic(@RequestParam(value = "file", required = false) MultipartFile file,
            HttpServletRequest request, @ModelAttribute("session_user") User user) {
        //@RequestParam(value="file", required=false) MultipartFile file,
        //MultipartFile file = null;
        if (file.getSize() == 0) {
            ModelAndView mv = new ModelAndView("redirect:personalCenter", "session_user", user);
            return mv;

        }

        user = ujc.findUser(user.getId());

        String path = request.getSession().getServletContext().getRealPath("/headPic");
        //path = request.getSession().getServletContext().getContextPath();

        //String path = request.getContextPath()+"/WherePic";
        //path = request.getRealPath(path)
        String tempfname = file.getOriginalFilename();
        tempfname = tempfname.substring(tempfname.length() - 4);
        String fileName = user.getId().toString() + tempfname;

        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }

        try {
            file.transferTo(targetFile);
            user.setHeadpic(fileName);
            ujc.edit(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ModelAndView mv = new ModelAndView("redirect:personalCenter", "session_user", user);
        return mv;
    }

    public void SaveFileFromInputStream(InputStream stream, String path, String filename) throws IOException {
        FileOutputStream fs = new FileOutputStream(path + "/" + filename);
        byte[] buffer = new byte[1024 * 1024];
        int bytesum = 0;
        int byteread = 0;
        while ((byteread = stream.read(buffer)) != -1) {
            bytesum += byteread;
            fs.write(buffer, 0, byteread);
            fs.flush();
        }
        fs.close();
        stream.close();
    }

    @RequestMapping(params = "modifyInfo")
    public ModelAndView modifyPInfo(@ModelAttribute("myInfo") MyInfo myInfo, HttpServletRequest request, @ModelAttribute("session_user") User user, HttpSession session) {

        user = ujc.findUser(user.getId());
        String name = myInfo.getName();
        String phone = myInfo.getPhone();
        String add1 = myInfo.getAddress1();
        String add2 = myInfo.getAddress2();
        String bir = myInfo.getBir();
        //String bir = myInfo.getBirthday();
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date birth = null;

        try {
            birth = bartDateFormat.parse(bir);
        } catch (ParseException ex) {
            Logger.getLogger(MyInfoService.class.getName()).log(Level.SEVERE, null, ex);
        }

        String path = request.getSession().getServletContext().getRealPath("upload");
        String fileName = "headpic_" + user.getId();

        user.setName(name);
        user.setAddress1(add1);
        user.setAddress2(add2);
        user.setPhone(phone);
        user.setBirthday(birth);

        //ModelAndView mv = new ModelAndView("redirect:personalCenter");
        try {
            ujc.edit(user);
            session.removeAttribute("session_user");
            session.setAttribute("session_user", user);
        } catch (Exception e) {

        }
        ModelAndView mv = new ModelAndView("redirect:personalCenter", "session_user", user);
        return mv;
    }

    @RequestMapping(params = "modi-pwd")
    public ModelAndView modifyPInfo(@ModelAttribute("modipw") ModifyPW modiPW, HttpServletRequest request, @ModelAttribute("session_user") User user, HttpSession session) {
        if (modiPW.getNew_password1().equals(modiPW.getNew_password2()) && modiPW.getOld_password().equals(user.getPassword())) {
            user.setPassword(modiPW.getNew_password1());
            user = ujc.findUser(user.getId());
            try {
                ujc.edit(user);
                session.removeAttribute("session_user");
                session.setAttribute("session_user", user);
            } catch (Exception e) {

            }
        }
        ModelAndView mv = new ModelAndView("redirect:personalCenter", "session_user", user);
        return mv;
    }

    @ModelAttribute("PendingTaskList")
    public List<MyTask> getPendingTaskList(@ModelAttribute("session_user") User user) {

        if (user.getId() == null) {

            return null;

        }

        user = ujc.findUser(user.getId());

        List<Task> tl = (List<Task>) user.getTaskCollection();
        List<Task> result = new ArrayList<>();
        for (int i = 0; i < tl.size(); ++i) {
            if (tl.get(i).getStatus() != 0) {
                result.add(tl.get(i));
            }
        }
        return getResultforTask(result);

    }

    @ModelAttribute("HistoryTaskList")
    public List<MyTask> getHistoryTaskList(@ModelAttribute("session_user") User user) {
        if (user.getId() == null) {

            return null;

        }

        user = ujc.findUser(user.getId());
        List<Task> tl = (List<Task>) user.getTaskCollection();
        List<Task> result = new ArrayList<>();
        for (int i = 0; i < tl.size(); ++i) {
            if (tl.get(i).getStatus() == 0) {
                result.add(tl.get(i));
            }
        }
        return getResultforTask(result);

    }

    public List<MyTask> getResultforTask(List<Task> tl) {
        ArrayList<MyTask> result = new ArrayList<>();


        for (int i = 0; i < tl.size(); ++i) {
            MyTask mt = new MyTask();
            mt.setId(tl.get(i).getId());
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            String temp = "";
            if (tl.get(i).getBeginTime() != null) {
                temp = df.format(tl.get(i).getBeginTime());
                mt.setAssignTime(temp);
            }
            if (tl.get(i).getFinishTime() != null) {
                temp = df.format(tl.get(i).getFinishTime());
                mt.setDueTime(temp);
            }
            mt.setTitle(tl.get(i).getId().toString());
            mt.setProjectName(tl.get(i).getProjectId().getName());
            result.add(mt);
        }
        return result;
    }

    @ModelAttribute("PendingMessageList")
    public List<Message> getPendingMessageList(@ModelAttribute("session_user") User user) {

        if (user.getId() == null) {

            return null;

        }

        user = ujc.findUser(user.getId());
        List<Message> ml = (List<Message>) user.getMessageCollection();
        ArrayList<Message> result = new ArrayList<>();
        for (int i = 0; i < ml.size(); ++i) {
            if (ml.get(i).getState() == true) {
                result.add(ml.get(i));
            }
        }
        return result;
    }

    @ModelAttribute("HistoryMessageList")
    public List<Message> getHistoryMessageList(@ModelAttribute("session_user") User user) {
        if (user.getId() == null) {

            return null;

        }

        user = ujc.findUser(user.getId());
        List<Message> ml = (List<Message>) user.getMessageCollection();
        ArrayList<Message> result = new ArrayList<>();
        for (int i = 0; i < ml.size(); ++i) {
            if (ml.get(i).getState() == false) {
                result.add(ml.get(i));
            }
        }
        return result;
    }

    @RequestMapping("/getTaskDetail/{taskid}")
    public String getTaskDetail(@PathVariable String taskid, HttpServletResponse res, @ModelAttribute("session_user") User user) throws IOException {

        PrintWriter out = res.getWriter();

        user = ujc.findUser(user.getId());
        Task t = tjc.findTask(Integer.parseInt(taskid));

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String description = t.getDescription();
        String begintime = sdf.format(t.getBeginTime());
        String finishtime = sdf.format(t.getFinishTime());
        String target = t.getUserId().getId() + "";
        String projectname = t.getProjectId().getName();
        JsonUtil ju = new JsonUtil();
        ju.put("projectname", projectname);
        ju.put("target", target);
        ju.put("description", description);
        ju.put("begindate", begintime);
        ju.put("finishdate", finishtime);
        out.print(ju);
        return null;
    }

    @RequestMapping("/getMessageDetail/{taskid}")
    public ModelAndView getMessageDetail(HttpServletRequest request, @PathVariable String taskid, HttpServletResponse res, HttpSession session, @ModelAttribute("session_user") User user) throws IOException {
        if (user.getId() == null) {
            return null;
        }

        user = ujc.findUser(user.getId());
        PrintWriter out = res.getWriter();


        Message m = mjc.findMessage(Integer.parseInt(taskid));
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String details = m.getDetails();
        String date = sdf.format(m.getTime());
        JsonUtil ju = new JsonUtil();
        ju.put("details", details);
        ju.put("date", date);
        out.print(ju);

        m.setState(Boolean.FALSE);
        try {
            mjc.edit(m);
            int tempid = user.getId();
            User newuser = ujc.findUser(tempid);
            user.setMessageCollection(newuser.getMessageCollection());

        } catch (Exception ex) {
            Logger.getLogger(PersonalCenterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @ModelAttribute("Invitation")
    public List<myInvitation> getInvitation(@ModelAttribute("session_user") User user) {
        if (user.getId() == null) {
            return null;
        }

        user = ujc.findUser(user.getId());
        List<Invitation> il = (List<Invitation>) user.getInvitationCollection();
        if (il.size() == 0) {
            return null;
        }
        List<myInvitation> im = new ArrayList<>();
        for (int i = 0; i < il.size(); ++i) {
            myInvitation mI = new myInvitation();
            mI.setId(il.get(i).getId());
            mI.setUser_id(user.getId());
            mI.setProject_id(il.get(i).getProjectId().getId());
            mI.setProject_name(il.get(i).getProjectId().getName());
            mI.setOwner_name(il.get(i).getProjectId().getOwner().getName());
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            if (il.get(i).getTime() != null) {
                mI.setTime(df.format(il.get(i).getTime()));
            }
            im.add(mI);
        }
        return im;
    }

    @RequestMapping("/acceptInvitation/{invitationId}")
    public ModelAndView acceptInvitation(@PathVariable String invitationId, @ModelAttribute("session_user") User user) throws IOException {
        if (user.getId() == null) {
            return null;
        }

        user = ujc.findUser(user.getId());
        Invitation i = ijc.findInvitation(Integer.parseInt(invitationId));
        Project tempp = i.getProjectId();
        tempp.getUserCollection().add(user);
        try {
            pjc.edit(tempp);
        } catch (NonexistentEntityException ex) {
            return null;
        } catch (Exception ex) {
            return null;
        }
        i.getProjectId().getUserCollection().add(user);
        try {

            ijc.destroy(Integer.parseInt(invitationId));
        } catch (NonexistentEntityException ex) {
            return null;
        }
        user.getProjectCollection().add(i.getProjectId());
        user.getInvitationCollection().remove(i);
        try {
            ujc.edit(user);
        } catch (NonexistentEntityException ex) {
            return null;
        } catch (Exception ex) {
            return null;
        }
        return null;
    }

    @RequestMapping("/refuseInvitation/{invitationId}")
    public ModelAndView refuseInvitation(@PathVariable String invitationId, @ModelAttribute("session_user") User user) throws IOException {
        if (user.getId() == null) {
            return null;
        }
        user = ujc.findUser(user.getId());
        Invitation i = ijc.findInvitation(Integer.parseInt(invitationId));
        try {
            ijc.destroy(Integer.parseInt(invitationId));
        } catch (NonexistentEntityException ex) {
            return null;
        }
        user.getInvitationCollection().remove(i);
        return null;
    }

    @RequestMapping("/settingChange")
    public ModelAndView settingChange(@ModelAttribute("session_user") User user, @RequestParam(value = "message", required = true) int message, @RequestParam(value = "email", required = true) int email) throws IOException {
        if (user.getId() == null) {
            return null;
        }

        user = ujc.findUser(user.getId());

        String setting = Integer.toString(message) + Integer.toString(email);
        user.setNotificationSettings(setting);
        try {
            ujc.edit(user);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PersonalCenterController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PersonalCenterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}

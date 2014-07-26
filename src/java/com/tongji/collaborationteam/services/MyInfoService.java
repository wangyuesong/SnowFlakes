package com.tongji.collaborationteam.services;

import com.tongji.collaborationteam.dbcontrollers.UserJpaController;
import com.tongji.collaborationteam.dbentities.Message;
import com.tongji.collaborationteam.dbentities.Project;
import com.tongji.collaborationteam.dbentities.User;
import com.tongji.collaborationteam.entities.MyInfo;
import com.tongji.collaborationteam.entities.SimpleProject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author coodoo
 */
@Controller
@SessionAttributes("session_user")
public class MyInfoService {

    EntityManagerFactory fm = Persistence.createEntityManagerFactory("SpringMVCPU");
    EntityManager em = fm.createEntityManager();
    UserJpaController handler = new UserJpaController(fm);

    @RequestMapping(value = "/index", method = RequestMethod.POST, params = "register-btn")
    public ModelAndView goToregister(@ModelAttribute("myinfo") MyInfo myinfo) {

        String password = myinfo.getPassword();
        String username = myinfo.getName();
        String email = myinfo.getEmail();

        User user = new User(0, password);
        user.setEmail(email);
        user.setName(username);
        handler.create(user);
        ModelAndView mv = new ModelAndView("personalCenter", "session_user", user);
        return mv;
    }

    @RequestMapping(value = "/index", params = "login-btn")
    public ModelAndView login(@ModelAttribute(value = "login_info") MyInfo myinfo, HttpServletRequest request) {

        String email = myinfo.getEmail();
        String password = myinfo.getPassword();
        Query query = em.createNamedQuery("User.findByEmail");
        query.setParameter("email", email);
        User user;
        try {
            user = (User) query.getSingleResult();
        } catch (Exception e) {
            ModelAndView mv = new ModelAndView("index");
            request.setAttribute("login_error", "true");
            return mv;
        }

        String dbpassword = user.getPassword();

        if (dbpassword.equals(password)) {
            //search for id and create a new MyInfo
            ModelAndView mv = new ModelAndView("redirect:projectManagement");
            mv.addObject("session_user", user);

            return mv;
        } else {
            ModelAndView mv = new ModelAndView("index");
            request.setAttribute("login_error", "true");
            return mv;
        }

    }

    @RequestMapping(value = "/index", params = "continue-register")
    public ModelAndView continue_register(@ModelAttribute("myinfo") MyInfo myinfo) {

        ModelAndView mv = new ModelAndView();
        mv.addObject("myinfo", myinfo);
        mv.setViewName("register");
        return mv;
    }

    @RequestMapping(value = "/index", params = "finish-register")
    public ModelAndView finish_register(@ModelAttribute("myinfo") MyInfo myinfo, HttpServletRequest request) {
        String password = myinfo.getPassword();
        String username = myinfo.getName();
        String email = myinfo.getEmail();
        String birthday = myinfo.getBirthday();

        String address1 = myinfo.getAddress1();
        String address2 = myinfo.getAddress2();
        String phone = myinfo.getPhone();

        SimpleDateFormat bartDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date birth = null;
        try {
            birth = bartDateFormat.parse(birthday);
        } catch (ParseException ex) {
            Logger.getLogger(MyInfoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        User user = new User(0, password);
        user.setEmail(email);
        user.setName(username);
        user.setBirthday(birth);
        user.setAddress1(address1);
        user.setAddress2(address2);
        user.setPhone(phone);
        handler.create(user);
        Query query = em.createNamedQuery("User.findByEmail");
        query.setParameter("email", email);
        User temp = (User) query.getSingleResult();
        user.setId(temp.getId());
        ModelAndView mv = new ModelAndView("redirect: personalCenter", "session_user", user);
        return mv;
    }

    @RequestMapping(value = "/register", params = "finish-register")
    public ModelAndView register(@ModelAttribute("myinfo") MyInfo myinfo, HttpServletRequest request) {
        String password = myinfo.getPassword();
        String username = myinfo.getName();
        String email = myinfo.getEmail();
        String birthday = myinfo.getBirthday();

        String address1 = myinfo.getAddress1();
        String address2 = myinfo.getAddress2();
        String phone = myinfo.getPhone();

        SimpleDateFormat bartDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date birth = null;
        try {
            birth = bartDateFormat.parse(birthday);
        } catch (ParseException ex) {
            Logger.getLogger(MyInfoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        User user = new User(0, password);
        user.setEmail(email);
        user.setName(username);
        user.setBirthday(birth);
        user.setAddress1(address1);
        user.setAddress2(address2);
        user.setPhone(phone);
        user.setHeadpic("default.png");
        handler.create(user);

        Query query = em.createNamedQuery("User.findByEmail");
        query.setParameter("email", email);
        User temp = (User) query.getSingleResult();
        user.setId(temp.getId());
        ModelAndView mv = new ModelAndView("redirect: personalCenter", "session_user", user);
        return mv;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author XARPUIA
 */
package com.tongji.collaborationteam.services;

import com.tongji.collaborationteam.dbcontrollers.InvitationJpaController;
import com.tongji.collaborationteam.dbcontrollers.MessageJpaController;
import com.tongji.collaborationteam.dbcontrollers.ProjectJpaController;
import com.tongji.collaborationteam.dbcontrollers.TaskJpaController;
import com.tongji.collaborationteam.dbcontrollers.UserJpaController;
import com.tongji.collaborationteam.dbentities.Invitation;
import com.tongji.collaborationteam.dbentities.Message;
import com.tongji.collaborationteam.dbentities.Project;
import com.tongji.collaborationteam.dbentities.Task;
import com.tongji.collaborationteam.dbentities.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class TeamService {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("SpringMVCPU");
    EntityManager em = emf.createEntityManager();
    UserJpaController ujc = new UserJpaController(emf);
    ProjectJpaController pjc = new ProjectJpaController(emf);
    TaskJpaController tjc = new TaskJpaController(emf);
    InvitationJpaController ijc = new InvitationJpaController(emf);

    //根据projectId查找Owner
    public User getProjectOwnerName(int projectId) {
        Query q = em.createNamedQuery("Project.findById");
        q.setParameter("id", projectId);
        ArrayList<Project> project = (ArrayList<Project>) q.getResultList();
        if (project.isEmpty()) {
            return new User();
        }
        return project.get(0).getOwner();
    }

    //得到所有该项目的成员
    public ArrayList<User> getAllProjectMembers(int projectId) {
//	Query query = em.createNamedQuery("Project.findById");
//	query.setParameter("id", projectId);
        Project p = pjc.findProject(projectId);
        Collection<User> allMembers = (p.getUserCollection());
        return new ArrayList(allMembers);

    }

    public String findUserNameById(int userId) {
        Query q = em.createNamedQuery("User.findById");
        q.setParameter("id", userId);
        return ((User) q.getResultList().get(0)).getName();
    }

    //搜索用户
    public User findUser(int userId) {
        Query q = em.createNamedQuery("User.findById");
        q.setParameter("id", userId);
        ArrayList<User> matchUsers = new ArrayList<>(q.getResultList());
        if (matchUsers.isEmpty()) {
            return null;
        } else {
            matchUsers = coverPassword(matchUsers);
            return matchUsers.get(0);
        }
    }

    //发送邀请
    public void sendInvitation(int userId, int projectId) {
//        MessageJpaController mJpa = new MessageJpaController(emf);
//        Message m = new Message();
//
//        m.setId(mJpa.getMessageCount());
//        m.setType(0);	//邀请的类型是？我这里处理成0了
//        m.setUserId(findUser(userId));
//        m.setState(false);
//        m.setTime(new Date());
//        m.setDetails("You were invited to join Project:" + pjc.findProject(projectId));
//	m.setDetails(details);
        User u = ujc.findUser(userId);
        Project p = pjc.findProject(projectId);
        Invitation i = new Invitation();
        i.setProjectId(p);
        i.setUserId(u);
        i.setTime(new Date());
        ijc.create(i);

//        mJpa.create(m);
    }

    //接受邀请后实际添加组员
    public boolean addGroupMember(int userId, int projectId) throws Exception {
        Query q = em.createNamedQuery("User.findById");
        q.setParameter("id", userId);
        ArrayList<User> users = new ArrayList<>(q.getResultList());

        q = em.createNamedQuery("Project.findById");
        q.setParameter("id", projectId);
        ArrayList<Project> projects = new ArrayList<>(q.getResultList());

        if (users.isEmpty() || projects.isEmpty()) {
            return false;
        } else {
            User u = users.get(0);
            Project p = projects.get(0);
            p.getUserCollection().add(u);

            ProjectJpaController pJpa = new ProjectJpaController(emf);
            pJpa.edit(p);

            return true;
        }
    }

    //接受邀请了的人，将其从组中删去
    public boolean deleteGroupMember(int userId, int projectId) throws Exception {
//	Query q = em.createNamedQuery("User.findById");
//	q.setParameter("id", userId);
//	ArrayList<User> users = new ArrayList<>(q.getResultList());

        User u = ujc.findUser(userId);
        Project p = pjc.findProject(projectId);
//	if (users.isEmpty() || projects.isEmpty()) {
//	    return false;
//	} else {

        p.getUserCollection().remove(u);
        pjc.edit(p);

        Collection<Task> t1 = u.getTaskCollection();
        Iterator iter = t1.iterator();
        Collection<Task> t2 = new ArrayList<Task>();
        while (iter.hasNext()) {
            Task a = (Task) iter.next();
            if (projectId == a.getProjectId().getId()) {
                t2.add(a);
            }
        }
        iter = t2.iterator();
        while (iter.hasNext()) {
            tjc.destroy(((Task) (iter.next())).getId());
        }
        //清除这个人的所有记录

//	    ProjectJpaController pJpa = new ProjectJpaController(emf);
//	    pJpa.edit(p);
        return true;
//	}
    }

    //有邀请但是还没有接受的用户，将邀请记录删除
//    public boolean deleteInvitation(int deleteMemberId, int projectId) {
//	//依靠userId和projectId无法确定邀请
//	//在message里面加一个projectId字段？
//    }
    //取得所有不在本组内的用户
    public ArrayList<User> getOtherUsers(int projectId, int userId) {
        ProjectJpaController pJpa = new ProjectJpaController(emf);
        Project p = pJpa.findProject(projectId);

//        Query q = em.createNamedQuery("User.findAll");
        Set userSet = new HashSet();
        User u = ujc.findUser(userId);
        Collection<Project> userProjects = u.getProjectCollection();

        Iterator iter = userProjects.iterator();
        while (iter.hasNext()) {
            Project eachProject = (Project) (iter.next());
            Collection<User> projectUsers = eachProject.getUserCollection();

            Iterator iter2 = projectUsers.iterator();
            while (iter2.hasNext()) {
                userSet.add((User) (iter2.next()));
            }

        }

        ArrayList<User> otherUsers = new ArrayList(userSet);
        iter = p.getUserCollection().iterator();
        while (iter.hasNext()) {
            Object user = iter.next();
            if (otherUsers.contains(user)) {
                otherUsers.remove(user);
            }
        }

        return otherUsers;
    }

    //处理掉密码
    private ArrayList<User> coverPassword(ArrayList<User> users) {
        for (User u : users) {
            u.setPassword("***");
        }
        return users;
    }

    //通过输入查找用户
    public List<User> searchUser(String nameOrEmail) {

        Query q = em.createNamedQuery("User.findByName");
        q.setParameter("name", nameOrEmail);
        List<User> nameUserList = q.getResultList();

        q = em.createNamedQuery("User.findByEmail");
        q.setParameter("email", nameOrEmail);
        List<User> emailUserList = q.getResultList();

        nameUserList.addAll(emailUserList);

        return nameUserList;
    }
}

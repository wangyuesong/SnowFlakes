/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tongji.collaborationteam.dbcontrollers;

import com.tongji.collaborationteam.dbcontrollers.exceptions.NonexistentEntityException;
import com.tongji.collaborationteam.dbentities.Message;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.tongji.collaborationteam.dbentities.User;
import com.tongji.collaborationteam.dbentities.Task;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author WYS
 */
public class MessageJpaController implements Serializable {

    public MessageJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Message message) {
        if (message.getTaskCollection() == null) {
            message.setTaskCollection(new ArrayList<Task>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User userId = message.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getId());
                message.setUserId(userId);
            }
            Collection<Task> attachedTaskCollection = new ArrayList<Task>();
            for (Task taskCollectionTaskToAttach : message.getTaskCollection()) {
                taskCollectionTaskToAttach = em.getReference(taskCollectionTaskToAttach.getClass(), taskCollectionTaskToAttach.getId());
                attachedTaskCollection.add(taskCollectionTaskToAttach);
            }
            message.setTaskCollection(attachedTaskCollection);
            em.persist(message);
            if (userId != null) {
                userId.getMessageCollection().add(message);
                userId = em.merge(userId);
            }
            for (Task taskCollectionTask : message.getTaskCollection()) {
                Message oldMessageIdOfTaskCollectionTask = taskCollectionTask.getMessageId();
                taskCollectionTask.setMessageId(message);
                taskCollectionTask = em.merge(taskCollectionTask);
                if (oldMessageIdOfTaskCollectionTask != null) {
                    oldMessageIdOfTaskCollectionTask.getTaskCollection().remove(taskCollectionTask);
                    oldMessageIdOfTaskCollectionTask = em.merge(oldMessageIdOfTaskCollectionTask);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Message message) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Message persistentMessage = em.find(Message.class, message.getId());
            User userIdOld = persistentMessage.getUserId();
            User userIdNew = message.getUserId();
            Collection<Task> taskCollectionOld = persistentMessage.getTaskCollection();
            Collection<Task> taskCollectionNew = message.getTaskCollection();
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getId());
                message.setUserId(userIdNew);
            }
            Collection<Task> attachedTaskCollectionNew = new ArrayList<Task>();
            for (Task taskCollectionNewTaskToAttach : taskCollectionNew) {
                taskCollectionNewTaskToAttach = em.getReference(taskCollectionNewTaskToAttach.getClass(), taskCollectionNewTaskToAttach.getId());
                attachedTaskCollectionNew.add(taskCollectionNewTaskToAttach);
            }
            taskCollectionNew = attachedTaskCollectionNew;
            message.setTaskCollection(taskCollectionNew);
            message = em.merge(message);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getMessageCollection().remove(message);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getMessageCollection().add(message);
                userIdNew = em.merge(userIdNew);
            }
            for (Task taskCollectionOldTask : taskCollectionOld) {
                if (!taskCollectionNew.contains(taskCollectionOldTask)) {
                    taskCollectionOldTask.setMessageId(null);
                    taskCollectionOldTask = em.merge(taskCollectionOldTask);
                }
            }
            for (Task taskCollectionNewTask : taskCollectionNew) {
                if (!taskCollectionOld.contains(taskCollectionNewTask)) {
                    Message oldMessageIdOfTaskCollectionNewTask = taskCollectionNewTask.getMessageId();
                    taskCollectionNewTask.setMessageId(message);
                    taskCollectionNewTask = em.merge(taskCollectionNewTask);
                    if (oldMessageIdOfTaskCollectionNewTask != null && !oldMessageIdOfTaskCollectionNewTask.equals(message)) {
                        oldMessageIdOfTaskCollectionNewTask.getTaskCollection().remove(taskCollectionNewTask);
                        oldMessageIdOfTaskCollectionNewTask = em.merge(oldMessageIdOfTaskCollectionNewTask);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = message.getId();
                if (findMessage(id) == null) {
                    throw new NonexistentEntityException("The message with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Message message;
            try {
                message = em.getReference(Message.class, id);
                message.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The message with id " + id + " no longer exists.", enfe);
            }
            User userId = message.getUserId();
            if (userId != null) {
                userId.getMessageCollection().remove(message);
                userId = em.merge(userId);
            }
            Collection<Task> taskCollection = message.getTaskCollection();
            for (Task taskCollectionTask : taskCollection) {
                taskCollectionTask.setMessageId(null);
                taskCollectionTask = em.merge(taskCollectionTask);
            }
            em.remove(message);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Message> findMessageEntities() {
        return findMessageEntities(true, -1, -1);
    }

    public List<Message> findMessageEntities(int maxResults, int firstResult) {
        return findMessageEntities(false, maxResults, firstResult);
    }

    private List<Message> findMessageEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Message.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Message findMessage(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Message.class, id);
        } finally {
            em.close();
        }
    }

    public int getMessageCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Message> rt = cq.from(Message.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

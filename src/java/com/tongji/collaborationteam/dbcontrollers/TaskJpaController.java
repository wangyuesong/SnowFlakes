/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tongji.collaborationteam.dbcontrollers;

import com.tongji.collaborationteam.dbcontrollers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.tongji.collaborationteam.dbentities.User;
import com.tongji.collaborationteam.dbentities.Project;
import com.tongji.collaborationteam.dbentities.Message;
import com.tongji.collaborationteam.dbentities.Task;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author WYS
 */
public class TaskJpaController implements Serializable {

    public TaskJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Task task) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User userId = task.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getId());
                task.setUserId(userId);
            }
            Project projectId = task.getProjectId();
            if (projectId != null) {
                projectId = em.getReference(projectId.getClass(), projectId.getId());
                task.setProjectId(projectId);
            }
            Message messageId = task.getMessageId();
            if (messageId != null) {
                messageId = em.getReference(messageId.getClass(), messageId.getId());
                task.setMessageId(messageId);
            }
            em.persist(task);
            if (userId != null) {
                userId.getTaskCollection().add(task);
                userId = em.merge(userId);
            }
            if (projectId != null) {
                projectId.getTaskCollection().add(task);
                projectId = em.merge(projectId);
            }
            if (messageId != null) {
                messageId.getTaskCollection().add(task);
                messageId = em.merge(messageId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Task task) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Task persistentTask = em.find(Task.class, task.getId());
            User userIdOld = persistentTask.getUserId();
            User userIdNew = task.getUserId();
            Project projectIdOld = persistentTask.getProjectId();
            Project projectIdNew = task.getProjectId();
            Message messageIdOld = persistentTask.getMessageId();
            Message messageIdNew = task.getMessageId();
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getId());
                task.setUserId(userIdNew);
            }
            if (projectIdNew != null) {
                projectIdNew = em.getReference(projectIdNew.getClass(), projectIdNew.getId());
                task.setProjectId(projectIdNew);
            }
            if (messageIdNew != null) {
                messageIdNew = em.getReference(messageIdNew.getClass(), messageIdNew.getId());
                task.setMessageId(messageIdNew);
            }
            task = em.merge(task);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getTaskCollection().remove(task);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getTaskCollection().add(task);
                userIdNew = em.merge(userIdNew);
            }
            if (projectIdOld != null && !projectIdOld.equals(projectIdNew)) {
                projectIdOld.getTaskCollection().remove(task);
                projectIdOld = em.merge(projectIdOld);
            }
            if (projectIdNew != null && !projectIdNew.equals(projectIdOld)) {
                projectIdNew.getTaskCollection().add(task);
                projectIdNew = em.merge(projectIdNew);
            }
            if (messageIdOld != null && !messageIdOld.equals(messageIdNew)) {
                messageIdOld.getTaskCollection().remove(task);
                messageIdOld = em.merge(messageIdOld);
            }
            if (messageIdNew != null && !messageIdNew.equals(messageIdOld)) {
                messageIdNew.getTaskCollection().add(task);
                messageIdNew = em.merge(messageIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = task.getId();
                if (findTask(id) == null) {
                    throw new NonexistentEntityException("The task with id " + id + " no longer exists.");
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
            Task task;
            try {
                task = em.getReference(Task.class, id);
                task.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The task with id " + id + " no longer exists.", enfe);
            }
            User userId = task.getUserId();
            if (userId != null) {
                userId.getTaskCollection().remove(task);
                userId = em.merge(userId);
            }
            Project projectId = task.getProjectId();
            if (projectId != null) {
                projectId.getTaskCollection().remove(task);
                projectId = em.merge(projectId);
            }
            Message messageId = task.getMessageId();
            if (messageId != null) {
                messageId.getTaskCollection().remove(task);
                messageId = em.merge(messageId);
            }
            em.remove(task);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Task> findTaskEntities() {
        return findTaskEntities(true, -1, -1);
    }

    public List<Task> findTaskEntities(int maxResults, int firstResult) {
        return findTaskEntities(false, maxResults, firstResult);
    }

    private List<Task> findTaskEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Task.class));
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

    public Task findTask(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Task.class, id);
        } finally {
            em.close();
        }
    }

    public int getTaskCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Task> rt = cq.from(Task.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

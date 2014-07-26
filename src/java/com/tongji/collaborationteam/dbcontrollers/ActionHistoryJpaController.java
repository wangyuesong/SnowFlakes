/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tongji.collaborationteam.dbcontrollers;

import com.tongji.collaborationteam.dbcontrollers.exceptions.NonexistentEntityException;
import com.tongji.collaborationteam.dbentities.ActionHistory;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.tongji.collaborationteam.dbentities.User;
import com.tongji.collaborationteam.dbentities.Project;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author WYS
 */
public class ActionHistoryJpaController implements Serializable {

    public ActionHistoryJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ActionHistory actionHistory) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User userId = actionHistory.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getId());
                actionHistory.setUserId(userId);
            }
            Project projectId = actionHistory.getProjectId();
            if (projectId != null) {
                projectId = em.getReference(projectId.getClass(), projectId.getId());
                actionHistory.setProjectId(projectId);
            }
            em.persist(actionHistory);
            if (userId != null) {
                userId.getActionHistoryCollection().add(actionHistory);
                userId = em.merge(userId);
            }
            if (projectId != null) {
                projectId.getActionHistoryCollection().add(actionHistory);
                projectId = em.merge(projectId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ActionHistory actionHistory) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ActionHistory persistentActionHistory = em.find(ActionHistory.class, actionHistory.getId());
            User userIdOld = persistentActionHistory.getUserId();
            User userIdNew = actionHistory.getUserId();
            Project projectIdOld = persistentActionHistory.getProjectId();
            Project projectIdNew = actionHistory.getProjectId();
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getId());
                actionHistory.setUserId(userIdNew);
            }
            if (projectIdNew != null) {
                projectIdNew = em.getReference(projectIdNew.getClass(), projectIdNew.getId());
                actionHistory.setProjectId(projectIdNew);
            }
            actionHistory = em.merge(actionHistory);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getActionHistoryCollection().remove(actionHistory);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getActionHistoryCollection().add(actionHistory);
                userIdNew = em.merge(userIdNew);
            }
            if (projectIdOld != null && !projectIdOld.equals(projectIdNew)) {
                projectIdOld.getActionHistoryCollection().remove(actionHistory);
                projectIdOld = em.merge(projectIdOld);
            }
            if (projectIdNew != null && !projectIdNew.equals(projectIdOld)) {
                projectIdNew.getActionHistoryCollection().add(actionHistory);
                projectIdNew = em.merge(projectIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = actionHistory.getId();
                if (findActionHistory(id) == null) {
                    throw new NonexistentEntityException("The actionHistory with id " + id + " no longer exists.");
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
            ActionHistory actionHistory;
            try {
                actionHistory = em.getReference(ActionHistory.class, id);
                actionHistory.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The actionHistory with id " + id + " no longer exists.", enfe);
            }
            User userId = actionHistory.getUserId();
            if (userId != null) {
                userId.getActionHistoryCollection().remove(actionHistory);
                userId = em.merge(userId);
            }
            Project projectId = actionHistory.getProjectId();
            if (projectId != null) {
                projectId.getActionHistoryCollection().remove(actionHistory);
                projectId = em.merge(projectId);
            }
            em.remove(actionHistory);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ActionHistory> findActionHistoryEntities() {
        return findActionHistoryEntities(true, -1, -1);
    }

    public List<ActionHistory> findActionHistoryEntities(int maxResults, int firstResult) {
        return findActionHistoryEntities(false, maxResults, firstResult);
    }

    private List<ActionHistory> findActionHistoryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ActionHistory.class));
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

    public ActionHistory findActionHistory(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ActionHistory.class, id);
        } finally {
            em.close();
        }
    }

    public int getActionHistoryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ActionHistory> rt = cq.from(ActionHistory.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tongji.collaborationteam.dbcontrollers;

import com.tongji.collaborationteam.dbcontrollers.exceptions.NonexistentEntityException;
import com.tongji.collaborationteam.dbentities.Copy;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.tongji.collaborationteam.dbentities.User;
import com.tongji.collaborationteam.dbentities.Project;
import com.tongji.collaborationteam.dbentities.InitialDocument;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author WYS
 */
public class CopyJpaController implements Serializable {

    public CopyJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Copy copy) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User userId = copy.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getId());
                copy.setUserId(userId);
            }
            Project projectId = copy.getProjectId();
            if (projectId != null) {
                projectId = em.getReference(projectId.getClass(), projectId.getId());
                copy.setProjectId(projectId);
            }
            InitialDocument belongsTo = copy.getBelongsTo();
            if (belongsTo != null) {
                belongsTo = em.getReference(belongsTo.getClass(), belongsTo.getId());
                copy.setBelongsTo(belongsTo);
            }
            em.persist(copy);
            if (userId != null) {
                userId.getCopyCollection().add(copy);
                userId = em.merge(userId);
            }
            if (projectId != null) {
                projectId.getCopyCollection().add(copy);
                projectId = em.merge(projectId);
            }
            if (belongsTo != null) {
                belongsTo.getCopyCollection().add(copy);
                belongsTo = em.merge(belongsTo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Copy copy) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Copy persistentCopy = em.find(Copy.class, copy.getId());
            User userIdOld = persistentCopy.getUserId();
            User userIdNew = copy.getUserId();
            Project projectIdOld = persistentCopy.getProjectId();
            Project projectIdNew = copy.getProjectId();
            InitialDocument belongsToOld = persistentCopy.getBelongsTo();
            InitialDocument belongsToNew = copy.getBelongsTo();
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getId());
                copy.setUserId(userIdNew);
            }
            if (projectIdNew != null) {
                projectIdNew = em.getReference(projectIdNew.getClass(), projectIdNew.getId());
                copy.setProjectId(projectIdNew);
            }
            if (belongsToNew != null) {
                belongsToNew = em.getReference(belongsToNew.getClass(), belongsToNew.getId());
                copy.setBelongsTo(belongsToNew);
            }
            copy = em.merge(copy);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getCopyCollection().remove(copy);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getCopyCollection().add(copy);
                userIdNew = em.merge(userIdNew);
            }
            if (projectIdOld != null && !projectIdOld.equals(projectIdNew)) {
                projectIdOld.getCopyCollection().remove(copy);
                projectIdOld = em.merge(projectIdOld);
            }
            if (projectIdNew != null && !projectIdNew.equals(projectIdOld)) {
                projectIdNew.getCopyCollection().add(copy);
                projectIdNew = em.merge(projectIdNew);
            }
            if (belongsToOld != null && !belongsToOld.equals(belongsToNew)) {
                belongsToOld.getCopyCollection().remove(copy);
                belongsToOld = em.merge(belongsToOld);
            }
            if (belongsToNew != null && !belongsToNew.equals(belongsToOld)) {
                belongsToNew.getCopyCollection().add(copy);
                belongsToNew = em.merge(belongsToNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = copy.getId();
                if (findCopy(id) == null) {
                    throw new NonexistentEntityException("The copy with id " + id + " no longer exists.");
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
            Copy copy;
            try {
                copy = em.getReference(Copy.class, id);
                copy.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The copy with id " + id + " no longer exists.", enfe);
            }
            User userId = copy.getUserId();
            if (userId != null) {
                userId.getCopyCollection().remove(copy);
                userId = em.merge(userId);
            }
            Project projectId = copy.getProjectId();
            if (projectId != null) {
                projectId.getCopyCollection().remove(copy);
                projectId = em.merge(projectId);
            }
            InitialDocument belongsTo = copy.getBelongsTo();
            if (belongsTo != null) {
                belongsTo.getCopyCollection().remove(copy);
                belongsTo = em.merge(belongsTo);
            }
            em.remove(copy);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Copy> findCopyEntities() {
        return findCopyEntities(true, -1, -1);
    }

    public List<Copy> findCopyEntities(int maxResults, int firstResult) {
        return findCopyEntities(false, maxResults, firstResult);
    }

    private List<Copy> findCopyEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Copy.class));
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

    public Copy findCopy(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Copy.class, id);
        } finally {
            em.close();
        }
    }

    public int getCopyCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Copy> rt = cq.from(Copy.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

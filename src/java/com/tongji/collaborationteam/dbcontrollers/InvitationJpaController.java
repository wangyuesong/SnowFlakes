/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tongji.collaborationteam.dbcontrollers;

import com.tongji.collaborationteam.dbcontrollers.exceptions.NonexistentEntityException;
import com.tongji.collaborationteam.dbentities.Invitation;
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
public class InvitationJpaController implements Serializable {

    public InvitationJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Invitation invitation) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User userId = invitation.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getId());
                invitation.setUserId(userId);
            }
            Project projectId = invitation.getProjectId();
            if (projectId != null) {
                projectId = em.getReference(projectId.getClass(), projectId.getId());
                invitation.setProjectId(projectId);
            }
            em.persist(invitation);
            if (userId != null) {
                userId.getInvitationCollection().add(invitation);
                userId = em.merge(userId);
            }
            if (projectId != null) {
                projectId.getInvitationCollection().add(invitation);
                projectId = em.merge(projectId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Invitation invitation) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Invitation persistentInvitation = em.find(Invitation.class, invitation.getId());
            User userIdOld = persistentInvitation.getUserId();
            User userIdNew = invitation.getUserId();
            Project projectIdOld = persistentInvitation.getProjectId();
            Project projectIdNew = invitation.getProjectId();
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getId());
                invitation.setUserId(userIdNew);
            }
            if (projectIdNew != null) {
                projectIdNew = em.getReference(projectIdNew.getClass(), projectIdNew.getId());
                invitation.setProjectId(projectIdNew);
            }
            invitation = em.merge(invitation);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getInvitationCollection().remove(invitation);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getInvitationCollection().add(invitation);
                userIdNew = em.merge(userIdNew);
            }
            if (projectIdOld != null && !projectIdOld.equals(projectIdNew)) {
                projectIdOld.getInvitationCollection().remove(invitation);
                projectIdOld = em.merge(projectIdOld);
            }
            if (projectIdNew != null && !projectIdNew.equals(projectIdOld)) {
                projectIdNew.getInvitationCollection().add(invitation);
                projectIdNew = em.merge(projectIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = invitation.getId();
                if (findInvitation(id) == null) {
                    throw new NonexistentEntityException("The invitation with id " + id + " no longer exists.");
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
            Invitation invitation;
            try {
                invitation = em.getReference(Invitation.class, id);
                invitation.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The invitation with id " + id + " no longer exists.", enfe);
            }
            User userId = invitation.getUserId();
            if (userId != null) {
                userId.getInvitationCollection().remove(invitation);
                userId = em.merge(userId);
            }
            Project projectId = invitation.getProjectId();
            if (projectId != null) {
                projectId.getInvitationCollection().remove(invitation);
                projectId = em.merge(projectId);
            }
            em.remove(invitation);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Invitation> findInvitationEntities() {
        return findInvitationEntities(true, -1, -1);
    }

    public List<Invitation> findInvitationEntities(int maxResults, int firstResult) {
        return findInvitationEntities(false, maxResults, firstResult);
    }

    private List<Invitation> findInvitationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Invitation.class));
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

    public Invitation findInvitation(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Invitation.class, id);
        } finally {
            em.close();
        }
    }

    public int getInvitationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Invitation> rt = cq.from(Invitation.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

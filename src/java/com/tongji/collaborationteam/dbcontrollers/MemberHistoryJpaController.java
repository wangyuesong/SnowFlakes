/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tongji.collaborationteam.dbcontrollers;

import com.tongji.collaborationteam.dbcontrollers.exceptions.NonexistentEntityException;
import com.tongji.collaborationteam.dbentities.MemberHistory;
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
public class MemberHistoryJpaController implements Serializable {

    public MemberHistoryJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MemberHistory memberHistory) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User memberId = memberHistory.getMemberId();
            if (memberId != null) {
                memberId = em.getReference(memberId.getClass(), memberId.getId());
                memberHistory.setMemberId(memberId);
            }
            Project projectId = memberHistory.getProjectId();
            if (projectId != null) {
                projectId = em.getReference(projectId.getClass(), projectId.getId());
                memberHistory.setProjectId(projectId);
            }
            em.persist(memberHistory);
            if (memberId != null) {
                memberId.getMemberHistoryCollection().add(memberHistory);
                memberId = em.merge(memberId);
            }
            if (projectId != null) {
                projectId.getMemberHistoryCollection().add(memberHistory);
                projectId = em.merge(projectId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MemberHistory memberHistory) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MemberHistory persistentMemberHistory = em.find(MemberHistory.class, memberHistory.getId());
            User memberIdOld = persistentMemberHistory.getMemberId();
            User memberIdNew = memberHistory.getMemberId();
            Project projectIdOld = persistentMemberHistory.getProjectId();
            Project projectIdNew = memberHistory.getProjectId();
            if (memberIdNew != null) {
                memberIdNew = em.getReference(memberIdNew.getClass(), memberIdNew.getId());
                memberHistory.setMemberId(memberIdNew);
            }
            if (projectIdNew != null) {
                projectIdNew = em.getReference(projectIdNew.getClass(), projectIdNew.getId());
                memberHistory.setProjectId(projectIdNew);
            }
            memberHistory = em.merge(memberHistory);
            if (memberIdOld != null && !memberIdOld.equals(memberIdNew)) {
                memberIdOld.getMemberHistoryCollection().remove(memberHistory);
                memberIdOld = em.merge(memberIdOld);
            }
            if (memberIdNew != null && !memberIdNew.equals(memberIdOld)) {
                memberIdNew.getMemberHistoryCollection().add(memberHistory);
                memberIdNew = em.merge(memberIdNew);
            }
            if (projectIdOld != null && !projectIdOld.equals(projectIdNew)) {
                projectIdOld.getMemberHistoryCollection().remove(memberHistory);
                projectIdOld = em.merge(projectIdOld);
            }
            if (projectIdNew != null && !projectIdNew.equals(projectIdOld)) {
                projectIdNew.getMemberHistoryCollection().add(memberHistory);
                projectIdNew = em.merge(projectIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = memberHistory.getId();
                if (findMemberHistory(id) == null) {
                    throw new NonexistentEntityException("The memberHistory with id " + id + " no longer exists.");
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
            MemberHistory memberHistory;
            try {
                memberHistory = em.getReference(MemberHistory.class, id);
                memberHistory.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The memberHistory with id " + id + " no longer exists.", enfe);
            }
            User memberId = memberHistory.getMemberId();
            if (memberId != null) {
                memberId.getMemberHistoryCollection().remove(memberHistory);
                memberId = em.merge(memberId);
            }
            Project projectId = memberHistory.getProjectId();
            if (projectId != null) {
                projectId.getMemberHistoryCollection().remove(memberHistory);
                projectId = em.merge(projectId);
            }
            em.remove(memberHistory);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MemberHistory> findMemberHistoryEntities() {
        return findMemberHistoryEntities(true, -1, -1);
    }

    public List<MemberHistory> findMemberHistoryEntities(int maxResults, int firstResult) {
        return findMemberHistoryEntities(false, maxResults, firstResult);
    }

    private List<MemberHistory> findMemberHistoryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MemberHistory.class));
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

    public MemberHistory findMemberHistory(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MemberHistory.class, id);
        } finally {
            em.close();
        }
    }

    public int getMemberHistoryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MemberHistory> rt = cq.from(MemberHistory.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

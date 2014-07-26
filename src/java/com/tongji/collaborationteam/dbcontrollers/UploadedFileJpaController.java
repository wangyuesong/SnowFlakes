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
import com.tongji.collaborationteam.dbentities.UploadedFile;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author WYS
 */
public class UploadedFileJpaController implements Serializable {

    public UploadedFileJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UploadedFile uploadedFile) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User userId = uploadedFile.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getId());
                uploadedFile.setUserId(userId);
            }
            Project projectId = uploadedFile.getProjectId();
            if (projectId != null) {
                projectId = em.getReference(projectId.getClass(), projectId.getId());
                uploadedFile.setProjectId(projectId);
            }
            em.persist(uploadedFile);
            if (userId != null) {
                userId.getUploadedFileCollection().add(uploadedFile);
                userId = em.merge(userId);
            }
            if (projectId != null) {
                projectId.getUploadedFileCollection().add(uploadedFile);
                projectId = em.merge(projectId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UploadedFile uploadedFile) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UploadedFile persistentUploadedFile = em.find(UploadedFile.class, uploadedFile.getId());
            User userIdOld = persistentUploadedFile.getUserId();
            User userIdNew = uploadedFile.getUserId();
            Project projectIdOld = persistentUploadedFile.getProjectId();
            Project projectIdNew = uploadedFile.getProjectId();
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getId());
                uploadedFile.setUserId(userIdNew);
            }
            if (projectIdNew != null) {
                projectIdNew = em.getReference(projectIdNew.getClass(), projectIdNew.getId());
                uploadedFile.setProjectId(projectIdNew);
            }
            uploadedFile = em.merge(uploadedFile);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getUploadedFileCollection().remove(uploadedFile);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getUploadedFileCollection().add(uploadedFile);
                userIdNew = em.merge(userIdNew);
            }
            if (projectIdOld != null && !projectIdOld.equals(projectIdNew)) {
                projectIdOld.getUploadedFileCollection().remove(uploadedFile);
                projectIdOld = em.merge(projectIdOld);
            }
            if (projectIdNew != null && !projectIdNew.equals(projectIdOld)) {
                projectIdNew.getUploadedFileCollection().add(uploadedFile);
                projectIdNew = em.merge(projectIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = uploadedFile.getId();
                if (findUploadedFile(id) == null) {
                    throw new NonexistentEntityException("The uploadedFile with id " + id + " no longer exists.");
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
            UploadedFile uploadedFile;
            try {
                uploadedFile = em.getReference(UploadedFile.class, id);
                uploadedFile.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The uploadedFile with id " + id + " no longer exists.", enfe);
            }
            User userId = uploadedFile.getUserId();
            if (userId != null) {
                userId.getUploadedFileCollection().remove(uploadedFile);
                userId = em.merge(userId);
            }
            Project projectId = uploadedFile.getProjectId();
            if (projectId != null) {
                projectId.getUploadedFileCollection().remove(uploadedFile);
                projectId = em.merge(projectId);
            }
            em.remove(uploadedFile);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UploadedFile> findUploadedFileEntities() {
        return findUploadedFileEntities(true, -1, -1);
    }

    public List<UploadedFile> findUploadedFileEntities(int maxResults, int firstResult) {
        return findUploadedFileEntities(false, maxResults, firstResult);
    }

    private List<UploadedFile> findUploadedFileEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UploadedFile.class));
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

    public UploadedFile findUploadedFile(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UploadedFile.class, id);
        } finally {
            em.close();
        }
    }

    public int getUploadedFileCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UploadedFile> rt = cq.from(UploadedFile.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

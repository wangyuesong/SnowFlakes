/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tongji.collaborationteam.dbcontrollers;

import com.tongji.collaborationteam.dbcontrollers.exceptions.IllegalOrphanException;
import com.tongji.collaborationteam.dbcontrollers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.tongji.collaborationteam.dbentities.User;
import com.tongji.collaborationteam.dbentities.Project;
import com.tongji.collaborationteam.dbentities.Copy;
import com.tongji.collaborationteam.dbentities.InitialDocument;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author WYS
 */
public class InitialDocumentJpaController implements Serializable {

    public InitialDocumentJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InitialDocument initialDocument) {
        if (initialDocument.getCopyCollection() == null) {
            initialDocument.setCopyCollection(new ArrayList<Copy>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User userId = initialDocument.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getId());
                initialDocument.setUserId(userId);
            }
            Project projectId = initialDocument.getProjectId();
            if (projectId != null) {
                projectId = em.getReference(projectId.getClass(), projectId.getId());
                initialDocument.setProjectId(projectId);
            }
            Collection<Copy> attachedCopyCollection = new ArrayList<Copy>();
            for (Copy copyCollectionCopyToAttach : initialDocument.getCopyCollection()) {
                copyCollectionCopyToAttach = em.getReference(copyCollectionCopyToAttach.getClass(), copyCollectionCopyToAttach.getId());
                attachedCopyCollection.add(copyCollectionCopyToAttach);
            }
            initialDocument.setCopyCollection(attachedCopyCollection);
            em.persist(initialDocument);
            if (userId != null) {
                userId.getInitialDocumentCollection().add(initialDocument);
                userId = em.merge(userId);
            }
            if (projectId != null) {
                projectId.getInitialDocumentCollection().add(initialDocument);
                projectId = em.merge(projectId);
            }
            for (Copy copyCollectionCopy : initialDocument.getCopyCollection()) {
                InitialDocument oldBelongsToOfCopyCollectionCopy = copyCollectionCopy.getBelongsTo();
                copyCollectionCopy.setBelongsTo(initialDocument);
                copyCollectionCopy = em.merge(copyCollectionCopy);
                if (oldBelongsToOfCopyCollectionCopy != null) {
                    oldBelongsToOfCopyCollectionCopy.getCopyCollection().remove(copyCollectionCopy);
                    oldBelongsToOfCopyCollectionCopy = em.merge(oldBelongsToOfCopyCollectionCopy);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(InitialDocument initialDocument) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InitialDocument persistentInitialDocument = em.find(InitialDocument.class, initialDocument.getId());
            User userIdOld = persistentInitialDocument.getUserId();
            User userIdNew = initialDocument.getUserId();
            Project projectIdOld = persistentInitialDocument.getProjectId();
            Project projectIdNew = initialDocument.getProjectId();
            Collection<Copy> copyCollectionOld = persistentInitialDocument.getCopyCollection();
            Collection<Copy> copyCollectionNew = initialDocument.getCopyCollection();
            List<String> illegalOrphanMessages = null;
            for (Copy copyCollectionOldCopy : copyCollectionOld) {
                if (!copyCollectionNew.contains(copyCollectionOldCopy)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Copy " + copyCollectionOldCopy + " since its belongsTo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getId());
                initialDocument.setUserId(userIdNew);
            }
            if (projectIdNew != null) {
                projectIdNew = em.getReference(projectIdNew.getClass(), projectIdNew.getId());
                initialDocument.setProjectId(projectIdNew);
            }
            Collection<Copy> attachedCopyCollectionNew = new ArrayList<Copy>();
            for (Copy copyCollectionNewCopyToAttach : copyCollectionNew) {
                copyCollectionNewCopyToAttach = em.getReference(copyCollectionNewCopyToAttach.getClass(), copyCollectionNewCopyToAttach.getId());
                attachedCopyCollectionNew.add(copyCollectionNewCopyToAttach);
            }
            copyCollectionNew = attachedCopyCollectionNew;
            initialDocument.setCopyCollection(copyCollectionNew);
            initialDocument = em.merge(initialDocument);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getInitialDocumentCollection().remove(initialDocument);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getInitialDocumentCollection().add(initialDocument);
                userIdNew = em.merge(userIdNew);
            }
            if (projectIdOld != null && !projectIdOld.equals(projectIdNew)) {
                projectIdOld.getInitialDocumentCollection().remove(initialDocument);
                projectIdOld = em.merge(projectIdOld);
            }
            if (projectIdNew != null && !projectIdNew.equals(projectIdOld)) {
                projectIdNew.getInitialDocumentCollection().add(initialDocument);
                projectIdNew = em.merge(projectIdNew);
            }
            for (Copy copyCollectionNewCopy : copyCollectionNew) {
                if (!copyCollectionOld.contains(copyCollectionNewCopy)) {
                    InitialDocument oldBelongsToOfCopyCollectionNewCopy = copyCollectionNewCopy.getBelongsTo();
                    copyCollectionNewCopy.setBelongsTo(initialDocument);
                    copyCollectionNewCopy = em.merge(copyCollectionNewCopy);
                    if (oldBelongsToOfCopyCollectionNewCopy != null && !oldBelongsToOfCopyCollectionNewCopy.equals(initialDocument)) {
                        oldBelongsToOfCopyCollectionNewCopy.getCopyCollection().remove(copyCollectionNewCopy);
                        oldBelongsToOfCopyCollectionNewCopy = em.merge(oldBelongsToOfCopyCollectionNewCopy);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = initialDocument.getId();
                if (findInitialDocument(id) == null) {
                    throw new NonexistentEntityException("The initialDocument with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InitialDocument initialDocument;
            try {
                initialDocument = em.getReference(InitialDocument.class, id);
                initialDocument.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The initialDocument with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Copy> copyCollectionOrphanCheck = initialDocument.getCopyCollection();
            for (Copy copyCollectionOrphanCheckCopy : copyCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This InitialDocument (" + initialDocument + ") cannot be destroyed since the Copy " + copyCollectionOrphanCheckCopy + " in its copyCollection field has a non-nullable belongsTo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            User userId = initialDocument.getUserId();
            if (userId != null) {
                userId.getInitialDocumentCollection().remove(initialDocument);
                userId = em.merge(userId);
            }
            Project projectId = initialDocument.getProjectId();
            if (projectId != null) {
                projectId.getInitialDocumentCollection().remove(initialDocument);
                projectId = em.merge(projectId);
            }
            em.remove(initialDocument);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<InitialDocument> findInitialDocumentEntities() {
        return findInitialDocumentEntities(true, -1, -1);
    }

    public List<InitialDocument> findInitialDocumentEntities(int maxResults, int firstResult) {
        return findInitialDocumentEntities(false, maxResults, firstResult);
    }

    private List<InitialDocument> findInitialDocumentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InitialDocument.class));
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

    public InitialDocument findInitialDocument(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InitialDocument.class, id);
        } finally {
            em.close();
        }
    }

    public int getInitialDocumentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InitialDocument> rt = cq.from(InitialDocument.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

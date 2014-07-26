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
import java.util.ArrayList;
import java.util.Collection;
import com.tongji.collaborationteam.dbentities.UploadedFile;
import com.tongji.collaborationteam.dbentities.ActionHistory;
import com.tongji.collaborationteam.dbentities.Task;
import com.tongji.collaborationteam.dbentities.Invitation;
import com.tongji.collaborationteam.dbentities.InitialDocument;
import com.tongji.collaborationteam.dbentities.Copy;
import com.tongji.collaborationteam.dbentities.MemberHistory;
import com.tongji.collaborationteam.dbentities.Project;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author WYS
 */
public class ProjectJpaController implements Serializable {

    public ProjectJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Project project) {
        if (project.getUserCollection() == null) {
            project.setUserCollection(new ArrayList<User>());
        }
        if (project.getUploadedFileCollection() == null) {
            project.setUploadedFileCollection(new ArrayList<UploadedFile>());
        }
        if (project.getActionHistoryCollection() == null) {
            project.setActionHistoryCollection(new ArrayList<ActionHistory>());
        }
        if (project.getTaskCollection() == null) {
            project.setTaskCollection(new ArrayList<Task>());
        }
        if (project.getInvitationCollection() == null) {
            project.setInvitationCollection(new ArrayList<Invitation>());
        }
        if (project.getInitialDocumentCollection() == null) {
            project.setInitialDocumentCollection(new ArrayList<InitialDocument>());
        }
        if (project.getCopyCollection() == null) {
            project.setCopyCollection(new ArrayList<Copy>());
        }
        if (project.getMemberHistoryCollection() == null) {
            project.setMemberHistoryCollection(new ArrayList<MemberHistory>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User owner = project.getOwner();
            if (owner != null) {
                owner = em.getReference(owner.getClass(), owner.getId());
                project.setOwner(owner);
            }
            Collection<User> attachedUserCollection = new ArrayList<User>();
            for (User userCollectionUserToAttach : project.getUserCollection()) {
                userCollectionUserToAttach = em.getReference(userCollectionUserToAttach.getClass(), userCollectionUserToAttach.getId());
                attachedUserCollection.add(userCollectionUserToAttach);
            }
            project.setUserCollection(attachedUserCollection);
            Collection<UploadedFile> attachedUploadedFileCollection = new ArrayList<UploadedFile>();
            for (UploadedFile uploadedFileCollectionUploadedFileToAttach : project.getUploadedFileCollection()) {
                uploadedFileCollectionUploadedFileToAttach = em.getReference(uploadedFileCollectionUploadedFileToAttach.getClass(), uploadedFileCollectionUploadedFileToAttach.getId());
                attachedUploadedFileCollection.add(uploadedFileCollectionUploadedFileToAttach);
            }
            project.setUploadedFileCollection(attachedUploadedFileCollection);
            Collection<ActionHistory> attachedActionHistoryCollection = new ArrayList<ActionHistory>();
            for (ActionHistory actionHistoryCollectionActionHistoryToAttach : project.getActionHistoryCollection()) {
                actionHistoryCollectionActionHistoryToAttach = em.getReference(actionHistoryCollectionActionHistoryToAttach.getClass(), actionHistoryCollectionActionHistoryToAttach.getId());
                attachedActionHistoryCollection.add(actionHistoryCollectionActionHistoryToAttach);
            }
            project.setActionHistoryCollection(attachedActionHistoryCollection);
            Collection<Task> attachedTaskCollection = new ArrayList<Task>();
            for (Task taskCollectionTaskToAttach : project.getTaskCollection()) {
                taskCollectionTaskToAttach = em.getReference(taskCollectionTaskToAttach.getClass(), taskCollectionTaskToAttach.getId());
                attachedTaskCollection.add(taskCollectionTaskToAttach);
            }
            project.setTaskCollection(attachedTaskCollection);
            Collection<Invitation> attachedInvitationCollection = new ArrayList<Invitation>();
            for (Invitation invitationCollectionInvitationToAttach : project.getInvitationCollection()) {
                invitationCollectionInvitationToAttach = em.getReference(invitationCollectionInvitationToAttach.getClass(), invitationCollectionInvitationToAttach.getId());
                attachedInvitationCollection.add(invitationCollectionInvitationToAttach);
            }
            project.setInvitationCollection(attachedInvitationCollection);
            Collection<InitialDocument> attachedInitialDocumentCollection = new ArrayList<InitialDocument>();
            for (InitialDocument initialDocumentCollectionInitialDocumentToAttach : project.getInitialDocumentCollection()) {
                initialDocumentCollectionInitialDocumentToAttach = em.getReference(initialDocumentCollectionInitialDocumentToAttach.getClass(), initialDocumentCollectionInitialDocumentToAttach.getId());
                attachedInitialDocumentCollection.add(initialDocumentCollectionInitialDocumentToAttach);
            }
            project.setInitialDocumentCollection(attachedInitialDocumentCollection);
            Collection<Copy> attachedCopyCollection = new ArrayList<Copy>();
            for (Copy copyCollectionCopyToAttach : project.getCopyCollection()) {
                copyCollectionCopyToAttach = em.getReference(copyCollectionCopyToAttach.getClass(), copyCollectionCopyToAttach.getId());
                attachedCopyCollection.add(copyCollectionCopyToAttach);
            }
            project.setCopyCollection(attachedCopyCollection);
            Collection<MemberHistory> attachedMemberHistoryCollection = new ArrayList<MemberHistory>();
            for (MemberHistory memberHistoryCollectionMemberHistoryToAttach : project.getMemberHistoryCollection()) {
                memberHistoryCollectionMemberHistoryToAttach = em.getReference(memberHistoryCollectionMemberHistoryToAttach.getClass(), memberHistoryCollectionMemberHistoryToAttach.getId());
                attachedMemberHistoryCollection.add(memberHistoryCollectionMemberHistoryToAttach);
            }
            project.setMemberHistoryCollection(attachedMemberHistoryCollection);
            em.persist(project);
            if (owner != null) {
                owner.getProjectCollection().add(project);
                owner = em.merge(owner);
            }
            for (User userCollectionUser : project.getUserCollection()) {
                userCollectionUser.getProjectCollection().add(project);
                userCollectionUser = em.merge(userCollectionUser);
            }
            for (UploadedFile uploadedFileCollectionUploadedFile : project.getUploadedFileCollection()) {
                Project oldProjectIdOfUploadedFileCollectionUploadedFile = uploadedFileCollectionUploadedFile.getProjectId();
                uploadedFileCollectionUploadedFile.setProjectId(project);
                uploadedFileCollectionUploadedFile = em.merge(uploadedFileCollectionUploadedFile);
                if (oldProjectIdOfUploadedFileCollectionUploadedFile != null) {
                    oldProjectIdOfUploadedFileCollectionUploadedFile.getUploadedFileCollection().remove(uploadedFileCollectionUploadedFile);
                    oldProjectIdOfUploadedFileCollectionUploadedFile = em.merge(oldProjectIdOfUploadedFileCollectionUploadedFile);
                }
            }
            for (ActionHistory actionHistoryCollectionActionHistory : project.getActionHistoryCollection()) {
                Project oldProjectIdOfActionHistoryCollectionActionHistory = actionHistoryCollectionActionHistory.getProjectId();
                actionHistoryCollectionActionHistory.setProjectId(project);
                actionHistoryCollectionActionHistory = em.merge(actionHistoryCollectionActionHistory);
                if (oldProjectIdOfActionHistoryCollectionActionHistory != null) {
                    oldProjectIdOfActionHistoryCollectionActionHistory.getActionHistoryCollection().remove(actionHistoryCollectionActionHistory);
                    oldProjectIdOfActionHistoryCollectionActionHistory = em.merge(oldProjectIdOfActionHistoryCollectionActionHistory);
                }
            }
            for (Task taskCollectionTask : project.getTaskCollection()) {
                Project oldProjectIdOfTaskCollectionTask = taskCollectionTask.getProjectId();
                taskCollectionTask.setProjectId(project);
                taskCollectionTask = em.merge(taskCollectionTask);
                if (oldProjectIdOfTaskCollectionTask != null) {
                    oldProjectIdOfTaskCollectionTask.getTaskCollection().remove(taskCollectionTask);
                    oldProjectIdOfTaskCollectionTask = em.merge(oldProjectIdOfTaskCollectionTask);
                }
            }
            for (Invitation invitationCollectionInvitation : project.getInvitationCollection()) {
                Project oldProjectIdOfInvitationCollectionInvitation = invitationCollectionInvitation.getProjectId();
                invitationCollectionInvitation.setProjectId(project);
                invitationCollectionInvitation = em.merge(invitationCollectionInvitation);
                if (oldProjectIdOfInvitationCollectionInvitation != null) {
                    oldProjectIdOfInvitationCollectionInvitation.getInvitationCollection().remove(invitationCollectionInvitation);
                    oldProjectIdOfInvitationCollectionInvitation = em.merge(oldProjectIdOfInvitationCollectionInvitation);
                }
            }
            for (InitialDocument initialDocumentCollectionInitialDocument : project.getInitialDocumentCollection()) {
                Project oldProjectIdOfInitialDocumentCollectionInitialDocument = initialDocumentCollectionInitialDocument.getProjectId();
                initialDocumentCollectionInitialDocument.setProjectId(project);
                initialDocumentCollectionInitialDocument = em.merge(initialDocumentCollectionInitialDocument);
                if (oldProjectIdOfInitialDocumentCollectionInitialDocument != null) {
                    oldProjectIdOfInitialDocumentCollectionInitialDocument.getInitialDocumentCollection().remove(initialDocumentCollectionInitialDocument);
                    oldProjectIdOfInitialDocumentCollectionInitialDocument = em.merge(oldProjectIdOfInitialDocumentCollectionInitialDocument);
                }
            }
            for (Copy copyCollectionCopy : project.getCopyCollection()) {
                Project oldProjectIdOfCopyCollectionCopy = copyCollectionCopy.getProjectId();
                copyCollectionCopy.setProjectId(project);
                copyCollectionCopy = em.merge(copyCollectionCopy);
                if (oldProjectIdOfCopyCollectionCopy != null) {
                    oldProjectIdOfCopyCollectionCopy.getCopyCollection().remove(copyCollectionCopy);
                    oldProjectIdOfCopyCollectionCopy = em.merge(oldProjectIdOfCopyCollectionCopy);
                }
            }
            for (MemberHistory memberHistoryCollectionMemberHistory : project.getMemberHistoryCollection()) {
                Project oldProjectIdOfMemberHistoryCollectionMemberHistory = memberHistoryCollectionMemberHistory.getProjectId();
                memberHistoryCollectionMemberHistory.setProjectId(project);
                memberHistoryCollectionMemberHistory = em.merge(memberHistoryCollectionMemberHistory);
                if (oldProjectIdOfMemberHistoryCollectionMemberHistory != null) {
                    oldProjectIdOfMemberHistoryCollectionMemberHistory.getMemberHistoryCollection().remove(memberHistoryCollectionMemberHistory);
                    oldProjectIdOfMemberHistoryCollectionMemberHistory = em.merge(oldProjectIdOfMemberHistoryCollectionMemberHistory);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Project project) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Project persistentProject = em.find(Project.class, project.getId());
            User ownerOld = persistentProject.getOwner();
            User ownerNew = project.getOwner();
            Collection<User> userCollectionOld = persistentProject.getUserCollection();
            Collection<User> userCollectionNew = project.getUserCollection();
            Collection<UploadedFile> uploadedFileCollectionOld = persistentProject.getUploadedFileCollection();
            Collection<UploadedFile> uploadedFileCollectionNew = project.getUploadedFileCollection();
            Collection<ActionHistory> actionHistoryCollectionOld = persistentProject.getActionHistoryCollection();
            Collection<ActionHistory> actionHistoryCollectionNew = project.getActionHistoryCollection();
            Collection<Task> taskCollectionOld = persistentProject.getTaskCollection();
            Collection<Task> taskCollectionNew = project.getTaskCollection();
            Collection<Invitation> invitationCollectionOld = persistentProject.getInvitationCollection();
            Collection<Invitation> invitationCollectionNew = project.getInvitationCollection();
            Collection<InitialDocument> initialDocumentCollectionOld = persistentProject.getInitialDocumentCollection();
            Collection<InitialDocument> initialDocumentCollectionNew = project.getInitialDocumentCollection();
            Collection<Copy> copyCollectionOld = persistentProject.getCopyCollection();
            Collection<Copy> copyCollectionNew = project.getCopyCollection();
            Collection<MemberHistory> memberHistoryCollectionOld = persistentProject.getMemberHistoryCollection();
            Collection<MemberHistory> memberHistoryCollectionNew = project.getMemberHistoryCollection();
            List<String> illegalOrphanMessages = null;
            for (UploadedFile uploadedFileCollectionOldUploadedFile : uploadedFileCollectionOld) {
                if (!uploadedFileCollectionNew.contains(uploadedFileCollectionOldUploadedFile)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UploadedFile " + uploadedFileCollectionOldUploadedFile + " since its projectId field is not nullable.");
                }
            }
            for (Task taskCollectionOldTask : taskCollectionOld) {
                if (!taskCollectionNew.contains(taskCollectionOldTask)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Task " + taskCollectionOldTask + " since its projectId field is not nullable.");
                }
            }
            for (InitialDocument initialDocumentCollectionOldInitialDocument : initialDocumentCollectionOld) {
                if (!initialDocumentCollectionNew.contains(initialDocumentCollectionOldInitialDocument)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InitialDocument " + initialDocumentCollectionOldInitialDocument + " since its projectId field is not nullable.");
                }
            }
            for (Copy copyCollectionOldCopy : copyCollectionOld) {
                if (!copyCollectionNew.contains(copyCollectionOldCopy)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Copy " + copyCollectionOldCopy + " since its projectId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (ownerNew != null) {
                ownerNew = em.getReference(ownerNew.getClass(), ownerNew.getId());
                project.setOwner(ownerNew);
            }
            Collection<User> attachedUserCollectionNew = new ArrayList<User>();
            for (User userCollectionNewUserToAttach : userCollectionNew) {
                userCollectionNewUserToAttach = em.getReference(userCollectionNewUserToAttach.getClass(), userCollectionNewUserToAttach.getId());
                attachedUserCollectionNew.add(userCollectionNewUserToAttach);
            }
            userCollectionNew = attachedUserCollectionNew;
            project.setUserCollection(userCollectionNew);
            Collection<UploadedFile> attachedUploadedFileCollectionNew = new ArrayList<UploadedFile>();
            for (UploadedFile uploadedFileCollectionNewUploadedFileToAttach : uploadedFileCollectionNew) {
                uploadedFileCollectionNewUploadedFileToAttach = em.getReference(uploadedFileCollectionNewUploadedFileToAttach.getClass(), uploadedFileCollectionNewUploadedFileToAttach.getId());
                attachedUploadedFileCollectionNew.add(uploadedFileCollectionNewUploadedFileToAttach);
            }
            uploadedFileCollectionNew = attachedUploadedFileCollectionNew;
            project.setUploadedFileCollection(uploadedFileCollectionNew);
            Collection<ActionHistory> attachedActionHistoryCollectionNew = new ArrayList<ActionHistory>();
            for (ActionHistory actionHistoryCollectionNewActionHistoryToAttach : actionHistoryCollectionNew) {
                actionHistoryCollectionNewActionHistoryToAttach = em.getReference(actionHistoryCollectionNewActionHistoryToAttach.getClass(), actionHistoryCollectionNewActionHistoryToAttach.getId());
                attachedActionHistoryCollectionNew.add(actionHistoryCollectionNewActionHistoryToAttach);
            }
            actionHistoryCollectionNew = attachedActionHistoryCollectionNew;
            project.setActionHistoryCollection(actionHistoryCollectionNew);
            Collection<Task> attachedTaskCollectionNew = new ArrayList<Task>();
            for (Task taskCollectionNewTaskToAttach : taskCollectionNew) {
                taskCollectionNewTaskToAttach = em.getReference(taskCollectionNewTaskToAttach.getClass(), taskCollectionNewTaskToAttach.getId());
                attachedTaskCollectionNew.add(taskCollectionNewTaskToAttach);
            }
            taskCollectionNew = attachedTaskCollectionNew;
            project.setTaskCollection(taskCollectionNew);
            Collection<Invitation> attachedInvitationCollectionNew = new ArrayList<Invitation>();
            for (Invitation invitationCollectionNewInvitationToAttach : invitationCollectionNew) {
                invitationCollectionNewInvitationToAttach = em.getReference(invitationCollectionNewInvitationToAttach.getClass(), invitationCollectionNewInvitationToAttach.getId());
                attachedInvitationCollectionNew.add(invitationCollectionNewInvitationToAttach);
            }
            invitationCollectionNew = attachedInvitationCollectionNew;
            project.setInvitationCollection(invitationCollectionNew);
            Collection<InitialDocument> attachedInitialDocumentCollectionNew = new ArrayList<InitialDocument>();
            for (InitialDocument initialDocumentCollectionNewInitialDocumentToAttach : initialDocumentCollectionNew) {
                initialDocumentCollectionNewInitialDocumentToAttach = em.getReference(initialDocumentCollectionNewInitialDocumentToAttach.getClass(), initialDocumentCollectionNewInitialDocumentToAttach.getId());
                attachedInitialDocumentCollectionNew.add(initialDocumentCollectionNewInitialDocumentToAttach);
            }
            initialDocumentCollectionNew = attachedInitialDocumentCollectionNew;
            project.setInitialDocumentCollection(initialDocumentCollectionNew);
            Collection<Copy> attachedCopyCollectionNew = new ArrayList<Copy>();
            for (Copy copyCollectionNewCopyToAttach : copyCollectionNew) {
                copyCollectionNewCopyToAttach = em.getReference(copyCollectionNewCopyToAttach.getClass(), copyCollectionNewCopyToAttach.getId());
                attachedCopyCollectionNew.add(copyCollectionNewCopyToAttach);
            }
            copyCollectionNew = attachedCopyCollectionNew;
            project.setCopyCollection(copyCollectionNew);
            Collection<MemberHistory> attachedMemberHistoryCollectionNew = new ArrayList<MemberHistory>();
            for (MemberHistory memberHistoryCollectionNewMemberHistoryToAttach : memberHistoryCollectionNew) {
                memberHistoryCollectionNewMemberHistoryToAttach = em.getReference(memberHistoryCollectionNewMemberHistoryToAttach.getClass(), memberHistoryCollectionNewMemberHistoryToAttach.getId());
                attachedMemberHistoryCollectionNew.add(memberHistoryCollectionNewMemberHistoryToAttach);
            }
            memberHistoryCollectionNew = attachedMemberHistoryCollectionNew;
            project.setMemberHistoryCollection(memberHistoryCollectionNew);
            project = em.merge(project);
            if (ownerOld != null && !ownerOld.equals(ownerNew)) {
                ownerOld.getProjectCollection().remove(project);
                ownerOld = em.merge(ownerOld);
            }
            if (ownerNew != null && !ownerNew.equals(ownerOld)) {
                ownerNew.getProjectCollection().add(project);
                ownerNew = em.merge(ownerNew);
            }
            for (User userCollectionOldUser : userCollectionOld) {
                if (!userCollectionNew.contains(userCollectionOldUser)) {
                    userCollectionOldUser.getProjectCollection().remove(project);
                    userCollectionOldUser = em.merge(userCollectionOldUser);
                }
            }
            for (User userCollectionNewUser : userCollectionNew) {
                if (!userCollectionOld.contains(userCollectionNewUser)) {
                    userCollectionNewUser.getProjectCollection().add(project);
                    userCollectionNewUser = em.merge(userCollectionNewUser);
                }
            }
            for (UploadedFile uploadedFileCollectionNewUploadedFile : uploadedFileCollectionNew) {
                if (!uploadedFileCollectionOld.contains(uploadedFileCollectionNewUploadedFile)) {
                    Project oldProjectIdOfUploadedFileCollectionNewUploadedFile = uploadedFileCollectionNewUploadedFile.getProjectId();
                    uploadedFileCollectionNewUploadedFile.setProjectId(project);
                    uploadedFileCollectionNewUploadedFile = em.merge(uploadedFileCollectionNewUploadedFile);
                    if (oldProjectIdOfUploadedFileCollectionNewUploadedFile != null && !oldProjectIdOfUploadedFileCollectionNewUploadedFile.equals(project)) {
                        oldProjectIdOfUploadedFileCollectionNewUploadedFile.getUploadedFileCollection().remove(uploadedFileCollectionNewUploadedFile);
                        oldProjectIdOfUploadedFileCollectionNewUploadedFile = em.merge(oldProjectIdOfUploadedFileCollectionNewUploadedFile);
                    }
                }
            }
            for (ActionHistory actionHistoryCollectionOldActionHistory : actionHistoryCollectionOld) {
                if (!actionHistoryCollectionNew.contains(actionHistoryCollectionOldActionHistory)) {
                    actionHistoryCollectionOldActionHistory.setProjectId(null);
                    actionHistoryCollectionOldActionHistory = em.merge(actionHistoryCollectionOldActionHistory);
                }
            }
            for (ActionHistory actionHistoryCollectionNewActionHistory : actionHistoryCollectionNew) {
                if (!actionHistoryCollectionOld.contains(actionHistoryCollectionNewActionHistory)) {
                    Project oldProjectIdOfActionHistoryCollectionNewActionHistory = actionHistoryCollectionNewActionHistory.getProjectId();
                    actionHistoryCollectionNewActionHistory.setProjectId(project);
                    actionHistoryCollectionNewActionHistory = em.merge(actionHistoryCollectionNewActionHistory);
                    if (oldProjectIdOfActionHistoryCollectionNewActionHistory != null && !oldProjectIdOfActionHistoryCollectionNewActionHistory.equals(project)) {
                        oldProjectIdOfActionHistoryCollectionNewActionHistory.getActionHistoryCollection().remove(actionHistoryCollectionNewActionHistory);
                        oldProjectIdOfActionHistoryCollectionNewActionHistory = em.merge(oldProjectIdOfActionHistoryCollectionNewActionHistory);
                    }
                }
            }
            for (Task taskCollectionNewTask : taskCollectionNew) {
                if (!taskCollectionOld.contains(taskCollectionNewTask)) {
                    Project oldProjectIdOfTaskCollectionNewTask = taskCollectionNewTask.getProjectId();
                    taskCollectionNewTask.setProjectId(project);
                    taskCollectionNewTask = em.merge(taskCollectionNewTask);
                    if (oldProjectIdOfTaskCollectionNewTask != null && !oldProjectIdOfTaskCollectionNewTask.equals(project)) {
                        oldProjectIdOfTaskCollectionNewTask.getTaskCollection().remove(taskCollectionNewTask);
                        oldProjectIdOfTaskCollectionNewTask = em.merge(oldProjectIdOfTaskCollectionNewTask);
                    }
                }
            }
            for (Invitation invitationCollectionOldInvitation : invitationCollectionOld) {
                if (!invitationCollectionNew.contains(invitationCollectionOldInvitation)) {
                    invitationCollectionOldInvitation.setProjectId(null);
                    invitationCollectionOldInvitation = em.merge(invitationCollectionOldInvitation);
                }
            }
            for (Invitation invitationCollectionNewInvitation : invitationCollectionNew) {
                if (!invitationCollectionOld.contains(invitationCollectionNewInvitation)) {
                    Project oldProjectIdOfInvitationCollectionNewInvitation = invitationCollectionNewInvitation.getProjectId();
                    invitationCollectionNewInvitation.setProjectId(project);
                    invitationCollectionNewInvitation = em.merge(invitationCollectionNewInvitation);
                    if (oldProjectIdOfInvitationCollectionNewInvitation != null && !oldProjectIdOfInvitationCollectionNewInvitation.equals(project)) {
                        oldProjectIdOfInvitationCollectionNewInvitation.getInvitationCollection().remove(invitationCollectionNewInvitation);
                        oldProjectIdOfInvitationCollectionNewInvitation = em.merge(oldProjectIdOfInvitationCollectionNewInvitation);
                    }
                }
            }
            for (InitialDocument initialDocumentCollectionNewInitialDocument : initialDocumentCollectionNew) {
                if (!initialDocumentCollectionOld.contains(initialDocumentCollectionNewInitialDocument)) {
                    Project oldProjectIdOfInitialDocumentCollectionNewInitialDocument = initialDocumentCollectionNewInitialDocument.getProjectId();
                    initialDocumentCollectionNewInitialDocument.setProjectId(project);
                    initialDocumentCollectionNewInitialDocument = em.merge(initialDocumentCollectionNewInitialDocument);
                    if (oldProjectIdOfInitialDocumentCollectionNewInitialDocument != null && !oldProjectIdOfInitialDocumentCollectionNewInitialDocument.equals(project)) {
                        oldProjectIdOfInitialDocumentCollectionNewInitialDocument.getInitialDocumentCollection().remove(initialDocumentCollectionNewInitialDocument);
                        oldProjectIdOfInitialDocumentCollectionNewInitialDocument = em.merge(oldProjectIdOfInitialDocumentCollectionNewInitialDocument);
                    }
                }
            }
            for (Copy copyCollectionNewCopy : copyCollectionNew) {
                if (!copyCollectionOld.contains(copyCollectionNewCopy)) {
                    Project oldProjectIdOfCopyCollectionNewCopy = copyCollectionNewCopy.getProjectId();
                    copyCollectionNewCopy.setProjectId(project);
                    copyCollectionNewCopy = em.merge(copyCollectionNewCopy);
                    if (oldProjectIdOfCopyCollectionNewCopy != null && !oldProjectIdOfCopyCollectionNewCopy.equals(project)) {
                        oldProjectIdOfCopyCollectionNewCopy.getCopyCollection().remove(copyCollectionNewCopy);
                        oldProjectIdOfCopyCollectionNewCopy = em.merge(oldProjectIdOfCopyCollectionNewCopy);
                    }
                }
            }
            for (MemberHistory memberHistoryCollectionOldMemberHistory : memberHistoryCollectionOld) {
                if (!memberHistoryCollectionNew.contains(memberHistoryCollectionOldMemberHistory)) {
                    memberHistoryCollectionOldMemberHistory.setProjectId(null);
                    memberHistoryCollectionOldMemberHistory = em.merge(memberHistoryCollectionOldMemberHistory);
                }
            }
            for (MemberHistory memberHistoryCollectionNewMemberHistory : memberHistoryCollectionNew) {
                if (!memberHistoryCollectionOld.contains(memberHistoryCollectionNewMemberHistory)) {
                    Project oldProjectIdOfMemberHistoryCollectionNewMemberHistory = memberHistoryCollectionNewMemberHistory.getProjectId();
                    memberHistoryCollectionNewMemberHistory.setProjectId(project);
                    memberHistoryCollectionNewMemberHistory = em.merge(memberHistoryCollectionNewMemberHistory);
                    if (oldProjectIdOfMemberHistoryCollectionNewMemberHistory != null && !oldProjectIdOfMemberHistoryCollectionNewMemberHistory.equals(project)) {
                        oldProjectIdOfMemberHistoryCollectionNewMemberHistory.getMemberHistoryCollection().remove(memberHistoryCollectionNewMemberHistory);
                        oldProjectIdOfMemberHistoryCollectionNewMemberHistory = em.merge(oldProjectIdOfMemberHistoryCollectionNewMemberHistory);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = project.getId();
                if (findProject(id) == null) {
                    throw new NonexistentEntityException("The project with id " + id + " no longer exists.");
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
            Project project;
            try {
                project = em.getReference(Project.class, id);
                project.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The project with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<UploadedFile> uploadedFileCollectionOrphanCheck = project.getUploadedFileCollection();
            for (UploadedFile uploadedFileCollectionOrphanCheckUploadedFile : uploadedFileCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Project (" + project + ") cannot be destroyed since the UploadedFile " + uploadedFileCollectionOrphanCheckUploadedFile + " in its uploadedFileCollection field has a non-nullable projectId field.");
            }
            Collection<Task> taskCollectionOrphanCheck = project.getTaskCollection();
            for (Task taskCollectionOrphanCheckTask : taskCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Project (" + project + ") cannot be destroyed since the Task " + taskCollectionOrphanCheckTask + " in its taskCollection field has a non-nullable projectId field.");
            }
            Collection<InitialDocument> initialDocumentCollectionOrphanCheck = project.getInitialDocumentCollection();
            for (InitialDocument initialDocumentCollectionOrphanCheckInitialDocument : initialDocumentCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Project (" + project + ") cannot be destroyed since the InitialDocument " + initialDocumentCollectionOrphanCheckInitialDocument + " in its initialDocumentCollection field has a non-nullable projectId field.");
            }
            Collection<Copy> copyCollectionOrphanCheck = project.getCopyCollection();
            for (Copy copyCollectionOrphanCheckCopy : copyCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Project (" + project + ") cannot be destroyed since the Copy " + copyCollectionOrphanCheckCopy + " in its copyCollection field has a non-nullable projectId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            User owner = project.getOwner();
            if (owner != null) {
                owner.getProjectCollection().remove(project);
                owner = em.merge(owner);
            }
            Collection<User> userCollection = project.getUserCollection();
            for (User userCollectionUser : userCollection) {
                userCollectionUser.getProjectCollection().remove(project);
                userCollectionUser = em.merge(userCollectionUser);
            }
            Collection<ActionHistory> actionHistoryCollection = project.getActionHistoryCollection();
            for (ActionHistory actionHistoryCollectionActionHistory : actionHistoryCollection) {
                actionHistoryCollectionActionHistory.setProjectId(null);
                actionHistoryCollectionActionHistory = em.merge(actionHistoryCollectionActionHistory);
            }
            Collection<Invitation> invitationCollection = project.getInvitationCollection();
            for (Invitation invitationCollectionInvitation : invitationCollection) {
                invitationCollectionInvitation.setProjectId(null);
                invitationCollectionInvitation = em.merge(invitationCollectionInvitation);
            }
            Collection<MemberHistory> memberHistoryCollection = project.getMemberHistoryCollection();
            for (MemberHistory memberHistoryCollectionMemberHistory : memberHistoryCollection) {
                memberHistoryCollectionMemberHistory.setProjectId(null);
                memberHistoryCollectionMemberHistory = em.merge(memberHistoryCollectionMemberHistory);
            }
            em.remove(project);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Project> findProjectEntities() {
        return findProjectEntities(true, -1, -1);
    }

    public List<Project> findProjectEntities(int maxResults, int firstResult) {
        return findProjectEntities(false, maxResults, firstResult);
    }

    private List<Project> findProjectEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Project.class));
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

    public Project findProject(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Project.class, id);
        } finally {
            em.close();
        }
    }

    public int getProjectCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Project> rt = cq.from(Project.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

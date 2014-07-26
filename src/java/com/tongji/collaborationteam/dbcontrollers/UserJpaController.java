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
import com.tongji.collaborationteam.dbentities.Project;
import java.util.ArrayList;
import java.util.Collection;
import com.tongji.collaborationteam.dbentities.UploadedFile;
import com.tongji.collaborationteam.dbentities.Message;
import com.tongji.collaborationteam.dbentities.ActionHistory;
import com.tongji.collaborationteam.dbentities.Task;
import com.tongji.collaborationteam.dbentities.Invitation;
import com.tongji.collaborationteam.dbentities.InitialDocument;
import com.tongji.collaborationteam.dbentities.Copy;
import com.tongji.collaborationteam.dbentities.MemberHistory;
import com.tongji.collaborationteam.dbentities.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author WYS
 */
public class UserJpaController implements Serializable {

    public UserJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(User user) {
        if (user.getProjectCollection() == null) {
            user.setProjectCollection(new ArrayList<Project>());
        }
        if (user.getUploadedFileCollection() == null) {
            user.setUploadedFileCollection(new ArrayList<UploadedFile>());
        }
        if (user.getMessageCollection() == null) {
            user.setMessageCollection(new ArrayList<Message>());
        }
        if (user.getActionHistoryCollection() == null) {
            user.setActionHistoryCollection(new ArrayList<ActionHistory>());
        }
        if (user.getTaskCollection() == null) {
            user.setTaskCollection(new ArrayList<Task>());
        }
        if (user.getProjectCollection1() == null) {
            user.setProjectCollection1(new ArrayList<Project>());
        }
        if (user.getInvitationCollection() == null) {
            user.setInvitationCollection(new ArrayList<Invitation>());
        }
        if (user.getInitialDocumentCollection() == null) {
            user.setInitialDocumentCollection(new ArrayList<InitialDocument>());
        }
        if (user.getCopyCollection() == null) {
            user.setCopyCollection(new ArrayList<Copy>());
        }
        if (user.getMemberHistoryCollection() == null) {
            user.setMemberHistoryCollection(new ArrayList<MemberHistory>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Project> attachedProjectCollection = new ArrayList<Project>();
            for (Project projectCollectionProjectToAttach : user.getProjectCollection()) {
                projectCollectionProjectToAttach = em.getReference(projectCollectionProjectToAttach.getClass(), projectCollectionProjectToAttach.getId());
                attachedProjectCollection.add(projectCollectionProjectToAttach);
            }
            user.setProjectCollection(attachedProjectCollection);
            Collection<UploadedFile> attachedUploadedFileCollection = new ArrayList<UploadedFile>();
            for (UploadedFile uploadedFileCollectionUploadedFileToAttach : user.getUploadedFileCollection()) {
                uploadedFileCollectionUploadedFileToAttach = em.getReference(uploadedFileCollectionUploadedFileToAttach.getClass(), uploadedFileCollectionUploadedFileToAttach.getId());
                attachedUploadedFileCollection.add(uploadedFileCollectionUploadedFileToAttach);
            }
            user.setUploadedFileCollection(attachedUploadedFileCollection);
            Collection<Message> attachedMessageCollection = new ArrayList<Message>();
            for (Message messageCollectionMessageToAttach : user.getMessageCollection()) {
                messageCollectionMessageToAttach = em.getReference(messageCollectionMessageToAttach.getClass(), messageCollectionMessageToAttach.getId());
                attachedMessageCollection.add(messageCollectionMessageToAttach);
            }
            user.setMessageCollection(attachedMessageCollection);
            Collection<ActionHistory> attachedActionHistoryCollection = new ArrayList<ActionHistory>();
            for (ActionHistory actionHistoryCollectionActionHistoryToAttach : user.getActionHistoryCollection()) {
                actionHistoryCollectionActionHistoryToAttach = em.getReference(actionHistoryCollectionActionHistoryToAttach.getClass(), actionHistoryCollectionActionHistoryToAttach.getId());
                attachedActionHistoryCollection.add(actionHistoryCollectionActionHistoryToAttach);
            }
            user.setActionHistoryCollection(attachedActionHistoryCollection);
            Collection<Task> attachedTaskCollection = new ArrayList<Task>();
            for (Task taskCollectionTaskToAttach : user.getTaskCollection()) {
                taskCollectionTaskToAttach = em.getReference(taskCollectionTaskToAttach.getClass(), taskCollectionTaskToAttach.getId());
                attachedTaskCollection.add(taskCollectionTaskToAttach);
            }
            user.setTaskCollection(attachedTaskCollection);
            Collection<Project> attachedProjectCollection1 = new ArrayList<Project>();
            for (Project projectCollection1ProjectToAttach : user.getProjectCollection1()) {
                projectCollection1ProjectToAttach = em.getReference(projectCollection1ProjectToAttach.getClass(), projectCollection1ProjectToAttach.getId());
                attachedProjectCollection1.add(projectCollection1ProjectToAttach);
            }
            user.setProjectCollection1(attachedProjectCollection1);
            Collection<Invitation> attachedInvitationCollection = new ArrayList<Invitation>();
            for (Invitation invitationCollectionInvitationToAttach : user.getInvitationCollection()) {
                invitationCollectionInvitationToAttach = em.getReference(invitationCollectionInvitationToAttach.getClass(), invitationCollectionInvitationToAttach.getId());
                attachedInvitationCollection.add(invitationCollectionInvitationToAttach);
            }
            user.setInvitationCollection(attachedInvitationCollection);
            Collection<InitialDocument> attachedInitialDocumentCollection = new ArrayList<InitialDocument>();
            for (InitialDocument initialDocumentCollectionInitialDocumentToAttach : user.getInitialDocumentCollection()) {
                initialDocumentCollectionInitialDocumentToAttach = em.getReference(initialDocumentCollectionInitialDocumentToAttach.getClass(), initialDocumentCollectionInitialDocumentToAttach.getId());
                attachedInitialDocumentCollection.add(initialDocumentCollectionInitialDocumentToAttach);
            }
            user.setInitialDocumentCollection(attachedInitialDocumentCollection);
            Collection<Copy> attachedCopyCollection = new ArrayList<Copy>();
            for (Copy copyCollectionCopyToAttach : user.getCopyCollection()) {
                copyCollectionCopyToAttach = em.getReference(copyCollectionCopyToAttach.getClass(), copyCollectionCopyToAttach.getId());
                attachedCopyCollection.add(copyCollectionCopyToAttach);
            }
            user.setCopyCollection(attachedCopyCollection);
            Collection<MemberHistory> attachedMemberHistoryCollection = new ArrayList<MemberHistory>();
            for (MemberHistory memberHistoryCollectionMemberHistoryToAttach : user.getMemberHistoryCollection()) {
                memberHistoryCollectionMemberHistoryToAttach = em.getReference(memberHistoryCollectionMemberHistoryToAttach.getClass(), memberHistoryCollectionMemberHistoryToAttach.getId());
                attachedMemberHistoryCollection.add(memberHistoryCollectionMemberHistoryToAttach);
            }
            user.setMemberHistoryCollection(attachedMemberHistoryCollection);
            em.persist(user);
            for (Project projectCollectionProject : user.getProjectCollection()) {
                projectCollectionProject.getUserCollection().add(user);
                projectCollectionProject = em.merge(projectCollectionProject);
            }
            for (UploadedFile uploadedFileCollectionUploadedFile : user.getUploadedFileCollection()) {
                User oldUserIdOfUploadedFileCollectionUploadedFile = uploadedFileCollectionUploadedFile.getUserId();
                uploadedFileCollectionUploadedFile.setUserId(user);
                uploadedFileCollectionUploadedFile = em.merge(uploadedFileCollectionUploadedFile);
                if (oldUserIdOfUploadedFileCollectionUploadedFile != null) {
                    oldUserIdOfUploadedFileCollectionUploadedFile.getUploadedFileCollection().remove(uploadedFileCollectionUploadedFile);
                    oldUserIdOfUploadedFileCollectionUploadedFile = em.merge(oldUserIdOfUploadedFileCollectionUploadedFile);
                }
            }
            for (Message messageCollectionMessage : user.getMessageCollection()) {
                User oldUserIdOfMessageCollectionMessage = messageCollectionMessage.getUserId();
                messageCollectionMessage.setUserId(user);
                messageCollectionMessage = em.merge(messageCollectionMessage);
                if (oldUserIdOfMessageCollectionMessage != null) {
                    oldUserIdOfMessageCollectionMessage.getMessageCollection().remove(messageCollectionMessage);
                    oldUserIdOfMessageCollectionMessage = em.merge(oldUserIdOfMessageCollectionMessage);
                }
            }
            for (ActionHistory actionHistoryCollectionActionHistory : user.getActionHistoryCollection()) {
                User oldUserIdOfActionHistoryCollectionActionHistory = actionHistoryCollectionActionHistory.getUserId();
                actionHistoryCollectionActionHistory.setUserId(user);
                actionHistoryCollectionActionHistory = em.merge(actionHistoryCollectionActionHistory);
                if (oldUserIdOfActionHistoryCollectionActionHistory != null) {
                    oldUserIdOfActionHistoryCollectionActionHistory.getActionHistoryCollection().remove(actionHistoryCollectionActionHistory);
                    oldUserIdOfActionHistoryCollectionActionHistory = em.merge(oldUserIdOfActionHistoryCollectionActionHistory);
                }
            }
            for (Task taskCollectionTask : user.getTaskCollection()) {
                User oldUserIdOfTaskCollectionTask = taskCollectionTask.getUserId();
                taskCollectionTask.setUserId(user);
                taskCollectionTask = em.merge(taskCollectionTask);
                if (oldUserIdOfTaskCollectionTask != null) {
                    oldUserIdOfTaskCollectionTask.getTaskCollection().remove(taskCollectionTask);
                    oldUserIdOfTaskCollectionTask = em.merge(oldUserIdOfTaskCollectionTask);
                }
            }
            for (Project projectCollection1Project : user.getProjectCollection1()) {
                User oldOwnerOfProjectCollection1Project = projectCollection1Project.getOwner();
                projectCollection1Project.setOwner(user);
                projectCollection1Project = em.merge(projectCollection1Project);
                if (oldOwnerOfProjectCollection1Project != null) {
                    oldOwnerOfProjectCollection1Project.getProjectCollection1().remove(projectCollection1Project);
                    oldOwnerOfProjectCollection1Project = em.merge(oldOwnerOfProjectCollection1Project);
                }
            }
            for (Invitation invitationCollectionInvitation : user.getInvitationCollection()) {
                User oldUserIdOfInvitationCollectionInvitation = invitationCollectionInvitation.getUserId();
                invitationCollectionInvitation.setUserId(user);
                invitationCollectionInvitation = em.merge(invitationCollectionInvitation);
                if (oldUserIdOfInvitationCollectionInvitation != null) {
                    oldUserIdOfInvitationCollectionInvitation.getInvitationCollection().remove(invitationCollectionInvitation);
                    oldUserIdOfInvitationCollectionInvitation = em.merge(oldUserIdOfInvitationCollectionInvitation);
                }
            }
            for (InitialDocument initialDocumentCollectionInitialDocument : user.getInitialDocumentCollection()) {
                User oldUserIdOfInitialDocumentCollectionInitialDocument = initialDocumentCollectionInitialDocument.getUserId();
                initialDocumentCollectionInitialDocument.setUserId(user);
                initialDocumentCollectionInitialDocument = em.merge(initialDocumentCollectionInitialDocument);
                if (oldUserIdOfInitialDocumentCollectionInitialDocument != null) {
                    oldUserIdOfInitialDocumentCollectionInitialDocument.getInitialDocumentCollection().remove(initialDocumentCollectionInitialDocument);
                    oldUserIdOfInitialDocumentCollectionInitialDocument = em.merge(oldUserIdOfInitialDocumentCollectionInitialDocument);
                }
            }
            for (Copy copyCollectionCopy : user.getCopyCollection()) {
                User oldUserIdOfCopyCollectionCopy = copyCollectionCopy.getUserId();
                copyCollectionCopy.setUserId(user);
                copyCollectionCopy = em.merge(copyCollectionCopy);
                if (oldUserIdOfCopyCollectionCopy != null) {
                    oldUserIdOfCopyCollectionCopy.getCopyCollection().remove(copyCollectionCopy);
                    oldUserIdOfCopyCollectionCopy = em.merge(oldUserIdOfCopyCollectionCopy);
                }
            }
            for (MemberHistory memberHistoryCollectionMemberHistory : user.getMemberHistoryCollection()) {
                User oldMemberIdOfMemberHistoryCollectionMemberHistory = memberHistoryCollectionMemberHistory.getMemberId();
                memberHistoryCollectionMemberHistory.setMemberId(user);
                memberHistoryCollectionMemberHistory = em.merge(memberHistoryCollectionMemberHistory);
                if (oldMemberIdOfMemberHistoryCollectionMemberHistory != null) {
                    oldMemberIdOfMemberHistoryCollectionMemberHistory.getMemberHistoryCollection().remove(memberHistoryCollectionMemberHistory);
                    oldMemberIdOfMemberHistoryCollectionMemberHistory = em.merge(oldMemberIdOfMemberHistoryCollectionMemberHistory);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(User user) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User persistentUser = em.find(User.class, user.getId());
            Collection<Project> projectCollectionOld = persistentUser.getProjectCollection();
            Collection<Project> projectCollectionNew = user.getProjectCollection();
            Collection<UploadedFile> uploadedFileCollectionOld = persistentUser.getUploadedFileCollection();
            Collection<UploadedFile> uploadedFileCollectionNew = user.getUploadedFileCollection();
            Collection<Message> messageCollectionOld = persistentUser.getMessageCollection();
            Collection<Message> messageCollectionNew = user.getMessageCollection();
            Collection<ActionHistory> actionHistoryCollectionOld = persistentUser.getActionHistoryCollection();
            Collection<ActionHistory> actionHistoryCollectionNew = user.getActionHistoryCollection();
            Collection<Task> taskCollectionOld = persistentUser.getTaskCollection();
            Collection<Task> taskCollectionNew = user.getTaskCollection();
            Collection<Project> projectCollection1Old = persistentUser.getProjectCollection1();
            Collection<Project> projectCollection1New = user.getProjectCollection1();
            Collection<Invitation> invitationCollectionOld = persistentUser.getInvitationCollection();
            Collection<Invitation> invitationCollectionNew = user.getInvitationCollection();
            Collection<InitialDocument> initialDocumentCollectionOld = persistentUser.getInitialDocumentCollection();
            Collection<InitialDocument> initialDocumentCollectionNew = user.getInitialDocumentCollection();
            Collection<Copy> copyCollectionOld = persistentUser.getCopyCollection();
            Collection<Copy> copyCollectionNew = user.getCopyCollection();
            Collection<MemberHistory> memberHistoryCollectionOld = persistentUser.getMemberHistoryCollection();
            Collection<MemberHistory> memberHistoryCollectionNew = user.getMemberHistoryCollection();
            List<String> illegalOrphanMessages = null;
            for (UploadedFile uploadedFileCollectionOldUploadedFile : uploadedFileCollectionOld) {
                if (!uploadedFileCollectionNew.contains(uploadedFileCollectionOldUploadedFile)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UploadedFile " + uploadedFileCollectionOldUploadedFile + " since its userId field is not nullable.");
                }
            }
            for (Message messageCollectionOldMessage : messageCollectionOld) {
                if (!messageCollectionNew.contains(messageCollectionOldMessage)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Message " + messageCollectionOldMessage + " since its userId field is not nullable.");
                }
            }
            for (InitialDocument initialDocumentCollectionOldInitialDocument : initialDocumentCollectionOld) {
                if (!initialDocumentCollectionNew.contains(initialDocumentCollectionOldInitialDocument)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InitialDocument " + initialDocumentCollectionOldInitialDocument + " since its userId field is not nullable.");
                }
            }
            for (Copy copyCollectionOldCopy : copyCollectionOld) {
                if (!copyCollectionNew.contains(copyCollectionOldCopy)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Copy " + copyCollectionOldCopy + " since its userId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Project> attachedProjectCollectionNew = new ArrayList<Project>();
            for (Project projectCollectionNewProjectToAttach : projectCollectionNew) {
                projectCollectionNewProjectToAttach = em.getReference(projectCollectionNewProjectToAttach.getClass(), projectCollectionNewProjectToAttach.getId());
                attachedProjectCollectionNew.add(projectCollectionNewProjectToAttach);
            }
            projectCollectionNew = attachedProjectCollectionNew;
            user.setProjectCollection(projectCollectionNew);
            Collection<UploadedFile> attachedUploadedFileCollectionNew = new ArrayList<UploadedFile>();
            for (UploadedFile uploadedFileCollectionNewUploadedFileToAttach : uploadedFileCollectionNew) {
                uploadedFileCollectionNewUploadedFileToAttach = em.getReference(uploadedFileCollectionNewUploadedFileToAttach.getClass(), uploadedFileCollectionNewUploadedFileToAttach.getId());
                attachedUploadedFileCollectionNew.add(uploadedFileCollectionNewUploadedFileToAttach);
            }
            uploadedFileCollectionNew = attachedUploadedFileCollectionNew;
            user.setUploadedFileCollection(uploadedFileCollectionNew);
            Collection<Message> attachedMessageCollectionNew = new ArrayList<Message>();
            for (Message messageCollectionNewMessageToAttach : messageCollectionNew) {
                messageCollectionNewMessageToAttach = em.getReference(messageCollectionNewMessageToAttach.getClass(), messageCollectionNewMessageToAttach.getId());
                attachedMessageCollectionNew.add(messageCollectionNewMessageToAttach);
            }
            messageCollectionNew = attachedMessageCollectionNew;
            user.setMessageCollection(messageCollectionNew);
            Collection<ActionHistory> attachedActionHistoryCollectionNew = new ArrayList<ActionHistory>();
            for (ActionHistory actionHistoryCollectionNewActionHistoryToAttach : actionHistoryCollectionNew) {
                actionHistoryCollectionNewActionHistoryToAttach = em.getReference(actionHistoryCollectionNewActionHistoryToAttach.getClass(), actionHistoryCollectionNewActionHistoryToAttach.getId());
                attachedActionHistoryCollectionNew.add(actionHistoryCollectionNewActionHistoryToAttach);
            }
            actionHistoryCollectionNew = attachedActionHistoryCollectionNew;
            user.setActionHistoryCollection(actionHistoryCollectionNew);
            Collection<Task> attachedTaskCollectionNew = new ArrayList<Task>();
            for (Task taskCollectionNewTaskToAttach : taskCollectionNew) {
                taskCollectionNewTaskToAttach = em.getReference(taskCollectionNewTaskToAttach.getClass(), taskCollectionNewTaskToAttach.getId());
                attachedTaskCollectionNew.add(taskCollectionNewTaskToAttach);
            }
            taskCollectionNew = attachedTaskCollectionNew;
            user.setTaskCollection(taskCollectionNew);
            Collection<Project> attachedProjectCollection1New = new ArrayList<Project>();
            for (Project projectCollection1NewProjectToAttach : projectCollection1New) {
                projectCollection1NewProjectToAttach = em.getReference(projectCollection1NewProjectToAttach.getClass(), projectCollection1NewProjectToAttach.getId());
                attachedProjectCollection1New.add(projectCollection1NewProjectToAttach);
            }
            projectCollection1New = attachedProjectCollection1New;
            user.setProjectCollection1(projectCollection1New);
            Collection<Invitation> attachedInvitationCollectionNew = new ArrayList<Invitation>();
            for (Invitation invitationCollectionNewInvitationToAttach : invitationCollectionNew) {
                invitationCollectionNewInvitationToAttach = em.getReference(invitationCollectionNewInvitationToAttach.getClass(), invitationCollectionNewInvitationToAttach.getId());
                attachedInvitationCollectionNew.add(invitationCollectionNewInvitationToAttach);
            }
            invitationCollectionNew = attachedInvitationCollectionNew;
            user.setInvitationCollection(invitationCollectionNew);
            Collection<InitialDocument> attachedInitialDocumentCollectionNew = new ArrayList<InitialDocument>();
            for (InitialDocument initialDocumentCollectionNewInitialDocumentToAttach : initialDocumentCollectionNew) {
                initialDocumentCollectionNewInitialDocumentToAttach = em.getReference(initialDocumentCollectionNewInitialDocumentToAttach.getClass(), initialDocumentCollectionNewInitialDocumentToAttach.getId());
                attachedInitialDocumentCollectionNew.add(initialDocumentCollectionNewInitialDocumentToAttach);
            }
            initialDocumentCollectionNew = attachedInitialDocumentCollectionNew;
            user.setInitialDocumentCollection(initialDocumentCollectionNew);
            Collection<Copy> attachedCopyCollectionNew = new ArrayList<Copy>();
            for (Copy copyCollectionNewCopyToAttach : copyCollectionNew) {
                copyCollectionNewCopyToAttach = em.getReference(copyCollectionNewCopyToAttach.getClass(), copyCollectionNewCopyToAttach.getId());
                attachedCopyCollectionNew.add(copyCollectionNewCopyToAttach);
            }
            copyCollectionNew = attachedCopyCollectionNew;
            user.setCopyCollection(copyCollectionNew);
            Collection<MemberHistory> attachedMemberHistoryCollectionNew = new ArrayList<MemberHistory>();
            for (MemberHistory memberHistoryCollectionNewMemberHistoryToAttach : memberHistoryCollectionNew) {
                memberHistoryCollectionNewMemberHistoryToAttach = em.getReference(memberHistoryCollectionNewMemberHistoryToAttach.getClass(), memberHistoryCollectionNewMemberHistoryToAttach.getId());
                attachedMemberHistoryCollectionNew.add(memberHistoryCollectionNewMemberHistoryToAttach);
            }
            memberHistoryCollectionNew = attachedMemberHistoryCollectionNew;
            user.setMemberHistoryCollection(memberHistoryCollectionNew);
            user = em.merge(user);
            for (Project projectCollectionOldProject : projectCollectionOld) {
                if (!projectCollectionNew.contains(projectCollectionOldProject)) {
                    projectCollectionOldProject.getUserCollection().remove(user);
                    projectCollectionOldProject = em.merge(projectCollectionOldProject);
                }
            }
            for (Project projectCollectionNewProject : projectCollectionNew) {
                if (!projectCollectionOld.contains(projectCollectionNewProject)) {
                    projectCollectionNewProject.getUserCollection().add(user);
                    projectCollectionNewProject = em.merge(projectCollectionNewProject);
                }
            }
            for (UploadedFile uploadedFileCollectionNewUploadedFile : uploadedFileCollectionNew) {
                if (!uploadedFileCollectionOld.contains(uploadedFileCollectionNewUploadedFile)) {
                    User oldUserIdOfUploadedFileCollectionNewUploadedFile = uploadedFileCollectionNewUploadedFile.getUserId();
                    uploadedFileCollectionNewUploadedFile.setUserId(user);
                    uploadedFileCollectionNewUploadedFile = em.merge(uploadedFileCollectionNewUploadedFile);
                    if (oldUserIdOfUploadedFileCollectionNewUploadedFile != null && !oldUserIdOfUploadedFileCollectionNewUploadedFile.equals(user)) {
                        oldUserIdOfUploadedFileCollectionNewUploadedFile.getUploadedFileCollection().remove(uploadedFileCollectionNewUploadedFile);
                        oldUserIdOfUploadedFileCollectionNewUploadedFile = em.merge(oldUserIdOfUploadedFileCollectionNewUploadedFile);
                    }
                }
            }
            for (Message messageCollectionNewMessage : messageCollectionNew) {
                if (!messageCollectionOld.contains(messageCollectionNewMessage)) {
                    User oldUserIdOfMessageCollectionNewMessage = messageCollectionNewMessage.getUserId();
                    messageCollectionNewMessage.setUserId(user);
                    messageCollectionNewMessage = em.merge(messageCollectionNewMessage);
                    if (oldUserIdOfMessageCollectionNewMessage != null && !oldUserIdOfMessageCollectionNewMessage.equals(user)) {
                        oldUserIdOfMessageCollectionNewMessage.getMessageCollection().remove(messageCollectionNewMessage);
                        oldUserIdOfMessageCollectionNewMessage = em.merge(oldUserIdOfMessageCollectionNewMessage);
                    }
                }
            }
            for (ActionHistory actionHistoryCollectionOldActionHistory : actionHistoryCollectionOld) {
                if (!actionHistoryCollectionNew.contains(actionHistoryCollectionOldActionHistory)) {
                    actionHistoryCollectionOldActionHistory.setUserId(null);
                    actionHistoryCollectionOldActionHistory = em.merge(actionHistoryCollectionOldActionHistory);
                }
            }
            for (ActionHistory actionHistoryCollectionNewActionHistory : actionHistoryCollectionNew) {
                if (!actionHistoryCollectionOld.contains(actionHistoryCollectionNewActionHistory)) {
                    User oldUserIdOfActionHistoryCollectionNewActionHistory = actionHistoryCollectionNewActionHistory.getUserId();
                    actionHistoryCollectionNewActionHistory.setUserId(user);
                    actionHistoryCollectionNewActionHistory = em.merge(actionHistoryCollectionNewActionHistory);
                    if (oldUserIdOfActionHistoryCollectionNewActionHistory != null && !oldUserIdOfActionHistoryCollectionNewActionHistory.equals(user)) {
                        oldUserIdOfActionHistoryCollectionNewActionHistory.getActionHistoryCollection().remove(actionHistoryCollectionNewActionHistory);
                        oldUserIdOfActionHistoryCollectionNewActionHistory = em.merge(oldUserIdOfActionHistoryCollectionNewActionHistory);
                    }
                }
            }
            for (Task taskCollectionOldTask : taskCollectionOld) {
                if (!taskCollectionNew.contains(taskCollectionOldTask)) {
                    taskCollectionOldTask.setUserId(null);
                    taskCollectionOldTask = em.merge(taskCollectionOldTask);
                }
            }
            for (Task taskCollectionNewTask : taskCollectionNew) {
                if (!taskCollectionOld.contains(taskCollectionNewTask)) {
                    User oldUserIdOfTaskCollectionNewTask = taskCollectionNewTask.getUserId();
                    taskCollectionNewTask.setUserId(user);
                    taskCollectionNewTask = em.merge(taskCollectionNewTask);
                    if (oldUserIdOfTaskCollectionNewTask != null && !oldUserIdOfTaskCollectionNewTask.equals(user)) {
                        oldUserIdOfTaskCollectionNewTask.getTaskCollection().remove(taskCollectionNewTask);
                        oldUserIdOfTaskCollectionNewTask = em.merge(oldUserIdOfTaskCollectionNewTask);
                    }
                }
            }
            for (Project projectCollection1OldProject : projectCollection1Old) {
                if (!projectCollection1New.contains(projectCollection1OldProject)) {
                    projectCollection1OldProject.setOwner(null);
                    projectCollection1OldProject = em.merge(projectCollection1OldProject);
                }
            }
            for (Project projectCollection1NewProject : projectCollection1New) {
                if (!projectCollection1Old.contains(projectCollection1NewProject)) {
                    User oldOwnerOfProjectCollection1NewProject = projectCollection1NewProject.getOwner();
                    projectCollection1NewProject.setOwner(user);
                    projectCollection1NewProject = em.merge(projectCollection1NewProject);
                    if (oldOwnerOfProjectCollection1NewProject != null && !oldOwnerOfProjectCollection1NewProject.equals(user)) {
                        oldOwnerOfProjectCollection1NewProject.getProjectCollection1().remove(projectCollection1NewProject);
                        oldOwnerOfProjectCollection1NewProject = em.merge(oldOwnerOfProjectCollection1NewProject);
                    }
                }
            }
            for (Invitation invitationCollectionOldInvitation : invitationCollectionOld) {
                if (!invitationCollectionNew.contains(invitationCollectionOldInvitation)) {
                    invitationCollectionOldInvitation.setUserId(null);
                    invitationCollectionOldInvitation = em.merge(invitationCollectionOldInvitation);
                }
            }
            for (Invitation invitationCollectionNewInvitation : invitationCollectionNew) {
                if (!invitationCollectionOld.contains(invitationCollectionNewInvitation)) {
                    User oldUserIdOfInvitationCollectionNewInvitation = invitationCollectionNewInvitation.getUserId();
                    invitationCollectionNewInvitation.setUserId(user);
                    invitationCollectionNewInvitation = em.merge(invitationCollectionNewInvitation);
                    if (oldUserIdOfInvitationCollectionNewInvitation != null && !oldUserIdOfInvitationCollectionNewInvitation.equals(user)) {
                        oldUserIdOfInvitationCollectionNewInvitation.getInvitationCollection().remove(invitationCollectionNewInvitation);
                        oldUserIdOfInvitationCollectionNewInvitation = em.merge(oldUserIdOfInvitationCollectionNewInvitation);
                    }
                }
            }
            for (InitialDocument initialDocumentCollectionNewInitialDocument : initialDocumentCollectionNew) {
                if (!initialDocumentCollectionOld.contains(initialDocumentCollectionNewInitialDocument)) {
                    User oldUserIdOfInitialDocumentCollectionNewInitialDocument = initialDocumentCollectionNewInitialDocument.getUserId();
                    initialDocumentCollectionNewInitialDocument.setUserId(user);
                    initialDocumentCollectionNewInitialDocument = em.merge(initialDocumentCollectionNewInitialDocument);
                    if (oldUserIdOfInitialDocumentCollectionNewInitialDocument != null && !oldUserIdOfInitialDocumentCollectionNewInitialDocument.equals(user)) {
                        oldUserIdOfInitialDocumentCollectionNewInitialDocument.getInitialDocumentCollection().remove(initialDocumentCollectionNewInitialDocument);
                        oldUserIdOfInitialDocumentCollectionNewInitialDocument = em.merge(oldUserIdOfInitialDocumentCollectionNewInitialDocument);
                    }
                }
            }
            for (Copy copyCollectionNewCopy : copyCollectionNew) {
                if (!copyCollectionOld.contains(copyCollectionNewCopy)) {
                    User oldUserIdOfCopyCollectionNewCopy = copyCollectionNewCopy.getUserId();
                    copyCollectionNewCopy.setUserId(user);
                    copyCollectionNewCopy = em.merge(copyCollectionNewCopy);
                    if (oldUserIdOfCopyCollectionNewCopy != null && !oldUserIdOfCopyCollectionNewCopy.equals(user)) {
                        oldUserIdOfCopyCollectionNewCopy.getCopyCollection().remove(copyCollectionNewCopy);
                        oldUserIdOfCopyCollectionNewCopy = em.merge(oldUserIdOfCopyCollectionNewCopy);
                    }
                }
            }
            for (MemberHistory memberHistoryCollectionOldMemberHistory : memberHistoryCollectionOld) {
                if (!memberHistoryCollectionNew.contains(memberHistoryCollectionOldMemberHistory)) {
                    memberHistoryCollectionOldMemberHistory.setMemberId(null);
                    memberHistoryCollectionOldMemberHistory = em.merge(memberHistoryCollectionOldMemberHistory);
                }
            }
            for (MemberHistory memberHistoryCollectionNewMemberHistory : memberHistoryCollectionNew) {
                if (!memberHistoryCollectionOld.contains(memberHistoryCollectionNewMemberHistory)) {
                    User oldMemberIdOfMemberHistoryCollectionNewMemberHistory = memberHistoryCollectionNewMemberHistory.getMemberId();
                    memberHistoryCollectionNewMemberHistory.setMemberId(user);
                    memberHistoryCollectionNewMemberHistory = em.merge(memberHistoryCollectionNewMemberHistory);
                    if (oldMemberIdOfMemberHistoryCollectionNewMemberHistory != null && !oldMemberIdOfMemberHistoryCollectionNewMemberHistory.equals(user)) {
                        oldMemberIdOfMemberHistoryCollectionNewMemberHistory.getMemberHistoryCollection().remove(memberHistoryCollectionNewMemberHistory);
                        oldMemberIdOfMemberHistoryCollectionNewMemberHistory = em.merge(oldMemberIdOfMemberHistoryCollectionNewMemberHistory);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = user.getId();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
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
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<UploadedFile> uploadedFileCollectionOrphanCheck = user.getUploadedFileCollection();
            for (UploadedFile uploadedFileCollectionOrphanCheckUploadedFile : uploadedFileCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the UploadedFile " + uploadedFileCollectionOrphanCheckUploadedFile + " in its uploadedFileCollection field has a non-nullable userId field.");
            }
            Collection<Message> messageCollectionOrphanCheck = user.getMessageCollection();
            for (Message messageCollectionOrphanCheckMessage : messageCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Message " + messageCollectionOrphanCheckMessage + " in its messageCollection field has a non-nullable userId field.");
            }
            Collection<InitialDocument> initialDocumentCollectionOrphanCheck = user.getInitialDocumentCollection();
            for (InitialDocument initialDocumentCollectionOrphanCheckInitialDocument : initialDocumentCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the InitialDocument " + initialDocumentCollectionOrphanCheckInitialDocument + " in its initialDocumentCollection field has a non-nullable userId field.");
            }
            Collection<Copy> copyCollectionOrphanCheck = user.getCopyCollection();
            for (Copy copyCollectionOrphanCheckCopy : copyCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Copy " + copyCollectionOrphanCheckCopy + " in its copyCollection field has a non-nullable userId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Project> projectCollection = user.getProjectCollection();
            for (Project projectCollectionProject : projectCollection) {
                projectCollectionProject.getUserCollection().remove(user);
                projectCollectionProject = em.merge(projectCollectionProject);
            }
            Collection<ActionHistory> actionHistoryCollection = user.getActionHistoryCollection();
            for (ActionHistory actionHistoryCollectionActionHistory : actionHistoryCollection) {
                actionHistoryCollectionActionHistory.setUserId(null);
                actionHistoryCollectionActionHistory = em.merge(actionHistoryCollectionActionHistory);
            }
            Collection<Task> taskCollection = user.getTaskCollection();
            for (Task taskCollectionTask : taskCollection) {
                taskCollectionTask.setUserId(null);
                taskCollectionTask = em.merge(taskCollectionTask);
            }
            Collection<Project> projectCollection1 = user.getProjectCollection1();
            for (Project projectCollection1Project : projectCollection1) {
                projectCollection1Project.setOwner(null);
                projectCollection1Project = em.merge(projectCollection1Project);
            }
            Collection<Invitation> invitationCollection = user.getInvitationCollection();
            for (Invitation invitationCollectionInvitation : invitationCollection) {
                invitationCollectionInvitation.setUserId(null);
                invitationCollectionInvitation = em.merge(invitationCollectionInvitation);
            }
            Collection<MemberHistory> memberHistoryCollection = user.getMemberHistoryCollection();
            for (MemberHistory memberHistoryCollectionMemberHistory : memberHistoryCollection) {
                memberHistoryCollectionMemberHistory.setMemberId(null);
                memberHistoryCollectionMemberHistory = em.merge(memberHistoryCollectionMemberHistory);
            }
            em.remove(user);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
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

    public User findUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

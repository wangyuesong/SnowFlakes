/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tongji.collaborationteam.dbentities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author WYS
 */
@Entity
@Table(name = "Project")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p"),
    @NamedQuery(name = "Project.findById", query = "SELECT p FROM Project p WHERE p.id = :id"),
    @NamedQuery(name = "Project.findByName", query = "SELECT p FROM Project p WHERE p.name = :name"),
    @NamedQuery(name = "Project.findByCategory", query = "SELECT p FROM Project p WHERE p.category = :category"),
    @NamedQuery(name = "Project.findByDescription", query = "SELECT p FROM Project p WHERE p.description = :description"),
    @NamedQuery(name = "Project.findByGoal", query = "SELECT p FROM Project p WHERE p.goal = :goal"),
    @NamedQuery(name = "Project.findByCreateTime", query = "SELECT p FROM Project p WHERE p.createTime = :createTime"),
    @NamedQuery(name = "Project.findByModifiedTime", query = "SELECT p FROM Project p WHERE p.modifiedTime = :modifiedTime"),
    @NamedQuery(name = "Project.findByDueTime", query = "SELECT p FROM Project p WHERE p.dueTime = :dueTime")})
public class Project implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "category")
    private String category;
    @Column(name = "description")
    private String description;
    @Column(name = "goal")
    private String goal;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;
    @Column(name = "due_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueTime;
    @ManyToMany(mappedBy = "projectCollection")
    private Collection<User> userCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId")
    private Collection<UploadedFile> uploadedFileCollection;
    @OneToMany(mappedBy = "projectId")
    private Collection<ActionHistory> actionHistoryCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId")
    private Collection<Task> taskCollection;
    @JoinColumn(name = "owner", referencedColumnName = "id")
    @ManyToOne
    private User owner;
    @OneToMany(mappedBy = "projectId")
    private Collection<Invitation> invitationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId")
    private Collection<InitialDocument> initialDocumentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId")
    private Collection<Copy> copyCollection;
    @OneToMany(mappedBy = "projectId")
    private Collection<MemberHistory> memberHistoryCollection;

    public Project() {
    }

    public Project(Integer id) {
        this.id = id;
    }

    public Project(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Date getDueTime() {
        return dueTime;
    }

    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }

    @XmlTransient
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    @XmlTransient
    public Collection<UploadedFile> getUploadedFileCollection() {
        return uploadedFileCollection;
    }

    public void setUploadedFileCollection(Collection<UploadedFile> uploadedFileCollection) {
        this.uploadedFileCollection = uploadedFileCollection;
    }

    @XmlTransient
    public Collection<ActionHistory> getActionHistoryCollection() {
        return actionHistoryCollection;
    }

    public void setActionHistoryCollection(Collection<ActionHistory> actionHistoryCollection) {
        this.actionHistoryCollection = actionHistoryCollection;
    }

    @XmlTransient
    public Collection<Task> getTaskCollection() {
        return taskCollection;
    }

    public void setTaskCollection(Collection<Task> taskCollection) {
        this.taskCollection = taskCollection;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @XmlTransient
    public Collection<Invitation> getInvitationCollection() {
        return invitationCollection;
    }

    public void setInvitationCollection(Collection<Invitation> invitationCollection) {
        this.invitationCollection = invitationCollection;
    }

    @XmlTransient
    public Collection<InitialDocument> getInitialDocumentCollection() {
        return initialDocumentCollection;
    }

    public void setInitialDocumentCollection(Collection<InitialDocument> initialDocumentCollection) {
        this.initialDocumentCollection = initialDocumentCollection;
    }

    @XmlTransient
    public Collection<Copy> getCopyCollection() {
        return copyCollection;
    }

    public void setCopyCollection(Collection<Copy> copyCollection) {
        this.copyCollection = copyCollection;
    }

    @XmlTransient
    public Collection<MemberHistory> getMemberHistoryCollection() {
        return memberHistoryCollection;
    }

    public void setMemberHistoryCollection(Collection<MemberHistory> memberHistoryCollection) {
        this.memberHistoryCollection = memberHistoryCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Project)) {
            return false;
        }
        Project other = (Project) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tongji.collaborationteam.dbentities.Project[ id=" + id + " ]";
    }
    
}

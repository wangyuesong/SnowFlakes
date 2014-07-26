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
@Table(name = "InitialDocument")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InitialDocument.findAll", query = "SELECT i FROM InitialDocument i"),
    @NamedQuery(name = "InitialDocument.findById", query = "SELECT i FROM InitialDocument i WHERE i.id = :id"),
    @NamedQuery(name = "InitialDocument.findByUploadTime", query = "SELECT i FROM InitialDocument i WHERE i.uploadTime = :uploadTime"),
    @NamedQuery(name = "InitialDocument.findByPath", query = "SELECT i FROM InitialDocument i WHERE i.path = :path"),
    @NamedQuery(name = "InitialDocument.findByEditCount", query = "SELECT i FROM InitialDocument i WHERE i.editCount = :editCount"),
    @NamedQuery(name = "InitialDocument.findByTitle", query = "SELECT i FROM InitialDocument i WHERE i.title = :title"),
    @NamedQuery(name = "InitialDocument.findByDescription", query = "SELECT i FROM InitialDocument i WHERE i.description = :description"),
    @NamedQuery(name = "InitialDocument.findByVersionId", query = "SELECT i FROM InitialDocument i WHERE i.versionId = :versionId"),
    @NamedQuery(name = "InitialDocument.findByNewestId", query = "SELECT i FROM InitialDocument i WHERE i.newestId = :newestId"),
    @NamedQuery(name = "InitialDocument.findByIsLocked", query = "SELECT i FROM InitialDocument i WHERE i.isLocked = :isLocked")})
public class InitialDocument implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "upload_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadTime;
    @Column(name = "path")
    private String path;
    @Column(name = "edit_count")
    private Integer editCount;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "version_id")
    private Integer versionId;
    @Column(name = "newest_id")
    private Integer newestId;
    @Column(name = "is_locked")
    private Short isLocked;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Project projectId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "belongsTo")
    private Collection<Copy> copyCollection;

    public InitialDocument() {
    }

    public InitialDocument(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getEditCount() {
        return editCount;
    }

    public void setEditCount(Integer editCount) {
        this.editCount = editCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public Integer getNewestId() {
        return newestId;
    }

    public void setNewestId(Integer newestId) {
        this.newestId = newestId;
    }

    public Short getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Short isLocked) {
        this.isLocked = isLocked;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Project getProjectId() {
        return projectId;
    }

    public void setProjectId(Project projectId) {
        this.projectId = projectId;
    }

    @XmlTransient
    public Collection<Copy> getCopyCollection() {
        return copyCollection;
    }

    public void setCopyCollection(Collection<Copy> copyCollection) {
        this.copyCollection = copyCollection;
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
        if (!(object instanceof InitialDocument)) {
            return false;
        }
        InitialDocument other = (InitialDocument) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tongji.collaborationteam.dbentities.InitialDocument[ id=" + id + " ]";
    }
    
}

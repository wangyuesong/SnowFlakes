/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tongji.collaborationteam.dbentities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author WYS
 */
@Entity
@Table(name = "Copy")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Copy.findAll", query = "SELECT c FROM Copy c"),
    @NamedQuery(name = "Copy.findById", query = "SELECT c FROM Copy c WHERE c.id = :id"),
    @NamedQuery(name = "Copy.findByUploadTime", query = "SELECT c FROM Copy c WHERE c.uploadTime = :uploadTime"),
    @NamedQuery(name = "Copy.findByPath", query = "SELECT c FROM Copy c WHERE c.path = :path"),
    @NamedQuery(name = "Copy.findByEditCount", query = "SELECT c FROM Copy c WHERE c.editCount = :editCount"),
    @NamedQuery(name = "Copy.findByTitle", query = "SELECT c FROM Copy c WHERE c.title = :title"),
    @NamedQuery(name = "Copy.findByDescription", query = "SELECT c FROM Copy c WHERE c.description = :description"),
    @NamedQuery(name = "Copy.findByVersionId", query = "SELECT c FROM Copy c WHERE c.versionId = :versionId"),
    @NamedQuery(name = "Copy.findByIsLocked", query = "SELECT c FROM Copy c WHERE c.isLocked = :isLocked")})
public class Copy implements Serializable {
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
    @Column(name = "is_locked")
    private Short isLocked;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Project projectId;
    @JoinColumn(name = "belongs_to", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private InitialDocument belongsTo;

    public Copy() {
    }

    public Copy(Integer id) {
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

    public InitialDocument getBelongsTo() {
        return belongsTo;
    }

    public void setBelongsTo(InitialDocument belongsTo) {
        this.belongsTo = belongsTo;
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
        if (!(object instanceof Copy)) {
            return false;
        }
        Copy other = (Copy) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tongji.collaborationteam.dbentities.Copy[ id=" + id + " ]";
    }
    
}

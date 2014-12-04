/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.nrm.simpletaxonmock.client.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ingimar
 */
@Entity
@Table(name = "MEDIA_TEXT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MediaText.findAll", query = "SELECT m FROM MediaText m"),
    @NamedQuery(name = "MediaText.findByUuid", query = "SELECT m FROM MediaText m WHERE m.uuid = :uuid"),
    @NamedQuery(name = "MediaText.findByLegend", query = "SELECT m FROM MediaText m WHERE m.legend = :legend"),
    @NamedQuery(name = "MediaText.findByLang", query = "SELECT m FROM MediaText m WHERE m.lang = :lang"),
    @NamedQuery(name = "MediaText.findByMediaUuid", query = "SELECT m FROM MediaText m WHERE m.mediaUuid = :mediaUuid"),
    @NamedQuery(name = "MediaText.findByComment", query = "SELECT m FROM MediaText m WHERE m.comment = :comment")})
public class MediaText implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "UUID")
    private Integer uuid;

    @Size(max = 255)
    @Column(name = "LEGEND")
    private String legend;

    @Size(max = 255)
    @Column(name = "LANG")
    private String lang;

    @Size(max = 255)
    @Column(name = "MEDIA_UUID")
    private String mediaUuid;

    @Size(max = 255)
    @Column(name = "COMMENT")
    private String comment;

    public MediaText() {
    }

    public MediaText(Integer uuid) {
        this.uuid = uuid;
    }

    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public String getLegend() {
        return legend;
    }

    public void setLegend(String legend) {
        this.legend = legend;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getMediaUuid() {
        return mediaUuid;
    }

    public void setMediaUuid(String mediaUuid) {
        this.mediaUuid = mediaUuid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uuid != null ? uuid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MediaText)) {
            return false;
        }
        MediaText other = (MediaText) object;
        if ((this.uuid == null && other.uuid != null) || (this.uuid != null && !this.uuid.equals(other.uuid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "is.zanzibar.projekt.controller.MediaText[ uuid=" + uuid + " ]";
    }
    
}

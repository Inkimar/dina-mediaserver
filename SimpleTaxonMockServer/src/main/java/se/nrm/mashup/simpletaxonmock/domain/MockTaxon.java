package se.nrm.mashup.simpletaxonmock.domain;

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
@Table(name = "mock_taxon")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MockTaxon.findAll", query = "SELECT m FROM MockTaxon m"),
    @NamedQuery(name = "MockTaxon.findById", query = "SELECT m FROM MockTaxon m WHERE m.id = :id"),
    @NamedQuery(name = "MockTaxon.findByExtUuid", query = "SELECT m FROM MockTaxon m WHERE m.extUuid = :extUuid"),
    @NamedQuery(name = "MockTaxon.findByCommonName", query = "SELECT m FROM MockTaxon m WHERE m.commonName = :commonName"),
    @NamedQuery(name = "MockTaxon.findByScientificName", query = "SELECT m FROM MockTaxon m WHERE m.scientificName = :scientificName")})
public class MockTaxon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Size(max = 255)
    @Column(name = "ext_uuid")
    private String extUuid;

    @Size(max = 255)
    @Column(name = "common_name")
    private String commonName;

    @Size(max = 255)
    @Column(name = "scientific_name")
    private String scientificName;

    public MockTaxon() {
    }

    public MockTaxon(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExtUuid() {
        return extUuid;
    }

    public void setExtUuid(String extUuid) {
        this.extUuid = extUuid;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
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
        if (!(object instanceof MockTaxon)) {
            return false;
        }
        MockTaxon other = (MockTaxon) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.nrm.mashup.simpletaxonmock.MockTaxon[ id=" + id + " ]";
    }
    
}

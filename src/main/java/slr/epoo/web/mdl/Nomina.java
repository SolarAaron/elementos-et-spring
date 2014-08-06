
package slr.epoo.web.mdl;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Aaron
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Nomina.findAll", query = "SELECT n FROM Nomina n"),
    @NamedQuery(name = "Nomina.findByIdN", query = "SELECT n FROM Nomina n WHERE n.idN = :idN"),
    @NamedQuery(name = "Nomina.findBySaldo", query = "SELECT n FROM Nomina n WHERE n.saldo = :saldo")})
public class Nomina implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_n")
    private Integer idN;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Float saldo;
    @JoinColumn(name = "id_e", referencedColumnName = "id_e")
    @OneToOne(optional = false)
    private Empleado idE;

    public Nomina() {
    }

    public Nomina(Integer idN) {
        this.idN = idN;
    }

    public Nomina(Integer idN, Float saldo, Empleado idE) {
        this.idN = idN;
        this.saldo = saldo;
        this.idE = idE;
    }

    public Integer getIdN() {
        return idN;
    }

    public void setIdN(Integer idN) {
        this.idN = idN;
    }

    public Float getSaldo() {
        return saldo;
    }

    public void setSaldo(Float saldo) {
        this.saldo = saldo;
    }

    @JsonIgnore
    @XmlTransient
    public Empleado getIdE() {
        return idE;
    }

    public void setIdE(Empleado idE) {
        this.idE = idE;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idN != null ? idN.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nomina)) {
            return false;
        }
        Nomina other = (Nomina) object;
        if ((this.idN == null && other.idN != null) || (this.idN != null && !this.idN.equals(other.idN))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "slr.epoo.web.mdl.Nomina[ idN=" + idN + " ]";
    }
    
}

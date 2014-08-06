
package slr.epoo.web.mdl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
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
    @NamedQuery(name = "Venta.findAll", query = "SELECT v FROM Venta v"),
    @NamedQuery(name = "Venta.findByIdV", query = "SELECT v FROM Venta v WHERE v.idV = :idV"),
    @NamedQuery(name = "Venta.findByFecha", query = "SELECT v FROM Venta v WHERE v.fecha = :fecha")})
public class Venta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_v")
    private Integer idV;
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "venta")
    private List<DetalleVenta> detalleVentaList;
    @JoinColumn(name = "id_e", referencedColumnName = "id_e")
    @ManyToOne(optional = false)
    private Empleado idE;
    @JoinColumn(name = "id_c", referencedColumnName = "id_c")
    @ManyToOne(optional = false)
    private Cliente idC;

    public Venta() {
    }

    public Venta(Integer idV) {
        this.idV = idV;
    }

    public Venta(Integer idV, Date fecha) {
        this.idV = idV;
        this.fecha = fecha;
    }

    public Integer getIdV() {
        return idV;
    }

    public void setIdV(Integer idV) {
        this.idV = idV;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @XmlTransient
    @JsonIgnore
    public List<DetalleVenta> getDetalleVentaList() {
        return detalleVentaList;
    }

    public void setDetalleVentaList(List<DetalleVenta> detalleVentaList) {
        this.detalleVentaList = detalleVentaList;
    }

    public Empleado getIdE() {
        return idE;
    }

    public void setIdE(Empleado idE) {
        this.idE = idE;
    }

    public Cliente getIdC() {
        return idC;
    }

    public void setIdC(Cliente idC) {
        this.idC = idC;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idV != null ? idV.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Venta)) {
            return false;
        }
        Venta other = (Venta) object;
        if ((this.idV == null && other.idV != null) || (this.idV != null && !this.idV.equals(other.idV))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "slr.epoo.web.mdl.Venta[ idV=" + idV + " ]";
    }
    
}

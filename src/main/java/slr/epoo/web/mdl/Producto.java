
package slr.epoo.web.mdl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Aaron Torres <solaraaron@gmail.com>
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p"),
    @NamedQuery(name = "Producto.findByCodP", query = "SELECT p FROM Producto p WHERE p.codP = :codP"),
    @NamedQuery(name = "Producto.findByDescripcion", query = "SELECT p FROM Producto p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "Producto.findByPrecio", query = "SELECT p FROM Producto p WHERE p.precio = :precio")})
public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "cod_p", nullable = false, length = 5)
    private String codP;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(nullable = false, length = 80)
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, mappedBy = "producto")
    private List<DetalleVenta> detalleVentaList;

    public Producto() {
    }

    public Producto(String codP) {
        this.codP = codP;
    }

    public Producto(String codP, String descripcion, BigDecimal precio) {
        this.codP = codP;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public String getCodP() {
        return codP;
    }

    public void setCodP(String codP) {
        this.codP = codP;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    @XmlTransient
    @JsonIgnore
    public List<DetalleVenta> getDetalleVentaList() {
        return detalleVentaList;
    }

    public void setDetalleVentaList(List<DetalleVenta> detalleVentaList) {
        this.detalleVentaList = detalleVentaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codP != null ? codP.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.codP == null && other.codP != null) || (this.codP != null && !this.codP.equals(other.codP))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "slr.epoo.web.mdl.Producto[ codP=" + codP + " ]";
    }

}

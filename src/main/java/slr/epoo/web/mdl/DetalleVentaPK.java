
package slr.epoo.web.mdl;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Aaron Torres <solaraaron@gmail.com>
 */
@Embeddable
public class DetalleVentaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_v", nullable = false)
    private int idV;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "cod_p", nullable = false, length = 5)
    private String codP;

    public DetalleVentaPK() {
    }

    public DetalleVentaPK(int idV, String codP) {
        this.idV = idV;
        this.codP = codP;
    }

    public int getIdV() {
        return idV;
    }

    public void setIdV(int idV) {
        this.idV = idV;
    }

    public String getCodP() {
        return codP;
    }

    public void setCodP(String codP) {
        this.codP = codP;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idV;
        hash += (codP != null ? codP.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleVentaPK)) {
            return false;
        }
        DetalleVentaPK other = (DetalleVentaPK) object;
        if (this.idV != other.idV) {
            return false;
        }
        if ((this.codP == null && other.codP != null) || (this.codP != null && !this.codP.equals(other.codP))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "slr.epoo.web.mdl.DetalleVentaPK[ idV=" + idV + ", codP=" + codP + " ]";
    }

}

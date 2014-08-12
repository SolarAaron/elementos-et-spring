
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
public class ClientePK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_c", nullable = false)
    private int idC;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nom_usuario", nullable = false, length = 20)
    private String nomUsuario;

    public ClientePK() {
    }

    public ClientePK(int idC, String nomUsuario) {
        this.idC = idC;
        this.nomUsuario = nomUsuario;
    }

    public int getIdC() {
        return idC;
    }

    public void setIdC(int idC) {
        this.idC = idC;
    }

    public String getNomUsuario() {
        return nomUsuario;
    }

    public void setNomUsuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idC;
        hash += (nomUsuario != null ? nomUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClientePK)) {
            return false;
        }
        ClientePK other = (ClientePK) object;
        if (this.idC != other.idC) {
            return false;
        }
        if ((this.nomUsuario == null && other.nomUsuario != null) || (this.nomUsuario != null && !this.nomUsuario.equals(other.nomUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "slr.epoo.web.mdl.ClientePK[ idC=" + idC + ", nomUsuario=" + nomUsuario + " ]";
    }

}

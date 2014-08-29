
package slr.epoo.web.mdl;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nom_usuario"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByIdC", query = "SELECT c FROM Cliente c WHERE c.clientePK.idC = :idC"),
    @NamedQuery(name = "Cliente.findByNomUsuario", query = "SELECT c FROM Cliente c WHERE c.clientePK.nomUsuario = :nomUsuario"),
    @NamedQuery(name = "Cliente.findByNombre", query = "SELECT c FROM Cliente c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Cliente.findByPassword", query = "SELECT c FROM Cliente c WHERE c.password = :password")})
public class Cliente implements Serializable{
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ClientePK clientePK;
    @Size(max = 40)
    @Column(length = 40)
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(nullable = false, length = 40)
    private String password;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, mappedBy = "idC")
    private List<Venta> ventaList;

    public Cliente(){
    }

    public Cliente(ClientePK clientePK){
        this.clientePK = clientePK;
    }

    public Cliente(ClientePK clientePK, String password){
        this.clientePK = clientePK;
        this.password = password;
    }

    public Cliente(ClientePK clientePK, String nombre, String password){
        this.clientePK = clientePK;
        this.nombre = nombre;
        this.password = password;
    }

    public Cliente(int idC, String nomUsuario){
        this.clientePK = new ClientePK(idC, nomUsuario);
    }

    public ClientePK getClientePK(){
        return clientePK;
    }

    public void setClientePK(ClientePK clientePK){
        this.clientePK = clientePK;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    @XmlTransient
    @JsonIgnore
    public List<Venta> getVentaList(){
        return ventaList;
    }

    public void setVentaList(List<Venta> ventaList){
        this.ventaList = ventaList;
    }

    @Override
    public int hashCode(){
        int hash = 0;
        hash += (clientePK != null ? clientePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object){
        // TODO: Warning - this method won't work in the case the id fields are not set
        if(!(object instanceof Cliente)){
            return false;
        }
        Cliente other = (Cliente)object;
        if((this.clientePK == null && other.clientePK != null) || (this.clientePK != null && !this.clientePK.equals(other.clientePK))){
            return false;
        }
        return true;
    }

    @Override
    public String toString(){
        return "slr.epoo.web.mdl.Cliente[ clientePK=" + clientePK + " ]";
    }

}

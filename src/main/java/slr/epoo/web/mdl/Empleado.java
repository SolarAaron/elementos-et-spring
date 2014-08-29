
package slr.epoo.web.mdl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author Aaron Torres <solaraaron@gmail.com>
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e"),
    @NamedQuery(name = "Empleado.findByIdE", query = "SELECT e FROM Empleado e WHERE e.idE = :idE"),
    @NamedQuery(name = "Empleado.findByNombre", query = "SELECT e FROM Empleado e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Empleado.findBySalario", query = "SELECT e FROM Empleado e WHERE e.salario = :salario"),
    @NamedQuery(name = "Empleado.findByPassword", query = "SELECT e FROM Empleado e WHERE e.password = :password")})
public class Empleado implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_e", nullable = false)
    private Integer idE;
    @Size(max = 40)
    @Column(length = 40)
    private String nombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 10, scale = 2)
    private BigDecimal salario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(nullable = false, length = 40)
    private String password;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, mappedBy = "idE")
    private List<Venta> ventaList;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, mappedBy = "idE")
    private Nomina nomina;

    public Empleado(){
    }

    public Empleado(Integer idE){
        this.idE = idE;
    }

    public Empleado(Integer idE, String password){
        this.idE = idE;
        this.password = password;
    }

    public Empleado(Integer idE, String nombre, BigDecimal salario, String password){
        this.idE = idE;
        this.nombre = nombre;
        this.salario = salario;
        this.password = password;
    }

    public Integer getIdE(){
        return idE;
    }

    public void setIdE(Integer idE){
        this.idE = idE;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public BigDecimal getSalario(){
        return salario;
    }

    public void setSalario(BigDecimal salario){
        this.salario = salario;
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

    public Nomina getNomina(){
        return nomina;
    }

    public void setNomina(Nomina nomina){
        this.nomina = nomina;
    }

    @Override
    public int hashCode(){
        int hash = 0;
        hash += (idE != null ? idE.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object){
        // TODO: Warning - this method won't work in the case the id fields are not set
        if(!(object instanceof Empleado)){
            return false;
        }
        Empleado other = (Empleado)object;
        if((this.idE == null && other.idE != null) || (this.idE != null && !this.idE.equals(other.idE))){
            return false;
        }
        return true;
    }

    @Override
    public String toString(){
        return "slr.epoo.web.mdl.Empleado[ idE=" + idE + " ]";
    }

}

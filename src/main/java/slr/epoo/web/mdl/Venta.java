
package slr.epoo.web.mdl;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author Aaron Torres <solaraaron@gmail.com>
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Venta.findAll", query = "SELECT v FROM Venta v"),
    @NamedQuery(name = "Venta.findByIdV", query = "SELECT v FROM Venta v WHERE v.idV = :idV"),
    @NamedQuery(name = "Venta.findByFecha", query = "SELECT v FROM Venta v WHERE v.fecha = :fecha"),
    @NamedQuery(name = "Venta.findByStatus", query = "SELECT v FROM Venta v WHERE v.status = :status")})
public class Venta implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_v", nullable = false)
    private Integer idV;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Timestamp fecha;
    private Integer status;
    @JoinColumn(name = "id_e", referencedColumnName = "id_e", nullable = false)
    @ManyToOne(optional = false)
    private Empleado idE;
    @JoinColumns({
        @JoinColumn(name = "id_c", referencedColumnName = "id_c", nullable = false),
        @JoinColumn(name = "nom_usuario", referencedColumnName = "nom_usuario", nullable = false)})
    @ManyToOne(optional = false)
    private Cliente idC;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, mappedBy = "venta")
    private List<DetalleVenta> detalleVentaList;

    public Venta(){

    }

    public Venta(Integer idV){

        this.idV = idV;
    }

    public Venta(Integer idV, Timestamp fecha){
        this.idV = idV;
        this.fecha = fecha;
    }

    public Venta(Integer idV, Empleado idE, Cliente idC){

        this.idV = idV;
        this.idE = idE;
        this.idC = idC;
    }

    public Integer getIdV(){
        return idV;
    }

    public void setIdV(Integer idV){
        this.idV = idV;
    }

    public Timestamp getFecha(){
        return fecha;
    }

    public void setFecha(Timestamp fecha){
        this.fecha = fecha;
    }

    public Integer getStatus(){
        return status;
    }

    public void setStatus(Integer status){
        this.status = status;
    }

    public Empleado getIdE(){
        return idE;
    }

    public void setIdE(Empleado idE){
        this.idE = idE;
    }

    public Cliente getIdC(){
        return idC;
    }

    public void setIdC(Cliente idC){
        this.idC = idC;
    }

    public List<DetalleVenta> getDetalleVentaList(){
        return detalleVentaList;
    }

    public void setDetalleVentaList(List<DetalleVenta> detalleVentaList){
        this.detalleVentaList = detalleVentaList;
    }

    @Override
    public int hashCode(){
        int hash = 0;
        hash += (idV != null ? idV.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object){
        // TODO: Warning - this method won't work in the case the id fields are not set
        if(!(object instanceof Venta)){
            return false;
        }
        Venta other = (Venta)object;
        if((this.idV == null && other.idV != null) || (this.idV != null && !this.idV.equals(other.idV))){
            return false;
        }
        return true;
    }

    @Override
    public String toString(){
        return "slr.epoo.web.mdl.Venta[ idV=" + idV + " ]";
    }

}

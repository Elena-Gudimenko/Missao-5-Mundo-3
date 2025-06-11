package model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;

@Entity
@Table(name = "PessoaJuridica")
@NamedQueries({
    @NamedQuery(name = "PessoaJuridica.findAll", query = "SELECT p FROM PessoaJuridica p"),
    @NamedQuery(name = "PessoaJuridica.findById", query = "SELECT p FROM PessoaJuridica p WHERE p.id = :id"),
    @NamedQuery(name = "PessoaJuridica.findByRazaoSocial", query = "SELECT p FROM PessoaJuridica p WHERE p.razaoSocial = :razaoSocial"),
    @NamedQuery(name = "PessoaJuridica.findByCnpj", query = "SELECT p FROM PessoaJuridica p WHERE p.cnpj = :cnpj"),
    @NamedQuery(name = "PessoaJuridica.findByEndereco", query = "SELECT p FROM PessoaJuridica p WHERE p.endereco = :endereco"),
    @NamedQuery(name = "PessoaJuridica.findByTelefone", query = "SELECT p FROM PessoaJuridica p WHERE p.telefone = :telefone"),
    @NamedQuery(name = "PessoaJuridica.findByEmail", query = "SELECT p FROM PessoaJuridica p WHERE p.email = :email")
})
public class PessoaJuridica extends Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "razao_social")
    private String razaoSocial;

    @Basic(optional = false)
    @Column(name = "cnpj")
    private String cnpj;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "email")
    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPessoaJuridica")
    private Collection<Compra> compraCollection;

    public PessoaJuridica() {
    }

    public PessoaJuridica(Integer id) {
        this.id = id;  // id унаследован из Pessoa
    }

    public PessoaJuridica(Integer id, String razaoSocial, String cnpj) {
        this.id = id;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
    }

}

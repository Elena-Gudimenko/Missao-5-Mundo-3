package model;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Compra;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-06-10T00:58:30", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(PessoaJuridica.class)
public class PessoaJuridica_ { 

    public static volatile SingularAttribute<PessoaJuridica, String> telefone;
    public static volatile SingularAttribute<PessoaJuridica, String> endereco;
    public static volatile CollectionAttribute<PessoaJuridica, Compra> compraCollection;
    public static volatile SingularAttribute<PessoaJuridica, Integer> id;
    public static volatile SingularAttribute<PessoaJuridica, String> cnpj;
    public static volatile SingularAttribute<PessoaJuridica, String> razaoSocial;
    public static volatile SingularAttribute<PessoaJuridica, String> email;

}
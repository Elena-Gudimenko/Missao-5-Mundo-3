package model;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Venda;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-06-10T12:35:50", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(PessoaFisica.class)
public class PessoaFisica_ { 

    public static volatile SingularAttribute<PessoaFisica, String> telefone;
    public static volatile SingularAttribute<PessoaFisica, String> endereco;
    public static volatile SingularAttribute<PessoaFisica, String> cpf;
    public static volatile SingularAttribute<PessoaFisica, String> nome;
    public static volatile SingularAttribute<PessoaFisica, Integer> id;
    public static volatile SingularAttribute<PessoaFisica, String> email;
    public static volatile CollectionAttribute<PessoaFisica, Venda> vendaCollection;

}
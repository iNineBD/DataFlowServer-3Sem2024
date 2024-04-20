package com.dataflow.apidomrock.repository;

import com.dataflow.apidomrock.entities.database.Arquivo;
import com.dataflow.apidomrock.entities.database.Organizacao;
import com.dataflow.apidomrock.entities.database.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArquivoRepository extends JpaRepository<Arquivo, Integer> {
    @Query(value = "SELECT a FROM Arquivo a where a.nomeArquivo = :nomeArquivo and a.organizacao.cnpj = :cnpjOrg")
    Optional<Arquivo> findByNameAndOrganization(String nomeArquivo, String cnpjOrg);

    @Query("select a from Arquivo a where a.organizacao.nome = :organizacao and a.isAtivo = true")
    List<Arquivo> findByOrganizacao(String organizacao);

    @Query("select a from Arquivo a where a.isAtivo = true")
    List<Arquivo> findArquivoByAtivo();

}

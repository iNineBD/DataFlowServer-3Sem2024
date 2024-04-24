package com.dataflow.apidomrock.repository;

import com.dataflow.apidomrock.entities.database.Arquivo;
import com.dataflow.apidomrock.entities.database.Metadata;
import com.dataflow.apidomrock.entities.database.Organizacao;
import com.dataflow.apidomrock.entities.database.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArquivoRepository extends JpaRepository<Arquivo, Integer> {
    @Query(value = "SELECT a FROM Arquivo a where a.nomeArquivo = :nomeArquivo and a.organizacao.cnpj = :cnpjOrg")
    Optional<Arquivo> findByNameAndOrganization(String nomeArquivo, String cnpjOrg);

    @Query(value = "SELECT a FROM Arquivo a where a.nomeArquivo = :nomeArquivo and a.organizacao.nome = :orgName")
    Optional<Arquivo> findByNameAndOrganizationName(String nomeArquivo, String orgName);

    @Query("select a from Arquivo a where a.organizacao.nome = :organizacao and a.isAtivo = true")
    List<Arquivo> findByOrganizacao(String organizacao);

    @Query("select a from Arquivo a where a.isAtivo = true")
    List<Arquivo> findArquivoByAtivo();

    @Query("select a from Arquivo a where a.nomeArquivo = :nomeArquivo")
    Arquivo findByNomeArquivo(String nomeArquivo);

    @Transactional
    @Modifying
    @Query(value = "insert into hash(id_arquivo,id_metadado) values (?1,?2)", nativeQuery = true)
    void saveHash(int idArquivo, int idMetadado);

    @Query("select a.hash from Arquivo a where a.id = :idArquivo")
    List<Metadata> findByMetadataHash(int idArquivo);

    @Modifying
    @Transactional
    @Query(value = "delete from hash h where h.id_arquivo = ?1", nativeQuery = true)
    void deleteHash(int idArquivo);

    @Query("select l.observacao from Log l where l.arquivo.id = :idArquivo and l.arquivo.status = 'NÃO APROVADO PELA SILVER'")
    String findObservacao(int idArquivo);
}

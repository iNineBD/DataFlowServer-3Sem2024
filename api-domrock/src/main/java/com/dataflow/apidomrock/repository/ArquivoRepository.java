package com.dataflow.apidomrock.repository;

import com.dataflow.apidomrock.entities.database.Arquivo;
import com.dataflow.apidomrock.entities.database.Metadata;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArquivoRepository extends JpaRepository<Arquivo, Integer> {
    @Query(value = "SELECT a FROM Arquivo a where a.nomeArquivo = :nomeArquivo and a.organizacao.cnpj = :cnpjOrg and a.isAtivo = true")
    Optional<Arquivo> findByNameAndOrganization(String nomeArquivo, String cnpjOrg);

    List<Arquivo> findAllByNomeArquivo(String nomeArquivo);

    @Query(value = "SELECT a FROM Arquivo a where a.nomeArquivo = :nomeArquivo and a.organizacao.nome = :orgName")
    Optional<Arquivo> findByNameAndOrganizationName(String nomeArquivo, String orgName);

    @Query("select a from Arquivo a where a.organizacao.cnpj = :cnpj and a.isAtivo = true")
    List<Arquivo> findByOrganizacao(String cnpj);

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

    @Query("select l.observacao from Log l  where l.arquivo.id = :idArquivo and l.arquivo.status = 'Não aprovado pela Silver' and l.acao = 'REPROVAR' order by l.dataHora desc limit 1")
    String findObservacao(int idArquivo);

    @Query("select a from Arquivo a where Arquivo.organizacao.cnpj = :cnpj and Arquivo.isAtivo = true")
    List<Arquivo> findAllFiles(String cnpj);
}

package med.voll.api.repository;

import java.time.LocalDateTime;
import med.voll.api.enums.Especialidade;
import med.voll.api.jpa.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findAllByStatusTrue(Pageable paginacao);

    @Query("""
            select m from Medico m
            where
            m.status = true
            and
            m.especialidade = :especialidade
            and
            m.id not in(
                select c.medico.id from Consulta c
                where
                c.data = :data
            and
                c.motivoCancelamento is null    
            )
            order by rand()
            limit 1
            """)
    Medico escolherMedicoDisponivelNaData(Especialidade especialidade, LocalDateTime data);

    @Query("""
            select m.status
            from Medico m
            where
            m.id = :idMedico
            """)
    Boolean findStatusById(Long idMedico);
}

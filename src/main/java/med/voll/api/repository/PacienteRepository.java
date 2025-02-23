package med.voll.api.repository;

import med.voll.api.jpa.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Page<Paciente> findAllByStatusTrue(Pageable paginacao);

    @Query("""
            select p.status
            from Paciente p
            where
            p.id = :id
            """)
    boolean findStatusById(Long id);
}

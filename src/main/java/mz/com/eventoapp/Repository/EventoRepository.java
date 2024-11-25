package mz.com.eventoapp.Repository;

import mz.com.eventoapp.Model.Evento;
import org.springframework.data.repository.CrudRepository;

public interface EventoRepository extends CrudRepository<Evento, Long> {

    Evento findByCodigo(Long codigo);
}

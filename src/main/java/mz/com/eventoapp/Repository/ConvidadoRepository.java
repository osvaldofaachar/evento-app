package mz.com.eventoapp.Repository;

import mz.com.eventoapp.Model.Convidado;
import mz.com.eventoapp.Model.Evento;
import org.springframework.data.repository.CrudRepository;

public interface ConvidadoRepository  extends CrudRepository<Convidado, String> {

    Iterable<Convidado> findByEvento(Evento evento);

    Convidado findByChave(String chave);
}

package mz.com.eventoapp.Controller;

import jakarta.validation.Valid;
import mz.com.eventoapp.Model.Convidado;
import mz.com.eventoapp.Model.Evento;
import mz.com.eventoapp.Repository.ConvidadoRepository;
import mz.com.eventoapp.Repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private ConvidadoRepository convidadoRepository;

    @RequestMapping(value="/cadastrarEvento", method = RequestMethod.GET)
    public String mostrarFormulario(){
        return "evento/formEvento";
    }

    @RequestMapping(value="/cadastrarEvento", method = RequestMethod.POST)
    public String cadastarEvento(@Valid Evento evento, BindingResult result, RedirectAttributes attributes){
       if(result.hasErrors()){
            attributes.addFlashAttribute("mensagem", "Preencha os campos correctamente!");
            return "redirect:/cadastrarEvento";
        }
        eventoRepository.save(evento);
        attributes.addFlashAttribute("mensagem", "Evento Salvo Com Sucesso!");

        return "redirect:/cadastrarEvento";
    }

    @RequestMapping("/eventos")
    public ModelAndView listaEventos(){
        ModelAndView mv = new ModelAndView("index");
        Iterable<Evento> eventos = eventoRepository.findAll();
        mv.addObject("eventos", eventos);
        return mv;
    }

    @RequestMapping(value="/{codigo}", method = RequestMethod.GET)
    public ModelAndView detalhesEvento(@PathVariable("codigo") Long codigo){

        Evento evento = eventoRepository.findByCodigo(codigo);
        ModelAndView mv = new ModelAndView("evento/detalhesEvento");
        mv.addObject("evento", evento);

        Iterable<Convidado> convidados = convidadoRepository.findByEvento(evento);
        mv.addObject("convidados", convidados);
        return mv;
    }

    @RequestMapping("/deletarEvento")
    public String deletarEvento(Long codigo){
        Evento evento = eventoRepository.findByCodigo(codigo);
        eventoRepository.delete(evento);
        return "redirect:/eventos";
    }

    @RequestMapping("/deletarConvidado")
    public String deletarConvidado(String chave){
        Convidado convidado = convidadoRepository.findByChave(chave);
        convidadoRepository.delete(convidado);
        Evento evento = convidado.getEvento();
        Long codigoEve = evento.getCodigo();
        String codigo = "" + codigoEve;
        return "redirect:/" + codigo;
    }

    @RequestMapping(value="/{codigo}", method = RequestMethod.POST)
    public String detalhesEventoUser(@PathVariable("codigo") Long codigo, @Valid Convidado convidado, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            attributes.addFlashAttribute("mensagem", "Preencha os campos correctamente!");
            return "redirect:/{codigo}";
        }
        Evento evento = eventoRepository.findByCodigo(codigo);
        convidado.setEvento(evento);
        convidadoRepository.save(convidado);
        attributes.addFlashAttribute("mensagem", "Convidado Salvo Com Sucesso!");
        return "redirect:/{codigo}";
    }






}

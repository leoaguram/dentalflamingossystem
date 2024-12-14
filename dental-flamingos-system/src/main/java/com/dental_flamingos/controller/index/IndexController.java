package com.dental_flamingos.controller.index;

import com.dental_flamingos.model.Cita;
import com.dental_flamingos.model.Dentista;
import com.dental_flamingos.service.cita.CitaService;
import com.dental_flamingos.service.dentista.DentistaService;
import com.dental_flamingos.service.paciente.PacienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    PacienteService pacienteService;

    @Autowired
    CitaService citaService;

    @Autowired
    DentistaService dentistaService;

    @GetMapping("/")
    public String summaryInfo(Model model) {
        Dentista dentistaActivo = dentistaService.getDentistaActivo();
        logger.info("Se inició sesión por: " + dentistaActivo);

        List<Cita> citasDeHoy = citaService.getCitasHoy(LocalDate.now(), dentistaActivo.getIdDentista());

        model.addAttribute("totalpacientes", (long) pacienteService.getPacientesPorDentista(
                dentistaActivo.getIdDentista()
        ).size());

        model.addAttribute("citas", citasDeHoy);
        model.addAttribute("totalcitas", citasDeHoy.size());
        model.addAttribute("dentista", "Dra. "+dentistaActivo.getNombre());
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}

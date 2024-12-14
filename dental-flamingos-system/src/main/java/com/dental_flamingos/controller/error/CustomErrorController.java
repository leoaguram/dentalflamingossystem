package com.dental_flamingos.controller.error;

import com.dental_flamingos.model.Dentista;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class CustomErrorController implements ErrorController {
    private static final Logger logger = LoggerFactory.getLogger(Dentista.class);

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorMessage = "Ocurrió un error inesperado.";

        if (statusCode != null) {
            switch (statusCode) {
                case 400:
                    errorMessage = "Solicitud incorrecta.";
                    break;
                case 404:
                    errorMessage = "Recurso no encontrado.";
                    break;
                case 500:
                    errorMessage = "Error interno del servidor.";
                    break;
                default:
                    errorMessage = "Error desconocido.";
            }
            logger.info("Se generó un error en el request HTTP error: "+statusCode.intValue());
        }

        model.addAttribute("statusCode", statusCode);
        model.addAttribute("errorMessage", errorMessage);
        return "error-page";
    }
}

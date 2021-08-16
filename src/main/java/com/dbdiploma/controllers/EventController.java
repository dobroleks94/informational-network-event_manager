package com.dbdiploma.controllers;

import com.dbdiploma.entities.ErrorEvent;
import com.dbdiploma.entities.ErrorEventDTO;
import com.dbdiploma.entities.EventDedication;
import com.dbdiploma.services.EventService;
import com.dbdiploma.services.HostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/events")
public class EventController {

    private final EventService eventService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String eventPage(Model model){
        ErrorEventDTO errorEventDTO = model.getAttribute("errorEvent") == null ? new ErrorEventDTO() : (ErrorEventDTO) model.getAttribute("errorEvent");
        model.addAttribute("errorEvent", errorEventDTO);
        model.addAttribute("host", HostService.getCurrentlyEdit());
        return "eventEditPage";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addEvent(@ModelAttribute("errorEvent") ErrorEventDTO errorEventDTO, RedirectAttributes model){
        ErrorEvent errorEvent = new ErrorEvent();
        errorEvent.setEventName(errorEventDTO.getEventName());
        errorEvent.setDescription(errorEventDTO.getDescription());
        errorEvent.setEventDedication(errorEventDTO.getEventDedication());
        if(errorEvent.getEventDedication().equals(EventDedication.HARDWARE_EVENT)) {
            if (errorEventDTO.getHardwareUUID() == null) {
                model.addFlashAttribute("errorEvent", errorEventDTO);
                return "redirect:/events/add?error=true";
            }
            HostService.setCurrentlyEdit(eventService.updateHostHardwareForEvent(
                    errorEvent,
                    errorEventDTO.getHardwareUUID(),
                    HostService.getCurrentlyEdit()));
        }
        else {
            if (errorEventDTO.getSoftwareUUID() == null) {
                model.addFlashAttribute("errorEvent", errorEventDTO);
                return "redirect:/events/add?error=true";
            }
            HostService.setCurrentlyEdit(eventService.updateHostSoftwareForEvent(
                    errorEvent,
                    errorEventDTO.getSoftwareUUID(),
                    HostService.getCurrentlyEdit()));
        }
        return "redirect:/hosts/edit/" + HostService.getCurrentlyEdit().getUuid();
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getAllEvents(Model model){
        model.addAttribute("events", eventService.getAllEvents());
        return "eventPage";
    }
}
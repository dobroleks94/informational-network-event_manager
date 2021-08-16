package com.dbdiploma.controllers;

import com.dbdiploma.entities.InfoDTO;
import com.dbdiploma.entities.User;
import com.dbdiploma.services.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@AllArgsConstructor
public class MainController {

    private final HostService hostService;
    private final UserService userService;
    private final HardwareService hardwareService;
    private final SoftwareService softwareService;
    private final EventService eventService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homePage(Model model){
        long hostCount = hostService.getHostsCount();
        long hardwareCount = hardwareService.getHardwareCount();
        long softwareCount = softwareService.getSoftwareCount();
        long errorEventsCount = eventService.getEventsCount();
        InfoDTO infoDTO = InfoDTO.create(hostCount, hardwareCount, softwareCount, errorEventsCount);
        model.addAttribute("infoDTO", infoDTO);
        return "index";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerPage(Model model){
        model.addAttribute("user", new User());
        return "sign-up";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute User user){
        userService.save(user);
        System.out.println(user.getPassword());
        return "redirect:/login";
    }
}

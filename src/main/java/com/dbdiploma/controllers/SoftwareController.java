package com.dbdiploma.controllers;

import com.dbdiploma.entities.Software;
import com.dbdiploma.entities.User;
import com.dbdiploma.security.CurrentUser;
import com.dbdiploma.security.Principal;
import com.dbdiploma.services.HostService;
import com.dbdiploma.services.SoftwareService;
import com.dbdiploma.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/software")
public class SoftwareController {

    private final SoftwareService softwareService;
    private final UserService userService;
    private final HostService hostService;

    @RequestMapping(value = "/edit/{softwareUUID}", method = RequestMethod.GET)
    public String softwarePage(@PathVariable String softwareUUID,
                               @RequestParam(value = "edit", required = false) boolean edit,
                               @ModelAttribute Software software,
                               Model model){
        model.addAttribute("software", softwareService.getSoftwareByUUID(softwareUUID).orElse(software));
        model.addAttribute("edit", edit);
        return "softwareEditPage";
    }

    @RequestMapping(value = "/edit/{softwareUUID}", method = RequestMethod.POST)
    public String addSoftware(@PathVariable String softwareUUID,
                              @RequestParam(value = "edit", required = false) boolean edit,
                              @ModelAttribute Software software){

        if (edit)
            software = softwareService.updateSoftware(software,
                        HostService.getCurrentlyEdit().getSoftwareList().stream().filter(sftw -> sftw.getUuid().equals(softwareUUID)).findFirst().get());

        HostService.getCurrentlyEdit().getSoftwareList().removeIf(hrdw -> hrdw.getUuid().equals(softwareUUID));
        HostService.getCurrentlyEdit().getSoftwareList().add(softwareService.save(software));

        return "redirect:/hosts/edit/" + HostService.getCurrentlyEdit().getUuid();
    }

    @RequestMapping(value = "/detach/{softwareUUID}", method = RequestMethod.GET)
    public String detachSoftware(@PathVariable String softwareUUID,
                                 @ModelAttribute("software") Software sftwr){
        Software software = softwareService.getSoftwareByUUID(softwareUUID).orElse(sftwr);
        if(!hostService.getHostByUUID(HostService.getCurrentlyEdit().getUuid())
                .getSoftwareList().stream()
                .map(Software::getUuid)
                .collect(Collectors.toList())
                .contains(softwareUUID))
            softwareService.deleteById(software.getId());
        HostService.getCurrentlyEdit().getSoftwareList().removeIf(hardw -> hardw.getUuid().equals(softwareUUID));
        return "redirect:/hosts/edit/" + HostService.getCurrentlyEdit().getUuid();
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String showHostTable(@CurrentUser Principal principal,
                                Model model){

        List<Software> userSoftware = softwareService.getAllSoftware().stream()
                .filter(software -> {
                    if(software.getOwnerHost() != null)
                        return software.getOwnerHost().getUsers().stream()
                                .map(User::getEmail)
                                .collect(Collectors.toList())
                                .contains(principal.getEmail());
                    return false;
                })
                .collect(Collectors.toList());

        model.addAttribute("allSoftware", softwareService.getAllSoftware());
        model.addAttribute("userSoftware", userSoftware);
        model.addAttribute("user", userService.findByEmail(principal.getEmail()).get());

        return "softwarePage";
    }
}

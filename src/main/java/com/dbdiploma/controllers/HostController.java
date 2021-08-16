package com.dbdiploma.controllers;

import com.dbdiploma.entities.*;
import com.dbdiploma.security.CurrentUser;
import com.dbdiploma.security.Principal;
import com.dbdiploma.services.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


@Controller
@AllArgsConstructor
@RequestMapping(value = "/hosts")
public class HostController {

    private final HostService hostService;
    private final UserService userService;
    private final HardwareService hardwareService;
    private final SoftwareService softwareService;
    private final EventService eventService;


    @ModelAttribute("user")
    public User currentUser(@CurrentUser Principal principal){
        return userService.findByEmail(principal.getEmail()).get();
    }


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String showHostTable(@CurrentUser Principal principal,
                                Model model,
                                @RequestParam(required = false) boolean update){
        HostService.setCurrentlyEdit(null);
        model.addAttribute("allHosts", hostService.getAllHosts());
        model.addAttribute("update", update);
        model.addAttribute("user", userService.findByEmail(principal.getEmail()).get());
        return "hostPage";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addHost(@CurrentUser Principal principal){
        return "redirect:/hosts/edit/" + hostService.save(new Host()).getUuid();
    }

    @RequestMapping(value = "/delete/{hostUUID}", method = RequestMethod.GET)
    public String deleteHost(@PathVariable String hostUUID){
        Host host = hostService.prepareToDelete(hostService.getHostByUUID(hostUUID));
        hostService.deleteHostByUUID(
                hostService.save(host).getUuid()
        );
        return "redirect:/hosts/all?update=true";
    }
    @RequestMapping(value = "/detach/{hostUUID}", method = RequestMethod.GET)
    public String detachHost(@PathVariable String hostUUID,
                             @CurrentUser Principal principal){
        User current = userService.findByEmail(principal.getEmail()).get();
        Host explored = hostService.getHostByUUID(hostUUID);

        current.getHosts().removeIf(host -> host.getId().equals(explored.getId()));
        explored.getUsers().removeIf(user -> user.getId().equals(current.getId()));

        hostService.save(explored);
        userService.update(current);

        return "redirect:/hosts/all";
    }

    @RequestMapping(value = "/attach/{hostUUID}", method = RequestMethod.GET)
    public String attachHost(@PathVariable String hostUUID,
                             @CurrentUser Principal principal){

        User current = userService.findByEmail(principal.getEmail()).get();
        Host explored = hostService.getHostByUUID(hostUUID);

        current.getHosts().add(explored);
        explored.getUsers().add(current);

        hostService.save(explored);
        userService.update(current);

        return "redirect:/hosts/all";
    }

    @RequestMapping(value = "/edit/{hostUUID}", method = RequestMethod.GET)
    public String editHost(@PathVariable String hostUUID,
                           @ModelAttribute("host") Host host,
                           @CurrentUser Principal principal,
                           Model model){

        if(HostService.getCurrentlyEdit() == null || !HostService.getCurrentlyEdit().getUuid().equals(hostUUID))
            HostService.setCurrentlyEdit(hostService.getHostByUUID(hostUUID));

        model.addAttribute("host", HostService.getCurrentlyEdit());
        model.addAttribute("user", userService.findByEmail(principal.getEmail()).get());
        return "hostEditPage";
    }

    @RequestMapping(value = "/edit/{hostUUID}", method = RequestMethod.POST)
    @Transactional
    public String updateHost(@PathVariable String hostUUID,
                             @ModelAttribute("host") Host host){

        host.setUsers(HostService.getCurrentlyEdit().getUsers());
        host.setSoftwareList(HostService.getCurrentlyEdit().getSoftwareList());
        host.setHardwareList(HostService.getCurrentlyEdit().getHardwareList());
        hostService.save(host);
        return "redirect:/hosts/all?update=true";
    }

    @RequestMapping(value = "/new/hardware", method = RequestMethod.GET)
    public String addNewHardware(RedirectAttributes model){
        Hardware hardware = new Hardware();
        hardware.setOwnerHost(HostService.getCurrentlyEdit());
        model.addFlashAttribute("hardware", hardware);
        return "redirect:/hardware/edit/" + hardware.getUuid() + "?edit=false";
    }
    @RequestMapping(value = "/new/software", method = RequestMethod.GET)
    public String addNewSoftware(RedirectAttributes model){
        Software software = new Software();
        software.setOwnerHost(HostService.getCurrentlyEdit());
        model.addFlashAttribute("software", software);
        return "redirect:/software/edit/" + software.getUuid() + "?edit=false";
    }
    @RequestMapping(value = "/new/event", method = RequestMethod.GET)
    public String addNewEvent(){
        return "redirect:/events/add";
    }
}

package com.dbdiploma.controllers;

import com.dbdiploma.entities.Hardware;
import com.dbdiploma.entities.Host;
import com.dbdiploma.entities.User;
import com.dbdiploma.security.CurrentUser;
import com.dbdiploma.security.Principal;
import com.dbdiploma.services.HardwareService;
import com.dbdiploma.services.HostService;
import com.dbdiploma.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/hardware")
public class HardwareController {

    private final HardwareService hardwareService;
    private final HostService hostService;
    private final UserService userService;

    @RequestMapping(value = "/edit/{hardwareUUID}", method = RequestMethod.GET)
    public String hardwarePage(@PathVariable String hardwareUUID,
                               @RequestParam(value = "edit", required = false) boolean edit,
                               @ModelAttribute Hardware hardware,
                               Model model){
        model.addAttribute("hardware", hardwareService.getHardwareByUUID(hardwareUUID).orElse(hardware));
        model.addAttribute("edit", edit);
        return "hardwareEditPage";
    }

    @RequestMapping(value = "/edit/{hardwareUUID}", method = RequestMethod.POST)
    public String addHardware(@PathVariable String hardwareUUID,
                              @RequestParam(value = "edit", required = false) boolean edit,
                              @ModelAttribute Hardware hardware){
        if (edit)
            hardware = hardwareService.updateHardware(hardware,
                    HostService.getCurrentlyEdit().getHardwareList().stream().filter(hrdw -> hrdw.getUuid().equals(hardwareUUID)).findFirst().get());

        HostService.getCurrentlyEdit().getHardwareList().removeIf(hrdw -> hrdw.getUuid().equals(hardwareUUID));
        HostService.getCurrentlyEdit().getHardwareList().add(hardwareService.save(hardware));

        return "redirect:/hosts/edit/" + HostService.getCurrentlyEdit().getUuid();
    }

    @RequestMapping(value = "/detach/{hardwareUUID}", method = RequestMethod.GET)
    public String detachHardware(@PathVariable String hardwareUUID,
                                 @ModelAttribute("hardware") Hardware hrdwr){

        Hardware hardware = hardwareService.getHardwareByUUID(hardwareUUID).orElse(hrdwr);
        if(!hostService.getHostByUUID(HostService.getCurrentlyEdit().getUuid())
                            .getHardwareList().stream()
                            .map(Hardware::getUuid)
                            .collect(Collectors.toList())
                            .contains(hardwareUUID))
            hardwareService.deleteById(hardware.getId());
        HostService.getCurrentlyEdit().getHardwareList().removeIf(hardw -> hardw.getUuid().equals(hardwareUUID));
        return "redirect:/hosts/edit/" + HostService.getCurrentlyEdit().getUuid();
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String showHostTable(@CurrentUser Principal principal,
                                Model model){

        List<Hardware> userHardware = hardwareService.getAllHardware().stream()
                .filter(hardware -> {
                    if(hardware.getOwnerHost() != null)
                        return hardware.getOwnerHost().getUsers().stream()
                                .map(User::getEmail)
                                .collect(Collectors.toList())
                                .contains(principal.getEmail());
                    return false;
                })
                .collect(Collectors.toList());

        model.addAttribute("allHardware", hardwareService.getAllHardware());
        model.addAttribute("userHardware", userHardware);
        model.addAttribute("user", userService.findByEmail(principal.getEmail()).get());

        return "hardwarePage";
    }
}

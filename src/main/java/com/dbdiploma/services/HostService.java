package com.dbdiploma.services;

import com.dbdiploma.entities.Host;
import com.dbdiploma.repositories.HostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class HostService {

    private final HostRepository hostRepository;

    private static Host currentlyEdit;
    public static Host getCurrentlyEdit() {
        return currentlyEdit;
    }
    public static void setCurrentlyEdit(Host host){
        HostService.currentlyEdit = host;
    }
    public List<Host> getAllHosts() {
        return hostRepository.findAll();
    }

    public long getHostsCount() {
        return hostRepository.count();
    }
    @Transactional
    public void deleteHostByUUID(String uuid){
        hostRepository.deleteHostByUuid(uuid);
    }
    public Host getHostByUUID(String hostUUID) {
        return hostRepository.getHostByUuid(hostUUID).orElseThrow(() -> new RuntimeException(String.format("Host %s doesn't exist", hostUUID)));
    }

    public Host save(Host host) {
        return hostRepository.save(host);
    }

    public Host prepareToDelete(Host host) {
        host.getUsers().forEach(user -> user.getHosts().removeIf(userHost -> userHost.getId().equals(host.getId())));
        host.setUsers(Collections.emptyList());
        return host;
    }
}

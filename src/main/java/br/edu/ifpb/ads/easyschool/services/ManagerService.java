package br.edu.ifpb.ads.easyschool.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.ads.easyschool.controllers.dtos.request.manager.ManagerPostRequestDTO;
import br.edu.ifpb.ads.easyschool.controllers.dtos.request.manager.ManagerUpdateRequestDTO;
import br.edu.ifpb.ads.easyschool.controllers.dtos.response.ManagerResponseDTO;
import br.edu.ifpb.ads.easyschool.exception.ManagerAlreadyExistsException;
import br.edu.ifpb.ads.easyschool.exception.ManagerNotFoundException;
import br.edu.ifpb.ads.easyschool.model.Manager;
import br.edu.ifpb.ads.easyschool.repositories.ManagerRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;

    private final ModelMapper mapper;

    @Transactional
    public ManagerResponseDTO createStudent(ManagerPostRequestDTO managerRequestDTO) {

        if (managerRepository.findByEmail(managerRequestDTO.getEmail()).isPresent()) {
            throw new ManagerAlreadyExistsException("Gestor com email " + managerRequestDTO.getEmail() + " já existe");
        }

        Manager createdManager = mapper.map(managerRequestDTO, Manager.class);
        createdManager = managerRepository.save(createdManager);
        createdManager.setPassword(new BCryptPasswordEncoder().encode(createdManager.getPassword()));

        return mapper.map(createdManager, ManagerResponseDTO.class);
    }

    public List<ManagerResponseDTO> findAllManagers() {
        List<Manager> managersList = managerRepository.findAll();

        List<ManagerResponseDTO> managersListDTO = new ArrayList<>();

        for (Manager manager : managersList) {
            managersListDTO.add(mapper.map(manager, ManagerResponseDTO.class));
        }

        return managersListDTO;
    }

    public ManagerResponseDTO findManagerById(Long id) {
        Manager manager = managerRepository.findById(id)
                .orElseThrow(() -> new ManagerNotFoundException("Gestor não encontrado."));
        return mapper.map(manager, ManagerResponseDTO.class);
    }

    public ManagerResponseDTO findManagerByEmail(String email) {
        Manager manager = managerRepository.findByEmail(email)
                .orElseThrow(() -> new ManagerNotFoundException("Gestor não encontrado."));
        return mapper.map(manager, ManagerResponseDTO.class);
    }

    public ManagerResponseDTO updateManagerResponseDTO(Long id, ManagerUpdateRequestDTO managerUpdateRequestDTO) {

        Manager manager = managerRepository.findById(id)
                .orElseThrow(() -> new ManagerNotFoundException("Gestor não encontrado."));

        manager.setEmail(managerUpdateRequestDTO.getEmail());
        manager.setPhoneNumber(managerUpdateRequestDTO.getPhoneNumber());
        Manager updatedManager = managerRepository.save(manager);
        return mapper.map(updatedManager, ManagerResponseDTO.class);

    }

    public void deleteManager(Long id) {
        managerRepository.deleteById(id);
    }

    public ManagerResponseDTO findByUsername(String name) {
        final var manager = managerRepository.findByUsername(name)
                .orElseThrow(() -> new ManagerNotFoundException("Gestor não encontrado."));
        return mapper.map(manager, ManagerResponseDTO.class);
    }

}

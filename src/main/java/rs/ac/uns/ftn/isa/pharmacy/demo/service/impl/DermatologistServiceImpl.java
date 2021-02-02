package rs.ac.uns.ftn.isa.pharmacy.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isa.pharmacy.demo.model.Dermatologist;
import rs.ac.uns.ftn.isa.pharmacy.demo.model.PharmacyAdmin;
import rs.ac.uns.ftn.isa.pharmacy.demo.model.User;
import rs.ac.uns.ftn.isa.pharmacy.demo.repository.DermatologistRepository;
import rs.ac.uns.ftn.isa.pharmacy.demo.service.DermatologistService;

import java.util.List;

@Service
public class DermatologistServiceImpl implements DermatologistService {

    private final DermatologistRepository dermatologistRepository;

    @Autowired
    public DermatologistServiceImpl(DermatologistRepository dermatologistRepository) {
        this.dermatologistRepository = dermatologistRepository;
    }

    @Override
    public List<Dermatologist> getAllDermatologists() {
        if (((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAdministrationRole().equals("ROLE_PHARMACY_ADMINISTRATOR")) {
            PharmacyAdmin pharmacyAdmin = (PharmacyAdmin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return getDermatologistsByPharmacy(pharmacyAdmin.getPharmacy().getName());
        } else {
            return dermatologistRepository.getAllDermatologists();
        }
    }

    public List<Dermatologist> getDermatologistsByPharmacy(String pharmacyName) {
        return dermatologistRepository.getDermatologistsByPharmacy(pharmacyName);
    }
}

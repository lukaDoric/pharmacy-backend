package rs.ac.uns.ftn.isa.pharmacy.demo.service;

import rs.ac.uns.ftn.isa.pharmacy.demo.model.Dermatologist;
import rs.ac.uns.ftn.isa.pharmacy.demo.model.Pharmacist;

import java.util.List;

public interface UserService {
    List<Dermatologist> getAllDermatologists();
    List<Dermatologist> getDermatologistsByPharmacy(String pharmacyName);
    List<Pharmacist> getAllPharmacists();
    List<Pharmacist> getPharmacistsByPharmacy(String pharmacyName);
}
package rs.ac.uns.ftn.isa.pharmacy.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import rs.ac.uns.ftn.isa.pharmacy.demo.helpers.dtoconverters.PharmacistConverter;
import rs.ac.uns.ftn.isa.pharmacy.demo.model.Pharmacist;
import rs.ac.uns.ftn.isa.pharmacy.demo.model.dto.PharmacistDto;
import rs.ac.uns.ftn.isa.pharmacy.demo.service.PharmacistService;

import java.util.List;

@Controller
@RequestMapping(value = "/pharmacist", produces = MediaType.APPLICATION_JSON_VALUE)
public class PharmacistsController implements PharmacistConverter {

    private final PharmacistService pharmacistService;

    @Autowired
    public PharmacistsController(PharmacistService pharmacistService) {
        this.pharmacistService = pharmacistService;
    }

    @GetMapping(value = "/getPharmacistsByPharmacy/{pharmacyId}")
    public ResponseEntity<List<PharmacistDto>> getPharmacistsByPharmacy(@PathVariable Long pharmacyId) {
        List<Pharmacist> pharmacists = pharmacistService.getPharmacistsByPharmacy(pharmacyId);
        return ResponseEntity.ok(createResponse(pharmacists));
    }

    @PreAuthorize("hasAnyRole('ROLE_PATIENT','ROLE_PHARMACIST', 'ROLE_DERMATOLOGIST', 'ROLE_PHARMACY_ADMINISTRATOR', 'ROLE_SUPPLIER')") // NOSONAR the focus of this project is not on web security
    @GetMapping(value = "/getAllPharmacists")
    public ResponseEntity<List<PharmacistDto>> getAllPharmacists() {
        return ResponseEntity.ok(createResponse(pharmacistService.getAllPharmacists()));
    }
}

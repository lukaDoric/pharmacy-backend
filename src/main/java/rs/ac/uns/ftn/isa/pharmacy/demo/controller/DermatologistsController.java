package rs.ac.uns.ftn.isa.pharmacy.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.isa.pharmacy.demo.helpers.dtoconverters.DermatologistConverter;
import rs.ac.uns.ftn.isa.pharmacy.demo.model.Dermatologist;
import rs.ac.uns.ftn.isa.pharmacy.demo.model.dto.DermatologistDto;
import rs.ac.uns.ftn.isa.pharmacy.demo.model.dto.DermatologistShiftDto;
import rs.ac.uns.ftn.isa.pharmacy.demo.service.DermatologistEmploymentService;
import rs.ac.uns.ftn.isa.pharmacy.demo.service.DermatologistService;

import java.util.List;

@RestController
@RequestMapping(value = "/dermatologist", produces = MediaType.APPLICATION_JSON_VALUE)
public class DermatologistsController implements DermatologistConverter {

    private final DermatologistService dermatologistService;
    private final DermatologistEmploymentService dermatologistEmploymentService;

    @Autowired
    public DermatologistsController(DermatologistService dermatologistService, DermatologistEmploymentService dermatologistEmploymentService) {
        this.dermatologistService = dermatologistService;
        this.dermatologistEmploymentService = dermatologistEmploymentService;
    }

    @GetMapping(value = "/getDermatologistsByPharmacy/{pharmacyName}")
    public ResponseEntity<List<DermatologistDto>> getDermatologistsByPharmacy(@PathVariable String pharmacyName) {
        List<Dermatologist> dermatologists = dermatologistService.getDermatologistsByPharmacy(pharmacyName);
        List<DermatologistDto> dermatologistsDto = createResponse(dermatologists);
        return ResponseEntity.ok(dermatologistsDto);
    }

    @GetMapping(value = "/getAllDermatologists")
    public ResponseEntity<List<DermatologistDto>> getAllDermatologists() {
        List<Dermatologist> dermatologists = dermatologistService.getAllDermatologists();
        return ResponseEntity.ok(createResponse(dermatologists));
    }

    @PreAuthorize("hasRole('ROLE_PHARMACY_ADMINISTRATOR')") // NOSONAR the focus of this project is not on web security
    @GetMapping(value = "/shiftIntervals/{dermatologistId}")
    public ResponseEntity<DermatologistShiftDto> getAllDermatologistShifts(@PathVariable Long dermatologistId) {
        return ResponseEntity.ok(dermatologistEmploymentService.getDermatologistShifts(dermatologistId));
    }
}

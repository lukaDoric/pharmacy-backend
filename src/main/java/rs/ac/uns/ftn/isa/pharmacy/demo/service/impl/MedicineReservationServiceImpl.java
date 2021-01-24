package rs.ac.uns.ftn.isa.pharmacy.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isa.pharmacy.demo.model.*;
import rs.ac.uns.ftn.isa.pharmacy.demo.model.dto.MedicineReservationDto;
import rs.ac.uns.ftn.isa.pharmacy.demo.repository.MedicineRepository;
import rs.ac.uns.ftn.isa.pharmacy.demo.repository.MedicineReservationRepository;
import rs.ac.uns.ftn.isa.pharmacy.demo.repository.PharmacyRepository;
import rs.ac.uns.ftn.isa.pharmacy.demo.repository.UserRepository;
import rs.ac.uns.ftn.isa.pharmacy.demo.service.MedicineReservationService;
import javax.persistence.EntityNotFoundException;
import java.util.Calendar;
import java.util.Optional;

@Service
public class MedicineReservationServiceImpl implements MedicineReservationService {
    private MedicineRepository medicineRepository;
    private MedicineReservationRepository medicineReservationRepository;
    private PharmacyRepository pharmacyRepository;
    private UserRepository userRepository;

    @Autowired
    public MedicineReservationServiceImpl(
            MedicineRepository medicineRepository,
            MedicineReservationRepository medicineReservationRepository,
            PharmacyRepository pharmacyRepository,
            UserRepository userRepository) {
        this.medicineRepository = medicineRepository;
        this.medicineReservationRepository = medicineReservationRepository;
        this.pharmacyRepository = pharmacyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Iterable<Medicine> getAllMedicine() {
        return medicineRepository.findAll();
    }

    @Override
    public Iterable<Pharmacy> getPharmaciesWithMedicineOnStock(Long medicineId) {
        return pharmacyRepository.findWithMedicineOnStock(medicineId);
    }

    @Override
    public boolean isReservationValid(MedicineReservationDto medicineReservationDto) {
        try {
            Medicine medicine = getMedicineById(medicineReservationDto.getMedicineId());
            Pharmacy pharmacy = getPharmacyById(medicineReservationDto.getPharmacyId());
        // TODO: Milica#1
//            if (pharmacy.getMedicineStock().containsKey(medicine)) {
//                return pharmacy.getMedicineStock().get(medicine) > 0;
//            } else {
//                return false;
//            }
            return false;
        } catch (EntityNotFoundException e) {
            return false;
        }
    }

    @Override
    public void confirmReservation(MedicineReservationDto medicineReservationDto) {
        Medicine medicine = getMedicineById(medicineReservationDto.getMedicineId());
        Pharmacy pharmacy = getPharmacyById(medicineReservationDto.getPharmacyId());

        // TODO: get user/userId based on session
        Patient patient = getPatientById(1l);

        Calendar expirationDate = Calendar.getInstance();
        expirationDate.setTime(medicineReservationDto.getExpirationDate());

        MedicineReservation medicineReservation = new MedicineReservation(medicine, patient, expirationDate);
        medicineReservationRepository.save(medicineReservation);

        // TODO: Milica#2
//        pharmacy.getMedicineStock().put(medicine, pharmacy.getMedicineStock().get(medicine) - 1);
//        pharmacyRepository.save(pharmacy);
    }

    private Pharmacy getPharmacyById(Long pharmacyId) throws EntityNotFoundException {
        Optional<Pharmacy> optionalPharmacy = pharmacyRepository.findById(pharmacyId);
        if (optionalPharmacy.isPresent()) {
            return optionalPharmacy.get();
        } else {
            throw new EntityNotFoundException();
        }
    }

    private Medicine getMedicineById(Long medicineId) throws EntityNotFoundException {
        Optional<Medicine> optionalMedicine = medicineRepository.findById(medicineId);
        if (optionalMedicine.isPresent()) {
            return optionalMedicine.get();
        } else {
            throw new EntityNotFoundException();
        }
    }

    private Patient getPatientById(Long patientId) throws EntityNotFoundException, ClassCastException {
        Optional<User> optionalPatient = userRepository.findById(patientId);
        if (optionalPatient.isPresent()) {
            return (Patient) optionalPatient.get();
        } else {
            throw new EntityNotFoundException();
        }
    }
}

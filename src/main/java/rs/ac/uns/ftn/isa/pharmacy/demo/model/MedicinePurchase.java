package rs.ac.uns.ftn.isa.pharmacy.demo.model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "medicine_purchase")
public class MedicinePurchase {

    @Id
    @SequenceGenerator(name = "medicine_purchase_sequence_generator", sequenceName = "medicine_purchase_sequence", initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medicine_purchase_sequence_generator")
    private Long id;

    @ElementCollection
    @CollectionTable(name = "purchase_medicine_mapping", joinColumns = @JoinColumn(name = "purchase_id"))
    @MapKeyJoinColumn(name = "medicine_id", referencedColumnName = "id")
    @Column(name = "medicine_amount")
    private Map<Medicine, Integer> medicineAmount;

    @ManyToOne
    @JoinColumn(name = "pharmacy_id")
    private Pharmacy pharmacy;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(name = "purchase_date")
    private Calendar purchaseDate;

    @Column(name = "price")
    private Long price;

    public MedicinePurchase(Map<Medicine, Integer> medicineAmount, Pharmacy pharmacy, Patient patient, Long price) {
        this.medicineAmount = medicineAmount;
        this.pharmacy = pharmacy;
        this.patient = patient;
        this.purchaseDate = Calendar.getInstance();
        ;
        this.price = price;
    }

    public MedicinePurchase() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<Medicine, Integer> getMedicineAmount() {
        return medicineAmount;
    }

    public void setMedicineAmount(Map<Medicine, Integer> medicineAmount) {
        this.medicineAmount = medicineAmount;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Calendar getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Calendar purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicinePurchase that = (MedicinePurchase) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(medicineAmount, that.medicineAmount) &&
                Objects.equals(pharmacy, that.pharmacy) &&
                Objects.equals(patient, that.patient) &&
                Objects.equals(purchaseDate, that.purchaseDate) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, medicineAmount, pharmacy, patient, purchaseDate, price);
    }
}

package rs.ac.uns.ftn.isa.pharmacy.demo.model.dto;

import java.io.Serializable;
import java.util.List;

public class OrderResponseDto implements Serializable {

    private String deadlineString;
    private List<MedicineAmountDto> medicineAmount;
    private Long adminId;
    private Long id;
    private boolean accepted;

    public OrderResponseDto() {

    }

    public OrderResponseDto(String deadlineString, List<MedicineAmountDto> medicineAmount, Long adminId, Long id) {
        this.deadlineString = deadlineString;
        this.medicineAmount = medicineAmount;
        this.adminId = adminId;
        this.id = id;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public String getDeadlineString() {
        return deadlineString;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDeadlineString(String deadlineString) {
        this.deadlineString = deadlineString;
    }

    public List<MedicineAmountDto> getMedicineAmount() {
        return medicineAmount;
    }

    public void setMedicineAmount(List<MedicineAmountDto> medicineAmount) {
        this.medicineAmount = medicineAmount;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }
}

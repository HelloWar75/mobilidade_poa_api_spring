package br.com.luisjustin.api.mobilidade.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="bus_lines")
public class BusLine extends AuditModel{

    @Id
    @GeneratedValue(generator = "bus_line_generator")
    @SequenceGenerator(
            name = "bus_line_generator",
            sequenceName = "bus_line_sequence",
            initialValue = 1
    )
    private Long id;

    private int lineId;

    @NotBlank
    private String lineCode;

    @NotBlank
    private String lineName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public String getLineCode() {
        return lineCode;
    }

    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }
}

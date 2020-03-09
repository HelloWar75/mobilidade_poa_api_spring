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

    @NotBlank
    private Integer lineId;

    @NotBlank
    private String lineCode;

    @NotBlank
    private String lineName;
}

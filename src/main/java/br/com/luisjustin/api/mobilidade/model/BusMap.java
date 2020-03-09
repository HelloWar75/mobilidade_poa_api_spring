package br.com.luisjustin.api.mobilidade.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "bus_map")
public class BusMap extends AuditModel{

    @Id
    @GeneratedValue(generator = "bus_map_generator")
    @SequenceGenerator(
            name = "bus_map_generator",
            sequenceName = "bus_map_sequence",
            initialValue = 1
    )
    private Long id;

    private Long position;
    private double lat;
    private double lng;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bus_line_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private BusLine busLine;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public BusLine getBusLine() {
        return busLine;
    }

    public void setBusLine(BusLine busLine) {
        this.busLine = busLine;
    }
}

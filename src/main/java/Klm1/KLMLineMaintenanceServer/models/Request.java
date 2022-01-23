package Klm1.KLMLineMaintenanceServer.models;

import Klm1.KLMLineMaintenanceServer.models.helper.PrefixSequenceIDGenerator;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "request")
@NamedQueries({
        @NamedQuery(name = "find_all_requests", query = "SELECT r FROM Request r"),
        @NamedQuery(name= "find_all_requests_group_by_departure", query = "select r from Request r order by r.departure asc"),
        @NamedQuery(name = "find_requests_by_status", query = "SELECT r FROM Request r WHERE r.status = :status"),
        @NamedQuery(name = "change_equipment", query = "update Request r set r.equipment=?1 where r.id=?2")

})
public class Request implements Serializable {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Request_seq")
    @GenericGenerator(name = "Request_seq",
            strategy = "Klm1.KLMLineMaintenanceServer.models.helper.PrefixSequenceIDGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = PrefixSequenceIDGenerator.INCREMENT_PARAM, value = "1"),
                    @org.hibernate.annotations.Parameter(name = PrefixSequenceIDGenerator.VALUE_PREFIX_PARAMETER, value = "GE-"),
                    @org.hibernate.annotations.Parameter(name = PrefixSequenceIDGenerator.NUMBER_FORMAT_PARAMETER, value = "%01d")
            })
    private String id;

    @Column(name = "status")
    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "requestedEquipment")
    private EquipmentType equipmentType;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "aircraftType")
    private Aircraft aircraft;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "requestedLocation")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "usedEquipment")
    private Equipment equipment;

    @Column(name = "timeStamp")
    private Date timeStamp = new Date();

    @Column(name = "departure")
    private Date departure;

    private String amountOfTires;

    public Request() {
    }

    public Request(@NotNull Status status, @NotNull EquipmentType equipmentType, @NotNull Aircraft aircraft, @NotNull Location location, Equipment equipment, Date departure, String amountOfTires) {
        this.status = status;
        this.equipmentType = equipmentType;
        this.aircraft = aircraft;
        this.location = location;
        this.equipment = equipment;
        this.departure = departure;
        this.amountOfTires = amountOfTires;
    }

    public enum Status {
        OP, IP, CAN, CL
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(EquipmentType equipmentType) {
        this.equipmentType = equipmentType;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    public String getAmountOfTires() {
        return amountOfTires;
    }

    public void setAmountOfTires(String amountOfTires) {
        this.amountOfTires = amountOfTires;
    }

    @Override
    public String toString() {
        return "Request{" +
                " id='" + id + '\'' +
                ", status=" + status +
                ", equipmentType=" + equipmentType +
                ", aircraft=" + aircraft +
                ", location=" + location +
                ", equipment=" + equipment +
                ", timeStamp=" + timeStamp +
                ", departure=" + departure +
                ", amountOfTires=" + amountOfTires +
                '}';
    }
}

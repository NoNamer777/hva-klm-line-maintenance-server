package Klm1.KLMLineMaintenanceServer.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "equipment_type")
@NamedQueries({
        @NamedQuery(name = "find_all_equipment_types", query = "select et from EquipmentType et")
})
public class EquipmentType {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "equipmentType")
    @JsonIgnore
    private List<Request> requestedEquipment;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "type")
    @JsonIgnore
    private List<Equipment> equipmentList;

    @Id
    @Column(name = "id")
    private int id;

    private String group;
    private String subgroup;
    private String abbreviation;

    public EquipmentType() {
    }

    public EquipmentType(List<Request> requestedEquipment, List<Equipment> equipmentList, int id, String group, String subgroup, String abbreviation) {
        this.requestedEquipment = requestedEquipment;
        this.equipmentList = equipmentList;
        this.id = id;
        this.group = group;
        this.subgroup = subgroup;
        this.abbreviation = abbreviation;
    }

    public List<Request> getRequestedEquipment() {
        return requestedEquipment;
    }

    public void setRequests(List<Request> requestedEquipment) {
        this.requestedEquipment = requestedEquipment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getSubgroup() {
        return subgroup;
    }

    public void setSubgroup(String subgroup) {
        this.subgroup = subgroup;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public void setRequestedEquipment(List<Request> requestedEquipment) {
        this.requestedEquipment = requestedEquipment;
    }

    public List<Equipment> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
    }

    @Override
    public String toString() {
        return this.group;
    }
}

package com.eci.cosw.taskplanner.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Task {

    @PrimaryKey
    @NonNull
    private String id;

    private String owner;
    private String description;
    private String responsible;
    private String status;
    private Date dueDate;
    private String file;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", owner='" + owner + '\'' +
                ", description='" + description + '\'' +
                ", responsible='" + responsible + '\'' +
                ", status='" + status + '\'' +
                ", dueDate=" + dueDate +
                '}';
    }
}

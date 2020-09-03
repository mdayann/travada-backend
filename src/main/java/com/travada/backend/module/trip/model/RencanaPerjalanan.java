package com.travada.backend.module.trip.model;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class RencanaPerjalanan {
    private int hari;
    private String deskripsi;
}

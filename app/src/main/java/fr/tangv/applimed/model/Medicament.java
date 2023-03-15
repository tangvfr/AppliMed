package fr.tangv.applimed.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Medicament {

    @PrimaryKey
    @NonNull
    private String depotLegal;



}

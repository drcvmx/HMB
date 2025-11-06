package com.juego.howmanyburgers.dao;

import com.juego.howmanyburgers.model.Record;
import com.juego.howmanyburgers.model.Juego;
import com.juego.howmanyburgers.model.User;

import java.util.ArrayList;

public interface RecordsDAO {
    Record crear(Record r);
    ArrayList<Record> consultar(Juego j);
    Record consultar(Juego j, User u);
    Record consultar(Record r);
    int consultarNumeroDeRecords(Juego j);
    Record actualizar(Record r);
    ArrayList<Record> obtenerTop10Records(Juego j); // Nuevo método
}

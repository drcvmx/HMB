package com.juego.howmanyburgers.model;

import java.util.Date;

public class Record {
    private User usuario;
    private int puntaje;
    private Date fecha;
    private Juego juego;
    private long id;
    private int posicion;
    
    public Record() {}
    
    public User getUsuario() { return usuario; }
    public void setUsuario(User usuario) { this.usuario = usuario; }
    
    public int getPuntaje() { return puntaje; }
    public void setPuntaje(int puntaje) { this.puntaje = puntaje; }
    
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    
    public Juego getJuego() { return juego; }
    public void setJuego(Juego juego) { this.juego = juego; }
    
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    
    public int getPosicion() { return posicion; }
    public void setPosicion(int posicion) { this.posicion = posicion; }
}

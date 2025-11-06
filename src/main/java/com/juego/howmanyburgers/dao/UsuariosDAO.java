package com.juego.howmanyburgers.dao;

import com.juego.howmanyburgers.model.User;
import java.util.ArrayList;

public interface UsuariosDAO {
    User crear(User u);
    ArrayList<User> consultar();
    ArrayList<User> consultar(User u);
    User modificar(User u);
    int borrar(User u); 
}

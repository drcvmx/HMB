package com.juego.howmanyburgers.model;

import java.util.Objects; // Necesario para equals y hashCode

public class User {
    private long id; // Nuevo: para el ID de la base de datos
    private String username; // `login` de la tabla, renombrado a `username`
    private String password; // `password` de la tabla (hash), renombrado a `password` para ser consistente con los getters/setters
    private String email; // `correo` de la tabla, renombrado a `email`

    public User() {} // Constructor vacío

    public User(String username, String password) { // Constructor para registro/login
        this.username = username;
        this.password = password;
    }

    // Nuevo: Constructor completo para cuando se recupera de la base de datos
    public User(long id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // Método para verificar la contraseña (muy básico para demostración)
    public boolean checkPassword(String plainPassword) {
        // En una aplicación real, aquí compararías el hash de 'plainPassword' con 'this.password'
        // Por simplicidad, aquí comparamos directamente con el hash almacenado (que es la misma contraseña por ahora)
        return this.password.equals(plainPassword); 
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}

package com.app.homeCircle.Service;

import java.util.List;
import java.util.Optional;

import com.app.homeCircle.dto.UsuarioBasicDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.homeCircle.Entity.Usuario;
import com.app.homeCircle.Entity.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional  // Habilita la gestión de transacciones para todos los métodos
@RequiredArgsConstructor
public class UsuarioService {

    // Inyección de dependencia del repositorio
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Crea un nuevo usuario en la base de datos
     *
     * @param usuario El usuario a crear
     */

    public void createUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    /**
     * Busca un usuario por su ID
     *
     * @param id ID del usuario a buscar
     * @return Usuario encontrado
     * @throws RuntimeException si no se encuentra el usuario
     */

    public Usuario searchById(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    /**
     * Obtiene todos los usuarios registrados
     *
     * @return Lista de todos los usuarios
     */

    // buscar todos los usuarios
    public List<Usuario> searchUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Busca un usuario por email y devuelve DTO con información básica
     *
     * @param email Email del usuario
     * @return DTO con información básica del usuario
     */

    public UsuarioBasicDTO searchByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
        if (usuario == null) {
            return null;
        }
        return new UsuarioBasicDTO(
                usuario.getNombre(),
                usuario.getApellidos(),
                usuario.getEmail(),
                usuario.getTelefono(),
                usuario.getDni(),
                usuario.getSede()
        );
    }

    /**
     * Obtiene la cuenta bancaria de un usuario por su email
     *
     * @param email Email del usuario
     * @return Optional con la cuenta bancaria del usuario
     */

    public Optional<String> getCuentaBancariaByEmail(String email) {
        // Busca el usuario por email y si existe, obtiene su cuenta bancaria
        return usuarioRepository.findByEmail(email)
                // Transforma el Optional<Usuario> en Optional<String> con la cuenta bancaria
                .map(Usuario::getCuenta_banco);
    }

    /**
     * Actualiza los datos de un usuario existente
     *
     * @param id ID del usuario a actualizar
     * @param usuarioActualizado Datos actualizados del usuario
     * @return Usuario actualizado
     * @throws RuntimeException si no se encuentra el usuario
     */

    public Usuario updateUsuario(Integer id, Usuario usuarioData) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(usuarioData.getNombre());
            usuario.setApellidos(usuarioData.getApellidos());
            usuario.setEmail(usuarioData.getEmail());
            usuario.setTelefono(usuarioData.getTelefono());
            usuario.setDni(usuarioData.getDni());
            usuario.setSede(usuarioData.getSede());
            usuario.setCuenta_banco(usuarioData.getCuenta_banco());
            // ...otros campos si los tienes...
            return usuarioRepository.save(usuario);
        }).orElse(null);
    }

    /**
     * Elimina un usuario por su ID
     *
     * @param id ID del usuario a eliminar
     */

    public void deleteUsuario(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario con id: " + id + " no encontrado"));
        usuarioRepository.delete(usuario);
    }

    /**
     * Verifica si existe un usuario con el DNI especificado
     *
     * @param dni DNI a verificar
     * @return true si existe un usuario con ese DNI, false en caso contrario
     */

    public boolean existsByDni(String dni) {
        return usuarioRepository.existsByDni(dni);
    }

    /**
     * Verifica si existe un usuario con el email especificado
     *
     * @param email Email a verificar
     * @return true si existe un usuario con ese email, false en caso contrario
     */
    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}

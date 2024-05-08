package org.uv.DAPP02Practica03;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 *
 * @author Codigy
 */
@RestController
@RequestMapping("/api/empleado")
public class ControllerEmpleado {

    @Autowired
    private RepositoryEmpleado repositoryEmpleado;

    @GetMapping()
    public List<Empleado> list() {
        List<Empleado> lstEmpleado = repositoryEmpleado.findAll();
        return lstEmpleado;
    }

    @GetMapping("/{id}")
    public Empleado get(@PathVariable Long id) {
        Optional<Empleado> optEmp = repositoryEmpleado.findById(id);
        if (optEmp.isPresent()) {
            return optEmp.get();
        } else {
            return null;
        }
    }

    @PutMapping("/{id}")
    public Empleado updateEmpleado(@PathVariable Long id, @RequestBody Empleado empleadoActualizado) {

        Optional<Empleado> repEmpleado = repositoryEmpleado.findById(id);

        if (repEmpleado.isPresent()) {
            Empleado empleadoExistente = repEmpleado.get();

            // Actualizar campos del empleado existente
            empleadoExistente.setNombre(empleadoActualizado.getNombre());
            empleadoExistente.setDireccion(empleadoActualizado.getDireccion());
            empleadoExistente.setTelefono(empleadoActualizado.getTelefono());

            Empleado empleadoGuardado = repositoryEmpleado.saveAndFlush(empleadoExistente);
            return empleadoGuardado;  // Devuelve el empleado actualizado
        } else {
            // Si no se encuentra el empleado, puede devolver null o lanzar un error HTTP
            return null;
        }
    }

    @PostMapping
    public Empleado createEmpleado(@RequestBody Empleado nuevoEmpleado) {
        // Guarda y escribe el empleado inmediatamente en la base de datos
        Empleado empleadoGuardado = repositoryEmpleado.saveAndFlush(nuevoEmpleado);
        return empleadoGuardado;  // Devuelve el empleado recién creado
    }

    @DeleteMapping("/{id}")
    public Empleado delete(@PathVariable Long id) {
        Optional<Empleado> repEmpleado = repositoryEmpleado.findById(id);

        if (repEmpleado.isPresent()) {
            repositoryEmpleado.deleteById(id);
            return repEmpleado.get();
        } else {
            return null;
        }
    }
}

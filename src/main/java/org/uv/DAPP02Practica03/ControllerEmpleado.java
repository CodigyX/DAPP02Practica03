package org.uv.DAPP02Practica03;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        if (!lstEmpleado.isEmpty()) {
            return lstEmpleado;
        } else {
            return null;
        }
    }

    @GetMapping("/{id}")
    public Object get(@PathVariable Long id) {
        Optional<Empleado> optEmpleado = repositoryEmpleado.findById(id);
        if (optEmpleado.isPresent()) {
            return ResponseEntity.ok().body(optEmpleado);
        } else {
            return null;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empleado> put(@PathVariable Long id, @RequestBody Empleado empleado) {
        Optional<Empleado> dataEmpleado = repositoryEmpleado.findById(id);
        if (dataEmpleado.isPresent()) {
            Empleado emp = dataEmpleado.get();
            emp.setNombre(empleado.getNombre());
            emp.setDireccion(empleado.getDireccion());
            emp.setTelefono(empleado.getTelefono());
            repositoryEmpleado.save(emp);
            return ResponseEntity.ok().body(emp);
        } else {
            return null;
        }
    }

    @PostMapping
    public ResponseEntity<Empleado> post(@RequestBody Empleado empleado) {
        try {
            Empleado savedEmpleado = repositoryEmpleado.save(empleado);
            return ResponseEntity.ok().body(savedEmpleado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Empleado> delete(@PathVariable Long id) {
        Optional<Empleado> optEmpleado = repositoryEmpleado.findById(id);
        if (optEmpleado.isPresent()) {
            try {
                repositoryEmpleado.deleteById(id);
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return null;
        }
    }
}
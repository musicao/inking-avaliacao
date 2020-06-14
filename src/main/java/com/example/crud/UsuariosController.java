package com.example.crud;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping({"/usuarios"})
public class UsuariosController {

    private UsuariosRepository repository;

    UsuariosController(UsuariosRepository usuariosRepository) {
        this.repository = usuariosRepository;
    }

    @GetMapping
    public List findAll(){
        return repository.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity findById(@PathVariable long id){
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = {"/{nome}"})
    public ResponseEntity findByNome(@PathVariable String nome){
        return repository.findByNome(nome)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public Usuarios create(@RequestBody Usuarios usuario){
        return repository.save(usuario);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity update(@PathVariable("id") long id,
                                 @RequestBody Usuarios usuario) {
        return repository.findById(id)
                .map(record -> {
                    record.setNome(usuario.getNome());
                    record.setEmail(usuario.getEmail());
                    record.setData_de_nascimento(usuario.getData_de_nascimento());
                    record.setTipo(usuario.getTipo());
                    record.setEndereco(usuario.getEndereco());
                    record.setActive(usuario.getActive());
                    Usuarios updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity<?> delete(@PathVariable long id) {
        return repository.findById(id)
                .map(record -> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }


}
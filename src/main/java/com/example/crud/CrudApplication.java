package com.example.crud;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;
import java.util.stream.LongStream;


@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UsuariosRepository.class)
public class CrudApplication {


	public static void main(String[] args) {
		SpringApplication.run(CrudApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UsuariosRepository repository) {
		return args -> {
			repository.deleteAll();
			LongStream.range(1, 2)
					.mapToObj(i -> {
						Usuarios u = new Usuarios();
						u.setNome("Usuario" + i);
						u.setEmail("usuario" + i + "@email.com");
						u.setData_de_nascimento("1982-24-01");
						u.setTipo("rua");
						u.setEndereco("Casa do Oriente n" + i);
						u.setSenha("123");
						u.setActive(Boolean.TRUE);
						u.setRoles("ROLE_USER");
						return u;
					})
					.map(v -> repository.save(v))
					.forEach(System.out::println);
		};
	}

}




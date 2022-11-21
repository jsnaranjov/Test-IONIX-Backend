package com.backend.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.controller.CipherController;
import com.backend.controller.JsonController;
import com.backend.model.Usuario;
import com.backend.repo.IUsuarioRepo;
import com.backend.service.UsuariosService;

//Autor: Jaime Naranjo
//última Modificación : 19-11-2022

@RestController
@RequestMapping(produces=MediaType.APPLICATION_JSON_VALUE, value = "/usuario")
public class RestUsuarioController {

	private static Logger log = LoggerFactory.getLogger(RestUsuarioController.class);

	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
	}

	@Autowired
	private IUsuarioRepo repo;

	@GetMapping
	public List<Usuario> listar() {

		return repo.findAll();
	}

	@PostMapping(produces=MediaType.APPLICATION_JSON_VALUE, value = "/agregar")
	public String agregarUsuario(@RequestBody @Valid Usuario usr, BindingResult result) {

		if (result.hasErrors()) {
			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors().forEach(err -> {
				errores.put(err.getField(),
						"El campo ".concat(err.getField().concat(" ").concat(err.getDefaultMessage())));
			});
			return errores.toString();
		}
		if (repo.findByEmail(usr.getEmail()) != null) {
			return "Correo ya existe";
		}
		if (repo.findByUsername(usr.getUsername()) != null) {
			return "Usuario ya existe";
		}
		repo.save(usr);
		return "Usuario Agregado";
	}

	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE, value = "/obtener/{email}")
	public String obtenerUsuario(@PathVariable("email") String email) {
		Usuario usr = repo.findByEmail(email);
		if (usr.getEmail().isEmpty()) {
			return "Usuario no encontrado con correo : ".concat(email);
		}
		return usr.toString();
	}

	@DeleteMapping(produces=MediaType.APPLICATION_JSON_VALUE, value = "/eliminar/{email}")
	public String eliminar(@PathVariable("email") String email) {
		Usuario usr = repo.findByEmail(email);
		if (usr.getEmail().isEmpty()) {

			return "Id de usuario no existe";
		}
		repo.deleteByEmail(email);
		return "Usuario Eliminado";
	}

	@PostMapping(produces=MediaType.APPLICATION_JSON_VALUE, value= "/consulta_api/{rut}")
	public String consultaApi(@PathVariable("rut") String rut ) {
		CipherController cipher = new CipherController();
		UsuariosService service = new UsuariosService();
		JsonController json = new JsonController();
		try {
			if (cipher.validarRut(rut)) {

				String cifrado = cipher.encriptKeyDES(rut);
				long iniTime = System.nanoTime();
				String resultado = service.llamadaApi(cifrado);
				long endTime = System.nanoTime();
				long tiempo = endTime - iniTime;
				tiempo = TimeUnit.MILLISECONDS.convert(tiempo, TimeUnit.NANOSECONDS);
				log.info("Tiempo de respuesta : " + tiempo + "ms");
				String response = json.procesarData(resultado, tiempo);
				return response;
			}
			return "Debe ingresar un RUT válido";
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
			return e.getMessage();
		}
	}
}
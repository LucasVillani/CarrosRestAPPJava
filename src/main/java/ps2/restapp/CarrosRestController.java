package ps2.restapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarrosRestController {
	
	@Autowired
	private CarrosRepository carrosRepo;
	

	
	@GetMapping ("/api/carros")
	public List<Carro> getCarros(){
		List<Carro> carros = new ArrayList<>();
		carrosRepo.findAll().forEach(carros::add);
		return carros;
	}
	
	@GetMapping("/api/carros/{id}")
	public Carro getCarro(@PathVariable long id) {
		Optional<Carro> retorno = carrosRepo.findById(id);
		Carro c = null;
		if(retorno.isPresent()) {
			
			c = retorno.get();
		}
		return c;
	}
	
	@PostMapping("/api/carros")
	public Carro createCarro(@RequestBody Carro c) {
		carrosRepo.save(c);
		return c;
	}
	
	@PutMapping("/api/carros/{id}")
	public Carro updateCarro(@RequestBody Carro c, @PathVariable long id) {
		Carro carro = null;
		carro = this.getCarro(id);
		
		if (carro != null) {
			carrosRepo.save(c);
			carro = c;
		}
		return carro;
	}
	
	@DeleteMapping(value = "/api/carros/{id}", produces = "application/json")
	public String deleteCarro(@PathVariable long id) {
		Carro carro = this.getCarro(id);
		boolean result = false;
		if (carro != null) {
			carrosRepo.delete(carro);
			result = true;
		}
		return "{ \" sucess\" : " + (result ? "true" : "false") + " }";
	}
}

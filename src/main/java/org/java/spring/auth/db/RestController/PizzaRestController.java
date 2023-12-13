package org.java.spring.auth.db.RestController;

import java.util.List;

import org.java.spring.pojo.Pizza;
import org.java.spring.serv.OffertaSpecialeService;
import org.java.spring.serv.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/pizza")
public class PizzaRestController {
	
	@Autowired
	private PizzaService pizzaService;
	
	@Autowired
	private OffertaSpecialeService offertaSpecialeService;
	
	@GetMapping
	public ResponseEntity<List<Pizza>> routeIndex(@RequestParam(required=false) String q ){
		
		List<Pizza> pizzas = q==null
				? pizzaService.findAll()
				: pizzaService.findByNomeOrDescrizione(q);
		
		return new ResponseEntity<>(pizzas, HttpStatus.OK);
		
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Pizza> routeShow(@PathVariable int id) {
		
		Pizza pizza = pizzaService.findById(id);
		
		if (pizza == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(pizza, HttpStatus.OK);
	}
	
	@PostMapping("create")
	public ResponseEntity<Pizza> routeCreate(@RequestBody Pizza pizza) {
		
		pizzaService.save(pizza);
		
		return new ResponseEntity<>(pizza, HttpStatus.OK);
	}
	
	@PutMapping("edit/{id}")
	public  ResponseEntity<Pizza> routeUpdate(
			@PathVariable int id,
			@RequestBody Pizza newPizza) {
	
		Pizza pizza = pizzaService.findById(id);
		
		if (pizza == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		pizza.setNome(newPizza.getNome());
		pizza.setDescrizione(newPizza.getDescrizione());
		
		if(newPizza.getFotoUrl()!=null && !newPizza.getFotoUrl().isBlank()) {
			pizza.setFotoUrl(newPizza.getFotoUrl());
		}
		
		pizza.setPrezzo(newPizza.getPrezzo());
		
		pizzaService.save(pizza);
		
		return new ResponseEntity<>(pizza, HttpStatus.OK);
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Pizza> routeDelete(
			@PathVariable int id) {
		
		Pizza pizza = pizzaService.findPizzaWithIngredients(id);
		
		if (pizza == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		pizza.getOffertaSpeciale().forEach(offertaSpecialeService::delete);
		
		pizzaService.delete(pizza);
		
		return new ResponseEntity<>(pizza, HttpStatus.OK); 
	}

}

package iuh.spring.product.controller;

import iuh.spring.product.entity.Product;
import iuh.spring.product.reponsitory.ProductReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class ProductController {

	@Autowired
	private ProductReponsitory productReponsitory;
	@GetMapping("/products")
	public List<Product> getListsProduct(){
		return productReponsitory.findAll();
	}

	@GetMapping("/products/{id}")
	@Cacheable(value = "Product", key = "#id")
	public Product getProductById(@PathVariable(value = "id") long id){
		return productReponsitory.findById(id).get();
	}

	@PostMapping("/products")
	public Product addProduct(@RequestBody Product product) {
		return productReponsitory.save(product);
	}

	@DeleteMapping("/products/{id}")
	@CacheEvict(value = "Product", key = "#id")
	public void deleteProduct(@PathVariable(value = "id") long id){
		productReponsitory.deleteById(id);
	}

	@PutMapping("/products/{id}")
	public Product updateProduct(@PathVariable(value = "id") long id, @RequestBody Product product) {
		Product p = productReponsitory.findById(id).get();
		p.setName(product.getName());
		p.setPrice(product.getPrice());
		return productReponsitory.save(p);
	}



}

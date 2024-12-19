package com.example.eccom_proj.controller;

import com.example.eccom_proj.model.Product;
import com.example.eccom_proj.service.ProductsService;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductsService productsService;

    @RequestMapping("/")
    public String greet(){
        return "hellow world";
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(productsService.getAllProducts(),HttpStatus.OK);

    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id){
        Product product = productsService.getProductByid(id);

        if (product!=null)
            return new ResponseEntity<>(productsService.getProductByid(id),HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product,
                                        @RequestPart MultipartFile imageFile){
try {
    Product product1=productsService.addProduct(product, imageFile);
    return new ResponseEntity<>(product1,HttpStatus.CREATED);
}catch (Exception e){
    return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
}
    }


    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]>getImageProductId(@PathVariable int productId){

        Product product =productsService.getProductByid(productId);
        byte [] imageFile = product.getImageData();

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile);
    }



    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id,@RequestPart Product product,
                                                @RequestPart MultipartFile imageFile) throws IOException {

        Product product1 = null;
try{
     product1 = productsService.updateProduct(id,product,imageFile);

}catch (IOException e){
    return new ResponseEntity<>("Failed to update",HttpStatus.BAD_REQUEST);

}
       if(product1 != null)
           return new ResponseEntity<>("Updated",HttpStatus.OK);
       else
           return new ResponseEntity<>("Failed to update",HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Product product = productsService.getProductByid(id);
        if (product != null){
            productsService.deleteProduct(id);
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Product not found",HttpStatus.NOT_FOUND);
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword){
        System.out.println("searching with "+keyword);
        List<Product> products =productsService.searchProducts(keyword);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
}

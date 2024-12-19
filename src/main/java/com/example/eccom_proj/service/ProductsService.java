package com.example.eccom_proj.service;

import com.example.eccom_proj.model.Product;
import com.example.eccom_proj.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductsService {
    @Autowired
    private ProductRepo productRepo;


    public List<Product> getAllProducts() {
       return productRepo.findAll();

    }

    public Product getProductByid(int id) {
        return  productRepo.findById(id).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
       return productRepo.save(product);
    }

    public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
        product.setImageData(imageFile.getBytes());
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
       return productRepo.save(product);
    }


    public void deleteProduct(int id) {
        productRepo.deleteById(id);
    }

    public List<Product> searchProducts(String keyword) {
        return  productRepo.searchProducts(keyword);
    }
}

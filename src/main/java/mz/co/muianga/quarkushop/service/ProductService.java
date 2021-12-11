package mz.co.muianga.quarkushop.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import mz.co.muianga.quarkushop.model.Product;
import mz.co.muianga.quarkushop.model.ProductStatus;
import mz.co.muianga.quarkushop.repository.CategoryRepository;
import mz.co.muianga.quarkushop.repository.ProductRepository;
import mz.co.muianga.quarkushop.resources.dto.ProductDto;

@Slf4j
@ApplicationScoped
@Transactional
public class ProductService {

    @Inject
    ProductRepository productRepository;

    @Inject
    CategoryRepository categoryRepository;

    public List<ProductDto> findAll() {
        log.debug("");
        return this.productRepository.findAll()
                .stream()
                .map(ProductService::mapToDto)
                .collect(Collectors.toList());
    }

    public ProductDto findById(Long id) {
        log.debug("Request to find Product : {}", id);
        return this.productRepository.findById(id)
                .map(ProductService::mapToDto)
                .orElse(null);
    }

    public Long countAll() {
        return this.productRepository.count();
    }

    public Long countByCategoryId(Long id) {
        return this.productRepository.countAllByCategoryId(id);
    }

    public ProductDto create(ProductDto productDto) {
        log.debug("Request to create Product : {}", productDto);
        return mapToDto(this.productRepository.save(
                new Product(
                        productDto.getName(),
                        productDto.getDescription(),
                        productDto.getPrice(),
                        ProductStatus.valueOf(productDto.getStatus()),
                        productDto.getCounterSales(),
                        Collections.emptySet(),
                        categoryRepository.findById(productDto.getCategoryId())
                                .orElse(null))));
    }

    public List<ProductDto> findByCategoryId(Long id) {
        return this.productRepository.findByCategoryId(id)
                .stream().map(ProductService::mapToDto)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        log.debug("Request to delete Product : {}", id);
        this.productRepository.deleteAllByCategoryId(id);
    }

    public static ProductDto mapToDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStatus().name(),
                product.getSalesCounter(),
                product.getReviews()
                        .stream()
                        .map(ReviewService::mapToDto)
                        .collect(Collectors.toSet()),
                product.getCategory().getId());
    }
}

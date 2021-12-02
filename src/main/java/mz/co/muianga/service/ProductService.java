package mz.co.muianga.service;

import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import mz.co.muianga.model.Product;
import mz.co.muianga.repository.CategoryRepository;
import mz.co.muianga.repository.ProductRepository;
import mz.co.muianga.resources.dto.ProductDto;

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

package mz.co.muianga.service;

import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import mz.co.muianga.model.Category;
import mz.co.muianga.resources.dto.CategoryDto;
import mz.co.muianga.repository.CategoryRepository;
import mz.co.muianga.repository.ProductRepository;

@Slf4j
@ApplicationScoped
@Transactional
public class CategoryService {

    @Inject
    CategoryRepository categoryRepository;

    @Inject
    ProductRepository productRepository;

    public static CategoryDto mapToDto(Category category, Long productsCount) {
        return new CategoryDto(category.getId(),
                category.getName(),
                category.getDescription(),
                productsCount);
    }

    public List<CategoryDto> findAll() {
        log.debug("Request to get all categories");

        return categoryRepository.findAll()
                .stream()
                .map(category ->
                        mapToDto(category, productRepository.countAllByCategoryId(category.getId())))
                .collect(Collectors.toList());
    }

    public CategoryDto findById(Long id) {
        log.debug("Request to get a Category : {}", id);

        return this.categoryRepository.findById(id)
                .map(category ->
                        mapToDto(category, productRepository.countAllByCategoryId(category.getId())))
                .orElse(null);
    }

    public CategoryDto create(CategoryDto categoryDto) {
        log.debug("Request to create Category {}", categoryDto);

        return mapToDto(new Category(
                categoryDto.getName(),
                categoryDto.getDescription()),
                0L);
    }

    public void delete (Long id) {
        log.debug("Request to delete Category {}", id);

        log.debug("Deleting all products for the Category {}", id);
        this.productRepository.deleteAllByCategoryId(id);

        log.debug("Deleting Category : {}", id);
        this.categoryRepository.deleteById(id);
    }

    public List<ProductDto> findProductsByCategoryId(Long id) {
        return this.productRepository.findAllByCategoryId(id)
                .stream()
                .map(ProductService::mapToDto)
                .collect(Collectors.toList());
    }

}

package com.cybersoft.uniclub.service.imp;

import com.cybersoft.uniclub.dto.ProductDTO;
import com.cybersoft.uniclub.entity.*;
import com.cybersoft.uniclub.repository.ProductRepository;
import com.cybersoft.uniclub.repository.VariantRepository;
import com.cybersoft.uniclub.request.AddProductRequest;
import com.cybersoft.uniclub.service.FilesStorageService;
import com.cybersoft.uniclub.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VariantRepository variantRepository;

    @Autowired
    private FilesStorageService filesStorageService;

    @Transactional
    @Override
    public void addProduct(AddProductRequest request) {
        ProductEntity product = new ProductEntity();
        product.setName(request.name());
        product.setDesc(request.desc());
        product.setInfo(request.information());
        product.setPrice(request.price());

        SizeEntity size = new SizeEntity();
        size.setId(request.idSize());

        ColorEntity color = new ColorEntity();
        color.setId(request.idColor());

        BrandEntity brand = new BrandEntity();
        brand.setId(request.idBrand());

        product.setBrand(brand);

        ProductEntity productSave = productRepository.save(product);

        VariantEntity variant = new VariantEntity();
        variant.setProduct(productSave);
        variant.setColor(color);
        variant.setSize(size);
        variant.setPrice(request.priceSize());
        variant.setQuantity(request.quantity());
        variant.setImage(request.file().getOriginalFilename());

        variantRepository.save(variant);

        filesStorageService.save(request.file());

    }

    @Override
    public List<ProductDTO> getProducts() {
        List<ProductEntity> products = productRepository.findAll();
        return products.stream().map(item -> {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setName(item.getName());
            productDTO.setPrice(item.getPrice());
            if (item.getVariants().size() > 0) {
            productDTO.setLinkImg("http://localhost:8080/uniclub/file/" + item.getVariants().getFirst().getImage());
            } else {
            productDTO.setLinkImg("");
            }
            return productDTO;
        }).toList();
    }
}

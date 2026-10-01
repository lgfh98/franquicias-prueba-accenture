package org.example.franchises.infrastructure.config;

import org.example.franchises.domain.ports.in.FranchiseCommandUseCase;
import org.example.franchises.domain.ports.in.FranchiseQueryUseCase;
import org.example.franchises.domain.ports.out.BranchRepositoryPort;
import org.example.franchises.domain.ports.out.FranchiseRepositoryPort;
import org.example.franchises.domain.ports.out.ProductRepositoryPort;
import org.example.franchises.domain.service.FranchiseService;
import org.example.franchises.infrastructure.decorator.ReadOnlyFranchiseQueryDecorator;
import org.example.franchises.infrastructure.decorator.TransactionalFranchiseCommandDecorator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FranchiseAppConfig {

    @Bean
    public FranchiseCommandUseCase franchiseCommandUseCase(
            FranchiseRepositoryPort franchiseRepository,
            BranchRepositoryPort branchRepository,
            ProductRepositoryPort productRepository) {
        FranchiseService service = new FranchiseService(franchiseRepository, branchRepository, productRepository);
        return new TransactionalFranchiseCommandDecorator(service);
    }

    @Bean
    public FranchiseQueryUseCase franchiseQueryUseCase(
            FranchiseRepositoryPort franchiseRepository,
            BranchRepositoryPort branchRepository,
            ProductRepositoryPort productRepository) {
        FranchiseService service = new FranchiseService(franchiseRepository, branchRepository, productRepository);
        return new ReadOnlyFranchiseQueryDecorator(service);
    }
}

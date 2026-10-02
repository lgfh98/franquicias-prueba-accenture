package org.example.franchises.infrastructure.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.franchises.domain.model.Branch;
import org.example.franchises.domain.model.Franchise;
import org.example.franchises.domain.model.Product;
import org.example.franchises.domain.model.dto.HighestStockProductDto;
import org.example.franchises.domain.ports.in.FranchiseCommandUseCase;
import org.example.franchises.domain.ports.in.FranchiseQueryUseCase;
import org.example.franchises.infrastructure.web.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class FranchiseControllerTest {

    @Mock
    private FranchiseCommandUseCase commandUseCase;

    @Mock
    private FranchiseQueryUseCase queryUseCase;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        FranchiseController controller = new FranchiseController(commandUseCase, queryUseCase);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void shouldCreateFranchise() throws Exception {
        // Arrange
        CreateFranchiseRequest request = new CreateFranchiseRequest("Café Martínez");
        when(commandUseCase.createFranchise("Café Martínez")).thenReturn(new Franchise(1L, "Café Martínez"));

        // Act & Assert
        mockMvc.perform(post("/api/v1/franchises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Café Martínez"));
    }

    @Test
    void shouldGetAllFranchises() throws Exception {
        // Arrange
        when(queryUseCase.getAllFranchises()).thenReturn(List.of(
                new Franchise(1L, "Franquicia 1"),
                new Franchise(2L, "Franquicia 2")
        ));

        // Act & Assert
        mockMvc.perform(get("/api/v1/franchises"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Franquicia 1"));
    }

    @Test
    void shouldGetFranchiseById() throws Exception {
        // Arrange
        when(queryUseCase.getFranchiseById(1L)).thenReturn(new Franchise(1L, "Franquicia 1"));

        // Act & Assert
        mockMvc.perform(get("/api/v1/franchises/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Franquicia 1"));
    }

    @Test
    void shouldReturn404WhenFranchiseNotFound() throws Exception {
        // Arrange
        when(queryUseCase.getFranchiseById(99L)).thenThrow(new NoSuchElementException("Franchise not found with ID: 99"));

        // Act & Assert
        mockMvc.perform(get("/api/v1/franchises/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Franchise not found with ID: 99"));
    }

    @Test
    void shouldUpdateFranchiseName() throws Exception {
        // Arrange
        UpdateNameRequest request = new UpdateNameRequest("Nuevo Nombre");
        when(commandUseCase.updateFranchiseName(1L, "Nuevo Nombre"))
                .thenReturn(new Franchise(1L, "Nuevo Nombre"));

        // Act & Assert
        mockMvc.perform(patch("/api/v1/franchises/1/name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Nuevo Nombre"));
    }

    @Test
    void shouldAddBranchToFranchise() throws Exception {
        // Arrange
        CreateBranchRequest request = new CreateBranchRequest("Sucursal Norte");
        when(commandUseCase.addBranchToFranchise(1L, "Sucursal Norte"))
                .thenReturn(new Branch(10L, 1L, "Sucursal Norte"));

        // Act & Assert
        mockMvc.perform(post("/api/v1/franchises/1/branches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.name").value("Sucursal Norte"))
                .andExpect(jsonPath("$.franchiseId").value(1L));
    }

    @Test
    void shouldGetBranchesByFranchise() throws Exception {
        // Arrange
        when(queryUseCase.getBranchesByFranchiseId(1L)).thenReturn(List.of(
                new Branch(10L, 1L, "Sucursal Norte")
        ));

        // Act & Assert
        mockMvc.perform(get("/api/v1/franchises/1/branches"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Sucursal Norte"));
    }

    @Test
    void shouldUpdateBranchName() throws Exception {
        // Arrange
        UpdateNameRequest request = new UpdateNameRequest("Sucursal Renombrada");
        when(commandUseCase.updateBranchName(10L, "Sucursal Renombrada"))
                .thenReturn(new Branch(10L, 1L, "Sucursal Renombrada"));

        // Act & Assert
        mockMvc.perform(patch("/api/v1/branches/10/name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sucursal Renombrada"));
    }

    @Test
    void shouldAddProductToBranch() throws Exception {
        // Arrange
        CreateProductRequest request = new CreateProductRequest("Café Espresso", 50);
        when(commandUseCase.addProductToBranch(10L, "Café Espresso", 50))
                .thenReturn(new Product(100L, 10L, "Café Espresso", 50));

        // Act & Assert
        mockMvc.perform(post("/api/v1/branches/10/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(100L))
                .andExpect(jsonPath("$.name").value("Café Espresso"))
                .andExpect(jsonPath("$.stock").value(50))
                .andExpect(jsonPath("$.branchId").value(10L));
    }

    @Test
    void shouldGetProductsByBranch() throws Exception {
        // Arrange
        when(queryUseCase.getProductsByBranch(10L)).thenReturn(List.of(
                new Product(100L, 10L, "Café Espresso", 50)
        ));

        // Act & Assert
        mockMvc.perform(get("/api/v1/branches/10/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Café Espresso"));
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        // Arrange
        doNothing().when(commandUseCase).deleteProductFromBranch(10L, 100L);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/branches/10/products/100"))
                .andExpect(status().isNoContent());

        verify(commandUseCase).deleteProductFromBranch(10L, 100L);
    }

    @Test
    void shouldUpdateProductStock() throws Exception {
        // Arrange
        UpdateStockRequest request = new UpdateStockRequest(120);
        when(commandUseCase.updateProductStock(100L, 120))
                .thenReturn(new Product(100L, 10L, "Café Espresso", 120));

        // Act & Assert
        mockMvc.perform(patch("/api/v1/products/100/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stock").value(120));
    }

    @Test
    void shouldUpdateProductName() throws Exception {
        // Arrange
        UpdateNameRequest request = new UpdateNameRequest("Café Espresso Doble");
        when(commandUseCase.updateProductName(100L, "Café Espresso Doble"))
                .thenReturn(new Product(100L, 10L, "Café Espresso Doble", 120));

        // Act & Assert
        mockMvc.perform(patch("/api/v1/products/100/name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Café Espresso Doble"));
    }

    @Test
    void shouldGetMaxStockProductsPerBranch() throws Exception {
        // Arrange
        List<HighestStockProductDto> dtos = List.of(
                new HighestStockProductDto(10L, "Sucursal Norte", 100L, "Café Espresso", 120)
        );
        when(queryUseCase.getMaxStockProductsPerBranch(1L)).thenReturn(dtos);

        // Act & Assert
        mockMvc.perform(get("/api/v1/franchises/1/max-stock-products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].branchName").value("Sucursal Norte"))
                .andExpect(jsonPath("$[0].productName").value("Café Espresso"))
                .andExpect(jsonPath("$[0].stock").value(120));
    }
}

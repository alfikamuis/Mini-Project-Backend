package com.alfika.backendecommerce.service;

import com.alfika.backendecommerce.model.OrderItems;
import com.alfika.backendecommerce.model.Product;
import com.alfika.backendecommerce.model.ViewOrder;
import com.alfika.backendecommerce.repository.OrderItemsRepository;
import com.alfika.backendecommerce.repository.ProductRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    private MockMvc mockMvc;
    @Mock
    private OrderItemsRepository orderItemsRepository;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private AdminServiceImp adminServiceImp;

    OrderItems orderItems = new OrderItems(1L, "alfika@gmail.com", "pending",
                                LocalDate.now(), 1400000D);
    OrderItems orderItems1 = new OrderItems(2L, "alfika@gmail.com", "shipping",
                                 LocalDate.now(), 230000D);
    OrderItems orderItems2 = new OrderItems(3L, "alfika@gmail.com", "shipping",
                                 LocalDate.now(), 56000D);

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(adminServiceImp).build();
    }

    @DisplayName("viewAllOrderByUser method negative scenario")
    @Test
    public void givenEmptyOrderList_thenReturnEmptyOrderList(){

        given(orderItemsRepository.findAll()).willReturn(Collections.emptyList());
        List<ViewOrder> orderItemsList = adminServiceImp.viewAllOrderByUser();

        assertThat(orderItemsList).isEmpty();
        assertThat(orderItemsList.size()).isEqualTo(0);
    }

    @DisplayName("viewAllOrderByUser method positive scenario")
    @Test
    public void givenOrderList_thenReturnOrderList(){

        given(orderItemsRepository.findAll()).willReturn(List.of(orderItems,orderItems1,orderItems2));
        List<OrderItems> orderItemsList = orderItemsRepository.findAll();

        assertThat(orderItemsList.size()).isEqualTo(3);
    }

    @DisplayName("viewAllShippingOrderByUser method")
    @Test
    public void givenOrderList_thenReturnOrderListWithShippingStatus(){

        List<OrderItems> expectedList = new ArrayList<>();
        expectedList.add(orderItems1);
        expectedList.add(orderItems2);
        given(orderItemsRepository.findByOrderStatus("shipping")).willReturn(expectedList);

        List<OrderItems> orderItemsList = adminServiceImp.viewAllShippingOrderByUser();

        assertThat(orderItemsList).isNotNull();
        Assert.assertEquals(expectedList,orderItemsList);
    }

    @DisplayName("viewAllPendingOrderByUser method")
    @Test
    public void givenOrderList_thenReturnOrderListWithPendingStatus(){

        List<OrderItems> expectedList = new ArrayList<>();
        expectedList.add(orderItems);
        given(orderItemsRepository.findByOrderStatus("pending")).willReturn(expectedList);

        List<OrderItems> orderItemsList = adminServiceImp.viewAllPendingOrderByUser();

        assertThat(orderItemsList).isNotNull();
        Assert.assertEquals(expectedList,orderItemsList);
    }

    @DisplayName("updateStatusOrder method")
    @Test
    public void givenOrderById_thenReturnUpdatedStatusOrder(){

        given(orderItemsRepository.findById(1L)).willReturn(Optional.ofNullable(orderItems));
        orderItems.setOrderStatus("shipping");

        adminServiceImp.updateStatusOrder(1L,"shipping");
        Optional<OrderItems> actualResult = orderItemsRepository.findById(1L);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.get().getOrderStatus()).isEqualTo("shipping");
    }

    //---------------------------------------------------------------------------------------product
    Product product = new Product(1L,"wool","blanket on summer", 5,
            12000D,null,null,null);
    Product product2 = new Product(2L,"rope","repair the blanket", 99,
            5000D,null,null,null);
    MockMultipartFile file =
            new MockMultipartFile(
                    "file",
                    "photo.jpg",
                    MediaType.APPLICATION_PDF_VALUE,
                    "<<jpg data>>".getBytes(StandardCharsets.UTF_8));

    @DisplayName("findAllProduct method")
    @Test
    public void givenProductList_thenReturnAllProductList(){
        given(productRepository.findAll()).willReturn(List.of(product,product2));

        List<Product> actualResult = adminServiceImp.findAllProduct();

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.size()).isEqualTo(2);
    }

    @DisplayName("addProduct method")
    @Test
    public void givenProductObject_thenReturnProductObject() throws IOException {

        adminServiceImp.addProduct("plastic","wrapping for blanket",
                    "20","20003", file );

        verify(productRepository,atLeastOnce()).save(any(Product.class));
    }

    @DisplayName("updateProduct method")
    @Test
    public void givenProductById_thenReturnUpdatedProduct() throws IOException {

        given(productRepository.findById(1L)).willReturn(Optional.ofNullable(product));

        adminServiceImp.updateProduct(1L,"red blanket","hot in summer",
                "2","999999", file );

        assertThat(productRepository.findById(1L).get().getName()).isEqualTo("red blanket");
    }

    @DisplayName("deleteProduct method")
    @Test
    public void givenProductById_thenDeletedProductById() throws IOException {

        willDoNothing().given(productRepository).deleteById(1L);

        adminServiceImp.deleteProduct(1L);

        verify(productRepository,times(1)).deleteById(1L);
    }
}

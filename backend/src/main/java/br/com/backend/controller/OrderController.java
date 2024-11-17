package br.com.backend.controller;

import br.com.backend.domain.models.order.CreateUpdateOrderDTO;
import br.com.backend.service.models.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Objects;

@RestController()
@RequestMapping("/order")
public class OrderController {

    private static Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/create?business={businessId}&address={addressId}&product={productId}&person={personId}")
    private ResponseEntity createOrder(
            @RequestParam(name = "businessId") Long businessId,
            @RequestParam(name = "addressId") Long addressId,
            @RequestParam(name = "productId") Long productId,
            @RequestParam(name = "personId") Long personId,
            @RequestBody CreateUpdateOrderDTO createUpdateOrderDTO
    ){
        try{
            return ResponseEntity.created(URI.create("")).body(orderService.createOrder(businessId, addressId, productId, personId, createUpdateOrderDTO.getNotes()));

        }catch (Exception e){
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/update?order={orderId}&status={status}")
    private ResponseEntity updateOrder(
            @RequestParam(name = "orderId") Long orderId,
            @RequestParam(name = "status") Long status,
            @RequestBody CreateUpdateOrderDTO createUpdateOrderDTO
    ){
        try{
            return ResponseEntity.created(URI.create(""))
                    .body(orderService.updateOrder(orderId, status, Objects.requireNonNullElse(createUpdateOrderDTO.getNotes(),"")));

        }catch (Exception e){
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

}

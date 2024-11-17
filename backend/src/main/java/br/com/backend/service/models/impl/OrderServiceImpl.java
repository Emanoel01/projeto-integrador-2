package br.com.backend.service.models.impl;

import br.com.backend.domain.enums.ExceptionsMessagesEnum;
import br.com.backend.domain.enums.OrderStatusEnum;
import br.com.backend.repository.interfaces.*;
import br.com.backend.repository.models.Order;
import br.com.backend.repository.models.OrderStatus;
import br.com.backend.repository.models.Person;
import br.com.backend.repository.models.ProductBusinessAddress;
import br.com.backend.service.models.OrderService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final ProductBusinessAddressRepository productBusinessAddressRepository;
    private final OrderRepository orderRepository;
    private final PersonRepository personRepository;
    private final OrderStatusRepository orderStatusRepository;
    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    public OrderServiceImpl(ProductBusinessAddressRepository productBusinessAddressRepository, OrderRepository orderRepository, PersonRepository personRepository, OrderStatusRepository orderStatusRepository) {
        this.productBusinessAddressRepository = productBusinessAddressRepository;
        this.orderRepository = orderRepository;
        this.personRepository = personRepository;
        this.orderStatusRepository = orderStatusRepository;
    }

    @Override
    @Transactional
    public Order createOrder(Long businessId, Long addressId, Long productId, Long personId, String notes) throws Exception{
        try{
            Optional<ProductBusinessAddress> productBusinessAddressOptional = productBusinessAddressRepository.findByProductIdAndBusinessAddressBusinessIdAndBusinessAddressAddressId(
              productId, businessId, addressId
            );

            if(productBusinessAddressOptional.isEmpty()) throw new Exception(ExceptionsMessagesEnum.PRODUCT_BUSINESS_ADDRESS_NOT_FOUND.getMessage());

            Optional<Person> personOptional = personRepository.findById(personId);

            if(personOptional.isEmpty()) throw new Exception(ExceptionsMessagesEnum.PERSON_NOT_FOUND.getMessage());


            return orderRepository.save(Order.builder()
                            .person(personOptional.get())
                            .productBusinessAddress(productBusinessAddressOptional.get())
                            .notes(notes)
                    .build());
        }catch (Exception e){
            logger.error(e.getMessage());
          throw e;
        }
    }

    @Override
    public Order personCancelOrder(Long orderId, String cancellationNotes) throws Exception {
        try{
            Optional<Order> orderOptional = orderRepository.findById(orderId);

            if(orderOptional.isEmpty()) throw new Exception(ExceptionsMessagesEnum.ORDER_NOT_FOUND.getMessage());

            Order order = orderOptional.get();

            OrderStatus orderStatus = orderStatusRepository.findById(OrderStatusEnum.CANCELLED.getStatus()).orElseThrow();

            order.setOrderStatus(orderStatus);
            order.setCancellationNotes(cancellationNotes);

            return orderRepository.save(order);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public Order updateOrder(Long orderId, Long status, String cancellationNotes) throws Exception {
        try{
            Optional<Order> orderOptional = orderRepository.findById(orderId);

            if(orderOptional.isEmpty()) throw new Exception(ExceptionsMessagesEnum.ORDER_NOT_FOUND.getMessage());

            Order order = orderOptional.get();

            Optional<OrderStatus> orderStatusOptional = orderStatusRepository.findById(status);

            if(orderStatusOptional.isEmpty()) throw new Exception(ExceptionsMessagesEnum.ORDER_STATUS_NOT_FOUND.getMessage());

            order.setOrderStatus(orderStatusOptional.get());

            if(OrderStatusEnum.CANCELLED.getStatus() == status)
                order.setCancellationNotes(cancellationNotes);

            return orderRepository.save(order);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw e;
        }
    }
}

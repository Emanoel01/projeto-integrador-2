package br.com.backend.service.models;

import br.com.backend.repository.models.Order;

public interface OrderService {


    Order createOrder(Long businessId, Long addressId, Long productId, Long personId, String notes) throws Exception;

    Order personCancelOrder(Long orderId, String cancellationNotes) throws Exception;

    Order updateOrder(Long orderId, Long status ,String cancellationNotes) throws Exception;


}

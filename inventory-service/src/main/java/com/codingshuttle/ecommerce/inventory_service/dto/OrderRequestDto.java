package com.codingshuttle.ecommerce.inventory_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {
    private List<OrderRequestItemDto> items;

    public List<OrderRequestItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderRequestItemDto> items) {
        this.items = items;
    }
}


package com.sky.vo;

import com.sky.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderVO implements Serializable {

    private String orderId;

    private String status;

    private String buyer;

    private String seller;

    private String createdAt;

    private String updatedAt;

    private BigDecimal price;

    private List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();

}

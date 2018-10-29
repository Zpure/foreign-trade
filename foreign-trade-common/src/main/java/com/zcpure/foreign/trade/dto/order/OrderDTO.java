package com.zcpure.foreign.trade.dto.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@ApiModel("订单信息")
public class OrderDTO implements Serializable {
	private static final long serialVersionUID = 1111320370190733556L;

	@ApiModelProperty("订单编码")
	private String code;
	@ApiModelProperty("订单所属集团")
	private String groupCode;
	@ApiModelProperty("订单名称")
	private String name;
	@ApiModelProperty("订单总金额")
	private BigDecimal totalAmount;
	@ApiModelProperty("订单总数量")
	private Integer totalNum;
	@ApiModelProperty("订单总分配数量")
	private Integer totalInitDisNum;
	@ApiModelProperty("订单总配货数量")
	private Integer totalDisNum;
	@ApiModelProperty("订单状态")
	private Integer status;
	@ApiModelProperty("订单配注")
	private String remark;
	@ApiModelProperty("订单创建时间")
	private Date createTime;
	@ApiModelProperty("订单详情")
	private List<OrderDetailDTO> detailList;
}

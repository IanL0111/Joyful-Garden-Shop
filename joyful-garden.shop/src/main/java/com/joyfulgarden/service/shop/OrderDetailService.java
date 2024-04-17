package com.joyfulgarden.service.shop;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joyfulgarden.model.shop.OrderDetail;
import com.joyfulgarden.model.shop.OrderDetailRepository;

@Service
@Transactional
public class OrderDetailService {
	
	@Autowired
	private OrderDetailRepository odr ;
	
	public OrderDetail insert(OrderDetail od) {
		return odr.save(od);
	}
	
	public OrderDetail update(OrderDetail od) {
		return odr.save(od);
	}
	
	public void deleteById(Integer odID) {
		odr.deleteById(odID);
	}
	
	public OrderDetail findById(Integer odID) {
		Optional<OrderDetail> od = odr.findById(odID);
		if(od.isPresent())
			return od.get();
		else
			return null;
	}
	
	public List<OrderDetail> findAllOrderDetail(){
		return odr.findAll();
	}

}

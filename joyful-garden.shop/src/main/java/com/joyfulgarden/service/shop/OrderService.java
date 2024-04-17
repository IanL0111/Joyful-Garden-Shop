package com.joyfulgarden.service.shop;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joyfulgarden.model.shop.Order;
import com.joyfulgarden.model.shop.OrderRepository;
	
	@Service
	@Transactional
	public class OrderService {
		
		@Autowired
		private OrderRepository or ;
		
		public Order insert(Order od) {
			return or.save(od);
		}
		
		public Order update(Order od) {
			return or.save(od);
		}
		
		public void deleteById(Integer odID) {
			or.deleteById(odID);
		}
		
		public Order findById(Integer odID) {
			Optional<Order> od = or.findById(odID);
			if(od.isPresent())
				return od.get();
			else
				return null;
		}
		
		public List<Order> findAllOrder(){
			return or.findAll();
		}

}

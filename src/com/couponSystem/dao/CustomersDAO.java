package com.couponSystem.dao;

import java.util.List;

import com.couponSystem.beans.Coupon;
import com.couponSystem.beans.Customer;
import com.couponSystem.classes.CouponSystemException;

public interface CustomersDAO {

	boolean isCustomerExists(String email, String password) throws CouponSystemException;

	void addCustomer(Customer customer) throws CouponSystemException;

	void updateCustomer(Customer customer) throws CouponSystemException;

	void deleteCustomer(int customerID) throws CouponSystemException;

	List<Customer> getAllCustomers() throws CouponSystemException;

	Customer getOneCustomer(int customerID) throws CouponSystemException;

	List<Coupon> getAllCoupons() throws CouponSystemException;

	Coupon getCouponsByCategoryID(int categoryID) throws CouponSystemException;

	Coupon getCouponsByMaxPrice(double maxPrice) throws CouponSystemException;

	void purCoupon(Coupon coupon, Customer customer) throws CouponSystemException;

	public void deleteCusVsCou(int customerId) throws CouponSystemException;

}

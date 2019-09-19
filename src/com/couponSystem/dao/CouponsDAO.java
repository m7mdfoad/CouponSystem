package com.couponSystem.dao;

import java.util.List;

import com.couponSystem.beans.Category;
import com.couponSystem.beans.Coupon;
import com.couponSystem.classes.CouponSystemException;

public interface CouponsDAO {
	/**
	 * 
	 * @param coupon
	 * @throws CouponSystemException
	 */
	public void addCupon(Coupon coupon) throws CouponSystemException;

	/**
	 * 
	 * @param coupon
	 * @throws CouponSystemException
	 */
	public void updateCoupon(Coupon coupon) throws CouponSystemException;

	/**
	 * 
	 * @param couponID
	 * @throws CouponSystemException
	 */
	public void deleteCoupon(int couponID) throws CouponSystemException;

	/**
	 * 
	 * @return list of coupons
	 * @throws CouponSystemException
	 */
	public List<Coupon> getAllCoupons() throws CouponSystemException;

	/**
	 * 
	 * @param coponID
	 * @return coupon
	 * @throws CouponSystemException
	 */
	public Coupon getOneCoupon(int coponID) throws CouponSystemException;

	/**
	 * 
	 * @param title
	 * @return true if coupon exists otherwise false
	 * @throws CouponSystemException
	 */
	public boolean couponExists(String title) throws CouponSystemException;

	/**
	 * 
	 * @param customerID
	 * @return list of coupons
	 * @throws CouponSystemException
	 */
	public List<Coupon> getCustomerCoupons(int customerID) throws CouponSystemException;

	/**
	 * 
	 * @param category
	 * @return list of coupons
	 * @throws CouponSystemException
	 */
	public List<Coupon> getCustomerCoupons(Category category) throws CouponSystemException;

	/**
	 * 
	 * @param maxPrice
	 * @return list of coupon
	 * @throws CouponSystemException
	 */
	public List<Coupon> getCustomerCoupons(double maxPrice) throws CouponSystemException;

	/**
	 * @return list of coupons
	 * @throws CouponSystemException
	 */
	public List<Coupon> dates() throws CouponSystemException;

	/**
	 * 
	 * @return expired coupons
	 * @throws CouponSystemException
	 */
	public int getExpired() throws CouponSystemException;

	/**
	 * 
	 * @param couponId
	 * @throws CouponSystemException
	 */
	public void deleteCusVsCou(int couponId) throws CouponSystemException;

	/**
	 * 
	 * @param CompanyId
	 * @throws CouponSystemException
	 */
	public void deleteCoupon1(int CompanyId) throws CouponSystemException;

}

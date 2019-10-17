package com.couponSystem.dao;

import java.util.List;

import com.couponSystem.beans.Category;
import com.couponSystem.beans.Company;
import com.couponSystem.beans.Coupon;
import com.couponSystem.classes.CouponSystemException;

public interface CompaniesDAO {

	/**
	 * 
	 * @param email
	 * @param password
	 * @return true or false
	 * @throws CouponSystemException
	 * @status test successfully
	 */
	boolean isCompanyExists(String email, String password) throws CouponSystemException;

	/**
	 * 
	 * @param company
	 * @throws CouponSystemException
	 * @status test successfully
	 */
	void addCompany(Company company) throws CouponSystemException;

	/**
	 * 
	 * @param company
	 * @throws CouponSystemException
	 */
	void updateCompany(Company company) throws CouponSystemException;

	/**
	 * 
	 * @param companyID
	 * @throws CouponSystemException
	 */
	void deleteCompany(int companyID) throws CouponSystemException;

	/**
	 * 
	 * @return list of companies
	 * @throws CouponSystemException
	 */
	List<Company> getAllCompanies() throws CouponSystemException;

	/**
	 * 
	 * @param companyID
	 * @return company
	 * @throws CouponSystemException
	 */
	Company getOneCompany(int companyID) throws CouponSystemException;

	/**
	 * 
	 * @param company_id
	 * @return list of coupons
	 * @throws CouponSystemException
	 */
	List<Coupon> getCompanyCoupons(int company_id) throws CouponSystemException;

	/**
	 * 
	 * @param category
	 * @return list of Coupons
	 * @throws CouponSystemException
	 */
	List<Coupon> getCompanyCoupons(Category category, int companyId) throws CouponSystemException;

	/**
	 * 
	 * @param maxPrice
	 * @param companyId
	 * @param companyId
	 * @return list of Coupons
	 * @throws CouponSystemException
	 */
	List<Coupon> getCompanyCoupons(double maxPrice, int companyId) throws CouponSystemException;

	/**
	 * 
	 * @param companyId
	 * @throws CouponSystemException
	 */
	void deletePurchased(int companyId) throws CouponSystemException;

	/**
	 * 
	 * @param id
	 * @return coupon by id
	 * @throws CouponSystemException
	 */
	Coupon getOneCoupon(int id, int companyId) throws CouponSystemException;

}

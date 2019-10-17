/**
 * 
 */
package com.couponSystem;

import java.sql.Date;

import com.couponSystem.beans.Category;
import com.couponSystem.beans.Coupon;
import com.couponSystem.classes.CouponSystemException;
import com.couponSystem.dao.CompaniesDAO;
import com.couponSystem.dao.CouponsDAO;
import com.couponSystem.dao.CustomersDAO;
import com.couponSystem.dbdao.CompaniesDBDAO;
import com.couponSystem.dbdao.CouponDBDAO;
import com.couponSystem.dbdao.CustomerDBDAO;
import com.couponSystem.facade.AdminFacade;
import com.couponSystem.facade.CustomerFacade;

/**
 * @author scary
 *
 */
public class FacadeTest {

	public static void main(String[] args) {

		CompaniesDAO companiesDAO = new CompaniesDBDAO();
		CustomersDAO customersDAO = new CustomerDBDAO();
		CouponsDAO couponsDAO = new CouponDBDAO();

		AdminFacade adminFacade = new AdminFacade(companiesDAO, customersDAO, couponsDAO);
//		Company company = new Company(12, "zaza", "zaza@zaz.com", "zaz123");
//		try {
//			adminFacade.deleteCompany(11);
//		} catch (CouponSystemException e) {

//			e.printStackTrace();
//		}
//		passed
//		try {
//			adminFacade.addCompany(company);
//		} catch (CouponSystemException e) {

//			e.printStackTrace();
//		}
//		passed
//		try {
//			adminFacade.updateCompany(company);
//		} catch (CouponSystemException e) {

//			e.printStackTrace();
//		}
//		passed
//		try {
//			adminFacade.isCompanyExists("zaza@zaz.com", "zaz123");
//		} catch (CouponSystemException e) {

//			e.printStackTrace();
//		}
//		passed
//		try {
//			adminFacade.getOneCompany(3);
//		} catch (CouponSystemException e) {

//			e.printStackTrace();
//		}
//		passed
//		try {
//			adminFacade.getAllCompanies();
//		} catch (CouponSystemException e) {

//			e.printStackTrace();
//		}
//		passed
//		try {
//			adminFacade.deleteCompany(16);
//		} catch (CouponSystemException e) {

//			e.printStackTrace();
//		}
//		passed
//		try {
//			adminFacade.getCompanyCoupons(15);
//		} catch (CouponSystemException e) {

//			e.printStackTrace();
//		}
//		passed
//		Customer customer = new Customer(1, "muhammad", "abu alhija", "muh11@gmail.com", "m1123");
//		try {
//			adminFacade.updateCustomer(customer);
//		} catch (CouponSystemException e) {

//			e.printStackTrace();
//		}
//		passed
//		try {
//			adminFacade.isCustomerExists("muh@gmail.com", "m123");
//		} catch (CouponSystemException e) {

//			e.printStackTrace();
//		}
//		passed
//		try {
//			adminFacade.getOneCustomer(5);
//		} catch (CouponSystemException e) {

//			e.printStackTrace();
//		}
//		passed
//		try {
//			adminFacade.getAllCustomers();
//		} catch (CouponSystemException e) {

//			e.printStackTrace();
//		}
//		try {
//			adminFacade.deleteCustomer(8);
//		} catch (CouponSystemException e) {

//			e.printStackTrace();
//		}
//		passed
//		Customer customer = new Customer("dad", "bad", "dad@bad.com", "dad123");
//		try {
//			adminFacade.addCustomer(customer);
//		} catch (CouponSystemException e) {

//			e.printStackTrace();
//		}
//		passed
//		Date sdate = Date.valueOf("2018-09-20");
//		Date edate = Date.valueOf("2019-09-20");
//		Coupon coupon = new Coupon(6, Category.BOOKS, "harry potter", "15% off", sdate, edate, 20, 14.99, "HP.jpg", 16,
//				13);
//		try {
//			adminFacade.updateCoupon(coupon);
//		} catch (CouponSystemException e) {

//			e.printStackTrace();
//		}
//		passed
//		try {
//			adminFacade.getOneCoupon(7);
//		} catch (CouponSystemException e) {

//			e.printStackTrace();
//		}
//		passed
//		try {
//			adminFacade.getAllCoupons();
//		} catch (CouponSystemException e) {

//			e.printStackTrace();
//		}
//		passed
//		try {
//			adminFacade.deleteCoupon(5);
//		} catch (CouponSystemException e) {

//			e.printStackTrace();
//		}
//		passed
//		Date sdate = Date.valueOf("2019-09-20");
//		Date edate = Date.valueOf("2020-09-20");
//		Coupon coupon = new Coupon(Category.BOOKS, "game of thrones", "20% off", sdate, edate, 20, 19.99, "GOT.jpg", 16,
//				13);
//		try {
//			adminFacade.addCupon(coupon);
//		} catch (CouponSystemException e) {

//			e.printStackTrace();
//		}
//		passed
//		try {
//			System.out.println(adminFacade.couponExists("game o1f thrones"));
//		} catch (CouponSystemException e) {

//			e.printStackTrace();
//		}
//		passed
//		try {
//			adminFacade.getExpired();
//		} catch (CouponSystemException e) {

//			e.printStackTrace();
//		}
//		passed

		/*
		 * admin facade passed successfully
		 */

		CustomerFacade customerFacade = new CustomerFacade(companiesDAO, customersDAO, couponsDAO);
		try {
			customerFacade.login("muh@gmail.com", "m123");
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}

		Date sdate = Date.valueOf("2018-11-30");
		Date edate = Date.valueOf("2019-11-01");
		Coupon coupon = new Coupon(15, Category.CARS, "Hyundai", "25% off", sdate, edate, 6, 499.99, "hyundai.jpg", 15,
				11);
//		try {
//			customerFacade.purCoupon(coupon);
//		} catch (CouponSystemException e) {
//			e.printStackTrace();
//		}
//		passed
//		try {
//			customerFacade.getCustomerCoupons();
//		} catch (CouponSystemException e) {

//			e.printStackTrace();
//		}
//		passed
//		try {
//			customerFacade.getCustomerCoupons(Category.BOOKS);
//		} catch (CouponSystemException e) {

//			e.printStackTrace();
//		}
//		passed
//		try {
//			customerFacade.getCustomerCoupons(11.00);
//		} catch (CouponSystemException e) {

//			e.printStackTrace();
//		}
//		passed
//		try {
//			customerFacade.getCustomerDetails();
//		} catch (CouponSystemException e) {

//			e.printStackTrace();
//		}
//		passed
//		try {
//			customerFacade.getCustomerID();
//		} catch (CouponSystemException e) {

//			e.printStackTrace();
//		}
//		passed
//		try {
//			System.out.println(customerFacade.isCustomerExists("muh@gmail.com", "m123"));
//		} catch (CouponSystemException e) {

//			e.printStackTrace();
//		}
//		passed

		/*
		 * customer facade passed successfully
		 */
//		CompanyFacade companyFacade = new CompanyFacade(companiesDAO, customersDAO, couponsDAO);
//		Date sdate = Date.valueOf("2018-11-30");
//		Date edate = Date.valueOf("2019-11-01");
//		Coupon coupon = new Coupon(Category.CARS, "BMW", "25% off", sdate, edate, 5, 999.99, "BMW.jpg", 15, 11);
//		Coupon coupon1 = new Coupon(15, Category.CARS, "BMW", "25% off", sdate, edate, 5, 799.99, "BMW.jpg", 15, 11);
//		Company company = new Company(15, "zaza", "zaza@zaz.com", "zaza123");
//		try {
//			companyFacade.login(company.getEmail(), company.getPassword());
//		} catch (CouponSystemException e) {

//			e.printStackTrace();
//		}
//		passed
//		try {
//			companyFacade.addCoupon(coupon);
//		} catch (CouponSystemException e) {

//			e.printStackTrace();
//		}
//		passed
//		try {
//			companyFacade.updateCoupon(coupon1);
//		} catch (CouponSystemException e) {
//			// 
//			e.printStackTrace();
//		}
//		passed
//		try {
//			companyFacade.deleteCoupon(16);
//		} catch (CouponSystemException e) {
//			// 
//			e.printStackTrace();
//		}
//		passed
//		try {
//			companyFacade.getCompanyCoupons();
//		} catch (CouponSystemException e) {

//			e.printStackTrace();
//		}
//		passed
//		try {
//			companyFacade.getCompanyCoupons(Category.CARS);
//		} catch (CouponSystemException e) {

//			e.printStackTrace();
//		}
//		passed
//		try {
//			companyFacade.getCompanyCoupons(800.00);
//		} catch (CouponSystemException e) {

//			e.printStackTrace();
//		}
//		passed
//		try {
//			companyFacade.getCompanyDetails();
//		} catch (CouponSystemException e) {

//			e.printStackTrace();
//		}
//		passed
//		try {
//			System.out.println(companyFacade.getCompanyId());
//		} catch (CouponSystemException e) {
//			
//			e.printStackTrace();
//		}
//		passed

		/*
		 * company facade passed successfully
		 */
	}
}

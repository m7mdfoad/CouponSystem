/**
 * 
 */
package com.couponSystem;

import java.sql.Date;

import com.couponSystem.beans.Category;
import com.couponSystem.beans.Company;
import com.couponSystem.beans.Coupon;
import com.couponSystem.beans.Customer;
import com.couponSystem.dbdao.CompaniesDBDAO;
import com.couponSystem.dbdao.CouponDBDAO;
import com.couponSystem.dbdao.CustomerDBDAO;

/**
 * @author scary
 *
 */
public class DbdaosTest1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Date sdate = new Date(2018 - 03 - 13);
		Date edate = new Date(2019 - 03 - 13);

		CompaniesDBDAO cd = new CompaniesDBDAO();
		Customer cus = new Customer(3);
		CustomerDBDAO cu = new CustomerDBDAO();
		Company company = new Company("intel", "int@int.com", "int12");
		Coupon coupon = new Coupon(4, Category.CELLULAR, "15% off", "everything in the store", sdate, edate, 20, 14.99,
				"aaa.jpg", 3, 10);
		CouponDBDAO db = new CouponDBDAO();

//		try {
//			cd.addCompany(company);
//		} catch (CouponSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////		test successfully
//
//		try {
//			cd.isCompanyExists("bbb@bbc.com", "aaa111");
//		} catch (CouponSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			cd.getAllCompanies();
//		} catch (CouponSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		passed
//		try {
//			cd.isCompanyExists("bbb@bbc11.com", "aaa111");
//		} catch (CouponSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////		test successfully
//
//		try {
//			cd.updateCompany(company);
//		} catch (CouponSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////		test successfully
//
//		try {
//			cd.deleteCompany(8);
//		} catch (CouponSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////		test successfully
//
//		try {
//			cd.getOneCompany(3);
//		} catch (CouponSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////		test successfully
//
//		try {
//			cd.getCompanyCoupons(1);
//		} catch (CouponSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////		test successfully
//
//		try {
//			System.out.println(cu.isCustomerExists("a.m7md.f@gmail.com", "123m"));
//			System.out.println("-------------------------");
//			System.out.println(cu.isCustomerExists("a.m7md.f@gmail.com", "1231m"));
//			System.out.println("-------------------------");
//			System.out.println(cu.isCustomerExists("1a.m7md.f@gmail.com", "1231m"));
//		} catch (CouponSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////		test successfully
//
//		Customer customer = new Customer("abdallah1", "mrisat1", "abd1@abd.com", "abd12");
//		try {
//			cu.addCustomer(customer);
//		} catch (CouponSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////		test successfully
//
//		try {
//			cu.updateCustomer(cus);
//		} catch (CouponSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////		passed
//		try {
//			cu.deleteCustomer(5);
//		} catch (CouponSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////		passed
//		try {
//			cu.getAllCustomers();
//		} catch (CouponSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////		passed
//		try {
//			cu.getOneCustomer(4);
//		} catch (CouponSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////		passed
//		try {
//			cu.getAllCoupons();
//		} catch (CouponSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////		passed
//		try {
//			cu.getCouponsByCategoryID(10);
//		} catch (CouponSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////		passed
//		try {
//			cu.CustomerVsCouponExists(cus, coupon);
//		} catch (CouponSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		passed
//		try {
//			cu.purCoupon(coupon, cus);
//		} catch (CouponSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		passes
//		try {
//			db.addCupon(coupon);
//		} catch (CouponSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		passed
//		try {
//			db.updateCoupon(coupon);
//		} catch (CouponSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		passed
//		try {
//			db.deleteCoupon(2);
//		} catch (CouponSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		passed
//		try {
//			db.getAllCoupons();
//		} catch (CouponSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		passed
//		try {
//			db.getOneCoupon(4);
//		} catch (CouponSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		passed
//		try {
//			db.couponExists("15% off");
//		} catch (CouponSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		passed
//		try {
//			db.getCustomerCoupons(3);
//		} catch (CouponSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		passed
//		try {
//			db.dates();
//		} catch (CouponSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		passed
//		try {
//			db.getExpired();
//		} catch (CouponSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		passed
//		try {
//			db.deleteCusVsCou(3);
//		} catch (CouponSystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		passed

		/*
		 * all dbdaos passed successfully
		 */

	}

}

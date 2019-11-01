package com.couponSystem.facade;

import java.util.List;
import java.util.Scanner;

import com.couponSystem.beans.Company;
import com.couponSystem.beans.Coupon;
import com.couponSystem.beans.Customer;
import com.couponSystem.classes.CouponSystemException;
import com.couponSystem.dao.CompaniesDAO;
import com.couponSystem.dao.CouponsDAO;
import com.couponSystem.dao.CustomersDAO;
import com.couponSystem.pool.ConnectionPool;

public class AdminFacade extends ClientFacade {

	ConnectionPool connectionPool;
	Scanner sc = new Scanner(System.in);
	String[] options = { "ADMINISTRATOR", "COMPANY", "CUSTOMER" };

	@Override
	public boolean login(String email, String password) {
		if (email.equals("admin") && password.equals("1234")) {
//			System.out.println("enter 'help' for help");
			return true;
//		} else {
//			System.out.println("wrong username or password please try again");
//			System.out.println("enter username:");
//			String user = sc.nextLine();
//			System.out.println("enter password: ");
//			String pass = sc.nextLine();
//			System.out.println("enter type: ");
//			login(user, pass);
		}
		return false;
	}

	public AdminFacade(CompaniesDAO companiesDao, CustomersDAO customersDao, CouponsDAO couponsDao) {
		super(companiesDao, customersDao, couponsDao);
	}

	public void addCompany(Company company) throws CouponSystemException {
		if (!isCompanyExists(company.getEmail(), company.getPassword())) {
			companiesDao.addCompany(company);
		} else {
			System.out.println("this company is already registered");
		}
	}

	public void addCustomer(Customer customer) throws CouponSystemException {
		customersDao.addCustomer(customer);
	}

	public void addCupon(Coupon coupon) throws CouponSystemException {
		if (!couponsDao.couponExists(coupon.getTitle())) {
			couponsDao.addCupon(coupon);
		} else {
			System.out.println("this coupon exists");
		}
	}

	public void updateCompany(Company company, String oldEmail, String oldPassword) throws CouponSystemException {
		System.out.print("enter the current email: ");
		String oEmail = sc.nextLine();
		System.out.print("enter current password: ");
		String oPass = sc.nextLine();
		if (!(isCompanyExists(oEmail, oPass))) {
			System.out.println("company isn't exists");
		} else {
			companiesDao.updateCompany(company);
		}
	}

	public void updateCustomer(Customer customer) throws CouponSystemException {
		customersDao.updateCustomer(customer);
	}

	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		couponsDao.updateCoupon(coupon);
	}

	public Company getOneCompany(int companyID) throws CouponSystemException {
		return companiesDao.getOneCompany(companyID);
	}

	public Customer getOneCustomer(int customerID) throws CouponSystemException {
		return customersDao.getOneCustomer(customerID);
	}

	public Coupon getOneCoupon(int couponID) throws CouponSystemException {
		return couponsDao.getOneCoupon(couponID);
	}

	public List<Customer> getAllCustomers() throws CouponSystemException {
		return customersDao.getAllCustomers();
	}

	public List<Coupon> getAllCoupons() throws CouponSystemException {
		return couponsDao.getAllCoupons();
	}

	public List<Coupon> getCompanyCoupons(int id) throws CouponSystemException {

		return companiesDao.getCompanyCoupons(id);
	}

	public boolean isCompanyExists(String email, String password) throws CouponSystemException {
		return companiesDao.isCompanyExists(email, password);
	}

	public List<Company> getAllCompanies() throws CouponSystemException {
		return companiesDao.getAllCompanies();
	}

	public void deleteCompany(int companyID) throws CouponSystemException {
		companiesDao.deletePurchased(companyID);
		couponsDao.deleteCoupon1(companyID);
		companiesDao.deleteCompany(companyID);

	}

	public void deleteCustomer(int customerID) throws CouponSystemException {
		customersDao.deleteCusVsCou(customerID);
		customersDao.deleteCustomer(customerID);
	}

	public void deleteCoupon(int couponID) throws CouponSystemException {
		couponsDao.deleteCusVsCou(couponID);
		couponsDao.deleteCoupon(couponID);
	}

	public boolean isCustomerExists(String email, String password) throws CouponSystemException {
		return customersDao.isCustomerExists(email, password);
	}

	public boolean couponExists(String title) throws CouponSystemException {
		return couponsDao.couponExists(title);
	}

	public int getExpired() throws CouponSystemException {
		return couponsDao.getExpired();

	}

}
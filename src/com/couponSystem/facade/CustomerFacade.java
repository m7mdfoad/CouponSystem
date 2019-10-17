package com.couponSystem.facade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.couponSystem.beans.Category;
import com.couponSystem.beans.Coupon;
import com.couponSystem.beans.Customer;
import com.couponSystem.classes.CouponSystemException;
import com.couponSystem.dao.CompaniesDAO;
import com.couponSystem.dao.CouponsDAO;
import com.couponSystem.dao.CustomersDAO;
import com.couponSystem.dbdao.CompaniesDBDAO;
import com.couponSystem.dbdao.CouponDBDAO;
import com.couponSystem.dbdao.CustomerDBDAO;
import com.couponSystem.pool.ConnectionPool;

public class CustomerFacade extends ClientFacade {

	private int customerID;
	private Customer customer = new Customer();
	ConnectionPool connectionPool;
	Scanner sc = new Scanner(System.in);
	String[] options = { "ADMINISTRATOR", "COMPANY", "CUSTOMER" };

	public CustomerFacade(CompaniesDAO companiesDao, CustomersDAO customersDao, CouponsDAO couponsDao) {
		super(companiesDao, customersDao, couponsDao);
		try {
			connectionPool = ConnectionPool.getInstance();
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		companiesDao = new CompaniesDBDAO();
		customersDao = new CustomerDBDAO();
		couponsDao = new CouponDBDAO();
	}

	@Override
	public boolean login(String email, String password) throws CouponSystemException {
		String sql = "select * from customers where email=? and password=?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				customer.setId(this.customerID = rs.getInt("id"));
				customer.setFirstName(rs.getString("first_name"));
				customer.setLastName(rs.getString("last_name"));
				customer.setEmail(rs.getString("email"));
				customer.setPassword(rs.getString("password"));
				System.out.println("you are connected to customers");
				return true;
			} else {
//				System.out.println("wrong username or password please try again");
//				System.out.println("enter email:");
//				String user = sc.nextLine();
//				System.out.println("enter password: ");
//				String pass = sc.nextLine();
//				System.out.println("enter type: ");
//				JOptionPane.showInputDialog(null, "Choose one", "Input", JOptionPane.INFORMATION_MESSAGE, null, options,
//						options[0]);
//				login(user, pass);
				return false;
			}
		} catch (SQLException e) {
			throw new CouponSystemException("there are error accessing the account", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	public void purCoupon(Coupon coupon) throws CouponSystemException {
		customersDao.purCoupon(coupon, customer);

	}

	public List<Coupon> getCustomerCoupons() throws CouponSystemException {
		return couponsDao.getCustomerCoupons(customerID);

	}

	public List<Coupon> getCustomerCoupons(Category category) throws CouponSystemException {
		return couponsDao.getCustomerCoupons(category);

	}

	public List<Coupon> getCustomerCoupons(double maxPrice) throws CouponSystemException {
		return couponsDao.getCustomerCoupons(maxPrice);

	}

	public Customer getCustomerDetails() throws CouponSystemException {
		return customersDao.getOneCustomer(customerID);

	}

	public int getCustomerID() throws CouponSystemException {
		System.out.println(customerID);
		return customerID;
	}

	public boolean isCustomerExists(String email, String password) throws CouponSystemException {
		return customersDao.isCustomerExists(email, password);

	}

//	public void help() {
//		System.out.println("main: ");
//		System.out.println("to purchaseCoupon coupon type--> purCoupon");
//		System.out.println("to get Customer Coupons type--> getCoupon");
//		System.out.println("to get Customer Details type--> getcustomer");
//		System.out.println("for help type--> help  ");
//	}

}

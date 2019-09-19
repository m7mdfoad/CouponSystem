package com.couponSystem.facade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import com.couponSystem.UIDemo;
import com.couponSystem.beans.Category;
import com.couponSystem.beans.Company;
import com.couponSystem.beans.Coupon;
import com.couponSystem.classes.CouponSystemException;
import com.couponSystem.dao.CompaniesDAO;
import com.couponSystem.dao.CouponsDAO;
import com.couponSystem.dao.CustomersDAO;
import com.couponSystem.pool.ConnectionPool;

public class CompanyFacade extends ClientFacade {

	private int companyId;
	Company company = new Company();
	ConnectionPool connectionPool;
	UIDemo ui = new UIDemo();
	Scanner sc = new Scanner(System.in);
	String[] options = { "ADMINISTRATOR", "COMPANY", "CUSTOMER" };

	public CompanyFacade(CompaniesDAO companiesDao, CustomersDAO customersDao, CouponsDAO couponsDao) {
		super(companiesDao, customersDao, couponsDao);
		try {
			connectionPool = ConnectionPool.getInstance();
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean login(String email, String password) throws CouponSystemException {
		String sql = "select * from companies where email=? and password=?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				company.setId(this.companyId = rs.getInt("id"));
				company.setName(rs.getString("name"));
				company.setEmail(rs.getString("email"));
				company.setPassword(rs.getString("password"));
				System.out.println("you are connected to companies");
				return true;
			} else {
				System.out.println("wrong username or password please try again");
				System.out.println("enter email:");
				String user = sc.nextLine();
				System.out.println("enter password: ");
				String pass = sc.nextLine();
				System.out.println("enter type: ");
				JOptionPane.showInputDialog(null, "Choose one", "Input", JOptionPane.INFORMATION_MESSAGE, null, options,
						options[0]);
				login(user, pass);
				return false;
			}
		} catch (SQLException e) {
			throw new CouponSystemException("there are error accessing the account", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	public void addCoupon(Coupon coupon) throws CouponSystemException {
		try {
			if (!couponsDao.couponExists(coupon.getTitle())) {
				couponsDao.addCupon(coupon);
			} else {
				System.out.println("this coupon is already exists");
			}
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}

	}

	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		try {
			couponsDao.updateCoupon(coupon);
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
	}

	public void deleteCoupon(int couponId) throws CouponSystemException {
		try {

			couponsDao.deleteCoupon(couponId);
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
	}

	public List<Coupon> getCompanyCoupons() throws CouponSystemException {
		try {
			return companiesDao.getCompanyCoupons(companyId);
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
		return getCompanyCoupons();

	}

	public ArrayList<Coupon> getCompanyCoupons(Category category) throws CouponSystemException {
		companiesDao.getCompanyCoupons(category, companyId);
		return null;

	}

	public ArrayList<Coupon> getCompanyCoupons(double maxPrice) throws CouponSystemException {
		companiesDao.getCompanyCoupons(maxPrice, companyId);
		return null;

	}

	public Company getCompanyDetails() throws CouponSystemException {
		companiesDao.getOneCompany(companyId);
		return null;

	}

	public void help() {
		System.out.println("main: ");
		System.out.println("to add new Coupon type--> addCoupon");
		System.out.println("to update Coupon type--> updateCoupon");
		System.out.println("to delete Coupon type--> deleteCoupon");
		System.out.println("to get Company Coupons type--> CompanyCoupons");
		System.out.println("to get Company Details type--> getCompany");
		System.out.println("to get Company Id type--> getCompanyId");
		System.out.println("for help type--> help  ");
	}
}

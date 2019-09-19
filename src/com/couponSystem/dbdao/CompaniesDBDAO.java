package com.couponSystem.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.couponSystem.beans.Category;
import com.couponSystem.beans.Company;
import com.couponSystem.beans.Coupon;
import com.couponSystem.classes.CouponSystemException;
import com.couponSystem.dao.CompaniesDAO;
import com.couponSystem.pool.ConnectionPool;

public class CompaniesDBDAO implements CompaniesDAO {
	Scanner sc = new Scanner(System.in);
	int id;
	private ConnectionPool connectionPool;

	public CompaniesDBDAO() {
		try {
			connectionPool = ConnectionPool.getInstance();
			System.out.println("you are connected");
		} catch (CouponSystemException e) {
			System.out.println("can't get connection");
			e.printStackTrace();
		}
	}

	@Override
	public boolean isCompanyExists(String email, String password) throws CouponSystemException {
		String sql = "select * from companies where email=? and password=?";
		List<Company> companies = new ArrayList<Company>();
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Company company = new Company(id);
				company.setId(rs.getInt("id"));
				company.setName(rs.getString("name"));
				company.setEmail(rs.getString("email"));
				company.setPassword(rs.getString("password"));
				companies.add(company);
			}
		} catch (SQLException e) {
			throw new CouponSystemException("we can't read companies", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
		if (companies.size() > 0) {
			return true;
		}
		return false;

	}

	@Override
	public void addCompany(Company company) throws CouponSystemException {
		if (isCompanyExists(company.getEmail(), company.getPassword())) {
			System.out.println("this company is exists.");
		} else {
			String sql = "insert into COMPANIES(name,email,password) values(?,?,?)";

			Connection con = connectionPool.getConnection();
			try (PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {
				pstmt.setString(1, company.getName());
				pstmt.setString(2, company.getEmail());
				pstmt.setString(3, company.getPassword());
				pstmt.executeUpdate();

				System.out.println("company " + company.getName() + " was added successfully");
			} catch (SQLException e) {
				throw new CouponSystemException("the company didn't added please check parameters and try again", e);
			} finally {
				connectionPool.restoreConnection(con);

			}
		}
	}

	@Override
	public void updateCompany(Company company) throws CouponSystemException {
		String sql = "update companies set name=?,email=?,password=? where ID=?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, company.getName());
			pstmt.setString(2, company.getEmail());
			pstmt.setString(3, company.getPassword());
			pstmt.setInt(4, company.getId());
			pstmt.executeUpdate();

			System.out.println("company updated successfully");
		} catch (SQLException e) {
			throw new CouponSystemException("the company didn't updated, please check parameters and try again", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	@Override
	public void deleteCompany(int companyID) throws CouponSystemException {
		String sql = "delete from companies where id=?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, companyID);
			connectionPool.restoreConnection(con);
			if (pstmt.executeUpdate() != 0) {
				System.out.println("company number: " + companyID + " deleted successfully");
			} else {
				System.out.println("there are no company with id " + companyID);
			}
		} catch (SQLException e) {
			throw new CouponSystemException("something went wrong deleting coupon", e);
		} finally {
			connectionPool.restoreConnection(con);
		}

	}

	@Override
	public List<Company> getAllCompanies() throws CouponSystemException {
		String sql = "select * from companies";
		List<Company> companies = new ArrayList<Company>();
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					Company company = new Company(id);
					company.setId(rs.getInt("id"));
					company.setName(rs.getString("name"));
					company.setEmail(rs.getString("email"));
					company.setPassword(rs.getString("password"));
					companies.add(company);
					System.out.println(company);
				} while (rs.next());
			} else {
				System.out.println("there is no companies");
			}
		} catch (SQLException e) {
			throw new CouponSystemException("we can't read all companies", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
		return companies;
	}

	@Override
	public Company getOneCompany(int companyID) throws CouponSystemException {
		String sql = "select * from companies where id=?";
		Company company = new Company(id);
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			pstmt.setInt(1, companyID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				company.setId(companyID);
				company.setName(rs.getString("name"));
				company.setEmail(rs.getString("email"));
				company.setPassword(rs.getString("password"));
				System.out.println(company);
			} else {
				System.out.println("company isn't exists");
			}
		} catch (SQLException e) {
			throw new CouponSystemException("error reading a company " + companyID, e);
		} finally {
			connectionPool.restoreConnection(con);
		}
		return company;

	}

	@Override
	public List<Coupon> getCompanyCoupons(int company_id) throws CouponSystemException {
		String sql = "select * from coupons left join categories on coupons.CATEGORY_ID = categories.ID  where company_Id=?";
		List<Coupon> comcou = new ArrayList<>();
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			Coupon coupon = new Coupon();
			pstmt.setInt(1, company_id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					coupon.setId(rs.getInt("id"));
					coupon.setTitle(rs.getString("title"));
					coupon.setDescription(rs.getString("DESCRIPTION"));
					coupon.setStartDate(rs.getDate("start_Date"));
					coupon.setEndDate(rs.getDate("end_Date"));
					coupon.setAmount(rs.getInt("amount"));
					coupon.setCompanyID(rs.getInt("company_id"));
					coupon.setCategoryID(rs.getInt("category_id"));
					coupon.setPrice(rs.getDouble("price"));
					coupon.setImage(rs.getString("image"));
					coupon.setCategory((Category.valueOf(rs.getString("name"))));
					comcou.add(coupon);
					System.out.println(coupon);
				} while (rs.next());
			} else {
				System.out.println("there are no coupons");
			}
		} catch (SQLException e) {
			throw new CouponSystemException("we can't read company coupons", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
		return comcou;
	}

	@Override
	public List<Coupon> getCompanyCoupons(Category category, int companyId) throws CouponSystemException {
		String sql = "select * from coupons inner join categories c on coupons.CATEGORY_ID = c.ID where coupons.ID=? and NAME=?";
		List<Coupon> comcou = new ArrayList<>();
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			Coupon coupon = new Coupon();
			pstmt.setInt(1, companyId);
			pstmt.setString(2, String.valueOf(category));
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					coupon.setId(rs.getInt("id"));
					coupon.setTitle(rs.getString("title"));
					coupon.setDescription(rs.getString("DESCRIPTION"));
					coupon.setStartDate(rs.getDate("start_Date"));
					coupon.setEndDate(rs.getDate("end_Date"));
					coupon.setAmount(rs.getInt("amount"));
					coupon.setCompanyID(rs.getInt("company_id"));
					coupon.setCategoryID(rs.getInt("category_id"));
					coupon.setPrice(rs.getDouble("price"));
					coupon.setImage(rs.getString("image"));
					coupon.setCategory((Category.valueOf(rs.getString("name"))));
					comcou.add(coupon);
					System.out.println(coupon);
				} while (rs.next());
			} else {
				System.out.println("there are no coupons");
			}
		} catch (SQLException e) {
			throw new CouponSystemException("we can't read company coupons", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
		return comcou;

	}

	@Override
	public List<Coupon> getCompanyCoupons(double maxPrice, int companyId) throws CouponSystemException {
		String sql = "select * from coupons inner join categories c on coupons.CATEGORY_ID = c.ID where coupons.ID=? and price<=?";
		List<Coupon> comcou = new ArrayList<>();
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			Coupon coupon = new Coupon();
			pstmt.setInt(1, companyId);
			pstmt.setDouble(2, maxPrice);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					coupon.setId(rs.getInt("id"));
					coupon.setTitle(rs.getString("title"));
					coupon.setDescription(rs.getString("DESCRIPTION"));
					coupon.setStartDate(rs.getDate("start_Date"));
					coupon.setEndDate(rs.getDate("end_Date"));
					coupon.setAmount(rs.getInt("amount"));
					coupon.setCompanyID(rs.getInt("company_id"));
					coupon.setCategoryID(rs.getInt("category_id"));
					coupon.setPrice(rs.getDouble("price"));
					coupon.setImage(rs.getString("image"));
					coupon.setCategory((Category.valueOf(rs.getString("name"))));
					comcou.add(coupon);
					System.out.println(coupon);
				} while (rs.next());
			} else {
				System.out.println("there are no coupons");
			}
		} catch (SQLException e) {
			throw new CouponSystemException("we can't read company coupons", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
		return comcou;

	}

	@Override
	public void deletePurchased(int companyId) throws CouponSystemException {
		String sql = "select * from companies inner join coupons c on companies.ID = c.COMPANY_ID inner join customers_vs_coupons cvc on c.ID = cvc.COUPON_ID where company_id=?";
		String ex = "delete from customers_vs_coupons where COUPON_ID=?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			pstmt.setInt(1, companyId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				do {
					Coupon coupon = new Coupon();
					coupon.setId(this.id = rs.getInt(5));
					try (PreparedStatement pstmt1 = con.prepareStatement(ex, PreparedStatement.RETURN_GENERATED_KEYS)) {
						pstmt1.setInt(1, coupon.getId());
						if (pstmt1.executeUpdate() != 0) {
							System.out.println("coupons purchased deleted successfully");
						} else {
							System.out.println("nothing to delete");
						}
					} catch (SQLException e) {
						throw new CouponSystemException("something went wrong deleting coupon", e);
					}
				} while (rs.next());
			} else {
				System.out.println("there is no companies");
			}
		} catch (

		SQLException e) {
			throw new CouponSystemException("we can't read all companies", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

}

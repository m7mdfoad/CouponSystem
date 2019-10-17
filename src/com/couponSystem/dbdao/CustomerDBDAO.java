package com.couponSystem.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.couponSystem.beans.Category;
import com.couponSystem.beans.Coupon;
import com.couponSystem.beans.Customer;
import com.couponSystem.classes.CouponSystemException;
import com.couponSystem.dao.CouponsDAO;
import com.couponSystem.dao.CustomersDAO;
import com.couponSystem.pool.ConnectionPool;

public class CustomerDBDAO implements CustomersDAO {
	Scanner sc = new Scanner(System.in);
	private ConnectionPool connectionPool;
	int id;

	public CustomerDBDAO() {
		try {
			connectionPool = ConnectionPool.getInstance();
		} catch (CouponSystemException e) {
			System.out.println("cannot be connected" + e);
			e.printStackTrace();
		}
	}

	@Override
	public boolean isCustomerExists(String email, String password) throws CouponSystemException {
		String sql = "select * from Customers where email=? and password=?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			throw new CouponSystemException("something went wrong checking if customer exists", e);
		}
	}

	@Override
	public void addCustomer(Customer customer) throws CouponSystemException {
		String ex = "select * from customers";
		String sql = "insert into customers(first_name,last_name,email,password) values(?,?,?,?)";
		Connection con = connectionPool.getConnection();
		if (isCustomerExists(customer.getEmail(), customer.getPassword())) {
			try (PreparedStatement pstmt = con.prepareStatement(ex)) {
				System.out.println("customer is exists");

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			try (PreparedStatement pstmt = con.prepareStatement(sql)) {
				pstmt.setString(1, customer.getFirstName());
				pstmt.setString(2, customer.getLastName());
				pstmt.setString(3, customer.getEmail());
				pstmt.setString(4, customer.getPassword());
				pstmt.executeUpdate();
				System.out.println("customer " + customer.getFirstName() + " added successfully");
			} catch (SQLException e) {
				throw new CouponSystemException("cannot add customer", e);
			} finally {
				connectionPool.restoreConnection(con);
			}
		}
	}

	@Override
	public void updateCustomer(Customer customer) throws CouponSystemException {
		System.out.print("enter current email: ");
		String oEmail = sc.nextLine();
		System.out.print("enter current password: ");
		String oPass = sc.nextLine();
		if (!(isCustomerExists(oEmail, oPass))) {
			System.out.println("customer isn't exists");
		} else {
			String sql = "update CUSTOMERS set FIRST_NAME=?, LAST_NAME=?, EMAIL=?, PASSWORD=? where id=?";
			Connection con = connectionPool.getConnection();
			try (PreparedStatement pstmt = con.prepareStatement(sql)) {
				pstmt.setString(1, customer.getFirstName());
				pstmt.setString(2, customer.getLastName());
				pstmt.setString(3, customer.getEmail());
				pstmt.setString(4, customer.getPassword());
				pstmt.setInt(5, customer.getId());
				pstmt.executeUpdate();
				System.out.println("customer " + customer.getId() + " updated successfully");
			} catch (SQLException e) {
				throw new CouponSystemException("cannot update customer", e);
			} finally {
				connectionPool.restoreConnection(con);
			}
		}
	}

	@Override
	public void deleteCustomer(int customerID) throws CouponSystemException {
		String sql = "delete from customers where id=?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, customerID);
			if (pstmt.executeUpdate() != 0) {
				System.out.println("customer " + customerID + " deleted successfully");
			} else {
				System.out.println("this customer is not exists");
			}

		} catch (SQLException e) {
			throw new CouponSystemException("cannot delete customer", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	@Override
	public List<Customer> getAllCustomers() throws CouponSystemException {
		String sql = "select * from customers";
		Connection con = connectionPool.getConnection();
		List<Customer> customer1 = new ArrayList<Customer>();
		CouponsDAO couponsDAO = new CouponDBDAO();
		try (Statement stmt = con.createStatement()) {
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				do {
					Customer customer = new Customer(id);
					customer.setId(rs.getInt("id"));
					customer.setFirstName(rs.getString("first_Name"));
					customer.setLastName(rs.getString("last_Name"));
					customer.setEmail(rs.getString("email"));
					customer.setPassword(rs.getString("password"));
					customer.setCoupons((ArrayList<Coupon>) couponsDAO.getCustomerCoupons(id));
					customer1.add(customer);
					System.out.println(customer);
				} while (rs.next());
			} else {
				System.out.println("there are no customers");
			}
		} catch (SQLException e) {
			throw new CouponSystemException("cannot get customers", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
		return customer1;
	}

	@Override
	public Customer getOneCustomer(int customerID) throws CouponSystemException {
		String sql = "select * from customers where id=?";
		Connection con = connectionPool.getConnection();
		Customer customer = new Customer(customerID);
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, customerID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				customer.setId(rs.getInt("id"));
				customer.setFirstName(rs.getString("first_Name"));
				customer.setLastName(rs.getString("last_Name"));
				customer.setEmail(rs.getString("email"));
				customer.setPassword(rs.getString("password"));
				customer.setCoupons((ArrayList<Coupon>) getAllCoupons());
				System.out.println(customer);
			} else {
				System.out.println("no customers with this id");
			}
		} catch (SQLException e) {
			throw new CouponSystemException("cannot get customer", e);
		} finally {
			connectionPool.restoreConnection(con);
		}

		return customer;
	}

	@Override
	public List<Coupon> getAllCoupons() throws CouponSystemException {
		String sql = "select * from coupons";
		Connection con = connectionPool.getConnection();
		Coupon coupon = new Coupon(id);
		List<Coupon> coup = new ArrayList<Coupon>();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
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
				coup.add(coupon);
				System.out.println(coupon);
			}

		} catch (SQLException e) {
			throw new CouponSystemException("we can't read all companies", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
		return coup;
	}

	@Override
	public List<Coupon> getCouponsByCategoryID(int categoryID) throws CouponSystemException {
		String sql = "select * from coupons inner join categories c on coupons.CATEGORY_ID = c.ID where CATEGORY_ID=?";
		Connection con = connectionPool.getConnection();
		List<Coupon> customerCoupons = new ArrayList<>();
		Coupon coupon = new Coupon(categoryID);
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, String.valueOf(categoryID));
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
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
				coupon.setCategory(Category.valueOf(rs.getString("name")));
				customerCoupons.add(coupon);
				System.out.println(coupon);
			} else {
				System.out.println("no coupons in this category");
			}
		} catch (SQLException e) {
			throw new CouponSystemException("something went wrong getting coupon", e);
		} finally {

			connectionPool.restoreConnection(con);
		}
		return customerCoupons;
	}

	@Override
	public List<Coupon> getCouponsByMaxPrice(double maxPrice) throws CouponSystemException {
		String sql = "select * from coupons inner join categories c on coupons.CATEGORY_ID = c.ID where PRICE <=? ";
		Connection con = connectionPool.getConnection();
		List<Coupon> customercoupons = new ArrayList<>();
		Coupon coupon = new Coupon();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setDouble(1, maxPrice);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
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
				coupon.setCategory(Category.valueOf(rs.getString("name")));
				customercoupons.add(coupon);
				System.out.println(coupon);

			}
			System.out.println("end");
		} catch (SQLException e) {
			throw new CouponSystemException("something went wrong getting coupon", e);
		} finally {

			connectionPool.restoreConnection(con);
		}
		return customercoupons;
	}

	public void purCoupon(Coupon coupon, Customer customer) throws CouponSystemException {
		String sql1 = "select * from coupons where id=?";
		String sql2 = "INSERT INTO CUSTOMERS_VS_COUPONS(CUSTOMER_ID,COUPON_ID) VALUES (?, ?)";
		String sql3 = "update coupons set AMOUNT=(AMOUNT-1) where ID=?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql1, PreparedStatement.RETURN_GENERATED_KEYS)) {
			pstmt.setInt(1, coupon.getId());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
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
				if (coupon.getAmount() > 0) {
					try (PreparedStatement pstmt1 = con.prepareStatement(sql2)) {
						if (CustomerVsCouponExists(customer, coupon) == false) {
							pstmt1.setInt(1, customer.getId());
							pstmt1.setInt(2, coupon.getId());
							pstmt1.executeUpdate();
							try (PreparedStatement pstmt2 = con.prepareStatement(sql3,
									PreparedStatement.RETURN_GENERATED_KEYS)) {
								pstmt2.setInt(1, coupon.getId());
								pstmt2.executeUpdate();
							}
							System.out.println("coupon purchased successfully");
						} else {
							System.out.println("coupon is already purchased");
						}
					}
				} else {
					System.out.println("coupon out of stock");
				}
			} else {
				System.out.println("ther is no coupons with that id");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			connectionPool.restoreConnection(con);
		}
	}

	public boolean CustomerVsCouponExists(Customer customer, Coupon coupon) throws CouponSystemException {
		String sql = "select * from Customers_vs_coupons where customer_id=? and coupon_Id=?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			pstmt.setInt(1, customer.getId());
			pstmt.setInt(2, coupon.getId());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			throw new CouponSystemException("something went wrong checking if customer exists", e);
		}
	}

	public void deleteCusVsCou(int customerId) throws CouponSystemException {
		String sql = "delete from CUSTOMERS_VS_COUPONS where Customer_ID=?";
		Connection con = connectionPool.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, customerId);
			pstmt.executeUpdate();
			System.out.println("coupons purchased deleted successfully");
		} catch (SQLException e) {
			throw new CouponSystemException("something went wrong deleting coupon", e);
		} finally {
			connectionPool.restoreConnection(con);
		}
	}
}

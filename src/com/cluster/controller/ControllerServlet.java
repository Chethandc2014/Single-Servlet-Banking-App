package com.cluster.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cluster.util.Validation;

public class ControllerServlet extends HttpServlet {
	Connection con = null;

	public ControllerServlet() {

		System.out.println("Inside constructor");
	}

	public static Context getContext() throws NamingException {
		Hashtable hashtable = new Hashtable();
		hashtable.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		hashtable.put(Context.PROVIDER_URL,"localhost:1099");
		Context context = new InitialContext(hashtable);
		return context;

	}

	public void init(ServletConfig config) throws ServletException {

		try {
			Class.forName(config.getInitParameter("driver"));
			System.out.println("driver is loaded");
			con = DriverManager.getConnection(config.getInitParameter("dburl"),
					config.getInitParameter("dbnme"),
					config.getInitParameter("pwd"));
			System.out.println("gont the db connection");
			System.out.println("driver is loaded");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		System.out.println("Enter the controller");
		/*try {
			AddRemote remote   =  (AddRemote)getContext().lookup("AddBean/remote");
			System.out.println("The value from Session Bean is:"+remote.addNumber(10,20));
			System.out.println("Added Succesful !!!!");

		} catch (NamingException e) {
			e.printStackTrace();
		}*/
		
		
		ResultSet rs = null;
		Statement st = null;
		ResultSet rsTrans = null;

		PreparedStatement pstInsert = null;
		PreparedStatement pstSelect = null;
		PreparedStatement pstUpdate = null;
		PreparedStatement pstUpdate2 = null;
		System.out.println("The ip address of Remote system is"
				+ req.getRemoteAddr());
		System.out.println("The ip address of Remote system is"
				+ req.getRemoteHost());
		System.out.println("inside service method");
		/* creting session object for each request which intern create JESSIONID */
		HttpSession ses = req.getSession();
		System.out.println(ses.isNew() + "after getSession()");
		/* setting content type */
		res.setContentType("text/html");
		/* getting the PrintWriter object to print to html page object */
		PrintWriter pw = res.getWriter();
		/* string variables for heder,title and footer */
		String header = "<html><head><title>NextGen</title></head><body >";
		String pageTitle = "<div style='border: #000 1px solid; width: 100%; height: 50px; background-color: silver; margin-top: 10px'>"
				+ "<h1 style='font-family: sans-serif; font-size: 20px; color: blue; text-align: center'>NEXTGEN BANK</h1></div>";
		String bodyStart = "<div style='border-top: #000 1px solid;border-left: #000 1px solid;border-right:#000 1px solid; width:100%; height:470px;background-color:aqua;margin-top:10px'>";

		String backHome = "<div "
				+ "style='border-bottom:#000 1px solid;border-left: #000 1px solid;border-right:#000 1px solid; width: 100%; height: 50px; margin-bottom: 10px; background-color:aqua'>"
				+ "<table style='margin-left:20px;margin-right:30px margin-bottom:30px'>"
				+ "<tr>"
				+ "<td style='width:50px;'> "
				+ " <form action='./'><input type='submit' value='Home'></form></td>"
				+ "<td style='width: 1300px;'></td>"
				+ "<td>"
				+ "<form action='./'><input type='submit' value='Back'></form></td>"
				+ "</tr></table></div>";

		String backHomeMenu = "<div "
				+ "style='border-bottom:#000 1px solid;border-left: #000 1px solid;border-right:#000 1px solid; width: 100%; height: 50px; margin-bottom: 10px; background-color:aqua'>"
				+ "<table style='margin-left:20px;margin-right:30px margin-bottom:30px'>"
				+ "<tr>"
				+ "<td style='width:50px;'> "
				+ " <form action='./'><input type='submit' value='Home'></form></td>"
				+ "<td style='width: 1300px;'></td>"
				+ "<td>"
				+ "<form action='./viewmenu.do'><input type='submit' value='Back'></form></td>"
				+ "</tr></table></div>";
		String backHomeLogin = "<div "
				+ "style='border-bottom:#000 1px solid;border-left: #000 1px solid;border-right:#000 1px solid; width: 100%; height: 50px; margin-bottom: 10px; background-color:aqua'>"
				+ "<table style='margin-left:20px;margin-right:30px margin-bottom:30px'>"
				+ "<tr>"
				+ "<td style='width:50px;'> "
				+ " <form action='./'><input type='submit' value='Home'></form></td>"
				+ "<td style='width: 1300px;'></td>"
				+ "<td>"
				+ "<form action='./exist.do'><input type='submit' value='Back'></form></td>"
				+ "</tr></table></div>";

		String footer = "<div style='border: #000 1px solid; width: 100%; height: 50px; background-color:silver; margin-top: 10px;margin-bttom:5px'>"
				+ "<h6 align='center'>Copyright &copy 2014, NextGen. All rights reserved.</h6></div></body></html>";
		String servletPath = req.getServletPath();
		System.out.println(servletPath);

		String bodyStartExist = "<div style='border: #000 1px solid; width:100%; height:500px;background-color:aqua;margin-top:10px'>";
		String footerExist = "<div style='border: #000 1px solid; width: 100%; height: 50px; background-color:silver; margin-top: 10px;margin-bttom:5px'>"
				+ "<h6 align='center'>Copyright &copy 2014, NextGen. All rights reserved.</h6></div></body></html>";

		if (servletPath.equals("/new.do")) {
			pw.write(header);
			pw.write(pageTitle);
			pw.write(bodyStart);
			pw.write("<form action='./check.do'  method='post'>");
			pw.write("<h1 align='center' ><b>ACCOUNT DETAILS</b></h1>");
			/* creating table and values inside it */
			pw.write("<br><center><table>"
					+ "<tr><td><b>Name</b></td>"
					+ "<td><input type='text'  name='nme'></td><td>Min-3 Max-20 characters.No Space between characters</td></tr>"
					+ "<tr><td><b>User Name</b></td>"
					+ "<td><input type='text'  name='usernme'></td><td>Min-3 Max-20 characters.No Space between characters</td></tr>"
					+ "<tr><td><b>Pssword</b></td>"
					+ "<td><input type='password'  name='passnme'></td><td>Must be Alphanumeric Min-4 Max-8 characters</td></tr>"
					+ "<tr><td><b>Confirm Password</b></td>"
					+ "<td><input type='password'  name='cpassnme'></td></tr>"
					+ "<tr><td><b>Select Country</b></td>"
					+

					"<td><select name='branchnme' style='height: 24px; width: 150px;'>"
					+ "<option value='INDIA'>INDIA</option>"
					+ "<option value='USA'>USA</option>"
					+ "<option value='CHINA'>CHINA</option>"
					+ "<option value='BRAZIL'>BRAZIL</option>"
					+ "<option value='AUSTRALIA'>AUSTRELIA</option>"
					+ "<option value='JAPAN'>JAPAN</option>"
					+ "<option value='SINGAPORE'>SINGAPORE</option>"
					+ "<option value='ENGLAND'>ENGLAND</option></select>"
					+ "</td></tr>"
					+ "<tr><td><b>Select country branch code</b></td>"
					+ "<td><select name='bcode' style='height: 24px; width: 150px;'>"
					+ "<option value='IND-BLR-20141101' >IND-BLR-20141101</option>"
					+ "<option value='IND-CHN-20141102' >IND-CHN-20141102</option>"
					+ "<option value='IND-HYD-20141103' >IND-HYD-20141103</option>"
					+ "<option value='IND-MUM-20141104' >IND-MUM-20141104</option>"
					+ "<option value='USA-WDC-20142201' >USA-WDC-20142201</option>"
					+ "<option value='CHI-BIJ-20143301' >CHI-BIJ-20143301</option>"
					+ "<option value='AUS-SNY-20144401' >AUS-SNY-20144401</option>"
					+ "<option value='SPR-SPR-20145501' >SPR-SPR-20145501</option>"
					+ "<option value='ENG-LON-20146601' >ENG-LON-20146601</option>"
					+ "<option value='BRZ-BRZ-20147701' >BRZ-BRZ-20147701</option>"
					+ "<option value='JPN-TOK-20148801' >JPN-TOK-20148801</option></select>"
					+ "</td><td>Select the correspoding country branch code</td></tr>"
					+ "<tr><td><b>Amount</b></td>"
					+ "<td><input type='text'  name='amountnme'></td><td>Min:5000</td></tr>"
					+ "<tr><td><b>Address</b></td>"
					+ "<td><textarea name='addnme' style='width:150px; height:30px;position:static;'> </textarea></td><td>Max:200 characters</td></tr>"
					+ "<tr><td><b>Email-id</b></td>"
					+ "<td><input type='text'  name='emailnme'></td><td>Example: abc@xyz.com</td></tr>"
					+ "<tr><td><b>Phone No</b></td>"
					+ "<td><input type='text'  name='phonenme'></td><td>With Country code</td></tr>"
					+ "<tr><td width='100px'></td><td></td><td></td></tr>"
					+ "<tr><td></td><td colspan='1' align='center'><input type='submit' value='SUBMIT' width='200px' ></td><td></td></tr>"
					+ "</table></center></form> ");

			pw.write("</div>");
			pw.write(backHome);
			pw.write(footer);
		} else if (servletPath.equals("/check.do")) {
			if (!ses.isNew()) {

				/* setting the values */
				ses.setAttribute("name", req.getParameter("nme"));
				ses.setAttribute("username", req.getParameter("usernme"));
				ses.setAttribute("passnme", req.getParameter("passnme"));
				ses.setAttribute("cpassnme", req.getParameter("cpassnme"));
				ses.setAttribute("country", req.getParameter("branchnme"));
				ses.setAttribute("bcode", req.getParameter("bcode"));
				ses.setAttribute("amountnme", req.getParameter("amountnme"));
				ses.setAttribute("addnme", req.getParameter("addnme"));
				ses.setAttribute("emailnme", req.getParameter("emailnme"));
				ses.setAttribute("phonenme", req.getParameter("phonenme"));
				/* getting values */
				String name = (String) ses.getAttribute("name");
				String username = (String) ses.getAttribute("username");
				String passnme = (String) ses.getAttribute("passnme");
				String cpassnme = (String) ses.getAttribute("cpassnme");
				String country = (String) ses.getAttribute("country");
				String branchCode = (String) ses.getAttribute("bcode");
				String amountnme = (String) ses.getAttribute("amountnme");
				String addnme = (String) ses.getAttribute("addnme");
				String emailnme = (String) ses.getAttribute("emailnme");
				String phonenme = (String) ses.getAttribute("phonenme");
				// System.out.println(""+addnme.length());
				/************
				 * GETTING BRANCH SPECIFIC CODE FOR GENERATING ACCOUNT_ID
				 * SEQUENCE
				 */

				String strTemp1 = branchCode.substring(0, 3) + "_";
				String strTemp2 = branchCode.substring(4, 7);
				String strSeq = strTemp1.concat(strTemp2);
				System.out.println(strTemp1 + strTemp2);

				ses.setAttribute("branchSeq", strSeq);

				/********************* validation name **************************************/
				boolean isName = false;
				String nameValiadateMess = "Min-3 Max-20 characters.No Space between characters";
				isName = Validation.nameValidate(name);
				if (!isName) {
					nameValiadateMess = "Plese maintain the number of characters Min:3 Max:20";
				}
				/***************** validating username and checking in DB ************************/

				String userNameValidate = "Min:3 Max:20 characters.No Space between characters";
				String chechUserNameMess = "";
				boolean isUserName = false;
				boolean checkUserName = false;

				isUserName = Validation.nameValidate(username);
				if (!isUserName) {
					userNameValidate = "Plese maintain the number of characters Min:3 Max:20";
				}

				if (isUserName) {
					try {

						System.out
								.println("I am in try block to check user name in db");

						String querry = "SELECT * FROM ACCOUNT";
						pstSelect = con.prepareStatement(querry);
						rs = pstSelect.executeQuery();
						while (rs.next()) {
							boolean b = rs.getString("USERNAME").equals(
									username);
							if (b) {
								checkUserName = true;
								chechUserNameMess = "Username is already exist.Try any other names";
								break;
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				System.out.println("OOOOOO" + chechUserNameMess);
				/****************** confiraming password and validating *************/
				String passnmeValidateMess = "Must be Alphanumeric Min-4 Max-8 characters";
				boolean isConfirmPassword = passnme.equals(cpassnme)
						& Validation.passwordValidate(passnme);
				if (!isConfirmPassword) {
					passnmeValidateMess = "Please enter the conifirm password same as above and must be alphanumeric only";
				}
				/******************* validating country with branch code */
				String branchValidateMess = "Select the correspoding country branch code";
				boolean isCountryEqBranch = false;

				isCountryEqBranch = Validation.branchValidate(country,
						branchCode);
				if (!isCountryEqBranch) {
					branchValidateMess = "Please select country corresponding branch code";
				}
				/*********************** validating amount *********************/
				String amountMess = "Min 5000";
				boolean isAmount = false;
				try {
					isAmount = Validation.amountValidate(Integer
							.parseInt(amountnme));
				} catch (Exception e) {
					e.printStackTrace();

				}

				if (!isAmount) {
					amountMess = "Amount should be >=5000";
				}
				/***************** validating address *****************/
				String addressMess = "Maximum 200 characters";
				boolean isAddress = Validation.addressValidate(addnme);
				if (!isAddress) {
					addressMess = "Maximum characters should be 200";
				}

				/***************** validatinng email id *****************/
				String emailMess = "Example: abc@xyz.com";
				boolean isEmail = Validation.emailidValidate(emailnme);
				if (!isEmail) {
					emailMess = "Email id entered is invlidate format";
				}
				String phoneMess = "With Country code";
				long phoneInt = 0;
				try {
					phoneInt = Long.parseLong(phonenme);
				} catch (Exception e) {
					e.printStackTrace();
				}
				boolean isPhone = Validation.phoneValidate(phoneInt);
				if (!isPhone) {
					phoneMess = "Please enter phone no with contry code[Must be 12 digits]";
				}
				boolean b = (isAddress & isAmount & isConfirmPassword
						& isCountryEqBranch & isEmail & isName & isPhone
						& isUserName & (!checkUserName));
				if (b) {
					RequestDispatcher rd = req
							.getRequestDispatcher("sucess.do");
					rd.forward(req, res);
				}
				pw.write(header);

				pw.write(pageTitle);
				pw.write(bodyStart);
				pw.write("<form action='./check.do'  method='post'>");
				pw.write("<h1 align='center' ><b>ACCOUNT DETAILS</b></h1>");
				/* creating table and values inside it */
				pw.write("<br><center><table>" + "<tr><td><b>Name</b></td>"
						+ "<td><input type='text'  name='nme'>");
				if (!isName) {
					pw.write("</td><td style='color:red'>" + nameValiadateMess
							+ "</td></tr>");
				} else {
					pw.write("</td><td >" + nameValiadateMess + "</td></tr>");
				}
				pw.write("<tr><td><b>User Name</b></td>"
						+ "<td><input type='text'  name='usernme'>");
				if (!isUserName) {
					pw.write("</td><td style='color:red'>" + userNameValidate
							+ "</td></tr>");
				} else {
					if (checkUserName) {
						pw.write("</td><td style='color:red' >"
								+ chechUserNameMess + "</td></tr>");
					} else {
						pw.write("</td><td >" + "Min:3 Max:20 characters"
								+ "</td></tr>");
					}
				}

				pw.write("<tr><td><b>Pssword</b></td>"
						+ "<td><input type='password'  name='passnme'></td><td ></td></tr>"
						+ "<tr><td><b>Confirm Password</b></td>"
						+ "<td><input type='password'  name='cpassnme'></td>");
				if (!isConfirmPassword) {
					pw.write("<td style='color:red'>" + passnmeValidateMess
							+ "</td></tr>");
				} else {
					pw.write("<td>" + passnmeValidateMess + "</td></tr>");
				}
				pw.write("<tr><td><b>Select Country</b></td><td><select name='branchnme' style='height: 24px; width: 150px;'>"
						+ "<option value='INDIA'>INDIA</option>"
						+ "<option value='USA'>USA</option>"
						+ "<option value='CHINA'>CHINA</option>"
						+ "<option value='BRAZIL'>BRAZIL</option>"
						+ "<option value='AUSTRALIA'>AUSTRELIA</option>"
						+ "<option value='JAPAN'>JAPAN</option>"
						+ "<option value='SINGAPORE'>SINGAPORE</option>"
						+ "<option value='ENGLAND'>ENGLAND</option></select>"
						+ "</td></tr>"
						+ "<tr><td><b>Select country branch code</b></td>"
						+ "<td><select name='bcode' style='height: 24px; width: 150px;'>"
						+ "<option value='IND-BLR-20141101' >IND-BLR-20141101</option>"
						+ "<option value='IND-CHN-20141102' >IND-CHN-20141102</option>"
						+ "<option value='IND-HYD-20141103' >IND-HYD-20141103</option>"
						+ "<option value='IND-MUM-20141104' >IND-MUM-20141104</option>"
						+ "<option value='USA-WDC-20142201' >USA-WDC-20142201</option>"
						+ "<option value='CHI-BIJ-20143301' >CHI-BIJ-20143301</option>"
						+ "<option value='AUS-SNY-20144401' >AUS-SNY-20144401</option>"
						+ "<option value='SPR-SPR-20145501' >SPR-SPR-20145501</option>"
						+ "<option value='ENG-LON-20146601' >ENG-LON-20146601</option>"
						+ "<option value='BRZ-BRZ-20147701' >BRZ-BRZ-20147701</option>"
						+ "<option value='JPN-TOK-20148801' >JPN-TOK-20148801</option></select>");
				if (!isCountryEqBranch) {
					pw.write("</td><td style='color:red'>" + branchValidateMess
							+ "</td></tr>");
				} else {
					pw.write("</td><td >" + branchValidateMess + "</td></tr>");
				}
				pw.write("<tr><td><b>Amount</b></td>"
						+ "<td><input type='text'  name='amountnme'>");
				if (!isAmount) {
					pw.write("</td><td style='color:red'>" + amountMess
							+ "</td></tr>");
				} else {
					pw.write("</td><td >" + amountMess + "</td></tr>");
				}
				pw.write("<tr><td><b>Address</b></td>"
						+ "<td><textarea name='addnme' style='width:150px; height:30px;position:static;'> </textarea></td>");

				if (!isAddress) {
					pw.write("<td style='color:red'>" + addressMess
							+ "</td></tr>");
				} else {

					pw.write("<td >" + addressMess + "</td></tr>");
				}
				pw.write("<tr><td><b>Email-id</b></td>"
						+ "<td><input type='text'  name='emailnme'></td>");

				if (!isEmail) {
					pw.write("<td style='color:red'>" + emailMess
							+ "</td></tr>");
				} else {
					pw.write("<td >" + emailMess + "</td></tr>");
				}

				pw.write("<tr><td><b>Phone No</b></td>"
						+ "<td><input type='text'  name='phonenme'></td>");
				if (!isPhone) {
					pw.write("<td style='color:red'>" + phoneMess
							+ "</td></tr>");
				} else {
					pw.write("<td>" + phoneMess + "</td></tr>");
				}
				pw.write("<tr><td width='100px'></td><td></td><td></td></tr>"
						+ "<tr><td></td><td colspan='1' align='center'><input type='submit' value='SUBMIT' width='200px' ></td><td></td></tr>"
						+ "</table></center></form> ");

				pw.write("</div>");
				pw.write(backHome);
				pw.write(footer);

			} else {
				RequestDispatcher rd = req.getRequestDispatcher("exist.do");
				rd.forward(req, res);

			}
		} else if (servletPath.equals("/transfer.do")) {
			if (!ses.isNew()) {
				pw.write(header);
				pw.write(pageTitle);
				pw.write(bodyStart);

				pw.write("<table><tr><td align='left'><h5 align='left'>Name:Mr."
						+ ((String) ses.getAttribute("loginname"))
								.toUpperCase()
						+ "</h5>"
						+ "</td>"
						+ "<td align='right' width='100%'><h5>Account-No:"
						+ ses.getAttribute("loginaccountno")
						+ "</h5></td></tr></table>");
				pw.write("<h2 align='center'>Transfer details </h2>");
				pw.write("<form action='./checktransfer.do'><table align='center'> "
						+ "<tr><td><b>Enter the Account-No</b></td><td><input type='text' name='acctno'></td></tr>"
						+ "<tr><td><b>Enter the Branch ID</b></td><td><input type='text' name='bid'></td></tr>"
						+ "<tr><td><b>Enter the Amount</b></td>"
						+ "<td><input type='text' name='transferamt'></td></tr>"
						+ "</tr><tr><td height='40px'>  </td></tr><tr><td colspan='3' align='center'><input type='submit' value='TRANSFER' ></td></tr></table></form>");

				pw.write("</div>");
				pw.write(backHomeMenu);
				pw.write(footer);
			} else {
				RequestDispatcher rd = req.getRequestDispatcher("exist.do");
				rd.forward(req, res);

			}
		} else if (servletPath.equals("/checktransfer.do")) {
			if (!ses.isNew()) {
				try {
					String acctno = req.getParameter("acctno");
					String bid = req.getParameter("bid");
					String transferAmt = req.getParameter("transferamt");

					ses.setAttribute("transacctno", acctno);
					ses.setAttribute("transbid", bid);
					ses.setAttribute("transamt", transferAmt);

					con.setAutoCommit(false);
					String transferQuer = "SELECT ACCOUNT_ID,AMOUNT FROM ACCOUNT WHERE ACCOUNT_ID="
							+ acctno;

					pstSelect = con.prepareStatement(transferQuer);
					rs = pstSelect.executeQuery();
					if (rs.next()) {
						String sourceQue = "SELECT AMOUNT FROM ACCOUNT WHERE ACCOUNT_ID="
								+ ses.getAttribute("loginaccountno");
						pstSelect = con.prepareStatement(sourceQue);
						rsTrans = pstSelect.executeQuery();
						if (rsTrans.next()) {
							int srcAmt = rsTrans.getInt("AMOUNT");
							int desAmt = rs.getInt("AMOUNT");
							if (srcAmt >= Integer.parseInt(transferAmt)) {

								int srcPlus = desAmt
										+ Integer.parseInt(transferAmt);
								int desMinus = srcAmt
										- Integer.parseInt(transferAmt);

								String tranferUpdate = "UPDATE ACCOUNT SET AMOUNT="
										+ srcPlus
										+ " WHERE ACCOUNT_ID="
										+ acctno;
								pstUpdate = con.prepareStatement(tranferUpdate);
								int desUp = pstUpdate.executeUpdate();
								String srcUpdate = "UPDATE ACCOUNT SET AMOUNT="
										+ desMinus
										+ " WHERE ACCOUNT_ID="
										+ (Integer) ses
												.getAttribute("loginaccountno");
								pstUpdate2 = con.prepareStatement(srcUpdate);
								int srcUp = pstUpdate2.executeUpdate();
								System.out.println(srcUp);

								/*
								 * The below attributes are needed for
								 * tranfersucess.do servlet
								 */
								ses.setAttribute("currbalance", desMinus);
								/*
								 * Update the transaction time in TRANSACTION
								 * table
								 */
								String transactionQue = "INSERT INTO TRANSACTION(TRANSACTION_ID,ACCOUNT_ID,TRANSACTION_TIME,DES_ACCOUNT_ID,AMOUNT,BALANCE,TRANSACTION_TYPE)"
										+ "VALUES(SEQ_TRANSACTION_ID.NEXTVAL,?,?,?,?,?,?)";
								pstInsert = con
										.prepareStatement(transactionQue);
								pstInsert.setInt(1, (Integer) ses
										.getAttribute("loginaccountno"));
								pstInsert.setTimestamp(
										2,
										new java.sql.Timestamp(System
												.currentTimeMillis()));
								pstInsert.setInt(3, Integer.parseInt(acctno));
								pstInsert.setInt(4,
										Integer.parseInt(transferAmt));
								pstInsert.setInt(5, desMinus);
								pstInsert.setInt(6, 1);
								int isInserted = pstInsert.executeUpdate();

								System.out.println(isInserted);

								con.commit();
								RequestDispatcher rd = req
										.getRequestDispatcher("tranfersucess.do");
								rd.forward(req, res);

							} else {

								ses.setAttribute("error", 1);
								RequestDispatcher rd = req
										.getRequestDispatcher("tranferfailure.do");
								rd.forward(req, res);

							}
						}

					} else {
						ses.setAttribute("error", 2);
						RequestDispatcher rd = req
								.getRequestDispatcher("tranferfailure.do");
						rd.forward(req, res);
					}

				} catch (Exception e) {
					e.printStackTrace();
					try {
						con.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

			} else {
				RequestDispatcher rd = req.getRequestDispatcher("exist.do");
				rd.forward(req, res);
			}
		} else if (servletPath.equals("/tranferfailure.do")) {
			if (!ses.isNew()) {
				pw.write(header);
				pw.write(pageTitle);
				pw.write(bodyStart);

				pw.write("<table><tr><td align='left'><h5 align='left'>Name:Mr."
						+ ((String) ses.getAttribute("loginname"))
								.toUpperCase()
						+ "</h5>"
						+ "</td>"
						+ "<td align='right' width='100%'><h5>Account-No:"
						+ ses.getAttribute("loginaccountno")
						+ "</h5></td></tr></table>");
				pw.write("<h2 align='center'>Transfer details </h2>");
				if ((Integer) ses.getAttribute("error") == 2) {
					pw.write("<br><br>");
					pw.write("<h4 align='center'  style='color:red'>There is no any Account-No in the selected branch.<br>"
							+ "<b>Plese enter the correct Account No and Branch Id</b></h4>");
				} else {
					pw.write("<br><br>");
					pw.write("<h4 align='center' style='color:red'><b>Your balance is insufficient for transfer.<br>"
							+ "Please enter the correct amount. </b></h4>");
				}
				pw.write("</div>");
				pw.write(backHomeMenu);
				pw.write(footer);

			} else {
				RequestDispatcher rd = req.getRequestDispatcher("exist.do");
				rd.forward(req, res);
			}

		} else if (servletPath.equals("/tranfersucess.do")) {
			if (!ses.isNew()) {
				ses.getAttribute("currbalance");
				ses.getAttribute("transamt");

				pw.write(header);
				pw.write(pageTitle);
				pw.write(bodyStart);

				pw.write("<table><tr><td align='left'><h5 align='left'>Name:Mr."
						+ ((String) ses.getAttribute("loginname"))
								.toUpperCase()
						+ "</h5>"
						+ "</td>"
						+ "<td align='right' width='100%'><h5>Account-No:"
						+ ses.getAttribute("loginaccountno")
						+ "</h5></td></tr></table>");

				/* This block is used to display after withdraw */
				pw.write("<br><br>");
				pw.write("<h4 align='center'>Total amount transferred:"
						+ ses.getAttribute("transamt") + "</h4>");
				pw.write("<h4 align='center'>Your current balance:"
						+ ses.getAttribute("currbalance") + "</h4>");
				pw.write("</div>");
				pw.write(backHomeMenu);
				pw.write(footer);

			} else {
				RequestDispatcher rd = req.getRequestDispatcher("exist.do");
				rd.forward(req, res);
			}

		} else if (servletPath.equals("/withdraw.do")) {
			if (!ses.isNew()) {
				pw.write(header);
				pw.write(pageTitle);
				pw.write(bodyStart);

				pw.write("<table><tr><td align='left'><h5 align='left'>Name:Mr."
						+ ((String) ses.getAttribute("loginname"))
								.toUpperCase()
						+ "</h5>"
						+ "</td>"
						+ "<td align='right' width='100%'><h5>Account-No:"
						+ ses.getAttribute("loginaccountno")
						+ "</h5></td></tr></table>");
				pw.write("<h2 align='center'>Withdraw details </h2>");
				/* bussiness logic of withdraw menu */
				pw.write("<br><br>");
				pw.write("<form action='./checkwithdraw.do'><table align='center'><tr> "
						+ "<td><b>Enter the amount to be withdrawn</b></td>"
						+ "<td><input type='text' name='amount'></td><td>Multiples of 100 only</td>"
						+ "</tr></tr><tr><td height='40px'>  </td><tr><td colspan='3' align='center'><input type='submit' value='WITHDRAW' ></td></tr></table></form>");

				pw.write("</div>");
				pw.write(backHomeMenu);
				pw.write(footer);
			} else {
				RequestDispatcher rd = req.getRequestDispatcher("exist.do");
				rd.forward(req, res);

			}
		} else if (servletPath.equals("/deposit.do")) {
			if (!ses.isNew()) {
				pw.write(header);
				pw.write(pageTitle);
				pw.write(bodyStart);

				pw.write("<table><tr><td align='left'><h5 align='left'>Name:Mr."
						+ ((String) ses.getAttribute("loginname"))
								.toUpperCase()
						+ "</h5>"
						+ "</td>"
						+ "<td align='right' width='100%'><h5>Account-No:"
						+ ses.getAttribute("loginaccountno")
						+ "</h5></td></tr></table>");
				pw.write("<h2 align='center'>Deposit details</h2>");
				pw.write("<br><br>");
				pw.write("<form action='./updatedeposit.do'><table align='center'><tr> "
						+ "<td><b>Enter the amount to be deposited</b></td>"
						+ "<td><input type='text' name='depositAmt'></td>"
						+ "</tr><tr><td height='40px'>  </td><tr><td colspan='3' align='center'><input type='submit' value='DEPOSIT' ></td></tr></table></form>");

				pw.write("</div>");

				pw.write(backHomeMenu);

				pw.write(footer);
			} else {
				RequestDispatcher rd = req.getRequestDispatcher("exist.do");
				rd.forward(req, res);

			}
		} else if (servletPath.equals("/updatedeposit.do")) {
			if (!ses.isNew()) {
				try {
					int newAmt = 0;
					int toalAmt = 0;
					con.setAutoCommit(false);
					String depositSelect = "SELECT AMOUNT FROM ACCOUNT WHERE ACCOUNT_ID="
							+ ses.getAttribute("loginaccountno");
					pstSelect = con.prepareStatement(depositSelect);
					rs = pstSelect.executeQuery();
					if (rs.next()) {
						int baseAmt = rs.getInt("AMOUNT");

						String strDepositAmt = req.getParameter("depositAmt");
						newAmt = Integer.parseInt(strDepositAmt);
						toalAmt = baseAmt + newAmt;

						/*
						 * The below two session objects are used to carry
						 * values to another servlet
						 */
						ses.setAttribute("depositAmt", strDepositAmt);
						ses.setAttribute("newtotal", toalAmt);

						String depositUpdate = "UPDATE ACCOUNT SET AMOUNT="
								+ toalAmt + " WHERE ACCOUNT_ID="
								+ ses.getAttribute("loginaccountno");
						pstUpdate = con.prepareStatement(depositUpdate);

						int isUpdate = pstUpdate.executeUpdate();

					}
					/* update TRANSACTION table */

					String transactionQue = "INSERT INTO TRANSACTION(TRANSACTION_ID,ACCOUNT_ID,TRANSACTION_TIME,DES_ACCOUNT_ID,AMOUNT,BALANCE,TRANSACTION_TYPE)"
							+ "VALUES(SEQ_TRANSACTION_ID.NEXTVAL,?,?,?,?,?,?)";
					pstInsert = con.prepareStatement(transactionQue);
					pstInsert.setInt(1,
							(Integer) ses.getAttribute("loginaccountno"));
					pstInsert.setTimestamp(2,
							new java.sql.Timestamp(System.currentTimeMillis()));
					pstInsert.setInt(3,
							(Integer) ses.getAttribute("loginaccountno"));
					pstInsert.setInt(4, newAmt);
					pstInsert.setInt(5, toalAmt);
					pstInsert.setInt(6, 2);
					int isInserted = pstInsert.executeUpdate();

					con.commit();
					RequestDispatcher rd = req
							.getRequestDispatcher("updatesuccess.do");
					rd.forward(req, res);

				} catch (SQLException e) {
					e.printStackTrace();
					try {
						con.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

			} else {
				RequestDispatcher rd = req.getRequestDispatcher("exist.do");
				rd.forward(req, res);
			}
		} else if (servletPath.equals("/updatesuccess.do")) {
			if (!ses.isNew()) {
				pw.write(header);
				pw.write(pageTitle);
				pw.write(bodyStart);

				pw.write("<table><tr><td align='left'><h5 align='left'>Name:Mr."
						+ ((String) ses.getAttribute("loginname"))
								.toUpperCase()
						+ "</h5>"
						+ "</td>"
						+ "<td align='right' width='100%'><h5>Account-No:"
						+ ses.getAttribute("loginaccountno")
						+ "</h5></td></tr></table>");
				pw.write("<br><br>");
				pw.write("<h4 align='center'>Your Deposited Amount:"
						+ ses.getAttribute("depositAmt") + "</h4>");
				pw.write("<h4 align='center'>Your current balance:"
						+ ses.getAttribute("newtotal") + "</h4>");
				pw.write("</div>");
				pw.write(backHomeMenu);
				pw.write(footer);

			} else {
				RequestDispatcher rd = req.getRequestDispatcher("exist.do");
				rd.forward(req, res);
			}

		} else if (servletPath.equals("/balance.do")) {
			if (!ses.isNew()) {
				pw.write(header);
				pw.write(pageTitle);
				pw.write(bodyStart);

				pw.write("<table><tr><td align='left'><h5 align='left'>Name:Mr."
						+ ((String) ses.getAttribute("loginname"))
								.toUpperCase()
						+ "</h5>"
						+ "</td>"
						+ "<td align='right' width='100%'><h5>Account-No:"
						+ ses.getAttribute("loginaccountno")
						+ "</h5></td></tr></table>");
				pw.write("<h2 align='center'>Balance details</h2>");

				/* bussiness logic of balance menu */
				try {
					String balanceQuery = "SELECT AMOUNT FROM ACCOUNT WHERE ACCOUNT_ID="
							+ ses.getAttribute("loginaccountno");

					pstSelect = con.prepareStatement(balanceQuery);
					rs = pstSelect.executeQuery();
					rs.next();
					String balance = rs.getString("AMOUNT");
					pw.write("<br><br>");
					pw.write("<h4 align='center'>Your current balance :"
							+ balance + "</h4>");
					pw.write("</div>");
					pw.write(backHomeMenu);
					pw.write(footer);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				RequestDispatcher rd = req.getRequestDispatcher("exist.do");
				rd.forward(req, res);

			}
		} else if (servletPath.equals("/minisatement.do")) {
			if (!ses.isNew()) {

				try {
					pw.write(header);
					pw.write(pageTitle);
					pw.write(bodyStart);

					pw.write("<table><tr><td align='left'><h5 align='left'>Name:Mr."
							+ ((String) ses.getAttribute("loginname"))
									.toUpperCase()
							+ "</h5>"
							+ "</td>"
							+ "<td align='right' width='100%'><h5>Account-No:"
							+ ses.getAttribute("loginaccountno")
							+ "</h5></td></tr></table>");
					pw.write("<h2 align='center'>All Trasaction details</h2>");
					String getallTransaction = "SELECT * FROM TRANSACTION WHERE ACCOUNT_ID="
							+ ses.getAttribute("loginaccountno");

					pstSelect = con.prepareStatement(getallTransaction);
					rs = pstSelect.executeQuery();

					pw.write("<table border='3' align='center'><td>TRANSACTIN-ID</td><td>TRANSACTION-TIME</td><td>DESTINATION Ac/no  </td>"
							+ "<td>AMOUNT</td><td>BALANCE</td><td>TRANSACTION-TYPE</td>");
					ArrayList<TransactionTO> arrayList = new ArrayList<TransactionTO>();
					while (rs.next()) {
						TransactionTO transactionTO = new TransactionTO();
						transactionTO.setAccNO(rs.getInt("DES_ACCOUNT_ID"));
						transactionTO.setAmt(rs.getInt("AMOUNT"));
						transactionTO.setBalance(rs.getInt("BALANCE"));

						java.util.Date date = rs
								.getTimestamp("TRANSACTION_TIME");
						long time = date.getTime();

						transactionTO.setTime(time);
						transactionTO.setTranID(rs.getInt("TRANSACTION_ID"));
						int tranType = rs.getInt("TRANSACTION_TYPE");

						String type = null;

						/*
						 * new java.text.SimpleDateFormat(
						 * "dd/MM/yy hh:mm:ss aa").format(rs
						 * .getTimestamp("TRANSACTION_TIME"))
						 */

						if (tranType == 1) {
							type = "TRANSFER";
						} else if (tranType == 2) {
							type = "	DEPOSIT";
						} else if (tranType == 3) {
							type = "WITHDRAW";
						}
						transactionTO.setType(type);

						arrayList.add(transactionTO);
					}
					Collections
							.sort(arrayList, new TransactionTimeComparator());

					Iterator<TransactionTO> iterator = arrayList.iterator();

					System.out.println(arrayList);
					int i = 0;

					while (iterator.hasNext()) {

						TransactionTO next = iterator.next();

						if (i != 10) {

							pw.write("<tr><td align='center'>"
									+ next.getTranID()
									+ "</td><td align='center'>"
									+ new java.text.SimpleDateFormat(
											"dd/MM/yy hh:mm:ss aa").format(next
											.getTime())
									+ "</td><td align='center'>"
									+ next.getAccNO()
									+ "</td><td  align='center'>"
									+ next.getAmt()
									+ "</td><td  align='center'>"
									+ next.getBalance()
									+ "</td><td align='center'>"
									+ next.getType() + "</td></tr>");

							System.out.println(next.getTranID());
							/*
							 * System.out.println(next.getTime());
							 * System.out.println(next.getAccNO());
							 * System.out.println(next.getAmt());
							 * System.out.println(next.getBalance());
							 * System.out.println(next.getType());
							 */

							i++;
						} else {
							break;
						}
					}
					pw.write("</table>");

				} catch (SQLException e) {
					e.printStackTrace();
				}

				pw.write("</div>");

				pw.write(backHomeMenu);

				pw.write(footer);
			} else {
				RequestDispatcher rd = req.getRequestDispatcher("exist.do");
				rd.forward(req, res);

			}
		} else if (servletPath.equals("/exist.do")) {
			/* These two attributes are used for locking the account */

			ses.setAttribute("maxAttempts", 4);
			ses.setAttribute("countAttempts", -1);
			int a = (Integer) ses.getAttribute("maxAttempts");
			int b = (Integer) ses.getAttribute("countAttempts");

			String homeButton = "<div "
					+ "style='border-bottom:#000 1px solid;border-left: #000 1px solid;border-right:#000 1px solid; width: 100%; height: 50px; margin-bottom: 10px; background-color:aqua'>"
					+ "<table style='margin-left:20px;margin-right:30px margin-bottom:30px'>"
					+ "<tr>"
					+ "<td style='width:50px;'> "
					+ " <form action='./'><input type='submit' value='Home'></form></td>"
					+ "<td style='width: 1300px;'></td>"
					+ "</tr></table></div>";

			/* HTML code */
			pw.write(header);
			pw.write(pageTitle);
			pw.write(bodyStart);

			pw.write("<br><br><br>");

			pw.write("<form action='./checklogin.do' method='post'><center><table>"
					+ "<tr><td><b>User Name</b></td> "
					+ "<td><input type='text' name='loginname'></td></tr>"
					+ "<tr><td><b>Password</b></td> "
					+ "<td><input type='password' name='loginpassword'></td></tr>"
					+ "<tr ><td colspan='2' align='center' ><input type='submit' value='Login'></td><td></td></tr></table><center></form>");
			pw.write("</div>");
			pw.write(homeButton);
			pw.write(footer);
		} else if (servletPath.equals("/sucess.do")) {
			String backHomeCreateAccount = "<div "
					+ "style='border-bottom:#000 1px solid;border-left: #000 1px solid;border-right:#000 1px solid; width: 100%; height: 50px; margin-bottom: 10px; background-color:aqua'>"
					+ "<table style='margin-left:20px;margin-right:30px margin-bottom:30px'>"
					+ "<tr>"
					+ "<td style='width:50px;'> "
					+ " <form action='./'><input type='submit' value='Home'></form></td>"
					+ "<td style='width: 1300px;'></td>"
					+ "<td>"
					+ "<form action='./new.do'><input type='submit' value='Back'></form></td>"
					+ "</tr></table></div>";

			pw.write(header);
			pw.write(pageTitle);
			pw.write(bodyStart);
			try {
				con.setAutoCommit(false);
				String query = "INSERT INTO ACCOUNT(ACCOUNT_ID,BRANCH_ID,NAME,USERNAME,PASSWORD,COUNTRY,AMOUNT,ADDRESS,EMAIL_ID,PHONE,LOGINTIME)"
						+ "VALUES(SEQ_"
						+ ses.getAttribute("branchSeq")
						+ "_ACCOUNT_ID.NEXTVAL,?,?,?,?,?,?,?,?,?,?)";
				Timestamp timestampDate = new java.sql.Timestamp(
						System.currentTimeMillis());
				pstInsert = con.prepareStatement(query);
				pstInsert.setString(1,
						((String) ses.getAttribute("bcode")).toUpperCase());
				pstInsert.setString(2,
						((String) ses.getAttribute("name")).toUpperCase());
				pstInsert.setString(3,
						((String) ses.getAttribute("username")).toUpperCase());
				pstInsert.setString(4,
						((String) ses.getAttribute("passnme")).toUpperCase());
				pstInsert.setString(5,
						((String) ses.getAttribute("country")).toUpperCase());
				pstInsert.setLong(6,
						Long.parseLong((String) ses.getAttribute("amountnme")));
				pstInsert.setString(7,
						((String) ses.getAttribute("addnme")).toUpperCase());
				pstInsert.setString(8,
						((String) ses.getAttribute("emailnme")).toUpperCase());
				pstInsert.setLong(9,
						Long.parseLong((String) ses.getAttribute("phonenme")));
				pstInsert.setTimestamp(10, timestampDate);
				int isUpdate = pstInsert.executeUpdate();
				if (isUpdate == 1) {
					String updateQuery = "SELECT ACCOUNT_ID,USERNAME FROM ACCOUNT WHERE USERNAME=?";
					pstSelect = con.prepareStatement(updateQuery);
					pstSelect.setString(1, ((String) ses
							.getAttribute("username")).toUpperCase());
					rs = pstSelect.executeQuery();
					if (rs.next()) {
						ses.setAttribute("newaccountno",
								rs.getInt("ACCOUNT_ID"));
						ses.setAttribute("newusernme", rs.getString("USERNAME"));
					}
				}

				con.commit();
			} catch (Exception e) {
				e.printStackTrace();
				try {
					con.rollback();
				} catch (Exception e1) {
					e.printStackTrace();
				}
			}
			pw.write("<br><br>");
			pw.write("<h2 align='center'>Your account is created sucessfully</h2>");
			pw.write("<table align='center'><tr><td>Your Username:</td>"
					+ "<td style='color:brown'><b>"
					+ ses.getAttribute("newusernme") + "</b></td></tr></table>");
			pw.write("<table align='center'><tr><td>Your Account Number:</td>"
					+ "<td style='color:brown'><b>"
					+ ses.getAttribute("newaccountno")
					+ "<b></td></tr></table>");
			pw.write("</div>");
			pw.write(backHomeCreateAccount);
			pw.write(footerExist);
		} else if (servletPath.equals("/checklogin.do")) {

			if (!ses.isNew()) {
				/* set the parameters login username and password */
				ses.setAttribute("loginname", req.getParameter("loginname"));
				ses.setAttribute("loginpassword",
						req.getParameter("loginpassword"));
				// ses.setAttribute(arg0, arg1)
				System.out.println((Integer) ses.getAttribute("maxAttempts")
						+ "******maxAttempts values*****");
				System.out.println((Integer) ses.getAttribute("countAttempts")
						+ "*******count attemps values**********");
				/* get the parameters login username and password */
				String strLoginName = (String) ses.getAttribute("loginname");
				String strLoginPass = (String) ses
						.getAttribute("loginpassword");

				String lockVerifyMess = "";
				String passVerifyMess = "";
				String maxAttemptsMess = "";
				String passWrongMess = "";
				int passWrongIndicator = 0;

				boolean lockVerify = false;
				boolean passVerify = false;
				try {
					/* First vrify the user name and password */
					String query = "SELECT * FROM ACCOUNT";
					pstSelect = con.prepareStatement(query,
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
					rs = pstSelect.executeQuery();
					System.out.println();

					/*
					 * this while is used to check user name and password is
					 * correct or not
					 */
					while (rs.next()) {
						/* This if block is used to lock the account */
						if (strLoginName.equalsIgnoreCase(rs
								.getString("USERNAME"))) {
							String lockedUserName = rs.getString("USERNAME");
							passWrongIndicator = 1;

							if ((Integer) ses.getAttribute("countAttempts") <= 3) {
								Integer maxAttempts = (Integer) ses
										.getAttribute("maxAttempts");
								maxAttempts--;
								ses.setAttribute("maxAttempts", maxAttempts);

								Integer countAttempts = (Integer) ses
										.getAttribute("countAttempts");
								countAttempts++;
								ses.setAttribute("countAttempts", countAttempts);

							}
							System.out.println((Integer) ses
									.getAttribute("maxAttempts")
									+ "******maxAttempts values*****");
							System.out.println((Integer) ses
									.getAttribute("countAttempts")
									+ "*******count attemps values**********");

							passWrongMess = "Your password is incorrect.You have last "
									+ (Integer) ses.getAttribute("maxAttempts")
									+ " attempts to try";

							if ((Integer) ses.getAttribute("countAttempts") == 3) {
								String innerQuery = "UPDATE ACCOUNT SET ACC_ACCESS='L' WHERE USERNAME="
										+ "'" + lockedUserName + "'";
								System.out.println(innerQuery);
								st = con.createStatement();
								int isUpdate = st.executeUpdate(innerQuery);
								System.out.println(isUpdate);
								maxAttemptsMess = "You have tried the maximum attempts.Your account is locked.Plese "
										+ "contact your branch manager";
								pw.write(header);
								pw.write(pageTitle);
								pw.write(bodyStartExist);

								pw.write("<br><br><br>");

								pw.write("<form action='./checklogin.do' method='post'><center><table>"
										+ "<tr><td><b>User Name</b></td> "
										+ "<td><input type='text' name='loginname'></td><td style='color:red'>");
								pw.write(maxAttemptsMess);
								pw.write("</td></tr>"
										+ "<tr><td><b>Password</b></td> "
										+ "<td><input type='password' name='loginpassword'></td></tr>"
										+ "<tr ><td colspan='2' align='center' ><input type='submit' value='Login'></td><td></td></tr></table><center></form>");

								pw.write("<br>");
								pw.write("</div>");
								pw.write(footerExist);
								break;
							}

						}

						passVerify = strLoginName.equalsIgnoreCase(rs
								.getString("USERNAME"))
								& strLoginPass.equalsIgnoreCase(rs
										.getString("PASSWORD"));
						if (passVerify) {
							break;
						}
					}
					/*
					 * After user name is correct then only we have to check
					 * password.The main thing is if user enters the user name
					 * correct but password woroung then his accout will be
					 * locked after 3 wrong attempts.
					 */

					/*
					 * display the on the screen if password and username are
					 * wrong
					 */
					if (!passVerify
							& ((Integer) ses.getAttribute("countAttempts") != 3)) {
						passVerifyMess = "Your User name or password is incorrect.Please Try again";
						pw.write(header);
						pw.write(pageTitle);
						pw.write(bodyStartExist);

						pw.write("<br><br><br>");

						pw.write("<form action='./checklogin.do' method='post'><center><table>"
								+ "<tr><td><b>User Name</b></td> "
								+ "<td><input type='text' name='loginname'></td><td style='color:red'>");
						if (passWrongIndicator != 1) {
							pw.write(passVerifyMess);
						} else {
							pw.write(passWrongMess);
						}
						pw.write("</td></tr>"
								+ "<tr><td><b>Password</b></td> "
								+ "<td><input type='password' name='loginpassword'></td></tr>"
								+ "<tr ><td colspan='2' align='center' ><input type='submit' value='Login'></td><td></td></tr></table><center></form>");

						pw.write("<br>");
						pw.write("</div>");
						pw.write(footerExist);

					}

					if (passVerify
							& ((Integer) ses.getAttribute("countAttempts") != 3)) {

						/*
						 * if password and user name are correct,then check lock
						 * on the account no
						 */

						/* display on the screen if there is lock on account no */
						String strAccess = rs.getString("ACC_ACCESS");
						if (strAccess.equalsIgnoreCase("UL")) {
							lockVerify = true;
						}

						if (!lockVerify) {
							System.out.println("***********" + lockVerify
									+ "*******");

							lockVerifyMess = "Your account was locked.Please contact your branch Manager";
							pw.write(header);
							pw.write(pageTitle);
							pw.write(bodyStartExist);

							pw.write("<br><br><br>");

							pw.write("<form action='./checklogin.do' method='post'><table align='center'>"
									+ "<tr><td><b>User Name</b></td> "
									+ "<td><input type='text' name='loginname'></td><td style='color:red'>"
									+ lockVerifyMess
									+ "</td></tr>"
									+ "<tr><td><b>Password</b></td> "
									+ "<td><input type='password' name='loginpassword'></td></tr>"
									+ "<tr ><td colspan='2' align='center' ><input type='submit' value='Login'></td><td></td></tr></table></form>");

							pw.write("<br>");
							pw.write("</div>");
							pw.write(footerExist);
						}
					}

					if (lockVerify
							& passVerify
							& ((Integer) ses.getAttribute("countAttempts") != 3)) {
						/*
						 * if both user name and password and also lock on the
						 * account are true without exceeding maximum attempts
						 * then only display the menu screen
						 */

						Integer accNo = rs.getInt("ACCOUNT_ID");
						ses.setAttribute("loginaccountno", accNo);

						/* Formatting the login time based on the country */

						Timestamp loginTime = rs.getTimestamp("LOGINTIME");
						System.out.println(loginTime + "Login time");
						String strLoginTime = null;
						if (((String) rs.getString("COUNTRY"))
								.equalsIgnoreCase("INDIA")) {
							DateFormat df = new SimpleDateFormat(
									"dd/MM/yy  hh:mm:ss aa", new Locale("kn",
											"IN"));

							df.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));

							strLoginTime = df.format(loginTime);

						} else if (((String) rs.getString("COUNTRY"))
								.equalsIgnoreCase("USA")) {
							DateFormat df = new SimpleDateFormat(
									"dd/MM/yy  hh:mm:ss aa", new Locale("en",
											"US"));

							df.setTimeZone(TimeZone.getTimeZone("GMT-06:00"));

							strLoginTime = df.format(loginTime);

						} else if (((String) rs.getString("COUNTRY"))
								.equalsIgnoreCase("CHINA")) {
							DateFormat df = new SimpleDateFormat(
									"dd/MM/yy  hh:mm:ss aa", new Locale("zh",
											"CN"));

							df.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));

							strLoginTime = df.format(loginTime);

						} else if (((String) rs.getString("COUNTRY"))
								.equalsIgnoreCase("BRAZIL")) {
							DateFormat df = new SimpleDateFormat(
									"dd/MM/yy  hh:mm:ss aa", new Locale("pt",
											"BR"));

							df.setTimeZone(TimeZone.getTimeZone("Brazil/Acre"));

							strLoginTime = df.format(loginTime);

						} else if (((String) rs.getString("COUNTRY"))
								.equalsIgnoreCase("AUSTRALIA")) {
							DateFormat df = new SimpleDateFormat(
									"dd/MM/yy  hh:mm:ss aa", new Locale("de",
											"AT"));

							df.setTimeZone(TimeZone
									.getTimeZone("Australia/Sydney"));

							strLoginTime = df.format(loginTime);

						} else if (((String) rs.getString("COUNTRY"))
								.equalsIgnoreCase("JAPAN")) {
							DateFormat df = new SimpleDateFormat(
									"dd/MM/yy  hh:mm:ss aa", Locale.JAPAN); // new
																			// Locale("ja",
																			// "JP")

							df.setTimeZone(TimeZone.getTimeZone("Japan"));

							strLoginTime = df.format(loginTime);

						} else if (((String) rs.getString("COUNTRY"))
								.equalsIgnoreCase("SINGAPORE")) {
							DateFormat df = new SimpleDateFormat(
									"dd/MM/yy  hh:mm:ss aa", new Locale("zh",
											"SG"));

							df.setTimeZone(TimeZone
									.getTimeZone("Asia/Singapore"));

							strLoginTime = df.format(loginTime);

						} else if (((String) rs.getString("COUNTRY"))
								.equalsIgnoreCase("ENGLAND")) {
							DateFormat df = new SimpleDateFormat(
									"dd/MM/yy   hh:mm:ss aa", new Locale("en",
											"EN"));

							df.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));

							strLoginTime = df.format(loginTime);

						}
						ses.setAttribute("logintime", strLoginTime);
						pw.write(header);
						pw.write(pageTitle);
						pw.write(bodyStart);

						pw.write("<table width='100%'><tr><td align='left' style='width:206px'><h5>Name:Mr."
								+ ((String) ses.getAttribute("loginname"))
										.toUpperCase()
								+ "</h5>"
								+ "</td><td align='right'><h5>Last Login Time at:"
								+ ses.getAttribute("logintime")
								+ "</h5></td><tr><td></td><td align='right' width='100%'><form action='./logout.do'><input type='submit' size='20' value='Logout'></form></td></tr></table>");
						pw.write("<table align='center' ><tr><td><form action='./transfer.do'><input type='submit'  style='width:100%' size='50' value='TRANSFER'></form></td><tr>");
						pw.write("<tr><td><form action='./showdetails.do'><input type='submit' style='width:100%' size='50' value='SHOW DETAIL'></form></td></tr>");
						pw.write("<tr><td><form action='./deposit.do'><input type='submit'  style='width:100%' size='50' value='DEPOSIT'></form></td></tr>");
						pw.write("<tr><td><form action='./withdraw.do'><input type='submit'  style='width:100%' size='50' value='WITHDRAW'></form></td></tr>");
						pw.write("<tr><td><form action='./balance.do'><input type='submit' s style='width:100%' size='50' value='BALANCE'></form></td></tr>");
						pw.write("<tr><td><form action='./minisatement.do'><input type='submit'  style='width:100%' size='50' value='MINISTATEMENT'></form></td></tr></table>");
						pw.write("</div>");
						pw.write(backHomeLogin);
						pw.write(footerExist);

						/*
						 * change the previous login time to current logintime
						 * in the database
						 */
						String changeLoginQuery = "UPDATE ACCOUNT SET LOGINTIME=? WHERE ACCOUNT_ID=?";
						pstUpdate = con.prepareStatement(changeLoginQuery);
						pstUpdate.setTimestamp(
								1,
								new java.sql.Timestamp(System
										.currentTimeMillis()));
						pstUpdate.setInt(2, rs.getInt("ACCOUNT_ID"));

						int executeUpdate = pstUpdate.executeUpdate();
						System.out.println(executeUpdate);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				RequestDispatcher rd = req.getRequestDispatcher("exist.do");
				rd.forward(req, res);
			}
		} else if (servletPath.equals("/admin.do")) {
			pw.write(header);
			pw.write(pageTitle);
			pw.write(bodyStartExist);

			pw.write("<br><br><br>");

			pw.write("<form action='./admincheck.do' method='post'><center><table>"
					+ "<tr><td><b>Manager-id</b></td> "
					+ "<td><input type='text' name='mngrid'></td></tr>"
					+ "<tr><td><b>Password</b></td> "
					+ "<td><input type='password' name='mngrpwd'></td></tr>"
					+ "<tr ><td colspan='2' align='center' ><input type='submit' value='Login'></td><td></td></tr></table><center></form>");

			pw.write("<br>");
			pw.write("</div>");
			pw.write(footerExist);
		} else if (servletPath.equals("/showdetails.do")) {
			if (!ses.isNew()) {
				pw.write(header);
				pw.write(pageTitle);
				pw.write(bodyStart);

				try {
					String showQuery = "SELECT * FROM ACCOUNT WHERE ACCOUNT_ID=?";
					pstSelect = con.prepareStatement(showQuery);

					if (ses.getAttribute("loginaccountno") != null) {
						pstSelect.setInt(1,
								(Integer) ses.getAttribute("loginaccountno"));
						rs = pstSelect.executeQuery();

						if (rs.next()) {

							pw.write("<table><tr><td align='left'><h5 align='left'>Name:Mr."
									+ ((String) ses.getAttribute("loginname"))
											.toUpperCase()
									+ "</h5>"
									+ "</td>"
									+ "<td align='right' width='100%'><h5>Account-No:"
									+ ses.getAttribute("loginaccountno")
									+ "</h5></td></tr></table>");

							pw.write("<br><br><br><br><br>");
							pw.write("<table align='center'>"
									+ "<tr><td><b>Account No -"
									+ "</b></td><td><b> "
									+ rs.getString("ACCOUNT_ID")
									+ "</b></td></tr>");
							pw.write("<tr><td><b>Username -"
									+ "</b></td><td><b> "
									+ rs.getString("USERNAME")
									+ "</b></td></tr>");
							pw.write("<tr><td><b>Name -" + "</b></td><td><b> "
									+ rs.getString("NAME") + "</b></td></tr>");
							pw.write("<tr><td><b>Branch id-"
									+ "</b></td><td><b> "
									+ rs.getString("BRANCH_ID")
									+ "</b></td></tr>");
							pw.write("<tr><td><b>Country -"
									+ "</b></td><td><b> "
									+ rs.getString("COUNTRY")
									+ "</b></td></tr>");
							pw.write("<tr><td><b>Balence -"
									+ "</b></td><td><b> "
									+ rs.getString("AMOUNT") + "</b></td></tr>");
							pw.write("<tr><td><b>Address -"
									+ "</b></td><td><b>"
									+ rs.getString("ADDRESS")
									+ "</b></td></tr>");
							pw.write("<tr><td><b>Email id-"
									+ "</b></td><td><b> "
									+ rs.getString("EMAIL_ID")
									+ "</b></td></tr></table>");

						}
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}
				pw.write("<br>");
				pw.write("</div>");
				pw.write(backHomeMenu);
				pw.write(footerExist);

			} else {
				RequestDispatcher rd = req.getRequestDispatcher("exist.do");
				rd.forward(req, res);
			}
		} else if (servletPath.equals("/editdetails.do")) {
			if (!ses.isNew()) {

			} else {
				RequestDispatcher rd = req.getRequestDispatcher("exist.do");
				rd.forward(req, res);
			}
		} else if (servletPath.equals("/viewmenu.do")) {
			if (!ses.isNew()) {
				pw.write(header);
				pw.write(pageTitle);
				pw.write(bodyStart);

				pw.write("<table width='100%'><tr><td align='left' style='width:206px'><h5>Name:Mr."
						+ ((String) ses.getAttribute("loginname"))
								.toUpperCase()
						+ "</h5>"
						+ "</td><td align='right'><h5>Last Login Time at:"
						+ ses.getAttribute("logintime")
						+ "</h5></td><tr><td></td><td align='right' width='100%'><form action='./logout.do'><input type='submit' size='20' value='Logout'></form></td></tr></table>");
				pw.write("<table align='center' ><tr><td><form action='./transfer.do'><input type='submit'  style='width:100%' size='50' value='TRANSFER'></form></td></tr>");
				pw.write("<tr><td><form action='./showdetails.do'><input type='submit' style='width:100%' size='50' value='SHOW DETAIL'></form></td></tr>");
				pw.write("<tr><td><form action='./deposit.do'><input type='submit'  style='width:100%' size='50' value='DEPOSIT'></form></td></tr>");
				pw.write("<tr><td><form action='./withdraw.do'><input type='submit'  style='width:100%' size='50' value='WITHDRAW'></form></td></tr>");
				pw.write("<tr><td><form action='./balance.do'><input type='submit' style='width:100%' size='50' value='BALANCE'></form></td></tr>");
				pw.write("<tr><td><form action='./minisatement.do'><input type='submit'  style='width:100%; size=50' value='MINISTATEMENT'></form></td></tr></table>");

				pw.write("</div>");
				pw.write(backHomeLogin);
				pw.write(footerExist);

			}

			else {
				RequestDispatcher rd = req.getRequestDispatcher("exist.do");
				rd.forward(req, res);
			}
		} else if (servletPath.equals("/logout.do")) {
			if (!ses.isNew()) {
				/* Explicitly delete the session object */
				String backLogout = "<div "
						+ "style='border-bottom:#000 1px solid;border-left: #000 1px solid;border-right:#000 1px solid; width: 100%; height: 50px; margin-bottom: 10px; background-color:aqua'>"
						+ "<table style='margin-left:20px;margin-right:30px margin-bottom:30px'>"
						+ "<tr>"
						+ "<td style='width:50px;'> "
						+ "</td>"
						+ "<td style='width: 1300px;'></td>"
						+ "<td>"
						+ "<form action='./exist.do'><input type='submit' value='Back'></form></td>"
						+ "</tr></table></div>";

				pw.write(header);
				pw.write(pageTitle);
				pw.write(bodyStart);

				pw.write("<h3 align='center'>Your accoout is logged out sucessfully <h3>");
				pw.write("<h4 align='center'>Thank you for using NEXTGEN BANK internet banking <h4>");
				pw.write("</div>");
				pw.write(backLogout);
				pw.write(footerExist);
				ses.invalidate();
			}

			else {
				RequestDispatcher rd = req.getRequestDispatcher("exist.do");
				rd.forward(req, res);
			}
		} else if (servletPath.equals("/checkwithdraw.do")) {
			if (!ses.isNew()) {

				String amountMess = "Amount in Multples of 100 only";

				String strAmount = req.getParameter("amount");
				System.out.println(strAmount);
				int intAmount = Integer.parseInt(strAmount);
				/* attribute is used to dispaly amount */
				ses.setAttribute("amount", intAmount);

				System.out.println(intAmount);
				int reminder = intAmount % 100;
				if (reminder == 0) {
					try {

						String withdrawQuery = "SELECT AMOUNT FROM ACCOUNT WHERE ACCOUNT_ID="
								+ (Integer) ses.getAttribute("loginaccountno");
						con.setAutoCommit(false);

						pstSelect = con.prepareStatement(withdrawQuery);
						rs = pstSelect.executeQuery();
						if (rs.next()) {
							int accountBalance = rs.getInt("AMOUNT");
							if (accountBalance > intAmount) {

								int balance = accountBalance - intAmount;

								ses.setAttribute("accountbalance", balance);

								String que = "UPDATE ACCOUNT SET AMOUNT="
										+ balance
										+ " WHERE ACCOUNT_ID="
										+ (Integer) ses
												.getAttribute("loginaccountno");

								pstUpdate = con.prepareStatement(que);
								int i = pstUpdate.executeUpdate();
								/* update to transaction table */
								String transactionQue = "INSERT INTO TRANSACTION(TRANSACTION_ID,ACCOUNT_ID,TRANSACTION_TIME,DES_ACCOUNT_ID,AMOUNT,BALANCE,TRANSACTION_TYPE)"
										+ "VALUES(SEQ_TRANSACTION_ID.NEXTVAL,?,?,?,?,?,?)";
								pstInsert = con
										.prepareStatement(transactionQue);
								pstInsert.setInt(1, (Integer) ses
										.getAttribute("loginaccountno"));
								pstInsert.setTimestamp(
										2,
										new java.sql.Timestamp(System
												.currentTimeMillis()));
								pstInsert.setInt(3, (Integer) ses
										.getAttribute("loginaccountno"));
								pstInsert.setInt(4, intAmount);
								pstInsert.setInt(5, balance);
								pstInsert.setInt(6, 3);
								int isInserted = pstInsert.executeUpdate();

							} else {
								RequestDispatcher rd = req
										.getRequestDispatcher("insufficientbalence.do");
								rd.forward(req, res);
							}
						}
						con.commit();
						RequestDispatcher rd = req
								.getRequestDispatcher("withdrawsuccess.do");
						rd.forward(req, res);

					} catch (Exception e) {
						e.printStackTrace();
						try {
							con.rollback();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				} else {
					pw.write(header);
					pw.write(pageTitle);
					pw.write(bodyStart);

					pw.write("<table><tr><td align='left'><h5 align='left'>Name:Mr."
							+ ((String) ses.getAttribute("loginname"))
									.toUpperCase()
							+ "</h5>"
							+ "</td>"
							+ "<td align='right' width='100%'><h5>Account-No:"
							+ ses.getAttribute("loginaccountno")
							+ "</h5></td></tr></table>");
					pw.write("<h2 align='center'>Withdraw details </h2>");
					/* bussiness logic of withdraw menu */
					pw.write("<br><br>");
					pw.write("<form action='./checkwithdraw.do'><table align='center'><tr> "
							+ "<td><b>Enter the amount to be withdrawn</b></td>"
							+ "<td><input type='text' name='amount'></td><td  style='color:red'>"
							+ amountMess
							+ "</td>"
							+ "</tr><tr><td height='40px'>  </td></tr><tr><td colspan='3' align='center'><input type='submit' value='WITHDRAW' ></td></tr></table></form>");

					pw.write("</div>");
					pw.write(backHomeMenu);
					pw.write(footer);

				}

			} else {
				RequestDispatcher rd = req.getRequestDispatcher("exist.do");
				rd.forward(req, res);
			}

		} else if (servletPath.equals("/insufficientbalence.do")) {
			if (!ses.isNew()) {
				String lowBalanceMess = "You have insufficent balance";

				pw.write(header);
				pw.write(pageTitle);
				pw.write(bodyStart);

				pw.write("<table><tr><td align='left'><h5 align='left'>Name:Mr."
						+ ((String) ses.getAttribute("loginname"))
								.toUpperCase()
						+ "</h5>"
						+ "</td>"
						+ "<td align='right' width='100%'><h5>Account-No:"
						+ ses.getAttribute("loginaccountno")
						+ "</h5></td></tr></table>");
				pw.write("<h2 align='center'>Withdraw details </h2>");
				/* bussiness logic of withdraw menu */
				pw.write("<br><br>");
				pw.write("<form action='./checkwithdraw.do'><table align='center'><tr> "
						+ "<td><b>Enter the amount to be withdrawn</b></td>"
						+ "<td><input type='text' name='amount'></td><td  style='color:red'>"
						+ lowBalanceMess
						+ "</td>"
						+ "</tr><tr><td height='40px'>  </td></tr><tr><td colspan='3' align='center'><input type='submit' value='WITHDRAW' ></td></tr></table></form>");

				pw.write("</div>");
				pw.write(backHomeMenu);
				pw.write(footer);

			} else {
				RequestDispatcher rd = req.getRequestDispatcher("exist.do");
				rd.forward(req, res);
			}
		} else if (servletPath.equals("/withdrawsuccess.do")) {
			pw.write(header);
			pw.write(pageTitle);
			pw.write(bodyStart);

			pw.write("<table><tr><td align='left'><h5 align='left'>Name:Mr."
					+ ((String) ses.getAttribute("loginname")).toUpperCase()
					+ "</h5>" + "</td>"
					+ "<td align='right' width='100%'><h5>Account-No:"
					+ ses.getAttribute("loginaccountno")
					+ "</h5></td></tr></table>");

			/* This block is used to display after withdraw */
			pw.write("<br><br>");
			pw.write("<h4 align='center'>Your withdrawn amount:"
					+ ses.getAttribute("amount") + "</h4>");
			pw.write("<h4 align='center'>Your current balance:"
					+ ses.getAttribute("accountbalance") + "</h4>");
			pw.write("</div>");
			pw.write(backHomeMenu);
			pw.write(footer);

		}
	}

	public void destroy() {
		try {

			if (con != null) {
				con.close();
			}

		}

		catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

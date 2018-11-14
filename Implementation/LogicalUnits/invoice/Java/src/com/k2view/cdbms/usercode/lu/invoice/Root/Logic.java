/////////////////////////////////////////////////////////////////////////
// LU Functions
/////////////////////////////////////////////////////////////////////////

package com.k2view.cdbms.usercode.lu.invoice.Root;

import java.util.*;
import java.sql.*;
import java.math.*;
import java.io.*;

import com.k2view.cdbms.shared.*;
import com.k2view.cdbms.shared.Globals;
import com.k2view.cdbms.shared.user.UserCode;
import com.k2view.cdbms.sync.*;
import com.k2view.cdbms.lut.*;
import com.k2view.cdbms.shared.utils.UserCodeDescribe.*;
import com.k2view.cdbms.shared.logging.LogEntry.*;
import com.k2view.cdbms.func.oracle.OracleToDate;
import com.k2view.cdbms.func.oracle.OracleRownum;
import com.k2view.cdbms.usercode.lu.invoice.*;

import static com.k2view.cdbms.shared.utils.UserCodeDescribe.FunctionType.*;
import static com.k2view.cdbms.shared.user.ProductFunctions.*;
import static com.k2view.cdbms.usercode.common.SharedLogic.*;
import static com.k2view.cdbms.usercode.lu.invoice.Globals.*;

@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public class Logic extends UserCode {


	@type(RootFunction)
	@out(name = "invoice_id", type = String.class, desc = "")
	@out(name = "due_date", type = String.class, desc = "")
	@out(name = "status", type = String.class, desc = "")
	@out(name = "balance", type = String.class, desc = "")
	public static void fnPopInvData(String inp) throws Exception {
		java.net.HttpURLConnection conn = null;
		StringBuilder sb = new StringBuilder();
		try{
			java.net.URL url = new java.net.URL("http://192.168.136.129:3213/api/wsInvoiceInfo");
			conn = (java.net.HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		
			String input = "format=raw&token=WS&SSN=" + inp;
		
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
		
			if(conn.getResponseCode() != 200){
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
		
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output = "";
			while((output = br.readLine()) != null){
				sb.append(output);
			}
		} catch( java.net.MalformedURLException e){
			throw e;
		} catch (IOException e){
			throw e;
		} finally {
			conn.disconnect();
		}
		
		javax.xml.parsers.DocumentBuilderFactory dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
		javax.xml.parsers.DocumentBuilder db = dbf.newDocumentBuilder();
		org.xml.sax.InputSource is = new org.xml.sax.InputSource(new StringReader(sb.toString()));
		org.w3c.dom.Document dom = db.parse(is);
		//org.w3c.dom.Element outer = (org.w3c.dom.Element) dom.getElementsByTagName("Outer").item(0);
		//org.w3c.dom.Element inner = (org.w3c.dom.Element) info.getElementsByTagName("Inner").item(0);
		//org.w3c.dom.Element invoices = (org.w3c.dom.Element) info.getElementsByTagName("invoices").item(0);
		org.w3c.dom.NodeList nList = dom.getElementsByTagName("invoice");
		
		for (int i = 0; i < nList.getLength(); i++) {
			org.w3c.dom.Node nNode = nList.item(i);
		
			if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
		
				org.w3c.dom.Element eElement = (org.w3c.dom.Element) nNode;
				String invoice_id = eElement.getElementsByTagName("invoice_id").item(0).getTextContent();
				String due_date = eElement.getElementsByTagName("due_date").item(0).getTextContent();
				String status = eElement.getElementsByTagName("status").item(0).getTextContent();
				String balance = eElement.getElementsByTagName("balance").item(0).getTextContent();
		
				log.info("Invoice Id : " + invoice_id);
				log.info("Due Date : " + due_date);
				log.info("Status : " + status);
				log.info("Balance : " + balance);
				yield(new Object[]{invoice_id,due_date,status,balance});
			}
		}
	}

	
	
	
	
}

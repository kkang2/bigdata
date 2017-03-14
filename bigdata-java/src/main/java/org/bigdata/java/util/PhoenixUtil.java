package org.bigdata.java.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PhoenixUtil {

	public static void main(String[] args) {
		Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder(512);
        
        query.append(" SELECT x.uuid, x.parent_name, x.hostname, x.openstack_yn, x.node_type, y.ip_address ");
        query.append(" FROM ( ");
        query.append("				SELECT a.resource_seq, a.uuid, b.alias as parent_name, a.hostname, a.openstack_yn, a.node_type ");
        query.append("				FROM troi_resource a , troi_resource b ");
        query.append("				WHERE a.resource_parent_seq != 0 ");
        query.append("					and a.node_type = 'VM' ");
        query.append("					and a.resource_parent_seq = b.resource_Seq ");
        query.append(" ) x left outer join ( ");
        query.append("								SELECT resource_seq, element_value as ip_address ");
        query.append("								FROM troi_resource_element");
        query.append("								WHERE element_name = 'IP_ADDRESS' ");
        query.append(" ) y ");
        query.append(" on x.resource_seq = y.resource_seq ");

        try {
            // Connect to the database
        	Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");
            connection = DriverManager.getConnection("jdbc:phoenix:100.168.10.111");

            // Query for table
            ps = connection.prepareStatement(query.toString());
            rs = ps.executeQuery();
            System.out.println("Table Values");
            
            while(rs.next()) {
            	String uuid 				= rs.getString(1);
            	String parentName 	= rs.getString(2);
            	String hostName 		= rs.getString(3);
            	String openstackYn 	= rs.getString(4);
            	String nodeType 		= rs.getString(5);
            	String ipAddress 		= rs.getString(6);
            	
                System.out.println("uuid : " + uuid + ", parentName: " + parentName + ", hostName: " + hostName 
                		+ ", openstackYn: " + openstackYn + ", nodeType: " + nodeType + ", ipAddress: " + ipAddress);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(ps != null)				try {ps.close();}catch(Exception e){}
            if(rs != null)				try {rs.close();}catch(Exception e){}
            if(connection != null)	try {connection.close();}catch(Exception e){}
        }
	}
}
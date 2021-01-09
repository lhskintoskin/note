package com.jsu.wwt.frm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;

import com.jsu.wwt.dao.*;
import com.jsu.wwt.dbc.Connnection01;
import com.jsu.wwt.vo.*;

public class A3 {
	/**
	 * 注册
	 */
	public void reg() {
		JFrame jf = new JFrame("注册");
		jf.setSize(370, 280);
		jf.setLocation(750, 300);
		JPanel jp = new JPanel();
		jf.add(jp);
		jp.setLayout(null);
		JLabel acc = new JLabel("帐号:");
		JLabel pass = new JLabel("密码:");
		JLabel id = new JLabel("用户名:");//key
		JLabel warn = new JLabel("用户名注册成功后不可修改！");
		jp.add(pass);
		jp.add(id);
		jp.add(acc);
		jp.add(warn);
		acc.setBounds(40, 20, 80, 25);
		pass.setBounds(40, 60, 80, 25);
		id.setBounds(40, 100, 80, 25);
		warn.setBounds(40, 125, 200, 25);
		JTextField text1 = new JTextField();// 帐号输入
		JPasswordField text2 = new JPasswordField();// 密码输入
		JTextField text3 = new JTextField();// 用户名输入
		text1.setBounds(100, 20, 165, 25);
		text2.setBounds(100, 60, 165, 25);
		text3.setBounds(100, 100, 165, 25);
		jp.add(text1);
		jp.add(text2);
		jp.add(text3);
		JButton regButton = new JButton("注册");
		regButton.setBounds(125, 160, 80, 30);
		jp.add(regButton);
		jf.setVisible(true);
		//A2 sql = new A2();
		Connnection01 sql=new Connnection01();
		
		regButton.addActionListener(new ActionListener() {// 注册按钮监听器
			private int ERROR_MESSAGE;

			public void actionPerformed(ActionEvent e) {
				String s1 = text1.getText().toString();// 账号获取
				String s2 = new String(text2.getPassword());// 密码获取
				String s3 = text3.getText().toString();// 用户名获取
				
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				PreparedStatement stmt4 = null;
				int i;// 判断注册信息
				try {
					Connection c =sql.getConnection();
					//判断信息是否已经被注册过
					if (s1.length() == 0 || s2.length() == 0 || s3.length() == 0) {//是否为空
						JOptionPane.showMessageDialog(null, "还有信息未填", "错误", ERROR_MESSAGE);
					} else if (s1.length() != 0 && s2.length() != 0 && s3.length() != 0) {
						String sql1 = "SELECT * FROM 贴吧用户表 where username=?";//用户名是否重复
						stmt1 = c.prepareStatement(sql1);// 预编译SQL，减少sql执行
						stmt1.setString(1, s3);
						ResultSet rs1 = stmt1.executeQuery();// 执行查询语句
						if (!rs1.next()) {
							String sql2 = "SELECT * FROM 贴吧用户表 where Unumber=?";//是否已存在账号
							stmt2 = c.prepareStatement(sql2);// 预编译SQL，减少sql执行
							stmt2.setString(1, s1);
							ResultSet rs2 = stmt2.executeQuery();// 执行查询语句
							if (!rs2.next()) {
								String sql3 = "INSERT INTO 贴吧用户表 (Unumber,password,username) VALUES (?,?,?)";
								stmt3 = c.prepareStatement(sql3);// 预编译SQL，减少sql执行
								stmt3.setString(1, s1);// 赋值语句，给第一个？赋值s1
								stmt3.setString(2, s2);
								stmt3.setString(3, s3);
								stmt3.executeUpdate();// executeUpdate 的返回值是一个整数，指示受影响的行数（即更新计数）。对于 CREATE TABLE 或 DROP
														// TABLE 等不操作行的语句，executeUpdate 的返回值总为零。
								JOptionPane.showMessageDialog(null, "注册成功");
							} else {
								JOptionPane.showMessageDialog(null, "账号已经存在", "错误", ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "用户名已经被使用", "错误", ERROR_MESSAGE);
						}

					}
				} catch (Exception e1) {
					System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
					System.exit(0);
				}
			}
		});
	}
}

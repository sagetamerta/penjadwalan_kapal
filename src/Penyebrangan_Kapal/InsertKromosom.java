package Penyebrangan_Kapal;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;

public class InsertKromosom extends JFrame {

	private JPanel contentPane;
	private JTextField txtGen_1;
	private JTextField txtGen_2;
	private JTextField txtGen_3;
	private JTextField txtGen_4;
	private JTextField txtGen_5;
	private JTextField txtGen_6;
	private JTextField txtGen_7;
	private JTextField txtGen_8;
	private JTextField txtGen_9;
	private JTextField txtGen_10;
	private JComboBox cbKromosom;

	SQL_Connection koneksi = new SQL_Connection();
	Connection con = koneksi.getKoneksi();
	Statement st;
	PreparedStatement ps;

	Kromosom kro[];
	DefaultComboBoxModel<String> cbModel = new DefaultComboBoxModel<String>();
	private JTextField txtNoKromosom;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InsertKromosom frame = new InsertKromosom();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InsertKromosom() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				cbKromosom.removeAllItems();
				getKromosom();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 374, 476);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblPilihKromosom = new JLabel("Pilih Kromosom");
		lblPilihKromosom.setBounds(45, 44, 104, 14);
		contentPane.add(lblPilihKromosom);

		cbKromosom = new JComboBox();
		cbKromosom.setBounds(159, 41, 139, 20);
		contentPane.add(cbKromosom);
		cbKromosom.setModel(cbModel);

		JLabel lblIsiKromosomsel = new JLabel("Isi Kromosom (Gen)");
		lblIsiKromosomsel.setBounds(45, 72, 139, 14);
		contentPane.add(lblIsiKromosomsel);

		JLabel label = new JLabel("1.");
		label.setBounds(45, 101, 22, 14);
		contentPane.add(label);

		JLabel label_1 = new JLabel("2.");
		label_1.setBounds(45, 132, 22, 14);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("4.");
		label_2.setBounds(45, 192, 22, 14);
		contentPane.add(label_2);

		JLabel label_3 = new JLabel("3.");
		label_3.setBounds(45, 162, 22, 14);
		contentPane.add(label_3);

		JLabel label_4 = new JLabel("5.");
		label_4.setBounds(45, 224, 22, 14);
		contentPane.add(label_4);

		JLabel label_5 = new JLabel("6.");
		label_5.setBounds(45, 254, 22, 14);
		contentPane.add(label_5);

		JLabel label_6 = new JLabel("7.");
		label_6.setBounds(45, 284, 22, 14);
		contentPane.add(label_6);

		JLabel label_7 = new JLabel("8.");
		label_7.setBounds(45, 314, 22, 14);
		contentPane.add(label_7);

		JLabel label_8 = new JLabel("9.");
		label_8.setBounds(45, 343, 22, 14);
		contentPane.add(label_8);

		JLabel label_9 = new JLabel("10.");
		label_9.setBounds(42, 373, 22, 14);
		contentPane.add(label_9);

		txtGen_1 = new JTextField();
		txtGen_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtGen_2.requestFocus();
			}
		});
		txtGen_1.setBounds(72, 98, 86, 20);
		contentPane.add(txtGen_1);
		txtGen_1.setColumns(10);

		txtGen_2 = new JTextField();
		txtGen_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtGen_3.requestFocus();
			}
		});
		txtGen_2.setColumns(10);
		txtGen_2.setBounds(72, 128, 86, 20);
		contentPane.add(txtGen_2);

		txtGen_3 = new JTextField();
		txtGen_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtGen_4.requestFocus();
			}
		});
		txtGen_3.setBounds(72, 158, 86, 20);
		contentPane.add(txtGen_3);
		txtGen_3.setColumns(10);

		txtGen_4 = new JTextField();
		txtGen_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtGen_5.requestFocus();
			}
		});
		txtGen_4.setColumns(10);
		txtGen_4.setBounds(72, 189, 86, 20);
		contentPane.add(txtGen_4);

		txtGen_5 = new JTextField();
		txtGen_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtGen_6.requestFocus();
			}
		});
		txtGen_5.setColumns(10);
		txtGen_5.setBounds(72, 220, 86, 20);
		contentPane.add(txtGen_5);

		txtGen_6 = new JTextField();
		txtGen_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtGen_7.requestFocus();
			}
		});
		txtGen_6.setColumns(10);
		txtGen_6.setBounds(72, 251, 86, 20);
		contentPane.add(txtGen_6);

		txtGen_7 = new JTextField();
		txtGen_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtGen_8.requestFocus();
			}
		});
		txtGen_7.setColumns(10);
		txtGen_7.setBounds(72, 281, 86, 20);
		contentPane.add(txtGen_7);

		txtGen_8 = new JTextField();
		txtGen_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtGen_9.requestFocus();
			}
		});
		txtGen_8.setColumns(10);
		txtGen_8.setBounds(72, 311, 86, 20);
		contentPane.add(txtGen_8);

		txtGen_9 = new JTextField();
		txtGen_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtGen_10.requestFocus();
			}
		});
		txtGen_9.setColumns(10);
		txtGen_9.setBounds(72, 340, 86, 20);
		contentPane.add(txtGen_9);

		txtGen_10 = new JTextField();
		txtGen_10.setColumns(10);
		txtGen_10.setBounds(72, 370, 86, 20);
		contentPane.add(txtGen_10);

		JButton btnSimpan = new JButton("Simpan");
		btnSimpan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					ps = con.prepareStatement("INSERT INTO kromosom VALUES(?,?,?,?,?,?,?,?,?,?,?)");
					ps.setString(1, (txtNoKromosom.getText()));
					ps.setString(2, (txtGen_1.getText().toString()));
					ps.setString(3, (txtGen_2.getText().toString()));
					ps.setString(4, (txtGen_3.getText().toString()));
					ps.setString(5, (txtGen_4.getText().toString()));
					ps.setString(6, (txtGen_5.getText().toString()));
					ps.setString(7, (txtGen_6.getText().toString()));
					ps.setString(8, (txtGen_7.getText().toString()));
					ps.setString(9, (txtGen_8.getText().toString()));
					ps.setString(10, (txtGen_9.getText().toString()));
					ps.setString(11, (txtGen_10.getText().toString()));

					ps.executeUpdate();

					cbKromosom.removeAllItems();
					getKromosom();
					reset();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnSimpan.setBounds(202, 129, 89, 23);
		contentPane.add(btnSimpan);

		JButton btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				print();
			}
		});
		btnPrint.setBounds(202, 159, 89, 23);
		contentPane.add(btnPrint);

		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reset();
			}
		});
		btnReset.setBounds(202, 188, 89, 23);
		contentPane.add(btnReset);

		txtNoKromosom = new JTextField();
		txtNoKromosom.setBounds(211, 10, 86, 20);
		contentPane.add(txtNoKromosom);
		txtNoKromosom.setColumns(10);

		JLabel lblMasukkanNoKomosom = new JLabel("Masukkan No. Komosom");
		lblMasukkanNoKomosom.setBounds(45, 13, 155, 14);
		contentPane.add(lblMasukkanNoKomosom);
	}

	private void getKromosom() {
		try {
			ResultSet rs;
			String sql = "SELECT no_kromosom FROM kromosom " + "ORDER BY no_kromosom ";
			st = con.createStatement();
			rs = st.executeQuery(sql);

			rs.last();
			int jumlahData = rs.getRow();
			kro = new Kromosom[jumlahData];

			rs.beforeFirst();
			int i = 0;
			while (rs.next()) {
				kro[i] = new Kromosom(rs.getString(1));
				cbModel.addElement(String.valueOf(kro[i].getKode()));
				i++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void print() {
		ResultSet rs;
		try {
			ps = con.prepareStatement("SELECT * FROM kromosom WHERE no_kromosom=?");
			ps.setString(1, cbKromosom.getSelectedItem().toString());
			rs = ps.executeQuery();

			if (rs.next()) {
				System.out.println("Kromosom " + rs.getString(1) + " || Isinya : ");
				System.out.println(rs.getString(2));
				System.out.println(rs.getString(3));
				System.out.println(rs.getString(4));
				System.out.println(rs.getString(5));
				System.out.println(rs.getString(6));
				System.out.println(rs.getString(7));
				System.out.println(rs.getString(8));
				System.out.println(rs.getString(9));
				System.out.println(rs.getString(10));
				System.out.println(rs.getString(11));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reset() {
		txtGen_1.setText("");
		txtGen_2.setText("");
		txtGen_3.setText("");
		txtGen_4.setText("");
		txtGen_5.setText("");
		txtGen_6.setText("");
		txtGen_7.setText("");
		txtGen_8.setText("");
		txtGen_9.setText("");
		txtGen_10.setText("");
	}
}

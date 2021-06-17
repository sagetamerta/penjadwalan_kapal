package Penyebrangan_Kapal;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Input_Data_Kapal extends JFrame {

	private JPanel contentPane;
	private JTextField txtID;
	private JTextField txtNamaKapal;
	private JLabel lblKeterangan;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTable table;
	
	SQL_Connection koneksi= new SQL_Connection();
	Connection con=koneksi.getKoneksi();
	Statement st;
	
	private String[] header = {"ID","Nama Kapal","Keterangan"};
	DefaultTableModel tbmodel = new DefaultTableModel(null, header);
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Input_Data_Kapal frame = new Input_Data_Kapal();
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
	public Input_Data_Kapal() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				getData("SELECT * FROM data_kapal");
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 479, 379);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblId = new JLabel("ID");
		lblId.setBounds(10, 11, 46, 14);
		contentPane.add(lblId);
		
		txtID = new JTextField();
		txtID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtNamaKapal.requestFocus();
			}
		});
		txtID.setBounds(108, 8, 130, 20);
		contentPane.add(txtID);
		txtID.setColumns(10);
		
		txtNamaKapal = new JTextField();
		txtNamaKapal.setColumns(10);
		txtNamaKapal.setBounds(108, 35, 130, 20);
		contentPane.add(txtNamaKapal);
		
		JLabel lblNamaKapal = new JLabel("Nama Kapal");
		lblNamaKapal.setBounds(10, 38, 71, 14);
		contentPane.add(lblNamaKapal);
		
		lblKeterangan = new JLabel("Keterangan");
		lblKeterangan.setBounds(10, 68, 71, 14);
		contentPane.add(lblKeterangan);
		
		JRadioButton rdbtnKapalBaru = new JRadioButton("Kapal Baru");
		buttonGroup.add(rdbtnKapalBaru);
		rdbtnKapalBaru.setBounds(108, 64, 95, 23);
		contentPane.add(rdbtnKapalBaru);
		
		JRadioButton rdbtnKapalLama = new JRadioButton("Kapal Lama");
		buttonGroup.add(rdbtnKapalLama);
		rdbtnKapalLama.setBounds(108, 87, 109, 23);
		contentPane.add(rdbtnKapalLama);
		
		JButton btnInput = new JButton("INPUT");
		btnInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try 
				{
						String jenis;
						if(rdbtnKapalBaru.isSelected())
							jenis=rdbtnKapalBaru.getText();
						else								
							jenis=rdbtnKapalLama.getText();
						st=con.createStatement();
						String sql="INSERT INTO data_kapal "
								+ "VALUES('"
								+ txtID.getText().toUpperCase()+"','"
								+ txtNamaKapal.getText() +"','"
								+ jenis +"')";
						
						int i = st.executeUpdate(sql);
						if(i>0)
							getData("SELECT * FROM data_kapal");	
						txtID.requestFocus();
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		});
		btnInput.setBounds(364, 7, 89, 23);
		contentPane.add(btnInput);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 119, 443, 210);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int pilih = table.getSelectedRow();
				if(pilih>-1) {
					txtID.setText(tbmodel.getValueAt(pilih, 0).toString());
					txtNamaKapal.setText(tbmodel.getValueAt(pilih, 1).toString());
				}
			}
		});
		scrollPane.setViewportView(table);
		table.setModel(tbmodel);
		
		JButton btnHapus = new JButton("HAPUS");
		btnHapus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try 
				{
					st=con.createStatement();
					String sql="DELETE FROM data_kapal "
							+ " WHERE ID='" + txtID.getText() + "'";
			
						int i = st.executeUpdate(sql);
						if(i>0) {
							getData("SELECT * FROM data_kapal");
						}
				} catch (Exception e) {
						e.printStackTrace();
				}
			}
		});
		btnHapus.setBounds(364, 34, 89, 23);
		contentPane.add(btnHapus);
		
		JButton btnExit = new JButton("EXIT");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnExit.setBounds(364, 59, 89, 23);
		contentPane.add(btnExit);
		
		JButton btnUbah = new JButton("UBAH");
		btnUbah.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					String jenis;
					if(rdbtnKapalBaru.isSelected())
						jenis=rdbtnKapalBaru.getText();
					else
						jenis=rdbtnKapalLama.getText();
					st=con.createStatement();
					String sql="UPDATE data_kapal "
					+ " SET ID='" + txtID.getText()
					+ "' ,nama='" + txtNamaKapal.getText()
					+ "' ,keterangan='" + jenis + "'";
				int i = st.executeUpdate(sql);
				if(i>0)
					getData("SELECT * FROM data_kapal");
				}catch(Exception ex){
					ex.printStackTrace();
				}
				
			}
		});
		btnUbah.setBounds(364, 87, 89, 23);
		contentPane.add(btnUbah);
	}
	private void getData(String sql){
		ResultSet rs;
		try {
			st=con.createStatement();
			rs=st.executeQuery(sql);
			tbmodel.getDataVector().removeAllElements();
			tbmodel.fireTableDataChanged();
			
			while(rs.next()) {
				Object obj[]=new Object[3];
				obj[0]=rs.getString(1);	
				obj[1]=rs.getString(2);
				obj[2]=rs.getString(3);
				tbmodel.addRow(obj);
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

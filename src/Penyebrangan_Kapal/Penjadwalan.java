package Penyebrangan_Kapal;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLayeredPane;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Penjadwalan extends JFrame {

	private JPanel contentPane;
	private JTable table_DK;
	private JTextField txtPopsize;
	private JTextField txtCr;
	private JTextField txtMr;
	private JTextField txtIterasi;
	private JTextField txtFitness;
	private JTable table_PA;
	private JTable table_CM;
	private JTable table_ES;
	private JTable table_IT;
	private JTable table_JKPPL;
	
	//penghubung ke database
	SQL_Connection koneksi= new SQL_Connection();
	Connection con=koneksi.getKoneksi();
	Statement st;
	
	int[][] data;
	int[][] childMutasi;
	int[][] childCrossover;
	int[][] halangan;
	int[][] gabungan;
	double[][] newFitness;
	double[][] fitness;
	double fitnessJipi; //nilai fitness individu terbaik setiap iterasi
	int individuTerbaik; //nomor individu terbaik setiap iterasi
	double thresholdJipi; //treshold yang akan diuji
	int indexTerbaik;
	int maxData = 160; 
	int maxKapal = 32;
	int getChildCO, ofCrossover, ofMutasi, cHalangan, popsize, iterasi, count, allPop;
	Double cons1, cons2, cons3, cons4, cons5;
	//int[] array;
	//int[] array2;
	int[] kapal;
	int[] fullJadwal;
	int[] jadwal1;
	int[] jadwal2;
	Double cr, mr;
	String jadwalTerbaik;
	
	/*Header dari setiap tabel*/
	private String[] header_1 = {"ID","Nama Kapal","Keterangan"};
	DefaultTableModel tbmodel_datakapal = new DefaultTableModel(null, header_1);
	
	private String[] header_2 = {"Individu","Kromosom"};
	DefaultTableModel tbmodel_popawal = new DefaultTableModel(null, header_2);
	
	private String[] header_3 = {"Individu","Persilangan","Kromosom"};
	DefaultTableModel tbmodel_child = new DefaultTableModel(null, header_3);
	
	private String[] header_4 = {"Individu","Nilai Fitness"};
	DefaultTableModel tbmodel_evaselek = new DefaultTableModel(null, header_4);
	
	private String[] header_5 = {"Iterasi","Individu", "Nilai Fitness","Kromosom"};
	DefaultTableModel tbmodel_indterb = new DefaultTableModel(null, header_5);
	
	private String[] header_6 = {"Hari Ke","00.00","01.30","03.00","04.30","06.00","07.30","09.00","10.30","12.00","13.30","15.00","16.30","18.00","19.30","21.00","22.30"};
	DefaultTableModel tbmodel_jadwal = new DefaultTableModel(null, header_6);
 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Penjadwalan frame = new Penjadwalan();
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
	public Penjadwalan() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				getData();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 801, 583);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 25, 747, 519);
		contentPane.add(tabbedPane);
		
		JPanel Tab_Data_Kapal = new JPanel();
		tabbedPane.addTab("Data Kapal", null, Tab_Data_Kapal, null);
		Tab_Data_Kapal.setLayout(null);
		
		JScrollPane Tabel_Data_Kapal = new JScrollPane();
		Tabel_Data_Kapal.setBounds(37, 11, 662, 436);
		Tab_Data_Kapal.add(Tabel_Data_Kapal);
		
		table_DK = new JTable();
		Tabel_Data_Kapal.setViewportView(table_DK);
		table_DK.setModel(tbmodel_datakapal);
		
		JLabel lblDataTabel = new JLabel("Data Tabel");
		lblDataTabel.setBounds(37, 24, 136, 14);
		Tab_Data_Kapal.add(lblDataTabel);
		
		JPanel Tab_Proses_Penjadwalan = new JPanel();
		tabbedPane.addTab("Proses Penjadwalan", null, Tab_Proses_Penjadwalan, null);
		Tab_Proses_Penjadwalan.setLayout(null);
		
		JLabel lblJumlahAbsolute = new JLabel("Jumlah Populasi");
		lblJumlahAbsolute.setBounds(11, 36, 107, 14);
		Tab_Proses_Penjadwalan.add(lblJumlahAbsolute);
		
		txtPopsize = new JTextField();
		txtPopsize.setText("10");
		txtPopsize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtCr.requestFocus();
			}
		});
		txtPopsize.setBounds(11, 54, 143, 20);
		Tab_Proses_Penjadwalan.add(txtPopsize);
		txtPopsize.setColumns(10);
		
		txtCr = new JTextField();
		txtCr.setText("0.5");
		txtCr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtMr.requestFocus();
			}
		});
		txtCr.setColumns(10);
		txtCr.setBounds(11, 103, 143, 20);
		Tab_Proses_Penjadwalan.add(txtCr);
		
		JLabel lblCrossover = new JLabel("Crossover Rate (Cr)");
		lblCrossover.setBounds(11, 85, 107, 14);
		Tab_Proses_Penjadwalan.add(lblCrossover);
		
		JLabel lblMutationRate = new JLabel("Mutation Rate (Mr)");
		lblMutationRate.setBounds(11, 134, 107, 14);
		Tab_Proses_Penjadwalan.add(lblMutationRate);
		
		txtMr = new JTextField();
		txtMr.setText("0.5");
		txtMr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtIterasi.requestFocus();
			}
		});
		txtMr.setColumns(10);
		txtMr.setBounds(11, 152, 143, 20);
		Tab_Proses_Penjadwalan.add(txtMr);
		
		JLabel lblJumlahIterasi = new JLabel("Epoch");
		lblJumlahIterasi.setBounds(11, 306, 107, 14);
		Tab_Proses_Penjadwalan.add(lblJumlahIterasi);
		
		txtIterasi = new JTextField();
		txtIterasi.setText("1000");
		txtIterasi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtFitness.requestFocus();
			}
		});
		txtIterasi.setColumns(10);
		txtIterasi.setBounds(11, 324, 143, 20);
		Tab_Proses_Penjadwalan.add(txtIterasi);
		
		JLabel lblInputParameter = new JLabel("Input Parameter");
		lblInputParameter.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblInputParameter.setBounds(11, 11, 120, 14);
		Tab_Proses_Penjadwalan.add(lblInputParameter);
		
		JLabel lblInputDataHalangan = new JLabel("Sentinel");
		lblInputDataHalangan.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblInputDataHalangan.setBounds(11, 232, 152, 14);
		Tab_Proses_Penjadwalan.add(lblInputDataHalangan);
		
		JLabel lblIdKapal = new JLabel("Threshold");
		lblIdKapal.setBounds(11, 257, 107, 14);
		Tab_Proses_Penjadwalan.add(lblIdKapal);
		
		txtFitness = new JTextField();
		txtFitness.setText("0.0007");
		txtFitness.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		txtFitness.setColumns(10);
		txtFitness.setBounds(11, 275, 143, 20);
		Tab_Proses_Penjadwalan.add(txtFitness);
		
		JButton btnNewButton = new JButton("Proses");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearTable();
				inisialisasi();
				for(int i=0; i<iterasi; i++) {
					hitungCrossover();
					hitungMutasi();
					hitungFitness();
					seleksiElitism();
					tbmodel_indterb.addRow(new Object[] {i+1, individuTerbaik+1, fitnessJipi, jadwalTerbaik});
					if (fitnessJipi >= thresholdJipi) {
						System.out.println("");
						System.out.println("Berhenti di iterasi ke : " + (i+1));
						break;
					}
				}
				tampilJadwal();
			}
		});
		btnNewButton.setBounds(11, 376, 143, 23);
		Tab_Proses_Penjadwalan.add(btnNewButton);
		
		JLabel lblPopulasiAwal = new JLabel("Populasi Awal");
		lblPopulasiAwal.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPopulasiAwal.setBounds(370, 4, 120, 14);
		Tab_Proses_Penjadwalan.add(lblPopulasiAwal);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(166, 8, 7, 401);
		Tab_Proses_Penjadwalan.add(separator);
		
		JScrollPane Tabel_PopulasiAwal = new JScrollPane();
		Tabel_PopulasiAwal.setBounds(184, 22, 489, 107);
		Tab_Proses_Penjadwalan.add(Tabel_PopulasiAwal);
		
		table_PA = new JTable();
		Tabel_PopulasiAwal.setViewportView(table_PA);
		table_PA.setModel(tbmodel_popawal);
		
		JLabel lblHasilCrossoverDan = new JLabel("Hasil Crossover dan Mutasi");
		lblHasilCrossoverDan.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblHasilCrossoverDan.setBounds(335, 134, 189, 14);
		Tab_Proses_Penjadwalan.add(lblHasilCrossoverDan);
		
		JScrollPane Tabel_CrossoverMutasi = new JScrollPane();
		Tabel_CrossoverMutasi.setBounds(183, 148, 489, 117);
		Tab_Proses_Penjadwalan.add(Tabel_CrossoverMutasi);
		
		table_CM = new JTable();
		Tabel_CrossoverMutasi.setViewportView(table_CM);
		table_CM.setModel(tbmodel_child);
		
		JLabel lblHasilEvaluasi = new JLabel("Hasil Evaluasi & Seleksi");
		lblHasilEvaluasi.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblHasilEvaluasi.setBounds(348, 277, 189, 14);
		Tab_Proses_Penjadwalan.add(lblHasilEvaluasi);
		
		JScrollPane Tabel_EvaluasiSeleksi = new JScrollPane();
		Tabel_EvaluasiSeleksi.setBounds(185, 296, 488, 113);
		Tab_Proses_Penjadwalan.add(Tabel_EvaluasiSeleksi);
		
		table_ES = new JTable();
		Tabel_EvaluasiSeleksi.setViewportView(table_ES);
		table_ES.setModel(tbmodel_evaselek);
		
		JPanel Tab_Hasil_Penjadwalan = new JPanel();
		tabbedPane.addTab("Hasil Penjadwalan", null, Tab_Hasil_Penjadwalan, null);
		Tab_Hasil_Penjadwalan.setLayout(null);
		
		JScrollPane Tabel_IndividuTerbaik = new JScrollPane();
		Tabel_IndividuTerbaik.setBounds(0, 30, 732, 104);
		Tab_Hasil_Penjadwalan.add(Tabel_IndividuTerbaik);
		
		table_IT = new JTable();
		Tabel_IndividuTerbaik.setViewportView(table_IT);
		table_IT.setModel(tbmodel_indterb);
		
		JScrollPane Tabel_JadwalKapal = new JScrollPane();
		Tabel_JadwalKapal.setBounds(0, 190, 732, 290);
		Tab_Hasil_Penjadwalan.add(Tabel_JadwalKapal);
		
		table_JKPPL = new JTable();
		Tabel_JadwalKapal.setViewportView(table_JKPPL);
		table_JKPPL.setModel(tbmodel_jadwal);
		
		JLabel lblIndividuTerbaik = new JLabel("Individu Terbaik");
		lblIndividuTerbaik.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblIndividuTerbaik.setBounds(290, 8, 118, 14);
		Tab_Hasil_Penjadwalan.add(lblIndividuTerbaik);
		
		JLabel lblJadwalKapalPenumpang = new JLabel("Jadwal Kapal Penumpang Pelabuhan Lembar");
		lblJadwalKapalPenumpang.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblJadwalKapalPenumpang.setBounds(213, 171, 294, 14);
		Tab_Hasil_Penjadwalan.add(lblJadwalKapalPenumpang);
		
		JLabel lblOptimasiPenjadwalanKapal = new JLabel("OPTIMASI PENJADWALAN KAPAL PENUMPANG MENGGUNAKAN ALGORITMA GENETIKA");
		lblOptimasiPenjadwalanKapal.setBounds(108, 3, 476, 14);
		contentPane.add(lblOptimasiPenjadwalanKapal);
		lblOptimasiPenjadwalanKapal.setFont(new Font("Tahoma", Font.BOLD, 11));
	}
	public void getData() {
		ResultSet rs;
		try{
			String data [][] =  new String[32][3];
			st=con.createStatement();
			String sql= "SELECT * FROM data_kapal ";
			rs=st.executeQuery(sql);
			System.out.println("ID | Nama Kapal 	| Keterangan");
			
			int i=0;
			while (rs.next()) {
				data[i][0]=rs.getString("ID");
				data[i][1]=rs.getString("nama");
				data[i][2]=rs.getString("keterangan");
				System.out.println(data[i][0] + " 	| " + data[i][1] +" 	| "+ data[i][2]);
				
				tbmodel_datakapal.addRow(new Object[] {
					data[i][0], data[i][1],data[i][2]
				});
				i++;
			}
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	
	private void inisialisasi() {
		String temp;
		Random random = new Random();
		popsize = Integer.parseInt(txtPopsize.getText());
		cr = Double.parseDouble(txtCr.getText());
		mr = Double.parseDouble(txtMr.getText());
		iterasi = Integer.parseInt(txtIterasi.getText());
		thresholdJipi = Double.parseDouble(txtFitness.getText());
		data = new int[popsize][maxData];
		
		for(int i=0; i<popsize; i++) {
			int [] arr = new int[maxData];
			for (int j=0; j < maxData; j++) {
				int n = random.nextInt(maxKapal) + 1;
				data[i][j] = n;
				arr[j] = data [i][j];
			}
			temp = Arrays.toString(arr);
			temp = temp.substring(0,32) + ",...";
			tbmodel_popawal.addRow(new Object[] {i+1, temp});
		}
	}
	
	/*method untuk menghitung constraint*/
	public void getConstraint1(int [] array,  int [] array2) {
		for(int i=0; i<array.length; i++) {
			for(int j=0; j<array2.length; j++) {
				if(array[i] == array2[j]) {
					cons1 = cons1 + 10;
				}
			}
		}
	}
	public void ge2(int [] array,  int [] array2) {
		for(int i=0; i<array.length; i++) {
			for(int j=0; j<array2.length; j++) {
				if(array[i] == array2[j]) {
					cons2 = cons2 + 20;
				}
			}
		}
	}
	public void getConstraint3(int [] array,  int [] array2) {
		for(int i=0; i<array.length; i++) {
			for(int j=0; j<array2.length; j++) {
				if(array[i] == array2[j]) {
					cons3 = cons3 + 50;
				}
			}
		}
	}
	public void getConstraint4(int [] array) {
		int[] s2remove = new int [array.length];
		for(int i=0; i<array.length; i++) {
			for(int j=i+1; j<array.length; j++) {
				if(i != j){
					if(array[i] == array[j]) {
						if(array[j] == s2remove[j]) {
							continue;
						}
						else {
							cons4 = cons4 + 55;
							s2remove[j] = array[j];
						}
					}
				}
			}
		}
	}
	public void getConstraint5(int [] array,  int value) {
		for(int i=0; i<array.length; i++) {
			if(array[i] == value) {
				cons5 = cons5 + 60;
			}
		}
	}
	
	public void hitungCrossover() {
		String temp;
		getChildCO = -1;
		ofCrossover = (int) Math.round(cr * popsize);
		System.out.println("Banyak Offspring Crossover = " +ofCrossover);
		childCrossover = new int[ofCrossover][maxData];
		
		while(ofCrossover - getChildCO != 1){
			Random random = new Random();
			int c[] = new int[2];
			c[0] = random.nextInt(popsize);
			c[1] = random.nextInt(popsize);
			
			int oneCut = random.nextInt(maxKapal);
			System.out.println("\n" + c[0] + " | " + c[1] + " | " + oneCut);
			
			int c1 = ++getChildCO;
			System.out.println(c1 + " " + getChildCO);
			
			if(ofCrossover - getChildCO == 1) {
				for(int i=0; i<maxData; i++) {
					childCrossover[c1][i] = data[c[0]][i];
				}
				for(int i=oneCut, j=0; j<maxData-oneCut; j++,i++) {
					childCrossover[c1][i] = data[c[1]][i];
				}
				System.out.println("Child " + c1 + " = ");
				int temp2[] = new int[maxData];
				for(int i=0; i<maxData; i++) {
					System.out.println(childCrossover[c1][i] + " ");
					temp2[i]=childCrossover[c1][i];
				}
				temp = Arrays.toString(temp2);
				temp = temp.substring(0,32) + "...";
				tbmodel_child.addRow(new Object[] {c1+1,c[0] + "x" + c[1], temp});
			}
			else {
				int c2 = ++getChildCO;
				System.out.println(c2 + " " + getChildCO);
				for(int i=0; i<maxData; i++) {
					childCrossover[c1][i] = data[c[0]][i];
					childCrossover[c2][i] = data[c[1]][i];
				}
				for(int i=oneCut, j=0; j<maxData-oneCut; j++,i++) {
					childCrossover[c2][i] = data[c[0]][i];
					childCrossover[c1][i] = data[c[1]][i];
				}
				for(int i = c1; i<=c2; i++) {
					System.out.println("\nChild " + i + " = ");
					int temp2[] = new int[maxData];
					for(int j=0; j<maxData; j++) {
						System.out.println(childCrossover[i][j] + " ");
						temp2[j]=childCrossover[i][j];
					}
					temp = Arrays.toString(temp2);
					temp = temp.substring(0,32) + "...";
					tbmodel_child.addRow(new Object[] {i+1,c[0] + "x" + c[1], temp});
				}
			}
		}
	}
	
	public void hitungMutasi() {
		String temp;
		ofMutasi = (int) Math.round(mr * popsize);
		System.out.println("\nBanyak Offspring Mutasi = " +ofMutasi);
		
		childMutasi = new int[ofMutasi][maxData];
		Random random = new Random();
		for (int j=0; j<ofMutasi; j++) {
			int p = random.nextInt(popsize);
			int r1 = random.nextInt(maxData);
			int r2 = random.nextInt(maxData);
			System.out.println("\n" + p + " | " + r1 + " | " + r2);
			
			reciprocalExchangeMutation(p, r1, r2, j);
			System.out.println("Child " + j + " = ");
			
			int arr[] = new int[maxData];
			for(int i=0; i<maxData; i++) {
				System.out.println(childMutasi[j][i] + " ");
				arr[i] = childMutasi[j][i];
			}
			temp = Arrays.toString(arr);
			temp = temp.substring(0,32) + "...";
			tbmodel_child.addRow(new Object[] {ofCrossover+j+1, p, temp});
		}
	}
	public void reciprocalExchangeMutation(int p, int r1, int r2, int j) {
		for(int i=0; i<maxData; i++) {
			childMutasi[j][i] = data[p][i];
			if(i==r1) {
				childMutasi[j][i] = data[p][r2];
			}
			if(i==r2) {
				childMutasi[j][i] = data[p][r1];
			}
		}
	}
	public void getFitness(int[][] array, int size, String nama) {
		try{
			for(int j=0; j<size; j++) {
				System.out.println("\n" + nama + (j+1) + " ");
				int temp[] = new int[maxData];
				int a = 0;
				cons1 = 0.0;
				cons2 = 0.0;
				cons3 = 0.0;
				cons4 = 0.0;
				cons5 = 0.0;
				
				for(int k=0; k<maxData; k++) {
					temp[k] = array[j][k];
					if(k==11) {
						a++;
						System.out.println("Hari ke-" + a + " : ");
						fullJadwal = Arrays.copyOfRange(temp, 0, 12);
						
						jadwal1 = Arrays.copyOfRange(fullJadwal, 0, fullJadwal.length/2);
						printArray("Jadwal 1", jadwal1);
						jadwal2 = Arrays.copyOfRange(fullJadwal, fullJadwal.length/2, fullJadwal.length);
						printArray("Jadwal 2", jadwal2);
						
						getConstraint4(jadwal1);
						getConstraint4(jadwal2);
						getConstraint3(jadwal1, jadwal2);
						if(cHalangan != 0) {
							for(int i=0; i<cHalangan; i++) {
								if(halangan[i][1] == a) {
									getConstraint5(fullJadwal, halangan[i][0]);
								}
							}
						}
					}
					else if((k+1)%12 == 0) {
						a++;
						System.out.println("Hari ke-" + a + " : ");
						printArray("Jadwal Kemarin", fullJadwal);
						fullJadwal = Arrays.copyOfRange(temp, k-11, k+1);
						
						getConstraint1(fullJadwal, jadwal1);
						getConstraint1(fullJadwal, jadwal2);
						jadwal1 = Arrays.copyOfRange(fullJadwal, 0, fullJadwal.length/2);
						printArray("Jadwal 1", jadwal1);
						jadwal2 = Arrays.copyOfRange(fullJadwal, fullJadwal.length/2, fullJadwal.length);
						printArray("Jadwal 2", jadwal2);
						
						getConstraint4(jadwal1);
						getConstraint4(jadwal2);
						getConstraint3(jadwal1, jadwal2);
						if(cHalangan != 0) {
							for(int i=0; i<cHalangan; i++) {
								if(halangan[i][1] == a) {
									getConstraint5(fullJadwal, halangan[i][0]);
								}
							}
						}
					}
				}
				fitness[count][0]=1./(1+cons1+cons2+cons3+cons4+cons5);
				fitness[count][1]=count;
				fitness[count][2]=cons1;
				fitness[count][3]=cons2;
				fitness[count][4]=cons3;
				fitness[count][5]=cons4;
				fitness[count][6]=cons5;
				System.out.println("\n");
				System.out.println("Cons1 : " + cons1);
				System.out.println("Cons2 : " + cons2);
				System.out.println("Cons3 : " + cons3);
				System.out.println("Cons4 : " + cons4);
				System.out.println("Cons5 : " + cons5);
				System.out.println("Nilai fitness : " + fitness[count][0]);
				count++;
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	public void hitungFitness() {
		try {
			count = 0;
			allPop = popsize+ofCrossover+ofMutasi;
			gabungan = new int[allPop][maxData];
			for(int i=0; i<allPop; i++) {
				for(int j=0; j<maxData; j++) {
					if(i < popsize) {
						gabungan[i][j] = data[i][j];
					}
					else if(i<popsize+ofCrossover) {
						gabungan[i][j] = childCrossover[i-popsize][j];
					}
					else if (i<allPop) {
						gabungan[i][j] = childMutasi[i-(popsize+ofCrossover)][j];
					}
				}
			}
			fitness = new double[allPop][7];
			getFitness(data, popsize, "Parent");
			getFitness(childCrossover, ofCrossover, "Child Crossover");
			getFitness(childMutasi, ofMutasi, "Child Mutasi");
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	// Mulai fungsi mengurutkan nilai fitness dari terbesar ke terkecil
	public void seleksiElitism() {
		newFitness = new double[allPop][2];
		System.out.println("\nGabungan Parent dan Child" + " : ");
		
		for(int i=0; i<allPop; i++) {
			for(int j=0; j<2; j++) {
				newFitness[i][j] = new Double(fitness[i][j]);
			}
			System.out.println(newFitness[i][0] + " || " +newFitness[i][1]);
			Double temp = newFitness[i][1];
			int int_allpop = temp.intValue();
			tbmodel_evaselek.addRow(new Object[] {int_allpop, newFitness[i][0]});
		}
		for(int i=0; i<allPop; i++) {
			for(int j=1; j<allPop; j++) {
				if(newFitness[j-1][0] <= newFitness[j][0]) {
					double temp = newFitness[j-1][0];
					double temp2 = newFitness[j-1][1];
					newFitness[j-1][0] = newFitness[j][0];
					newFitness[j-1][1] = newFitness[j][1];
					newFitness[j][0] = temp;
					newFitness[j][1] = temp2;
				}
			}
		}
		System.out.println("Order Fitness : ");
		for(int i=0; i<allPop; i++) {
			Double temp = newFitness[i][1];
			int int_allpop = temp.intValue();
			System.out.println(newFitness[i][0] + " | " + newFitness[i][1] + " | ");
			for(int j=0; j<maxData; j++){
				System.out.print(gabungan[int_allpop][j] + ", ");
			}
			System.out.println("");
		}
		fitnessJipi = newFitness[0][0];
		Double indter = newFitness[0][1];
		individuTerbaik = indter.intValue();
		int arr[] = new int[maxData];
		String temp2;
		for(int i=0; i<1; i++) {
			Double temp = newFitness[i][1];
			int int_allpop = temp.intValue();
			for(int j=0; j<maxData; j++){
				arr[j] = gabungan[int_allpop][j];
			}
		}
		temp2 = Arrays.toString(arr);
		temp2 = temp2.substring(0,32) +",...";
		jadwalTerbaik = temp2;
	}
	//Fungsi untuk menampilkan jadwal dari individu terbaik ke tabel jadwal
	public void tampilJadwal() {
		System.out.println("Individu terbaik : ");
		for(int i=0; i<1; i++) {
			Double temp = newFitness[i][1];
			int int_allpop = temp.intValue();
			System.out.println(newFitness[i][0] + " | " + newFitness[i][1] + " | ");
			indexTerbaik = (int) newFitness[i][1];
			for(int j=0; j<maxData; j++){
				System.out.print(gabungan[int_allpop][j] + ", ");
			}
			System.out.println("");
		}
		for(int i=0;i<10;i++) {
			tbmodel_jadwal.addRow(new  Object[] {i+1, gabungan[indexTerbaik][(i*16)+0], gabungan[indexTerbaik][(i*16)+1]
					, gabungan[indexTerbaik][(i*16)+2], gabungan[indexTerbaik][(i*16)+3], gabungan[indexTerbaik][(i*16)+4]
					, gabungan[indexTerbaik][(i*16)+5], gabungan[indexTerbaik][(i*16)+6], gabungan[indexTerbaik][(i*16)+7]
					, gabungan[indexTerbaik][(i*16)+8], gabungan[indexTerbaik][(i*16)+9], gabungan[indexTerbaik][(i*16)+10]
					, gabungan[indexTerbaik][(i*16)+11], gabungan[indexTerbaik][(i*16)+12], gabungan[indexTerbaik][(i*16)+13]
					, gabungan[indexTerbaik][(i*16)+14], gabungan[indexTerbaik][(i*16)+15]});
		}
	}
	// Fungsi untuk print array
	public static void printArray(String jadwal, int[] jadwal12) {
		System.out.println(jadwal);
		System.out.print(Arrays.toString(jadwal12));
	}
	// Fungsi untuk refresh table
	public void clearTable() {
		tbmodel_child.getDataVector().removeAllElements();
		tbmodel_child.fireTableDataChanged();
		tbmodel_evaselek.getDataVector().removeAllElements();
		tbmodel_evaselek.fireTableDataChanged();
		tbmodel_indterb.getDataVector().removeAllElements();
		tbmodel_indterb.fireTableDataChanged();
		tbmodel_jadwal.getDataVector().removeAllElements();
		tbmodel_jadwal.fireTableDataChanged();
		tbmodel_popawal.getDataVector().removeAllElements();
		tbmodel_popawal.fireTableDataChanged();
	}
}

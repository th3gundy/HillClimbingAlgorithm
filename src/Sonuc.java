import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class Sonuc extends JFrame {

	private final JPanel contentPane; // componentleri yerlestirecegimiz Panel
	public final JButton [][] kare; // Satranc tahtasindaki elemanlar
	public int matris[][] = new int[9][9]; // tahtayi temsil eden matris
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Sonuc frame = new Sonuc();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Sonuc() {
		setTitle("Yapay Zeka Odev 2 Sonuc Ekrani");
		setResizable(false);
		setBounds(70, 50, 795, 615);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Satranc tahtasini olusturuyoruz.
		kare = new JButton[9][9];
		int satir = 18;
		int sutun = 18;
		for(int i=0;i<9;i++) {
			sutun = 18;
			for(int j=0;j<9;j++) {
				kare[i][j] = new JButton("");
				if((i+j)%2 == 0) 
					kare[i][j].setBackground(Color.black); // Bir siyah bir beyaz gorunumunde olmasi icin renklendirme yapiliyor
                else 
                	kare[i][j].setBackground(Color.white); // Bir siyah bir beyaz gorunumunde olmasi icin renklendirme yapiliyor			
            	
				kare[i][j].setEnabled(false);
				kare[i][j].setBounds(sutun, satir, 60, 60); // Butonlar belli bir duzende yerlestiriliyor.
				kare[i][j].setFont(new Font("Dialog", Font.PLAIN, 12));
                contentPane.add(kare[i][j]); // olusturulan butonlar panele ekleniyor.
                sutun = sutun+60;
			}
			satir = satir+60;
		}
		
		JLabel lblHarcananSure = new JLabel("Harcanan Sure :");
		lblHarcananSure.setBounds(80, 565, 122, 15);
		contentPane.add(lblHarcananSure);
		
		final JLabel txtSure = new JLabel(""); // Harcanan sureyi yazdiracagimiz label
		txtSure.setBounds(185, 565, 70, 15);
		contentPane.add(txtSure);
		
		JLabel lblEnFazlaEleman = new JLabel("En Fazla Eleman Sayisi :");
		lblEnFazlaEleman.setBounds(300, 565, 174, 15);
		contentPane.add(lblEnFazlaEleman);
		
		final JLabel txtEnfEl = new JLabel(""); // Algoritma islenirken kuyrukta kullanilan toplam elemanlari gostermek icin
		txtEnfEl.setBounds(445, 565, 70, 15); 
		contentPane.add(txtEnfEl);
		
		JTextArea txtSonuc = new JTextArea();
		txtSonuc.setBounds(565, 18, 213, 538);
		txtSonuc.setEditable(false);
		contentPane.add(txtSonuc);
		
		TepeTirmanma tt = new TepeTirmanma();
		matris = tt.bul();
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++)
				if(matris[i][j]==1) { // sonuc tahtasina yerlestirilecek olan atlar
					kare[i][j].setBackground(Color.cyan);
					kare[i][j].setText("A");
				}
				else if(matris[i][j]==2) { // sonuc tahtasina yerlestirilecek olan vezirler
					kare[i][j].setBackground(Color.red);
					kare[i][j].setText("V");
				}
		
		txtSonuc.setText(TepeTirmanma.sonuc); // iterasyonlar sonucunda elde edilen bilgiler
		txtSure.setText(""+tt.getSure() + "  ns"); // lokal aramanin islemesi boyunca gecen sure
		txtEnfEl.setText(String.valueOf(9*9+(AnaEkran.at+AnaEkran.vezir)));
	}
}

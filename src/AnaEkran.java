import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AnaEkran extends JFrame {

	private final JPanel contentPane; // componentleri yerlestirecegimiz Panel
	public final JButton [][] kare; // Satranc tahtasindaki elemanlar
	public static int at; // kullanicidan alinan at sayisi
	public static int vezir; // kullanicidan alinan vezir sayisi
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					AnaEkran frame = new AnaEkran();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AnaEkran() {
		setTitle("Yapay Zeka Odev 2");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 612);
		setLocationRelativeTo(null); // Frame acildiginda ekrani ortalamasi icin
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Satranc tahtasini olusturuyoruz.
		kare = new JButton[9][9];
		int satir = 25;
		int sutun = 25;
		for(int i=0;i<9;i++) {
			sutun = 25;
			for(int j=0;j<9;j++) {
				kare[i][j] = new JButton("");
				if((i+j)%2 == 0) 
					kare[i][j].setBackground(Color.black); // Bir siyah bir beyaz gorunumunde olmasi icin renklendirme yapiliyor
                else 
                	kare[i][j].setBackground(Color.white); // Bir siyah bir beyaz gorunumunde olmasi icin renklendirme yapiliyor			
            	
				kare[i][j].setEnabled(false);
				kare[i][j].setBounds(sutun, satir, 60, 60); // Butonlar belli bir düzende yerlestiriliyor.
				kare[i][j].setFont(new Font("Dialog", Font.PLAIN, 12));
                contentPane.add(kare[i][j]); // olusturulan butonlar panele ekleniyor.
                sutun = sutun+60;
			}
			satir = satir+60;
		}
	
		JLabel lblAtSayisi = new JLabel("At Sayisi :");
		lblAtSayisi.setBounds(580, 180, 122, 18);
		contentPane.add(lblAtSayisi);
		
		final JTextField txtAtSayisi = new JTextField(); // Harcanan sureyi yazdiracagimiz label
		txtAtSayisi.setBounds(580, 205, 70, 20);
		contentPane.add(txtAtSayisi);
		
		JLabel lblVezirSayisi = new JLabel("Vezir Sayisi :");
		lblVezirSayisi.setBounds(580, 260, 122, 18);
		contentPane.add(lblVezirSayisi);
		
		final JTextField txtVezirSayisi = new JTextField(); // Harcanan sureyi yazdiracagimiz label
		txtVezirSayisi.setBounds(580, 285, 70, 20);
		contentPane.add(txtVezirSayisi);
				
		JButton btnBasla = new JButton("Bul"); // Algoritmayi baslatacak buton.
		btnBasla.setFont(new Font("Dialog", Font.BOLD, 12));
		btnBasla.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (txtAtSayisi.getText().isEmpty() || txtVezirSayisi.getText().isEmpty())
					JOptionPane.showMessageDialog(null, "Lütfen At ve Vezir Sayýlarýný Giriniz.!", "Hata", JOptionPane.INFORMATION_MESSAGE);
				else{
					Random random = new Random(); // random baslangic noktalarini bulacak sinif
					int row,column; // random satir ve sutun sayilari icin kullanilan lokal degiskenler
						for(int i=0;i<9;i++)
							for(int j=0;j<9;j++){
								if((i+j)%2 == 0) 
									kare[i][j].setBackground(Color.black); // program kapailmadan tekrar arama yapilmasi durumunda tahtayi duzelten satir
					            else 
					              	kare[i][j].setBackground(Color.white); // program kapatilmadan tekrar arama yapilmasi durumunda tahtayi duzelten satir
			
			                	kare[i][j].setText("");
					         }
					at = Integer.parseInt(txtAtSayisi.getText()); // kullanicidan alinan at sayisi
					vezir = Integer.parseInt(txtVezirSayisi.getText()); // kullanicidan alinan vezir sayisi
					Sonuc snc = new Sonuc(); // sonuc tahtasi
					snc.setVisible(true);
					for(int i=0;i<9;i++)
						for(int j=0;j<9;j++)
							if(TepeTirmanma.tahtaIlk[i][j]==1) { // random olarak bulunan lokasyonyonlara atlarin yerlestirilmesi
								kare[i][j].setBackground(Color.cyan);
								kare[i][j].setText("A");
							}
							else if(TepeTirmanma.tahtaIlk[i][j]==2) { // random olarak bulunan lokasyonyonlara vezirlerin yerlestirilmesi
								kare[i][j].setBackground(Color.red);
								kare[i][j].setText("V");
							}
				}
			}
		});
		btnBasla.setBounds(580, 350, 86, 45);
		contentPane.add(btnBasla);
	}

}
